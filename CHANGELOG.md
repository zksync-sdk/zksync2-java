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
