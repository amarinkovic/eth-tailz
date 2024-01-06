# eth-tailz

Purely functional stream processing of Ethereum transaction Event Logs for [nayms/contracts-v3](https://github.com/nayms/contracts-v3), implemented with [ZIO](https://zio.dev/reference/stream/zstream/) and [web3j](https://github.com/web3j/web3j).

### Build native image

Application can be built as a native binary executable, that does not require the JVM runtime. Run the following to produce the native binary:

```zsh
sbt nativeImage
```

Native image descriptors are produced by running the app with an agent:

```zsh
sbt nativeImageRunAgent
```

Executable binary `eth-tailz` will be available inside the `target` folder.

Run it by giving it a contract address and a block number from which to start streaming. For example, you can run:

```zsh
./target/eth-tailz --forever --polling-interval 12 --chunk-size 10000 0x39e2f550fef9ee15b459d16bD4B243b04b1f60e5 17088059
```

### Workaround for the sbt-zio-cli plugin

Currently there is a problem with fetching the `sbt-zio-cli` jar from the internet. It is required to build it locally. Clone the [zio-cli](https://github.com/zio/zio-cli) project locally and build the plugin and publish it to you local environment. To do that, first of all make sure the following is set for the `sbt-zio-cli` module of this project:

```groovy
publish / skip     := false,
```

Then publish the jar file by starting the `sbt` and running the followint tasks:

```sbt
project sbtZioCli
publishLocal
```
 Jar file will be published locally somehere under:
 ```zsh
 ~/.ivy2/local/zio.cli.sbt/sbt-zio-cli/scala_2.12/sbt_1.0/
 ```

Find the exact version produced and update it in this projects `plugins.sbt`

### Workaround GraalVM issues

Current version of this code works with Graal VM Java 21. 

Sbt native image plugin uses [Coursier](https://get-coursier.io/) to detect java. Make sure to have Graal VM installed via Coursier.

Make sure to set `GRAAL_HOME` environment variable pointing to a GraalVM installation folder. 

You might also get the following error:

```java
[error] Failed to automatically install native-image. To fix this problem, install native-image manually and start sbt with the environment variable 'NATIVE_IMAGE_INSTALLED=true'
```

In that case make sure to have `NATIVE_IMAGE_INSTALLED` environment variable set to `true`.