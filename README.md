

# 🚀 zksync2-java Java SDK 🚀

![downloads](https://img.shields.io/github/downloads/atom/atom/total.svg)
![build](https://img.shields.io/appveyor/ci/:user/:repo.svg)
![chat](https://img.shields.io/discord/:serverId.svg)

![Era Logo](https://github.com/matter-labs/era-contracts/raw/main/eraLogo.svg)

In order to provide easy access to all the features of zkSync Era, the `zksync2-java` Java SDK was created,
which is made in a way that has an interface very similar to those of [web3j](https://web3py.readthedocs.io/en/v6.6.1/). In
fact, `web3j` is a peer dependency of our library and most of the objects exported by `zksync2-java` inherit from the corresponding `web3j` objects and override only the fields that need
to be changed.

While most of the existing SDKs functionalities should work out of the box, deploying smart contracts or using unique zkSync features,
like account abstraction, requires providing additional fields to those that Ethereum transactions have by default.

The library is made in such a way that after replacing `web3j` with `zksync2-java` most client apps will work out of
box.

🔗 For a detailed walkthrough, refer to the [official documentation](https://era.zksync.io/docs/api/java).


## Dependency

For connecting ZkSync2 library just add the following dependency to your build file.

Maven `pom.xml`

```xml
<project>
  ...
  <dependencies>
    <dependency>
      <groupId>io.zksync</groupId>
      <artifactId>zksync2</artifactId>
      <version>v0.2.0</version>
    </dependency>
  </dependencies>
</project>
```

Gradle `build.gradle`

```groovy
dependencies {
    implementation "io.zksync:zksync2:v0.2.0"
}
```

## 📝 Examples

The complete examples with various use cases are available [here](https://github.com/zksync-sdk/zksync2-examples/tree/main/java).

### Connect to the zkSync Era network:

```java
import io.zksync.protocol.ZkSync;
import org.web3j.protocol.http.HttpService;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync = ZkSync.build(new HttpService("http://127.0.0.1:3050"));
    }
}
```

### Create a wallet

```java
import com.sun.net.httpserver.HttpServer;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.account.wallet;

public class Main {
    public static void main(String... args) {
        ZkSync zksync = ZkSync.build(new HttpService("https://sepolia.era.zksync.dev"));
        Credentials credentials = Credentials.create("PRIVATE_KEY");
        Web3j l1Web3 = Web3j.build(new HttpService("https://rpc.ankr.com/eth_sepolia"));

        Wallet wallet = new Wallet(l1Web3, zksync, credentials);
    }
}
```

### Check account balances

```ts
BigInteger ethBalance = wallet.getBalance().send(); // balance on zkSync Era network

BigInteger ethBalanceL1 = wallet.getBalanceL1().send(); // balance on Sepolia network
```

### Transfer funds

Transfer funds among accounts on L2 network.

```java
TransferTransaction transaction = new TransferTransaction("RECEIVER", amount, signer.getAddress());
TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
```

### Deposit funds

Transfer funds from L1 to L2 network.

```java
DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.ETH_ADDRESS, amount);
String hash = testWallet.deposit(transaction).sendAsync().join().getResult();
```

### Withdraw funds

Transfer funds from L2 to L1 network.

```java
WithdrawTransaction transaction = new WithdrawTransaction(ZkSyncAddresses.ETH_ADDRESS, amount, testWallet.getAddress());
TransactionReceipt result = testWallet.withdraw(transaction).sendAsync().join();
```

## 🤖 Running tests

In order to run test you need to run [local-setup](https://github.com/matter-labs/local-setup) on your machine.
For running tests, use:

```shell
gradle clean test
```

## 🤝 Contributing

We welcome contributions from the community! If you're interested in contributing to the `zksync2-java` Java SDK,
please take a look at our [CONTRIBUTING.md](./.github/CONTRIBUTING.md) for guidelines and details on the process.

Thank you for making `zksync2-java` Java SDK better! 🙌
