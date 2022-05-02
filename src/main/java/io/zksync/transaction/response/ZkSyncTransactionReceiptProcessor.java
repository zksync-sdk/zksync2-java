package io.zksync.transaction.response;

import io.zksync.protocol.ZkSync;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.util.Optional;

public class ZkSyncTransactionReceiptProcessor extends TransactionReceiptProcessor {

    private final ZkSync zkSync;

    protected final long sleepDuration;
    protected final int attempts;

    public ZkSyncTransactionReceiptProcessor(ZkSync zkSync, long sleepDuration, int attempts) {
        super(zkSync);
        this.zkSync = zkSync;
        this.attempts = attempts;
        this.sleepDuration = sleepDuration;
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException {

        return getTransactionReceipt(transactionHash, sleepDuration, attempts);
    }

    private TransactionReceipt getTransactionReceipt(
            String transactionHash, long sleepDuration, int attempts)
            throws IOException, TransactionException {

        Optional<? extends TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent() || receiptOptional.get().getBlockHash() == null) {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new TransactionException(e);
                }

                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                return receiptOptional.get();
            }
        }

        throw new TransactionException(
                "Transaction receipt was not generated after "
                        + ((sleepDuration * attempts) / 1000
                        + " seconds for transaction: "
                        + transactionHash),
                transactionHash);
    }

    Optional<? extends TransactionReceipt> sendTransactionReceiptRequest(String transactionHash)
            throws IOException, TransactionException {
        EthGetTransactionReceipt transactionReceipt =
                zkSync.ethGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new TransactionException(
                    "Error processing request: " + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
