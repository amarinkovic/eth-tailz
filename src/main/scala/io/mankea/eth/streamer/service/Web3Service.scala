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
import io.mankea.eth.streamer.service._

import java.io.IOException
import java.math.BigInteger
import scala.jdk.CollectionConverters._
import java.net.SocketTimeoutException

case class EthLogEvent(blockNumber: BigInt, transactionHash: String, logIndex: Long, event: TypedEvent)

sealed trait Web3Service {
  def getCurrentBlockNumber: Task[BigInt]
  def getLogs(contractAddress: String, from: BigInt, to: BigInt): Task[List[EthLogEvent]]
}

case class Web3ServiceImpl(web3j: Web3j) extends Web3Service {

  private val eventResolver = EventResolver

  override def getCurrentBlockNumber: Task[BigInt] =
    ZIO.attempt(web3j.ethBlockNumber.send.getBlockNumber).map(BigInt.javaBigInteger2bigInt)

  override def getLogs(contractAddress: String, from: BigInt, to: BigInt): ZIO[Any, SocketTimeoutException | IOException, List[EthLogEvent]] =
    val filter = new EthFilter(
      DefaultBlockParameter.valueOf(from.bigInteger),
      DefaultBlockParameter.valueOf(to.bigInteger),
      contractAddress
    )

    ZIO.attemptBlocking {
      val logs = web3j.ethGetLogs(filter).send().getLogs.asScala.toList
      println(s"#$from -> #$to | ${logs.size} events")

      logs
        .map(_.asInstanceOf[EthLog.LogObject])
        .map { logObject =>
          EthLogEvent(
            BigInt.javaBigInteger2bigInt(logObject.getBlockNumber),
            logObject.getTransactionHash,
            logObject.getLogIndex.longValueExact,
            eventResolver.getTypedEvent(logObject)
          )
        }
    }.retryN(4).orDie
}

object Web3Service {

  def getCurrentBlockNumber: ZIO[Web3Service, Throwable, BigInt] =
    ZIO.serviceWithZIO[Web3Service](_.getCurrentBlockNumber)

  def getLogs(contractAddress: String, from: BigInteger, to: BigInteger): ZIO[Web3Service, Throwable, List[EthLogEvent]] =
    ZIO.serviceWithZIO[Web3Service](_.getLogs(contractAddress, from, to))

  val live: ZLayer[Any, zio.Config.Error, Web3Service] =
    ZLayer {
      for {
        nodeUrl <- ZIO.config(Config.string("ETH_SEPOLIA_RPC_URL"))
        web3j <- ZIO.succeed(Web3j.build(new HttpService(nodeUrl)))
      } yield Web3ServiceImpl(web3j)
    }
}
