# [0.4.0](https://github.com/zksync-sdk/zksync2-java/compare/v0.3.0...v0.4.0) (2024-11-17)


### Bug Fixes

* **provider:** fix `getProof` to call correct method ID ([9f4addc](https://github.com/zksync-sdk/zksync2-java/commit/9f4addc3e705ecc1a3f24d9e068ea924498ef1d6))


### Features

* add `getFeeParams` RPC method ([52b9aae](https://github.com/zksync-sdk/zksync2-java/commit/52b9aaef6abecfb12804c1bb25e7abc58b7eac1e))
* add `getProtocolVersion` RPC method ([24f6892](https://github.com/zksync-sdk/zksync2-java/commit/24f68920af1a69ce822f0754e8f023eaa22b3aa6))
* add `sendRawTransactionWithDetailedOutput` RPC method ([b385eff](https://github.com/zksync-sdk/zksync2-java/commit/b385effaeef24ee0223a53faa1ac2486c1b52200))
* implement `SmartAccount` class ([c6bfd9d](https://github.com/zksync-sdk/zksync2-java/commit/c6bfd9d76abfc36a37a05caf6ae57ed79a92d595))

# [0.3.0](https://github.com/zksync-sdk/zksync2-java/compare/v0.2.1...v0.3.0) (2024-06-07)


### Features

* provide support for Bridgehub ([c0858a7](https://github.com/zksync-sdk/zksync2-java/commit/c0858a7f263eb6ec79d6758aa425aee5b5151551))

# [0.3.0](https://github.com/zksync-sdk/zksync2-java/compare/v0.2.1...v0.3.0) (2024-06-07)


### Features

* provide support for Bridgehub ([c0858a7](https://github.com/zksync-sdk/zksync2-java/commit/c0858a7f263eb6ec79d6758aa425aee5b5151551))

# [0.3.0](https://github.com/zksync-sdk/zksync2-java/compare/v0.2.1...v0.3.0) (2024-06-07)


### Features

* provide support for Bridgehub ([c0858a7](https://github.com/zksync-sdk/zksync2-java/commit/c0858a7f263eb6ec79d6758aa425aee5b5151551))

## [0.2.1](https://github.com/zksync-sdk/zksync2-java/compare/v0.2.0...v0.2.1) (2024-03-16)


### Bug Fixes

* **utils:** fix `applyL1ToL2Alias` and `undoL1ToL2Alias` ([9fd3b23](https://github.com/zksync-sdk/zksync2-java/commit/9fd3b234b89729138cba37cfa299e75b76b85341))
* **wallet:** fix `maxPriorityFeePerGas` hiegher than `maxFeePerGas` ([3f1fcbf](https://github.com/zksync-sdk/zksync2-java/commit/3f1fcbfd2bcbc0c8e2e59b9a9c4664d716e1cdcf))

## [0.2.0](https://github.com/zksync-sdk/zksync2-java/releases/tag/v0.2.0) (19.02.2024)

### Features
* paymster withdraw and transfer support ([d5edf2b](https://github.com/zksync-sdk/zksync2-python/pull/58/files#diff-48ab0b98b39c678e3ed1d6418610c8ace1281cbf9f43b86e0b85dd52b713baff))
* support for weth bridge and custom bridge ([d5edf2b](https://github.com/zksync-sdk/zksync2-python/pull/58/files#diff-48ab0b98b39c678e3ed1d6418610c8ace1281cbf9f43b86e0b85dd52b713baff))
* provider: add zks_logProof ([d5edf2b](https://github.com/zksync-sdk/zksync2-python/pull/58/files#diff-48ab0b98b39c678e3ed1d6418610c8ace1281cbf9f43b86e0b85dd52b713baff))
* updated contract wrappers ([d5edf2b](https://github.com/zksync-sdk/zksync2-python/pull/58/files#diff-48ab0b98b39c678e3ed1d6418610c8ace1281cbf9f43b86e0b85dd52b713baff))
* Add WalletL1 for interacting with L1 chain ([d5edf2b](https://github.com/zksync-sdk/zksync2-python/pull/58/files#diff-48ab0b98b39c678e3ed1d6418610c8ace1281cbf9f43b86e0b85dd52b713baff))
* Wallet for interacting with L1 and L2 chain ([d5edf2b](https://github.com/zksync-sdk/zksync2-python/pull/58/files#diff-48ab0b98b39c678e3ed1d6418610c8ace1281cbf9f43b86e0b85dd52b713baff))

### Deprecated:
* DefaultEthereumProvider in favor of Wallet
* ZkSyncWallet in favor of Wallet


## [0.1.1](https://github.com/zksync-sdk/zksync2-java/compare/v0.1.0...v0.1.1) (2023-06-01)

### Features

*   `DefaultEthereumProvider` finalize withdraw ([14f8a70](https://github.com/zksync-sdk/zksync2-java/commit/14f8a7008f03836551ed982a88e939ebbca50275))
