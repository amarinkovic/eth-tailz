package io.mankea.eth.streamer;

import io.mankea.eth.streamer.EventResolver
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.abi.FunctionReturnDecoder
import scala.jdk.CollectionConverters._

import zio._
import zio.stream._
import zio.Console._
import zio.Duration._
import org.web3j.protocol.core.DefaultBlockParameter
import java.math.BigInteger
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.abi.datatypes.Address

case class LogEvent(transactionHash: String, logIndex: String, data: String)

object App extends ZIOAppDefault {

  def fetchTransactionLogs(web3j: Web3j, contractAddress: String): Task[List[LogEvent]] =
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
          logResult.getTransactionHash,
          logResult.getLogIndexRaw,
          logResult.getData
        )
      }
    }

  def txLogs(web3j: Web3j, contractAddress: String, interval: Duration): ZStream[Any, Throwable, LogEvent] =
    ZStream.unwrap {
      for {
        logs <- fetchTransactionLogs(web3j, contractAddress)
      } yield ZStream.fromIterable(logs)
    }
  //.repeat(Schedule.spaced(interval))

  def ethLogStream(web3j: Web3j): ZStream[Any, Throwable, EthBlock] =
    ZStream.fromIterable[EthBlock](web3j.replayPastAndFutureBlocksFlowable(DefaultBlockParameter.valueOf(BigInteger.valueOf(8737849)), true).blockingIterable().asScala)

  def processLogEvent(logEvent: LogEvent): IO[java.io.IOException, Unit] =
    Console.printLine(s"Processing log event: $logEvent")

  val web3j = Web3j.build(new org.web3j.protocol.http.HttpService("https://eth-sepolia.g.alchemy.com/v2/Z65-j_jSB3M8NxG4TSsniOul0DVxP6Qo"))
  val contractAddress = "0x7E5462DA297440D2a27fE27d1F291Cf67202302B"
  val pollingInterval = 3.seconds

  val stream = txLogs(web3j, contractAddress, pollingInterval).map(processLogEvent)

  // val stream = ethLogStream(web3j)


  def run = stream.runDrain.exitCode
}
