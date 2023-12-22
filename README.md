

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
      <version>0.1.1</version>
    </dependency>
  </dependencies>
</project>
```

Gradle `build.gradle`

```groovy
dependencies {
    implementation "io.zksync:zksync2:0.1.1"
}
```

## Differences to Web3j

## ZkSync Web3 client

```java
import io.zksync.protocol.ZkSync;
import org.web3j.protocol.http.HttpService;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync = ZkSync.build(new HttpService("http://127.0.0.1:3050"));
    }
}
```

## EthSigner

```java
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import org.web3j.crypto.Credentials;

public class Main {
    public static void main(String ...args) {
        long chainId = 123L;// Chainid of the ZkSync network

        Credentials credentials = Credentials.create("0x<private_key>");

        EthSigner signer = new PrivateKeyEthSigner(credentials, chainId);
    }
}
```

## ZkSync wallet

```java
import io.zksync.crypto.signer.EthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer

        ZkSyncWallet wallet = new ZkSyncWallet(zksync, signer, Token.ETH);
    }
}
```

## Transactions

ZkSync2 supports Ethereum's `Legacy` and `EIP-1155` transaction except deploying contract.

### EIP712

### Deploy contract (Create 2) [EIP-1014](https://eips.ethereum.org/EIPS/eip-1014)

Code:

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.ContractDeployer;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;


import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {
    public static void main(String... args) {
        ZkSync zksync; // Initialize client
        Credentials credentials = Credentials.create("PRIVATE_KEY"); // Initialize signer
        BigInteger chainId = zksync.ethChainId().send().getChainId();
        EthSigner signer = new PrivateKeyEthSigner(credentials, chainId);
        ZkSyncTransactionReceiptProcessor processor = new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
        
        String binary = "0x<bytecode_of_the_contract>";

        byte[] salt = SecureRandom.getSeed(32);

        // Here we can precompute contract address before its deploying
        String precomputedAddress = ContractDeployer.computeL2Create2Address(new Address(signer.getAddress()), Numeric.hexStringToByteArray(binary), new byte[]{}, salt).getValue();

        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();

        Transaction estimate = Transaction.create2ContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                binary,
                "0x",
                salt
        );

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                feeProvider.getGasLimit(estimate),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                feeProvider.getGasPrice(),
                credentials.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());    
    }
}
```

### Deploy contract (Create)

Code:

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.ContractDeployer;
import io.zksync.utils.ZkSyncAddresses;
import io.zksync.wrappers.NonceHolder;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;

import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;


import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer
        ZkSyncTransactionReceiptProcessor processor = new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

        String binary = "0x<bytecode_of_the_contract>";

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        NonceHolder nonceHolder = NonceHolder.load(ZkSyncAddresses.NONCE_HOLDER_ADDRESS, zksync, new ReadonlyTransactionManager(zksync, signer.getAddress()), new DefaultGasProvider());

        BigInteger deploymentNonce = nonceHolder.getDeploymentNonce(signer.getAddress()).send();
        
        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        Transaction estimate = Transaction.createContractTransaction(
                signer.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                binary,
                "0x"
        );

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                feeProvider.getGasLimit(estimate),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                feeProvider.getGasPrice(),
                signer.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();
        
        // You can check transaction status as the same way as in Web3
        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());
    }
}
```

### Deploy contract via ZkSyncWallet

Code:

```java
import io.zksync.ZkSyncWallet;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

public class Main {
    public static void main(String ...args) {
        ZkSyncWallet wallet; // Initialize wallet

        TransactionReceipt receipt = wallet.deploy(Numeric.hexStringToByteArray("0x<bytecode_of_the_contract>")).send();
    }
}
```

### Execute contract

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer
        ZkSyncTransactionReceiptProcessor processor = new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        String contractAddress = "0x<contract_address>";
        String calldata = "0x<calldata>"; // Here is an encoded contract function

        Transaction estimate = Transaction.createFunctionCallTransaction(
                signer.getAddress(),
                contractAddress,
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        );

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                feeProvider.getGasLimit(estimate),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                feeProvider.getGasPrice(),
                signer.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();
        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());
    }
}
```

