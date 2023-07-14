package io.mankea.eth.streamer.service

import io.mankea.eth.streamer.config.AppConfig
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName}
import org.web3j.protocol.{Web3j, Web3jService}
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.{EthBlock, EthLog}
import org.web3j.protocol.core.methods.response.EthLog
import org.web3j.protocol.http.HttpService
import zio._
import zio.stream.ZStream
import io.mankea.eth.streamer.service._

import java.math.BigInteger
import scala.jdk.CollectionConverters._

case class EthLogEvent(blockNumber: BigInt, transactionHash: String, logIndex: Long, event: TypedEvent)

trait Web3Service {
  def getCurrentBlockNumber: Task[BigInt]
  def getLogs(contractAddress: String, from: BigInt, to: BigInt): Task[List[EthLogEvent]]
  def streamBlocks: ZStream[Any, Throwable, EthBlock]
}

case class Web3ServiceImpl(web3j: Web3j) extends Web3Service {

  private val eventResolver = EventResolver

  override def getCurrentBlockNumber: Task[BigInt] =
    ZIO.attempt(web3j.ethBlockNumber.send.getBlockNumber).map(BigInt.javaBigInteger2bigInt)

  override def getLogs(contractAddress: String, from: BigInt, to: BigInt): Task[List[EthLogEvent]] =
    println(s"getting logs: ${from} -> ${to}")
    ZIO.attempt {
      val filter = new org.web3j.protocol.core.methods.request.EthFilter(
        DefaultBlockParameter.valueOf(from.bigInteger),
        DefaultBlockParameter.valueOf(to.bigInteger),
        contractAddress
      )

      val logs = web3j.ethGetLogs(filter).send().getLogs.asScala.toList
      println(s"Got ${logs.size} events")

      logs.map { log =>
        val logObject = log.asInstanceOf[EthLog.LogObject]
        EthLogEvent(
          BigInt.javaBigInteger2bigInt(logObject.getBlockNumber),
          logObject.getTransactionHash,
          logObject.getLogIndex.longValueExact,
          eventResolver.getTypedEvent(logObject)
        )
      }
    }

  override def streamBlocks: ZStream[Any, Throwable, EthBlock] =
    val from = DefaultBlockParameter.valueOf(BigInteger.valueOf(8737849))
    ZStream.fromIterable[EthBlock](web3j.replayPastAndFutureBlocksFlowable(from, true).blockingIterable().asScala)

}

object Web3Service {

  def getBlockNumber: ZIO[Web3Service, Throwable, BigInt] =
    ZIO.serviceWithZIO[Web3Service](_.getCurrentBlockNumber)

  def getLogs(contractAddress: String, from: BigInteger, to: BigInteger): ZIO[Web3Service, Throwable, List[EthLogEvent]] =
    ZIO.serviceWithZIO[Web3Service](_.getLogs(contractAddress, from, to))

  val live: ZLayer[AppConfig, Nothing, Web3Service] =
    ZLayer {
      for {
        cfg <- ZIO.service[AppConfig]
        web3j <- ZIO.succeed(Web3j.build(new HttpService(cfg.nodeUrl)))
      } yield Web3ServiceImpl(web3j)
    }
}