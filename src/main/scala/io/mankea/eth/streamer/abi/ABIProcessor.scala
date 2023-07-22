package io.mankea.eth.streamer.abi

import java.nio.file.{Files, Path as JPath}
import zio.*
import zio.http.*
import zio.json.*
import zio.Console.printLine
import zio.cli.HelpDoc.Span.text
import zio.cli.*

import java.io.IOException

case class HttpWrapper(
  status: String,
  message: String,
  result: String
)
case class ABIEventDefinition(
  stateMutability: Option[String],
  `type`: Option[String],
  status: Option[String],
  message: Option[String],
  anonymous: Option[Boolean],
  name: Option[String],
  inputs: Option[List[ABIEventAttribute]]
)

case class ABIEventAttribute(
  indexed: Option[Boolean],
  internalType: String,
  name: String,
  `type`: String
)

object HttpWrapper {
  implicit val decoder: JsonDecoder[HttpWrapper] = DeriveJsonDecoder.gen[HttpWrapper]
}
object ABIEventDefinition {
  implicit val decoder: JsonDecoder[ABIEventDefinition] = DeriveJsonDecoder.gen[ABIEventDefinition]
}
object ABIEventAttribute {
  implicit val decoder: JsonDecoder[ABIEventAttribute] = DeriveJsonDecoder.gen[ABIEventAttribute]
}

object ABIProcessor extends ZIOCliDefault {

  sealed trait Subcommand extends Product with Serializable
  private object Subcommand {
    final case class Http(contractAddress: String) extends Subcommand
    final case class File(filePath: JPath) extends Subcommand
  }

  private val httpCmd = Command("http", Options.none, Args.text("contractAddress"))
    .withHelp("Get ABI from given contract address on blockchain")
    .map { case contractAddress => Subcommand.Http(contractAddress) }

  private val fileCmd = Command("file", Args.file("filePath", Exists.Yes))
    .withHelp("Get ABI from local file")
    .map { case filePath => Subcommand.File(filePath) }


  val abiProcCmd = Command(name = "abi-proc", options = Options.none, args = Args.none)
    .subcommands(httpCmd, fileCmd)

  val cliApp = CliApp.make(
    name = "ABI Processor CLI",
    version = "0.0.1",
    summary = text("Process smart contract ABIs"),
    footer = HelpDoc.p("©Copyright 2023"),
    command = abiProcCmd
  ) {
    case Subcommand.Http(contractAddress) => {
      for {
//            apiKey <- System.envOrElse("ETHERSCAN_API_KEY", "29JC5TIQY6N2XTXU3GHMNVEI4T3DGNYX57")
            _ <- printLine(s" >> HTTP: ${contractAddress}")
            url <- ZIO.succeed(s"https://api.etherscan.io/api?module=contract&action=getabi&address=${contractAddress}&apikey=29JC5TIQY6N2XTXU3GHMNVEI4T3DGNYX57")
            res <- Client.request(url)
            data <- res.body.asString
            events <- ZIO.attempt(data.fromJson[HttpWrapper] match {
              case Right(HttpWrapper(_, _, eventDefString)) => eventDefString.fromJson[List[ABIEventDefinition]] match {
                case Right(events: List[ABIEventDefinition]) => events
                case Left(error) => error
              }
              case Left(error) => error
            })
            _ <- printLine(events)
      } yield ()
    }.provide(Client.default)

    case Subcommand.File(filePath) => {
      for {
        _ <- printLine(s" >> File: ${filePath}")
        events <- ZIO.attempt(new String(Files.readAllBytes(filePath)).fromJson[List[ABIEventDefinition]] match {
          case Right(events: List[ABIEventDefinition]) => events.filter(e => e.`type`.get equals "event").map(_.name)
          case Left(error) => error
        })
        _ <- printLine(events)
      } yield ()
    }
  }
}
