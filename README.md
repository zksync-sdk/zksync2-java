---
title: 'ZkSync 2 Java SDK'
disqus: hackmd
---

ZkSync 2 Java SDK
===
![downloads](https://img.shields.io/github/downloads/atom/atom/total.svg)
![build](https://img.shields.io/appveyor/ci/:user/:repo.svg)
![chat](https://img.shields.io/discord/:serverId.svg)

## Table of Contents

[TOC]

## Dependency

For connecting ZkSync library just add the following dependency to your build file.

Maven `pom.xml`

```xml

```

Gradle `build.gradle`

```groovy
dependencies {
    implementation "io.zksync:zksync2:0.0.1"
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

## Transactions

### EIP712

### Deploy contract

Code:

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync = ...;
        EthSigner signer = ...;

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        DeployContract zkDeploy = new DeployContract(
                "0x<bytecode_of_the_contract>",
                signer.getAddress(),
                new Fee(Token.ETH.getAddress()),
                nonce);

        Fee fee = zksync.zksEstimateFee(new Transaction(zkDeploy)).send().getResult();
        zkDeploy.setFee(fee);

        Transaction712<DeployContract> transaction = new Transaction712<>(chainId.longValue(), zkDeploy);

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkDeploy))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        String sentTransactionHash = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send().getTransactionHash();

        // You can check transaction status as the same way as in Web3
        TransactionReceipt receipt = zksync.ethGetTransactionReceipt(sentTransactionHash).send().getTransactionReceipt();
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
        ZkSyncWallet wallet = ...;

        TransactionReceipt receipt = wallet.deploy(Numeric.hexStringToByteArray("0x<bytecode_of_the_contract>")).send();
    }
}
```

### Deploy contract via Web3j generic Contract

```java
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class SomeContract extends Contract {
  ...

    public static final String FUNC_GET = "get";

    public static final String FUNC_INCREMENT = "increment";

    public RemoteFunctionCall<BigInteger> get() {
        final Function function = new Function(FUNC_GET,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> increment(BigInteger x) {
        final Function function = new Function(
                FUNC_INCREMENT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(x)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }
    
    @Deprecated
    public static SomeContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SomeContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SomeContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SomeContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SomeContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SomeContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SomeContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SomeContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
  
    public static RemoteCall<SomeContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SomeContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SomeContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SomeContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SomeContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SomeContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SomeContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SomeContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
  
  ...
}
```

Code:

```java
import io.zksync.crypto.signer.EthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

public class Main {
    public static void main(String... args) {
        ZkSync zksync = ...;
        EthSigner signer = ...;

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);
        ZkSyncTransactionManager transactionManager = new ZkSyncTransactionManager(zksync, signer, feeProvider);

        SomeContract contract = SomeContract.deploy(zksync, transactionManager, new DefaultGasProvider()).send();

        System.out.println(contract.getContractAddress());// Let's print deployed contract address
    }
}
```


### Execute contract

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.Execute;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync = ...;
        EthSigner signer = ...;

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();

        String contractAddress = "0x<contract_address>";
        String calldata = "0x<calldata>"; // Here is an encoded contract function call

        Execute zkExecute = new Execute(
                contractAddress,
                calldata,
                signer.getAddress(),
                new Fee(Token.ETH.getAddress()),
                nonce);

        Fee fee = zksync.zksEstimateFee(new Transaction(zkExecute)).send().getResult();
        zkExecute.setFee(fee);

        Transaction712<Execute> transaction = new Transaction712<>(chainId.longValue(), zkExecute);

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkExecute))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        String sentTransactionHash = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send().getTransactionHash();

        // You can check transaction status as the same way as in Web3
        TransactionReceipt receipt = zksync.ethGetTransactionReceipt(sentTransactionHash).send().getTransactionReceipt();
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
        ZkSyncWallet wallet = ...;

        Function contractFunction = new Function(
                "increment",
                Collections.singletonList(new Uint256(BigInteger.ONE)),
                Collections.emptyList());

        TransactionReceipt receipt = wallet.execute(contractFunction).send();
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
        ZkSync zksync = ...;
        EthSigner signer = ...;

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);
        ZkSyncTransactionManager transactionManager = new ZkSyncTransactionManager(zksync, signer, feeProvider);

        SomeContract contract = SomeContract.load("0x<contract_address>", zksync, transactionManager, new DefaultGasProvider()).send();

        TransactionReceipt receipt = contract.increment(BigInteger.ONE).send();

        //The same way you can call read function

        BigInteger result = contract.get().send();
    }
}
```

### Transfer funds

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.Execute;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync = ...;
        EthSigner signer = ...;

        BigInteger chainId = zksync.ethChainId().send().getChainId();
        
        String tokenAddress = "0x<token_address>";// Can be `Token.ETH.getAddress()`

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        String calldata = FunctionEncoder.encode(ERC20.encodeTransfer("0x<receiver_address>", Token.ETH.toBigInteger(1.0)));

        Execute zkExecute = new Execute(
                tokenAddress,
                calldata,
                signer.getAddress(),
                new Fee(Token.ETH.getAddress()),
                nonce);

        Fee fee = zksync.zksEstimateFee(new Transaction(zkExecute)).send().getResult();
        zkExecute.setFee(estimateFee);

        Transaction712<Execute> transaction = new Transaction712<>(chainId.longValue(), zkExecute);

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkExecute))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        String sentTransactionHash = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send().getTransactionHash();

        // You can check transaction status as the same way as in Web3
        TransactionReceipt receipt = zksync.ethGetTransactionReceipt(sentTransactionHash).send().getTransactionReceipt();
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
        ZkSyncWallet wallet = ...;

        BigInteger amount = Token.ETH.toBigInteger(0.5);

        TransactionReceipt receipt = wallet.transfer("0x<receiver_address>", amount).send();

        //You can check balance
        BigInteger balance = wallet.getBalance().send();

        //Also, you can convert amount number to decimal
        BigDecimal decimalBalance = Token.ETH.intoDecimal(balance);
    }
}
```