### Execute contact via ZkSyncWallet

```java
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Collections;

public class Main {
    public static void main(String... args) {
        ZkSyncWallet wallet; // Initialize wallet

        String contractAddress = "0x<contract_address>";

        // Example contract function
        Function contractFunction = new Function(
                "increment",
                Collections.singletonList(new Uint256(BigInteger.ONE)),
                Collections.emptyList());

        TransactionReceipt receipt = wallet.execute(contractAddress, contractFunction).send();
    }
}
```

### Execute contract via Web3j generic Contract

```java
import io.zksync.crypto.signer.EthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);
        ZkSyncTransactionManager transactionManager = new ZkSyncTransactionManager(zksync, signer, feeProvider);

        // Wrapper class of a contract generated by Web3j or Epirus ClI
        SomeContract contract = SomeContract.load("0x<contract_address>", zksync, transactionManager, new DefaultGasProvider()).send();

        // Generated method in wrapper
        TransactionReceipt receipt = contract.increment(BigInteger.ONE).send();

        //The same way you can call read function

        BigInteger result = contract.get().send();
    }
}
```

### Transfer funds (Native coins)

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        BigInteger amountInWei = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        Transaction estimate = Transaction.createFunctionCallTransaction(
                signer.getAddress(),
                "0x<receiver_address>",
                BigInteger.ZERO,
                BigInteger.ZERO,
                amountInWei,
                "0x"
        );

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                feeProvider.getGasLimit(estimate),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                feeProvider.getGasPrice(),
                signer.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());
    }
}
```

### Transfer funds (ERC20 tokens)

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import io.zksync.wrappers.ERC20;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        // Here we're getting tokens supported by ZkSync
        Token token = zksync.zksGetConfirmedTokens(0, (short) 100).send()
                .getResult().stream()
                .findAny().orElseThrow(IllegalArgumentException::new);

        BigInteger amount = token.toBigInteger(1.0);

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        String calldata = FunctionEncoder.encode(ERC20.encodeTransfer("0x<receiver_address>", amount));

        Transaction estimate = Transaction.createFunctionCallTransaction(
                signer.getAddress(),
                token.getL2Address(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        );

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                feeProvider.getGasLimit(estimate),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                feeProvider.getGasPrice(),
                signer.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());
    }
}
```

### Transfer funds via ZkSyncWallet

```java
import io.zksync.protocol.core.Token;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        ZkSyncWallet wallet; // Initialize wallet

        BigInteger amount = Token.ETH.toBigInteger(0.5);

        TransactionReceipt receipt = wallet.transfer("0x<receiver_address>", amount).send();

        //You can check balance
        BigInteger balance = wallet.getBalance().send();

        //Also, you can convert amount number to decimal
        BigDecimal decimalBalance = Token.ETH.intoDecimal(balance);
    }
}
```
### Deposit

```java
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.provider.EthereumProvider;
import org.web3j.protocol.Web3j;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        Web3j web3j; // Initialize web3j client
        Credentials credentials; // Initialize credentials
        BigInteger chainId; // Initialize chainId
        
        TransactionManager manager = new RawTransactionManager(web3j, credentials, chainId.longValue());
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(300_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(wallet.getZksync(), web3j, manager, gasProvider).join()
                .deposit(Token.ETH, Convert.toWei("0.001", Convert.Unit.ETHER).toBigInteger(), BigInteger.ZERO, credentials.getAddress()).join();

        System.out.println(receipt);
    }
}
```

### Deposit ERC20

```java
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.provider.EthereumProvider;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        Web3j web3j; // Initialize web3j client
        Credentials credentials; // Initialize credentials
        BigInteger chainId; // Initialize chainId
        Token token = new Token("L1_ADDRESS", "L2_ADDRESS", 18);
        
        TransactionManager manager = new RawTransactionManager(web3j, credentials, chainId.longValue());
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(300_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(wallet.getZksync(), web3j, manager, gasProvider).join()
                .deposit(token, Convert.toWei("0.001", Convert.Unit.ETHER).toBigInteger(), BigInteger.ZERO, credentials.getAddress()).join();

        System.out.println(receipt);
    }
}
```

