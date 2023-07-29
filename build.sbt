scalaVersion := "3.2.2"

ThisBuild / name := "eth-tailz"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.13",
  "dev.zio" %% "zio-streams" % "2.0.13",
  "dev.zio" %% "zio-config" % "3.0.7",
  "dev.zio" %% "zio-config-typesafe" % "3.0.7",
  "dev.zio" %% "zio-config-magnolia" % "3.0.7",
  "org.web3j" % "core" % "4.9.5"
)

addCommandAlias("tc", "Test/compile")
addCommandAlias("ctc", "clean; Test/compile")
addCommandAlias("rctc", "reload; clean; Test/compile")

addCommandAlias("generateCliNativeConfig", "nativeImageRunAgent")
addCommandAlias("compileCliNativeBinary", "nativeImage")
addCommandAlias("generateCliNativeConfigAndBinary", "generateCliNativeConfig;compileCliNativeBinary")

lazy val root =
  Project(id = "eth-tailz", base = file("."))
    .enablePlugins(NativeImagePlugin)
    .settings(
      Compile / mainClass := Some("io.mankea.eth.streamer.App"),

      // sbt-native-image configs
      nativeImageVersion := "17.0.7",
      nativeImageJvm := "graalvm",
      nativeImageOptions := {
        Seq(
          "--no-fallback",
          "--install-exit-handlers",
          "--diagnostics-mode",
          "-Djdk.http.auth.tunneling.disabledSchemes=",
        )
      },
      nativeImageAgentMerge := true,
      nativeImageOptions += s"-H:ConfigurationFileDirectories=${(Compile / resourceDirectory).value}/META-INF/native-image",
      nativeImageAgentOutputDir := (Compile / resourceDirectory).value / "META-INF/native-image",
      nativeImageOutput := (ThisBuild / baseDirectory).value / "target/eth-tailz", // The generated executable binary

      Global / excludeLintKeys ++= Set(nativeImageVersion, nativeImageJvm), // Wrongly reported as unused keys by sbt
    )
