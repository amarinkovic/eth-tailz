package io.mankea.eth.streamer;

import io.mankea.eth.streamer.config.AppConfig
import io.mankea.eth.streamer.service.{EthLogEvent, EventResolver, EventResolverImpl, TypedEvent, Unsupported, Web3Service, Web3ServiceImpl}
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.datatypes.Address
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.EthLog.LogObject
import org.web3j.protocol.core.methods.response.{EthBlock, EthLog}
import org.web3j.protocol.core.{DefaultBlockParameter, DefaultBlockParameterName, DefaultBlockParameterNumber}
import zio.*
import zio.stream.*

import java.math.BigInteger
import scala.jdk.CollectionConverters.*

object App extends ZIOAppDefault {

  private val contractAddress = "0x7E5462DA297440D2a27fE27d1F291Cf67202302B"
  private val pollingDelay = 12.seconds
  private val chunkSize = 10000
  private val from = 3276471 // block when it's deployed

  private def logStream(contractAddress: String, initialFrom: BigInt): ZStream[Web3Service with EventResolver, Throwable, EthLogEvent] = {
    ZStream.fromZIO(ZIO.service[Web3Service]).flatMap { web3 =>
      ZStream.unfoldChunkZIO(initialFrom) { from =>
        for {
          currentBlock <- web3.getCurrentBlockNumber
          to <- ZIO.succeed(currentBlock.min(from + chunkSize))
          logs <- web3.getLogs(contractAddress, from, to)
          _ <- if (to == currentBlock) {
            println(s" >> reached current block, sleeping for ${pollingDelay.getSeconds} seconds")
            ZIO.sleep(pollingDelay)
          } else ZIO.unit
        } yield
          // if (to == currentBlock) None else  // uncomment to finish at current block
          Some((Chunk.fromIterable(logs), to + 1))
      }
    }
  }

  private val stream = logStream(contractAddress, from)
  private val pipeToString = ZPipeline.map[EthLogEvent, String](l => s"#${l.blockNumber} ${l.transactionHash} | ${l.logIndex} | ${l.event}")
  private def onlySupported: EthLogEvent => Boolean = {
    case EthLogEvent(_, _, _, Unsupported(_)) => false
    case _ => true
  }

  def run = stream
    .filter(onlySupported)
    .via(pipeToString)
    .foreach(Console.printLine(_))
    .provide(
      Web3ServiceImpl.live,
      EventResolverImpl.live,
      AppConfig.live
    )

}
