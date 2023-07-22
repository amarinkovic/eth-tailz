scalaVersion := "3.2.2"

name := "eth-tailz"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.13",
  "dev.zio" %% "zio-streams" % "2.0.13",
  "dev.zio" %% "zio-json" % "0.5.0",
  "dev.zio" %% "zio-http" % "3.0.0-RC2",
  "dev.zio" %% "zio-interop-cats" % "3.1.1.0"
)

libraryDependencies += "dev.zio" %% "zio-config"          % "3.0.7"
libraryDependencies += "dev.zio" %% "zio-config-typesafe" % "3.0.7"
libraryDependencies += "dev.zio" %% "zio-config-magnolia" % "3.0.7"
libraryDependencies += "org.web3j" % "core" % "4.9.5"
libraryDependencies += "org.reactivestreams" % "reactive-streams" % "1.0.4"
