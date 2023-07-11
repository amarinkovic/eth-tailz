package io.mankea.eth.streamer;

import io.mankea.eth.streamer.config.AppConfig
import io.mankea.eth.streamer.service.*
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.datatypes.Address
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.core.methods.response.{EthBlock, EthLog}
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName, DefaultBlockParameterNumber}
import zio.Console.*
import zio.Duration.*
import zio.stream.*
import zio.{Task, *}

import java.math.BigInteger
import scala.jdk.CollectionConverters.*

object App extends ZIOAppDefault {

  val contractAddress = "0x7E5462DA297440D2a27fE27d1F291Cf67202302B"
  val pollingInterval = 3.seconds
  val from = BigInteger.valueOf(3276471)

  def logStream(contractAddress: String, from: BigInteger): ZStream[Web3Service, Throwable, LogEvent] = {
    ZStream.unwrap {
      for {
        web3 <- ZIO.service[Web3Service]
        currentBlock <- web3.getCurrentBlockNumber
        to <- ZIO.succeed(currentBlock.min(from.add(BigInteger.valueOf(1000))))
        logs <- web3.getLogs(contractAddress, from, to)
      } yield ZStream.fromIterable(logs) // .repeat(Schedule.spaced(pollingInterval))
    }
  }

  val stream = logStream(contractAddress, from)
  private val toStringPipe = ZPipeline.map[LogEvent, String](l => s"block: ${l.blockNumber} | tx: ${l.transactionHash} | ${l.logIndex} | ${l.event}")

  def run = stream.via(toStringPipe)
    .tap(l => Console.printLine(l))
    .runDrain
    .provide(
      Web3Service.live,
      AppConfig.layer
    )

//  val program = for {
//    web3 <- ZIO.service[Web3Service]
//    bn <- web3.getCurrentBlockNumber
//    _ <- Console.printLine(bn)
//  } yield (bn)
//  def run = program
//    .provide(Web3Service.live, AppConfig.layer)

}
