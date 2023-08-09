package io.mankea.eth.streamer

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
import zio.System.SystemLive
import zio.cli.HelpDoc.Span.text
import zio.cli.*
import zio.stream.*

import java.math.BigInteger
import java.nio.file.Path as JPath
import scala.jdk.CollectionConverters.*

object App extends ZIOCliDefault {

  override val bootstrap: ZLayer[Any, Nothing, Unit] = Runtime.setConfigProvider(ConfigProvider.envProvider)

  private val defaultPollingInterval = 12.seconds
  private val defaultChunkSize = 10000

  private val pipeToString = ZPipeline.map[EthLogEvent, String](l => s"#${l.blockNumber} ${l.transactionHash} | ${l.logIndex} | ${l.event}")

  private def onlySupported: EthLogEvent => Boolean = {
    case EthLogEvent(_, _, _, Unsupported(_)) => false
    case _ => true
  }

  private def logStream(
       contractAddress: String,
       initialFrom: BigInt,
       forever: Boolean,
       pollingInterval: Duration,
       chunkSize: Int
  ): ZStream[Web3Service, Throwable, EthLogEvent] = {
    val waitMessage = s"--- reached current block, sleeping for ${pollingInterval.getSeconds} seconds"

    ZStream.fromZIO(ZIO.service[Web3Service]).flatMap { web3Service =>
      ZStream.unfoldChunkZIO(initialFrom) { from =>
        for {
          currentBlock <- web3Service.getCurrentBlockNumber
          to <- ZIO.succeed(currentBlock.min(from + chunkSize))
          _ <- if (to == currentBlock && forever) {
            Console.printLine(waitMessage) *> ZIO.sleep(pollingInterval)
          } else ZIO.unit
          logs <- web3Service.getLogs(contractAddress, from, to)
        } yield
          if (to == currentBlock && !forever) None // finish at current block
          else Some((Chunk.fromIterable(logs), to + 1))
      }
    }
  }

  private val args = Args.text("contractAddress") ++ (Args.integer ?? "Starting block number")

  private val opts =
    Options.boolean("forever", true).alias("f")
      ?? "This option causes tail to not stop when end of blockchain is reached, but rather to wait for additional blocks to be appended"
    ++ Options.integer("polling-interval").alias("i")
      .map(_.intValue)
      .map(Duration.fromSeconds(_))
      .withDefault(defaultPollingInterval)
      ?? "Interval in seconds to poll for new blocks, makes sense with forever(f) option"
    ++ Options.integer("chunk-size").alias("c")
      .map(_.intValue)
      .withDefault(defaultChunkSize)
      ?? "Number of blocks to query in one JSON RPC request"

  private val mainCmd = Command("eth-tailz", opts, args)
    .withHelp(HelpDoc.p("Stream ethereum log events"))

  val cliApp = CliApp.make(
    name = "eth-tailz",
    version = "0.0.1",
    summary = text("Ethereum Event Log Tail"),
    footer = HelpDoc.p("Let's stream some events!"),
    command = mainCmd
  ) {
    case ((forever:Boolean, pollingInterval: Duration, chunkSize: Int), (contractAddress: String, blockNumber: BigInt)) =>
      logStream(contractAddress, blockNumber, forever, pollingInterval, chunkSize)
        .filter(onlySupported)
        .via(pipeToString)
        .foreach(Console.printLine(_))
        .provide(
          Web3Service.live,
          EventResolver.live
        )
  }
}
