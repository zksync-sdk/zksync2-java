package io.zksync.transaction.fee;

import java.math.BigInteger;

import io.zksync.protocol.core.Token;
import io.zksync.transaction.Transaction;

public interface ZkTransactionFeeProvider {

    <T extends Transaction> Fee getFee(T transaction);

    Token getFeeToken();
    
}
