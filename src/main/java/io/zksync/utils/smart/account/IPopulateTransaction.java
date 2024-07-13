package io.zksync.utils.smart.account;

import io.zksync.methods.request.Transaction;
import io.zksync.protocol.ZkSync;
import io.zksync.transaction.type.Transaction712;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IPopulateTransaction {
    /**
     * Populates missing properties meant for signing using multiple ECDSA private keys.
     *
     * @param transaction The transaction that needs to be populated.
     * @param secrets The list of the ECDSA private keys used for populating the transaction.
     * @param provider The provider is used to fetch data from the network if it is required for signing.
     * @param nonce The nonce to be used when populating transaction(optional).
     */
    CompletableFuture<Transaction712> populateTransaction(Transaction transaction, List<String> secrets, ZkSync provider, @Nullable BigInteger nonce);
}
