# include .env file and export its env vars
# (-include to ignore error if it does not exist)
-include .env

build:		## Compile the code
	sbt compile

b: build

build-native:		## Build native image
	sbt "nativeImage"

run-mainnet:	## Run tailz on Etherum MAINNET
	sbt "eth-tailz / runMain io.mankea.eth.streamer.App \
		--rpc-url ${ETH_MAINNET_RPC_URL} \
		--forever \
		--polling-interval 12 \
		--chunk-size 10000 \
		0x39e2f550fef9ee15b459d16bD4B243b04b1f60e5 \
		17088058"

run-mainnet-native:	## Run tailz on Etherum MAINNET
	target/eth-tailz \
		--rpc-url ${ETH_MAINNET_RPC_URL} \
		--forever \
		--polling-interval 12 \
		--chunk-size 10000 \
		0x39e2f550fef9ee15b459d16bD4B243b04b1f60e5 \
		17088058

run-base:		## Run tailz on Base MAINNET
	sbt "eth-tailz / runMain io.mankea.eth.streamer.App \
		--rpc-url ${BASE_MAINNET_RPC_URL} \
		--forever \
		--polling-interval 12 \
		--chunk-size 10000 \
		0x546Fb1621CF8C0e8e3ED8E3508b7c5100ADdBc03 \
		7760826"

run-base-native:		## Run tailz on Base MAINNET
	target/eth-tailz \
		--rpc-url ${BASE_MAINNET_RPC_URL} \
		--forever \
		--polling-interval 12 \
		--chunk-size 10000 \
		0x546Fb1621CF8C0e8e3ED8E3508b7c5100ADdBc03 \
		7760826

.DEFAULT_GOAL := help

.PHONY: help docs test run
help:		## Display this help message
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)

