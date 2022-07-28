package io.zksync.transaction.fee;

import io.zksync.methods.request.Transaction;
import io.zksync.protocol.core.Token;

public interface ZkTransactionFeeProvider {

    @Deprecated
    <T extends io.zksync.transaction.Transaction> Fee getFee(T transaction);
    Fee getFee(Transaction transaction);

    Token getFeeToken();
    
}
