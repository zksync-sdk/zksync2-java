package io.zksync.transaction.fee;

import io.zksync.methods.request.Transaction;
import io.zksync.protocol.core.Token;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public interface ZkTransactionFeeProvider extends ContractGasProvider {

    @Deprecated
    <T extends io.zksync.transaction.Transaction> Fee getFee(T transaction);
    Fee getFee(Transaction transaction);

    BigInteger getGasLimit(Transaction transaction);

    Token getFeeToken();
    
}
