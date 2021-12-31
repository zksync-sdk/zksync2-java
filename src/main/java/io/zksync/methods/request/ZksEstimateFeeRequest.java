package io.zksync.methods.request;

import org.web3j.crypto.RawTransaction;

import io.zksync.abi.TransactionEncoder;
import io.zksync.transaction.Transaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ZksEstimateFeeRequest {

    private String data;

    public static ZksEstimateFeeRequest fromRawTransaction(RawTransaction transaction) {
        return new ZksEstimateFeeRequest("0x" + transaction.getData());
    }

    public static ZksEstimateFeeRequest fromZkTransaction(Transaction transaction) {
        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(transaction);
        return new ZksEstimateFeeRequest("0x" + transactionForEstimate.getData());
    }

}
