scalaVersion := "3.3.7"

ThisBuild / name := "eth-tailz"

githubTokenSource := TokenSource.Environment("GITHUB_TOKEN") || TokenSource.GitConfig("github.token")

resolvers += Resolver.githubPackages("amarinkovic", "contracts-v3-java")

libraryDependencies ++= Seq(
  "io.nayms" % "contracts" % "3.9.3",
  "dev.zio" %% "zio" % "2.1.26",
  "dev.zio" %% "zio-streams" % "2.1.26",
  "dev.zio" %% "zio-config" % "3.0.7",
  "dev.zio" %% "zio-cli" % "0.8.1",
  "org.web3j" % "core" % "4.14.0",
  "ch.qos.logback" % "logback-classic" % "1.5.32"
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
