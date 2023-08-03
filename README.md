# eth-tailz

Purely functional stream processing of Ethereum transaction Event Logs for [nayms/contracts-v3](https://github.com/nayms/contracts-v3), implemented with [ZIO](https://zio.dev/reference/stream/zstream/).

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

Run it by giving it a contract address and a block number from which to start streaming. For example, you can run this on Sepolia:

```zsh
./eth-tailz --forever --polling-interval 12 --chunk-size 10000 0x7E5462DA297440D2a27fE27d1F291Cf67202302B 3276471
```
