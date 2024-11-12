package io.zksync.utils.smart.account;

import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import org.jetbrains.annotations.Nullable;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthChainId;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PopulateTransactionECDSA implements IPopulateTransaction {
    @Override
    public CompletableFuture<Transaction712> populateTransaction(Transaction transaction, List<String> secrets, ZkSync provider, @Nullable BigInteger nonce) {
        Credentials credentials = Credentials.create(secrets.get(0));
        CompletableFuture<EthChainId> chainIdFuture = provider.ethChainId().sendAsync();

        BigInteger value = transaction.getValueNumber() == null ? BigInteger.ZERO : transaction.getValueNumber();
        String data = transaction.getData() == null ? "0x" : transaction.getData();
        Eip712Meta meta = transaction.getEip712Meta() == null ? new Eip712Meta(BigInteger.valueOf(50000), null, null, null) : transaction.getEip712Meta();
        if (meta.getGasPerPubdataNumber() == null) {
            meta.setGasPerPubdata(BigInteger.valueOf(50000));
        }

        String from = transaction.getFrom().isEmpty() ? credentials.getAddress() : transaction.getFrom();
        CompletableFuture<Boolean> isContractAddressFuture = provider.ethGetCode(from, DefaultBlockParameterName.PENDING)
                .sendAsync()
                .thenApply(response -> response.getCode().length() != 0);

        CompletableFuture<BigInteger> nonceFuture = nonce == null
                ? provider.ethGetTransactionCount(from, DefaultBlockParameterName.PENDING).sendAsync().thenApply(response -> response.getTransactionCount())
                : CompletableFuture.completedFuture(nonce);

        CompletableFuture<ZksEstimateFee> feeFuture = isContractAddressFuture.thenCompose(isContractAddress -> {
            if (isContractAddress) {
                Transaction tx = new Transaction(credentials.getAddress(), transaction.getTo(), null, null, value, data, meta);
                return provider.zksEstimateFee(tx).sendAsync();
            } else {
                Transaction tx = new Transaction(from, transaction.getTo(), null, null, value, data, meta);
                return provider.zksEstimateFee(tx).sendAsync();
            }
        });

        return CompletableFuture.allOf(chainIdFuture, nonceFuture, feeFuture)
                .thenApply(ignored -> {
                    Fee fee = feeFuture.join().getResult();
                    BigInteger gasLimit = transaction.getGasNumber().compareTo(BigInteger.ZERO) == 0 ? fee.getGasLimitNumber() : transaction.getGasNumber();
                    BigInteger maxPriorityFee = transaction.getMaxFeePerGas() == null ? fee.getMaxFeePerGas().getValue() : transaction.getMaxFeePerGas();
                    BigInteger maxPriorityFeePerGas = transaction.getMaxPriorityFeePerGas() == null ? fee.getMaxPriorityFeePerGas().getValue() : transaction.getMaxPriorityFeePerGas();

                    return new Transaction712(
                            chainIdFuture.join().getChainId().longValue(),
                            nonceFuture.join(),
                            gasLimit,
                            transaction.getTo(),
                            value,
                            data,
                            maxPriorityFeePerGas,
                            maxPriorityFee,
                            from,
                            meta
                    );
                });
    }
}
