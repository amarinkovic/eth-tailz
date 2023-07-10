package io.mankea.eth.streamer.service

import io.mankea.eth.streamer.config.AppConfig
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName}
import org.web3j.protocol.{Web3j, Web3jService}
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.{EthBlock, EthLog}
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.http.HttpService
import zio.*
import zio.stream.ZStream
import io.mankea.eth.streamer.service._

import java.math.BigInteger
import scala.jdk.CollectionConverters.*

case class LogEvent(blockNumber: BigInt, transactionHash: String, logIndex: Long, event: String)

trait Web3Service {
  def getCurrentBlockNumber: Task[BigInteger]
  def getLogs(contractAddress: String, from: BigInteger, to: BigInteger): Task[List[LogEvent]]
  def streamLogs(contractAddress: String, from: Long): ZStream[Any, Throwable, LogEvent]
  def streamBlocks: ZStream[Any, Throwable, EthBlock]
}

case class Web3ServiceImpl(web3j: Web3j) extends Web3Service{

  override def getCurrentBlockNumber: Task[BigInteger] =
    ZIO.attempt(web3j.ethBlockNumber.send.getBlockNumber)

  override def getLogs(contractAddress: String, from: BigInteger, to: BigInteger): Task[List[LogEvent]] =
    println(s"getting logs: ${from.longValueExact()} -> ${to.longValueExact()}")
    ZIO.attempt {
      val filter = new org.web3j.protocol.core.methods.request.EthFilter(
        DefaultBlockParameter.valueOf(from),
        DefaultBlockParameter.valueOf(to),
        contractAddress
      )

      val logs = web3j.ethGetLogs(filter).send().getLogs.asScala.toList
      println(s"Got ${logs.size} events")

      logs.map { log =>
        val logResult = log.asInstanceOf[LogObject]

//        val event = get(logResult.getTopics.get(0))
//        val eventValues = FunctionReturnDecoder.decode(logResult.getData, event.getIndexedParameters)

        // eventValues.get(0).asInstanceOf[Address].getValue()

        println(s"log: $logResult")
        LogEvent(
            BigInt.javaBigInteger2bigInt(logResult.getBlockNumber),
          logResult.getTransactionHash,
          logResult.getLogIndex.longValueExact,
          logResult.getTopics.get(0) match {
            case "0x3ed12c13d0bb497300280ace747c30f49edef4b4d814ac5c2a6395f256fa6c97" => "InitializeDiamond"
            case "0x736c56e4bb16c438047d822d53251d5034edf27808ac582857e8863b898c9529" => "RoleCanAssignUpdated"
            case "0x8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0" => "OwnershipTransferred"
            case _  => logResult.getTopics.get(0)
          }
        )
      }
    }

  override def streamLogs(contractAddress: String, from: Long): ZStream[Any, Throwable, LogEvent] =
    ZStream.unwrap {
      for {
        currentBlock <- getCurrentBlockNumber
        to <- ZIO.succeed(currentBlock.min(BigInteger.valueOf(from + 1000)))
        logs <- getLogs(contractAddress, BigInteger.valueOf(from), to)
      } yield ZStream.fromIterable(logs)
    }
  //.repeat(Schedule.spaced(interval))

  override def streamBlocks: ZStream[Any, Throwable, EthBlock] =
    val from = DefaultBlockParameter.valueOf(BigInteger.valueOf(8737849))
    ZStream.fromIterable[EthBlock](web3j.replayPastAndFutureBlocksFlowable(from, true).blockingIterable().asScala)

}

object Web3Service {

  def getBlockNumber: ZIO[Web3Service, Throwable, BigInteger] =
    ZIO.serviceWithZIO[Web3Service](_.getCurrentBlockNumber)

  def getLogs(contractAddress: String, from: BigInteger, to: BigInteger): ZIO[Web3Service, Throwable, List[LogEvent]] =
    ZIO.serviceWithZIO[Web3Service](_.getLogs(contractAddress, from, to))

  val live: ZLayer[AppConfig, Nothing, Web3Service] =
    ZLayer {
      for {
        cfg <- ZIO.service[AppConfig]
        web3j <- ZIO.succeed(Web3j.build(new HttpService(cfg.nodeUrl)))
      } yield Web3ServiceImpl(web3j)
    }
}