### Withdraw funds (Native coins)

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import io.zksync.wrappers.IL2Bridge;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer
        ZkSyncTransactionReceiptProcessor processor = new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        // Get address of the default bridge contract
        String l2EthBridge = L2_ETH_TOKEN_ADDRESS;
        final Function withdraw = new Function(
                IL2Bridge.FUNC_WITHDRAW,
                Arrays.asList(new Address(to)),
                Collections.emptyList());

        String calldata = FunctionEncoder.encode(withdraw);

        Transaction estimate = Transaction.createFunctionCallTransaction(
                signer.getAddress(),
                l2EthBridge,
                BigInteger.ZERO,
                BigInteger.ZERO,
                amount,
                calldata
        );

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                feeProvider.getGasLimit(estimate),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                feeProvider.getGasPrice(),
                signer.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());
    }
}
```

### Withdraw funds (ERC20 tokens)

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import io.zksync.wrappers.IL2Bridge;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync; // Initialize client
        EthSigner signer; // Initialize signer

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        // Here we're getting tokens supported by ZkSync
        Token token = zksync.zksGetConfirmedTokens(0, (short) 100).send()
                .getResult().stream()
                .findAny().orElseThrow(IllegalArgumentException::new);

        // Get address of the default bridge contract (ERC20)
        String l2Erc20Bridge = zksync.zksGetBridgeContracts().send().getResult().getL2Erc20DefaultBridge();
        final Function withdraw = new Function(
                IL2Bridge.FUNC_WITHDRAW,
                Arrays.asList(new Address(to),
                        new Address(tokenToUse.getL2Address()),
                        new Uint256(amount)),
                Collections.emptyList());
        
        String calldata = FunctionEncoder.encode(withdraw);

        Transaction estimate = Transaction.createFunctionCallTransaction(
                signer.getAddress(),
                l2Erc20Bridge,
                BigInteger.ZERO,
                BigInteger.ZERO,
                calldata
        );

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                feeProvider.getGasLimit(estimate),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                BigInteger.valueOf(100000000L),
                feeProvider.getGasPrice(),
                signer.getAddress(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());
    }
}
```

### Withdraw funds via ZkSyncWallet

```java
import io.zksync.protocol.core.Token;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        ZkSyncWallet wallet; // Initialize wallet

        BigInteger amount = Token.ETH.toBigInteger(0.5);

        // ETH By default
        TransactionReceipt receipt = wallet.withdraw("0x<receiver_address>", amount).send();

        // Also we can withdraw ERC20 token
        Token token;

        TransactionReceipt receipt = wallet.withdraw("0x<receiver_address>", amount, token).send();
    }
}
```

### Finalize Withdraw
```java
import io.zksync.protocol.core.Token;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        Web3j webj; //Initialize web3j
        ZkSync zksync; //Initalize zksync
        Credentials credentials; //Initialize credentials
        
        TransactionManager manager = new RawTransactionManager(web3j, credentials, chainId.longValue());
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, BigInteger.valueOf(300_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(zksync, web3j, manager, gasProvider).join()
                .finalizeWithdraw("TRANSACTION_HASH", 0);
    }
}
```

## Wallet

## Contract interaction using wrapper

## Fee

### Get price of the execution of the transaction

```java
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.transaction.fee.Fee;

import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        ZkSync zksync; // Initialize client

        Transaction forEstimate; // Create transaction as described above

        Fee fee = zksync.zksEstimateFee(forEstimate).send().getResult();

        // Also possible to use eth_estimateGas
        BigInteger gasUsed = zksync.ethEstimateGas(forEstimate).send().getAmountUsed();
    }
}
```

### Get fee via TransactionFeeProvider

```java
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync; // Initialize client

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Transaction forEstimate; // Create transaction as described above

        Fee fee = feeProvider.getFee(forEstimate);
    }
}
```

## Transaction manager

## Appendix and FAQ

:::info
**Find this document incomplete?** Leave a comment!
:::

###### tags: `ZkSync` `Documentation`
