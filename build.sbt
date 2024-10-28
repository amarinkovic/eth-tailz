scalaVersion := "3.3.1"

ThisBuild / name := "eth-tailz"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.13",
  "dev.zio" %% "zio-streams" % "2.0.13",
  "dev.zio" %% "zio-config" % "3.0.7",
  "dev.zio" %% "zio-cli" % "0.5.0",
  "org.web3j" % "core" % "4.9.5",
  "ch.qos.logback" % "logback-classic" % "1.4.7"
)

addCommandAlias("generateCliNativeConfig", "nativeImageRunAgent")
addCommandAlias("compileCliNativeBinary", "nativeImage")
addCommandAlias("generateCliNativeConfigAndBinary", "generateCliNativeConfig;compileCliNativeBinary")

lazy val root =
  Project(id = "eth-tailz", base = file("."))
    .enablePlugins(NativeImagePlugin)
    .settings(
      Compile / mainClass := Some("io.mankea.eth.streamer.App"),

      // sbt-native-image configs
      nativeImageOptions := {
        Seq(
          "--no-fallback",
          "--install-exit-handlers",
          "--diagnostics-mode",
          "-Djdk.http.auth.tunneling.disabledSchemes=",
        )
      },
      nativeImageInstalled := true,
      nativeImageAgentMerge := true,
      nativeImageOptions += s"-H:ConfigurationFileDirectories=${(Compile / resourceDirectory).value}/META-INF/native-image",
      nativeImageAgentOutputDir := (Compile / resourceDirectory).value / "META-INF/native-image",
      nativeImageOutput := (ThisBuild / baseDirectory).value / "target/eth-tailz", // The generated executable binary

      Global / excludeLintKeys ++= Set(nativeImageVersion, nativeImageJvm), // Wrongly reported as unused keys by sbt
    )
