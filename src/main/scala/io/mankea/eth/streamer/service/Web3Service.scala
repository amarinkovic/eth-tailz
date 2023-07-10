package io.mankea.eth.streamer.service

import io.mankea.eth.streamer.config.AppConfig
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName}
import org.web3j.protocol.{Web3j, Web3jService}
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.{EthBlock, EthLog}
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.http.HttpService
import zio._
import zio.stream.ZStream

import java.math.BigInteger
import scala.jdk.CollectionConverters.*

case class LogEvent(blockNumber: BigInt, transactionHash: String, logIndex: String, data: String)

trait Web3Service {
  def getBlockNumber: Task[BigInteger]
  def getLogs(contractAddress: String, from: Int, to: Int): Task[List[LogEvent]]
  def streamLogs(contractAddress: String, from: Int): ZStream[Any, Throwable, LogEvent]
  def streamBlocks: ZStream[Any, Throwable, EthBlock]
}

case class Web3ServiceImpl(web3j: Web3j) extends Web3Service{
  override def getBlockNumber: Task[BigInteger] =
    ZIO.attempt(web3j.ethBlockNumber().send().getBlockNumber)

  override def getLogs(contractAddress: String, from: Int, to: Int): Task[List[LogEvent]] =
    ZIO.attempt {
      val filter = new org.web3j.protocol.core.methods.request.EthFilter(
        DefaultBlockParameterName.EARLIEST,
        DefaultBlockParameterName.LATEST,
        contractAddress
      )

      val logs = web3j.ethGetLogs(filter).send().getLogs.asScala.toList
      logs.map { log =>
        val logResult = log.asInstanceOf[LogObject]

        val event = EventResolver.get(logResult.getTopics().get(0))
        val eventValues = FunctionReturnDecoder.decode(logResult.getData, event.getIndexedParameters)

        // eventValues.get(0).asInstanceOf[Address].getValue()

        println(s"log: $logResult")
        LogEvent(
          BigInt.long2bigInt(logResult.getBlockNumber.longValueExact()),
          logResult.getTransactionHash,
          logResult.getLogIndexRaw,
          logResult.getData
        )
      }
    }

  override def streamLogs(contractAddress: String, from: Int): ZStream[Any, Throwable, LogEvent] =
    ZStream.unwrap {
      for {
        logs <- getLogs(contractAddress, from, from  + 1000)
      } yield ZStream.fromIterable(logs)
    }
  //.repeat(Schedule.spaced(interval))

  override def streamBlocks: ZStream[Any, Throwable, EthBlock] =
    val from = DefaultBlockParameter.valueOf(BigInteger.valueOf(8737849))
    ZStream.fromIterable[EthBlock](web3j.replayPastAndFutureBlocksFlowable(from, true).blockingIterable().asScala)

}

object Web3Service {

  def getBlockNumber: ZIO[Web3Service, Throwable, BigInteger] =
    ZIO.serviceWithZIO[Web3Service](_.getBlockNumber)

  val live: ZLayer[AppConfig, Nothing, Web3Service] =
    ZLayer {
      for {
        cfg <- ZIO.service[AppConfig]
        web3j <- ZIO.succeed(Web3j.build(new HttpService(cfg.nodeUrl)))
      } yield Web3ServiceImpl(web3j)
    }
}