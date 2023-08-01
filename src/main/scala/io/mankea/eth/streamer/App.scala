package io.mankea.eth.streamer

import io.mankea.eth.streamer.config.AppConfig
import io.mankea.eth.streamer.service.*
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.datatypes.Address
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.core.methods.response.{EthBlock, EthLog}
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName, DefaultBlockParameterNumber}
import zio.*
import zio.Console.printLine
import zio.cli.HelpDoc.Span.text
import zio.cli.*
import zio.stream.*

import java.math.BigInteger
import scala.jdk.CollectionConverters.*
import java.nio.file.Path as JPath

object App extends ZIOCliDefault {

  // private val contractAddress = "0x7E5462DA297440D2a27fE27d1F291Cf67202302B"
  // private val from = 3276471 // block when it's deployed
  private val pollingDelay = 12.seconds
  private val chunkSize = 10000

  private val pipeToString = ZPipeline.map[EthLogEvent, String](l => s"#${l.blockNumber} ${l.transactionHash} | ${l.logIndex} | ${l.event}")

  private def onlySupported: EthLogEvent => Boolean = {
    case EthLogEvent(_, _, _, Unsupported(_)) => false
    case _ => true
  }

  private val args = Args.text("contractAddress") ++ Args.integer

  private val opt = Options.boolean("forever", true).alias("f")
    ?? "This option causes tail to not stop when end of blockchain is reached, but rather to wait for additional blocks to be appended"

  private val mainCmd = Command("eth-tailz", opt, args)
    .withHelp(HelpDoc.p("Stream ethereum log events"))

  val cliApp = CliApp.make(
    name = "eth-tailz",
    version = "0.0.1",
    summary = text("Ethereum Event Log Tail"),
    footer = HelpDoc.p("Let's stream some events!"),
    command = mainCmd
  ) {
    case (forever:Boolean, (contractAddress: String, blockNumber: BigInt)) =>
      logStream(contractAddress, blockNumber, forever)
        .filter(onlySupported)
        .via(pipeToString)
        .foreach(Console.printLine(_))
        .provide(
          Web3Service.live,
          AppConfig.live
        )
  }

  private def logStream(contractAddress: String, initialFrom: BigInt, forever: Boolean): ZStream[Web3Service, Throwable, EthLogEvent] = {
    ZStream.fromZIO(ZIO.service[Web3Service]).flatMap { web3 =>
      ZStream.unfoldChunkZIO(initialFrom) { from =>
        for {
          currentBlock <- web3.getCurrentBlockNumber
          to <- ZIO.succeed(currentBlock.min(from + chunkSize))
          logs <- web3.getLogs(contractAddress, from, to)
          _ <- if (to == currentBlock && forever) {
            println(s" >> reached current block, sleeping for ${pollingDelay.getSeconds} seconds")
            ZIO.sleep(pollingDelay)
          } else ZIO.unit
        } yield
          if (to == currentBlock && !forever) None else  // uncomment to finish at current block
          Some((Chunk.fromIterable(logs), to + 1))
      }
    }
  }

}
