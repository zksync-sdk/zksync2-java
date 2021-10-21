package io.zksync.transaction.fee;

import java.math.BigInteger;

import io.zksync.methods.response.ZksFee;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.Transaction;

public interface ZkTransactionFeeProvider {

    <T extends Transaction> ZksFee getFee(T transaction);

    Token getFeeToken();

    default <T extends Transaction> BigInteger getTotalFee(T transaction) {
        return getFee(transaction).getTotalFeeInteger();
    }
    
}
