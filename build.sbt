scalaVersion := "3.2.0"

name := "eth_log_streamer"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.0",
  "dev.zio" %% "zio-streams" % "2.0.0",
  "dev.zio" %% "zio-json" % "0.3.0",
  "io.d11" %% "zhttp" % "2.0.0-RC11",
  "dev.zio" %% "zio-interop-cats" % "3.1.1.0"
)

libraryDependencies += "dev.zio" %% "zio-config"          % "3.0.1"
libraryDependencies += "dev.zio" %% "zio-config-typesafe" % "3.0.1"
libraryDependencies += "dev.zio" %% "zio-config-magnolia" % "3.0.1"
libraryDependencies += "dev.zio" %% "zio-json" % "0.3.0-RC10"
libraryDependencies += "org.web3j" % "core" % "4.9.5"
libraryDependencies += "org.reactivestreams" % "reactive-streams" % "1.0.4"
