package io.zksync;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
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
import io.zksync.methods.request.ZksEstimateFeeRequest;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.TimeRange;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
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

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount) {
        return transfer(to, amount, null, null, null);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, Token token) {
        return transfer(to, amount, token, null, null);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, Token token,
            Integer nonce) {
        return transfer(to, amount, token, nonce, null);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, Token token,
            TimeRange timeRange) {
        return transfer(to, amount, token, null, timeRange);
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
                    signer.getAddress(),
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

    public RemoteCall<TransactionReceipt> migrateToPorter() {
        return migrateToPorter(null, null);
    }

    public RemoteCall<TransactionReceipt> migrateToPorter(Integer nonce) {
        return migrateToPorter(nonce, null);
    }

    public RemoteCall<TransactionReceipt> migrateToPorter(TimeRange timeRange) {
        return migrateToPorter(null, timeRange);
    }

    public RemoteCall<TransactionReceipt> migrateToPorter(@Nullable Integer nonce, @Nullable TimeRange timeRange) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
            MigrateToPorter zkMigrateToPorter = new MigrateToPorter(
                    signer.getAddress(),
                    signer.getAddress(),
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

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount) {
        return withdraw(to, amount, null, null, null);
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, Token token) {
        return withdraw(to, amount, token, null, null);
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, Token token,
            Integer nonce) {
        return withdraw(to, amount, token, nonce, null);
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, Token token,
            TimeRange timeRange) {
        return withdraw(to, amount, token, null, timeRange);
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
                    signer.getAddress(),
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

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, AccountType accountType) {
        return deploy(bytecode, accountType, null, null, null);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, AccountType accountType, byte[] calldata) {
        return deploy(bytecode, accountType, calldata, null, null);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, AccountType accountType, byte[] calldata,
            Integer nonce) {
        return deploy(bytecode, accountType, calldata, nonce, null);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, AccountType accountType, byte[] calldata,
            TimeRange timeRange) {
        return deploy(bytecode, accountType, calldata, null, timeRange);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, AccountType accountType, @Nullable byte[] calldata,
            @Nullable Integer nonce, @Nullable TimeRange timeRange) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();

            byte[] calldataToUse;
            if (calldata != null) {
                calldataToUse = calldata;
            } else {
                byte[] c = new byte[256];
                c[224] = 1;
                calldataToUse = c;
            }

            DeployContract zkDeployContract = new DeployContract(
                    accountType,
                    Numeric.toHexString(bytecode),
                    Numeric.toHexString(calldataToUse),
                    signer.getAddress(),
                    feeProvider.getFeeToken().getAddress(),
                    BigInteger.ZERO,
                    nonceToUse,
                    timeRange == null ? new TimeRange() : timeRange);

            EthSendTransaction sent = estimateAndSend(zkDeployContract).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function,
            Integer nonce) {
        return execute(contractAddress, function, nonce, null);
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function,
            TimeRange timeRange) {
        return execute(contractAddress, function, null, timeRange);
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function,
            @Nullable Integer nonce, @Nullable TimeRange timeRange) {
        return new RemoteCall<>(() -> {
            Integer nonceToUse = nonce != null ? nonce : getNonce().send();
            byte[] calldata = ZkFunctionEncoder.encodeCalldata(function);

            Execute zkExecute = new Execute(
                    contractAddress,
                    Numeric.toHexString(calldata),
                    signer.getAddress(),
                    feeProvider.getFeeToken().getAddress(),
                    BigInteger.ZERO,
                    nonceToUse,
                    timeRange == null ? new TimeRange() : timeRange);

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
