package io.zksync;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import io.zksync.methods.request.Eip712Meta;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.transaction.*;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;
import io.zksync.transaction.type.Transaction712;
import io.zksync.transaction.fee.Fee;
import io.zksync.wrappers.ERC20;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import lombok.Getter;

import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;
import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_FREQUENCY;

@Getter
public class ZkSyncWallet {

    private final ZkSync zksync;
    private final EthSigner signer;
    private final TransactionReceiptProcessor transactionReceiptProcessor;
    private final ZkTransactionFeeProvider feeProvider;

    public ZkSyncWallet(ZkSync zksync, EthSigner signer, TransactionReceiptProcessor transactionReceiptProcessor,
                        ZkTransactionFeeProvider feeProvider) {
        this.zksync = zksync;
        this.signer = signer;
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.feeProvider = feeProvider;
    }

    public ZkSyncWallet(ZkSync zksync, EthSigner signer) {
        this(zksync, signer, new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                new DefaultTransactionFeeProvider(zksync, Token.ETH));
    }

    public ZkSyncWallet(ZkSync zksync, EthSigner signer, Token feeToken) {
        this(zksync, signer, new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                new DefaultTransactionFeeProvider(zksync, feeToken));
    }

    public ZkSyncWallet(ZkSync zksync, Credentials credentials, Long chainId) {
        this(zksync, new PrivateKeyEthSigner(credentials, chainId));
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount) {
        return transfer(to, amount, null, null);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, Token token) {
        return transfer(to, amount, token, null);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, @Nullable Token token, @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
            Function function = ERC20.encodeTransfer(to, amount);
            String calldata = FunctionEncoder.encode(function);

            Execute zkExecute = new Execute(
                    (token == null ? Token.ETH : token).getL2Address(),
                    calldata,
                    signer.getAddress(),
                    new Fee(feeProvider.getFeeToken().getL2Address()),
                    nonceToUse);

            EthSendTransaction sent = estimateAndSend(zkExecute).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

//    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount) {
//        return withdraw(to, amount, null, null);
//    }
//
//    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, Token token) {
//        return withdraw(to, amount, token, null);
//    }
//
//    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, @Nullable Token token, @Nullable BigInteger nonce) {
//        return new RemoteCall<>(() -> {
//            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
//            Withdraw zkWithdraw = new Withdraw(
//                    (token == null ? Token.ETH : token).getL2Address(),
//                    to,
//                    amount,
//                    signer.getAddress(),
//                    new Fee(feeProvider.getFeeToken().getL2Address()),
//                    nonceToUse);
//
//            EthSendTransaction sent = estimateAndSend(zkWithdraw).join();
//
//            try {
//                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
//            } catch (IOException | TransactionException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode) {
//        return deploy(bytecode, null, null);
//    }
//
//    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, byte[] calldata) {
//        return deploy(bytecode, calldata, null);
//    }
//
//    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, @Nullable byte[] calldata,
//                                                 @Nullable BigInteger nonce) {
//        return new RemoteCall<>(() -> {
//            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
//
//            DeployContract zkDeployContract = new DeployContract(
//                            bytecode,
//                            calldata,
//                            signer.getAddress(),
//                            new Fee(feeProvider.getFeeToken().getL2Address()),
//                            nonceToUse);
//
//            EthSendTransaction sent = estimateAndSend(zkDeployContract).join();
//
//            try {
//                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
//            } catch (IOException | TransactionException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function) {
        return execute(contractAddress, function, null);
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function, @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
            String calldata = FunctionEncoder.encode(function);

            Execute zkExecute = new Execute(
                    contractAddress,
                    calldata,
                    signer.getAddress(),
                    new Fee(feeProvider.getFeeToken().getL2Address()),
                    nonceToUse);

            EthSendTransaction sent = estimateAndSend(zkExecute).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<BigInteger> getBalance() {
        return getBalance(signer.getAddress(), Token.ETH, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(Token token) {
        return getBalance(signer.getAddress(), token, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(String address) {
        return getBalance(address, Token.ETH, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(String address, Token token) {
        return getBalance(address, token, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(String address, Token token, DefaultBlockParameter at) {
        return new RemoteCall<>(() ->
                this.zksync.ethGetBalance(address, at, token.getL2Address()).sendAsync().join().getBalance());
    }

    public RemoteCall<BigInteger> getNonce(DefaultBlockParameter at) {
        return new RemoteCall<>(() -> this.zksync
                .ethGetTransactionCount(signer.getAddress(), at).sendAsync().join()
                .getTransactionCount());
    }

    public RemoteCall<BigInteger> getNonce() {
        return getNonce(ZkBlockParameterName.COMMITTED);
    }

    private CompletableFuture<EthSendTransaction> estimateAndSend(Execute transaction) {
        return CompletableFuture
                .supplyAsync(() -> {
                    Fee fee = feeProvider.getFee(transaction);
                    transaction.setFee(fee);

                    long chainId = signer.getDomain().join().getChainId().getValue().longValue();
                    Transaction712 prepared = new Transaction712(
                            transaction.getNonceNumber(),
                            transaction.getFee().getErgsPriceLimitNumber(),
                            transaction.getFee().getErgsLimitNumber(),
                            transaction.getContractAddressString(),
                            BigInteger.ZERO,
                            Numeric.toHexString(transaction.getCalldata()),
                            chainId,
                            new Eip712Meta(
                                    transaction.getFee().getFeeTokenString(),
                                    transaction.getFee().getErgsPerPubdataLimitNumber(),
                                    BigInteger.ZERO,
                                    null,
                                    null
                            )
                    );

                    String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(transaction))).join();
                    byte[] signed = TransactionEncoder.encode(prepared, TransactionEncoder.getSignatureData(signature));

                    return this.zksync.ethSendRawTransaction(Numeric.toHexString(signed))
                            .sendAsync().join();
                })
                .thenApply(response -> {
                    if (response.hasError()) {
                        throw new JsonRpcResponseException(response);
                    } else {
                        return response;
                    }
                });
    }

}
