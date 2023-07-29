scalaVersion := "3.2.2"

name := "eth-tailz"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.13",
  "dev.zio" %% "zio-streams" % "2.0.13",
  "dev.zio" %% "zio-config" % "3.0.7",
  "dev.zio" %% "zio-config-typesafe" % "3.0.7",
  "dev.zio" %% "zio-config-magnolia" % "3.0.7",
  "org.web3j" % "core" % "4.9.5"
)

