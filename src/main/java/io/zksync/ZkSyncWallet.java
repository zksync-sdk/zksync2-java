package io.zksync;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.methods.request.ZksEstimateFeeRequest;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.TimeRange;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.MigrateToPorter;
import io.zksync.transaction.Transaction;
import io.zksync.transaction.Transfer;
import io.zksync.transaction.Withdraw;
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

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, @Nullable Token token,
            @Nullable Integer nonce,
            @Nullable TimeRange timeRange) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
            Transfer zkTransfer = new Transfer(
                    (token == null ? Token.ETH : token).getAddress(),
                    to,
                    amount,
                    this.signer.getAddress(),
                    feeProvider.getFeeToken().getAddress(),
                    BigInteger.ZERO,
                    nonceToUse,
                    timeRange == null ? new TimeRange() : timeRange);

            EthSendTransaction sent = estimateAndSend(zkTransfer).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<TransactionReceipt> migrateToPorter(@Nullable Integer nonce, @Nullable TimeRange timeRange) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
            MigrateToPorter zkMigrateToPorter = new MigrateToPorter(
                    this.signer.getAddress(),
                    this.signer.getAddress(),
                    feeProvider.getFeeToken().getAddress(),
                    BigInteger.ZERO,
                    nonceToUse,
                    timeRange == null ? new TimeRange() : timeRange);

            EthSendTransaction sent = estimateAndSend(zkMigrateToPorter).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, @Nullable Token token,
            @Nullable Integer nonce,
            @Nullable TimeRange timeRange) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
            Withdraw zkWithdraw = new Withdraw(
                    (token == null ? Token.ETH : token).getAddress(),
                    to,
                    amount,
                    this.signer.getAddress(),
                    feeProvider.getFeeToken().getAddress(),
                    BigInteger.ZERO,
                    nonceToUse,
                    timeRange == null ? new TimeRange() : timeRange);

            EthSendTransaction sent = estimateAndSend(zkWithdraw).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<Integer> getNonce() {
        return new RemoteCall<>(() -> this.zksync
                .ethGetTransactionCount(this.signer.getAddress(), ZkBlockParameterName.COMMITTED).sendAsync().join()
                .getTransactionCount().intValue());
    }

    private <T extends Transaction> CompletableFuture<EthSendTransaction> estimateAndSend(T transaction) {
        return CompletableFuture
                .supplyAsync(() -> {
                    RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(transaction);
                    ZksEstimateFee estimateFee = this.zksync
                            .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate))
                            .sendAsync().join();
                    transaction.setFee(new BigInteger(estimateFee.getResult().getTotalFee()));

                    RawTransaction transactionForSubmit = TransactionEncoder.encodeToRawTransaction(transaction,
                            signer);

                    byte[] message = TransactionEncoder.signMessage(transactionForSubmit,
                            Credentials.create(ECKeyPair.create(BigInteger.ZERO)));

                    EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message))
                            .sendAsync().join();

                    return sent;
                });
    }

}
