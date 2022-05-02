package io.zksync;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import io.zksync.transaction.*;
import io.zksync.transaction.type.Transaction712;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import io.zksync.wrappers.ERC20;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import io.zksync.abi.TransactionEncoder;
import io.zksync.abi.ZkFunctionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import lombok.Getter;

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
        this(zksync, signer, new PollingTransactionReceiptProcessor(zksync, 200, 10),
                new DefaultTransactionFeeProvider(zksync, Token.ETH));
    }

    public ZkSyncWallet(ZkSync zksync, EthSigner signer, Token feeToken) {
        this(zksync, signer, new PollingTransactionReceiptProcessor(zksync, 200, 10),
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

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, @Nullable Token token, @Nullable Integer nonce) {
        ERC20 erc20 = ERC20.load(
                (token == null ? Token.ETH : token).getAddress(),
                zksync,
                new ZkSyncTransactionManager(zksync, signer, feeProvider),
                null

        );

//        Integer nonceToUse = nonce != null ? nonce : getNonce().send();
        return erc20.transfer(to, amount);
    }

//    public RemoteCall<TransactionReceipt> migrateToPorter() {
//        return migrateToPorter(null, null);
//    }
//
//    public RemoteCall<TransactionReceipt> migrateToPorter(Integer nonce) {
//        return migrateToPorter(nonce, null);
//    }
//
//    public RemoteCall<TransactionReceipt> migrateToPorter(TimeRange timeRange) {
//        return migrateToPorter(null, timeRange);
//    }

//    public RemoteCall<TransactionReceipt> migrateToPorter(@Nullable Integer nonce, @Nullable TimeRange timeRange) {
//        return new RemoteCall<>(() -> {
//            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
//            MigrateToPorter zkMigrateToPorter = new MigrateToPorter(
//                    signer.getAddress(),
//                    signer.getAddress(),
//                    feeProvider.getFeeToken().getAddress(),
//                    BigInteger.ZERO,
//                    nonceToUse,
//                    timeRange == null ? new TimeRange() : timeRange);
//
//            EthSendTransaction sent = estimateAndSend(zkMigrateToPorter).join();
//
//            try {
//                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
//            } catch (IOException | TransactionException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount) {
        return withdraw(to, amount, null, null);
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, Token token) {
        return withdraw(to, amount, token, null);
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, @Nullable Token token, @Nullable Integer nonce) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
            Withdraw zkWithdraw = new Withdraw(
                    (token == null ? Token.ETH : token).getAddress(),
                    to,
                    amount,
                    signer.getAddress(),
                    null,
                    BigInteger.valueOf(nonceToUse));

            EthSendTransaction sent = estimateAndSend(zkWithdraw).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode) {
        return deploy(bytecode, null, null);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, byte[] calldata) {
        return deploy(bytecode, calldata, null);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, @Nullable byte[] calldata,
                                                 @Nullable Integer nonce) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();

            DeployContract zkDeployContract = calldata != null ? new DeployContract(
                    Numeric.toHexString(bytecode),
                    Numeric.toHexString(calldata),
                    signer.getAddress(),
                    null,
                    BigInteger.valueOf(nonceToUse)) :
                    new DeployContract(
                            Numeric.toHexString(bytecode),
                            signer.getAddress(),
                            null,
                            BigInteger.valueOf(nonceToUse));

            EthSendTransaction sent = estimateAndSend(zkDeployContract).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function) {
        return execute(contractAddress, function, null);
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function, @Nullable Integer nonce) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
            byte[] calldata = ZkFunctionEncoder.encodeCalldata(function);

            Execute zkExecute = new Execute(
                    contractAddress,
                    Numeric.toHexString(calldata),
                    signer.getAddress(),
                    null,
                    BigInteger.valueOf(nonceToUse));

            EthSendTransaction sent = estimateAndSend(zkExecute).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<Integer> getNonce(DefaultBlockParameter at) {
        return new RemoteCall<>(() -> this.zksync
                .ethGetTransactionCount(signer.getAddress(), at).sendAsync().join()
                .getTransactionCount().intValue());
    }

    public RemoteCall<Integer> getNonce() {
        return getNonce(ZkBlockParameterName.COMMITTED);
    }

    private <T extends Transaction> CompletableFuture<EthSendTransaction> estimateAndSend(T transaction) {
        return CompletableFuture
                .supplyAsync(() -> {
                    Fee fee = feeProvider.getFee(transaction);
                    transaction.setFee(fee);

                    long chainId = signer.getDomain().join().getChainId().getValue().longValue();
                    Transaction712<?> prepared = new Transaction712<>(chainId, transaction);

                    String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, TransactionRequest.from(prepared))).join();
                    byte[] signed = TransactionEncoder.encode(prepared, TransactionEncoder.getSignatureData(signature));

                    return this.zksync.ethSendRawTransaction(Numeric.toHexString(signed))
                            .sendAsync().join();
                });
    }

}