### Withdraw funds

```java
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.Withdraw;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync = ...;
        EthSigner signer = ...;

        BigInteger chainId = zksync.ethChainId().send().getChainId();

        BigInteger nonce = zksync
                .ethGetTransactionCount(signer.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        Withdraw zkWithdraw = new Withdraw(
                Token.ETH.getAddress(),
                signer.getAddress(),
                Convert.toWei("1", Convert.Unit.ETHER).toBigInteger(),
                "0x<receiver_of_the_funds>",
                new Fee(Token.ETH.getAddress()),
                nonce);

        Fee fee = zksync.zksEstimateFee(new Transaction(zkWithdraw)).send().getResult();
        zkWithdraw.setFee(fee);

        Transaction712<Withdraw> transaction = new Transaction712<>(chainId.longValue(), zkWithdraw);

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkWithdraw))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        String sentTransactionHash = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send().getTransactionHash();

        // You can check transaction status as the same way as in Web3
        TransactionReceipt receipt = zksync.ethGetTransactionReceipt(sentTransactionHash).send().getTransactionReceipt();
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
        ZkSyncWallet wallet = ...;

        BigInteger amount = Token.ETH.toBigInteger(0.5);

        TransactionReceipt receipt = wallet.withdraw("0x<receiver_address>", amount).send();

        //You can get withdrawal transaction hash in L1 chain
        String l1withdrawalHash = wallet.getZksync().zksGetL1WithdrawalTx(receipt.getTransactionHash()).send().getTransactionHash();
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
import io.zksync.protocol.core.Token;
import io.zksync.transaction.Withdraw;
import io.zksync.transaction.fee.Fee;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public class Main {
    public static void main(String... args) {
        ZkSync zksync = ...;
        EthSigner signer = ...;

        Withdraw zkWithdraw = new Withdraw(
                Token.ETH.getAddress(),
                signer.getAddress(),
                Convert.toWei("1", Convert.Unit.ETHER).toBigInteger(),
                "0x<receiver_of_the_funds>",
                new Fee(Token.ETH.getAddress()),
                BigInteger.ZERO);

        Fee fee = zksync.zksEstimateFee(new Transaction(zkWithdraw)).send().getResult();
    }
}
```

### Get fee via TransactionFeeProvider

```java
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.Withdraw;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;

public class Main {
    public static void main(String ...args) {
        ZkSync zksync = ...;

        ZkTransactionFeeProvider feeProvider = new DefaultTransactionFeeProvider(zksync, Token.ETH);

        Withdraw zkWithdraw = ...;

        Fee fee = feeProvider.getFee(zkWithdraw);
    }
}
```

## Transaction manager

## Appendix and FAQ

:::info
**Find this document incomplete?** Leave a comment!
:::

###### tags: `ZkSync` `Documentation`
