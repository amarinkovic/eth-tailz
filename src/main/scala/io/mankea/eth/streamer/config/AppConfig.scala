package io.mankea.eth.streamer.config

import zio._

import zio.config._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource

case class AppConfig(host: String, port: Int, contractAddress: String, nodeUrl: String)

object AppConfig {
  val live: ZLayer[Any, ReadError[String], AppConfig] =
    ZLayer {
      read {
        descriptor[AppConfig].from(
          TypesafeConfigSource.fromResourcePath.at(PropertyTreePath.$("AppConfig"))
        )
      }
    }
}