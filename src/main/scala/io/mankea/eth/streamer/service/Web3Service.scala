package io.mankea.eth.streamer.service

import org.web3j.abi.FunctionReturnDecoder
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName}
import org.web3j.protocol.{Web3j, Web3jService}
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.{EthBlock, EthLog}
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.http.HttpService
import zio._
import zio.stream.ZStream
import io.mankea.eth.streamer.service.{EventResolver, TypedEvent}

import java.io.IOException
import java.math.BigInteger
import scala.jdk.CollectionConverters._
import java.net.SocketTimeoutException

given Conversion[BigInt, DefaultBlockParameter] = b => DefaultBlockParameter.valueOf(b.bigInteger)
case class EthLogEvent(blockNumber: BigInt, transactionHash: String, logIndex: Long, event: TypedEvent)

sealed trait Web3Service {
  def getCurrentBlockNumber: Task[BigInt]
  def getLogs(contractAddress: String, from: BigInt, to: BigInt): Task[List[EthLogEvent]]
}

case class Web3ServiceImpl(web3j: Web3j, eventResolver: EventResolver) extends Web3Service {

  override def getCurrentBlockNumber: Task[BigInt] =
    ZIO.attempt(web3j.ethBlockNumber.send.getBlockNumber).map(BigInt.javaBigInteger2bigInt)

  override def getLogs(contractAddress: String, from: BigInt, to: BigInt): Task[List[EthLogEvent]] =
    val filter = EthFilter(from, to, contractAddress)

    for {
      logs <- ZIO.attemptBlockingIO(web3j.ethGetLogs(filter).send().getLogs.asScala).map(Option(_).getOrElse(List()))
      _ <- Console.printLine(s"#$from -> #$to | ${logs.size} events")
      typedEventsEffects = logs.map { l =>
        for {
          logObj <- ZIO.attempt(l.asInstanceOf[EthLog.LogObject])
          typedEvent <- eventResolver.getTypedEvent(logObj)
        } yield EthLogEvent(
          BigInt(logObj.getBlockNumber),
          logObj.getTransactionHash,
          logObj.getLogIndex.longValueExact,
          typedEvent
        )
      }
      typedEvents <- ZIO.collectAll(typedEventsEffects)
    } yield typedEvents.toList

}

object Web3Service {

  def getCurrentBlockNumber: ZIO[Web3Service, Throwable, BigInt] =
    ZIO.serviceWithZIO[Web3Service](_.getCurrentBlockNumber)

  def getLogs(contractAddress: String, from: BigInteger, to: BigInteger): ZIO[Web3Service, Throwable, List[EthLogEvent]] =
    ZIO.serviceWithZIO[Web3Service](_.getLogs(contractAddress, from, to))

  val live: ZLayer[EventResolver, zio.Config.Error, Web3Service] =
    ZLayer {
      for {
        nodeUrl <- ZIO.config(Config.string("ETH_SEPOLIA_RPC_URL"))
        web3j <- ZIO.succeed(Web3j.build(new HttpService(nodeUrl)))
        eventResolver <- ZIO.service[EventResolver]
      } yield Web3ServiceImpl(web3j, eventResolver)
    }
}
