# include .env file and export its env vars
# (-include to ignore error if it does not exist)
-include .env

run-mainnet:	## Run tailz on Etherum MAINNET
	sbt "eth-tailz / runMain io.mankea.eth.streamer.App \
		--forever \
		--polling-interval 12 \
		--chunk-size 10000 \
		0x39e2f550fef9ee15b459d16bD4B243b04b1f60e5 \
		17088058"

.DEFAULT_GOAL := help

.PHONY: help docs test run
help:		## display this help message
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)

