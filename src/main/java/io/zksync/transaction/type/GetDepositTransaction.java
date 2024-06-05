package io.zksync.transaction.type;

import lombok.AllArgsConstructor;
import org.web3j.protocol.core.methods.request.Transaction;

import java.math.BigInteger;

@AllArgsConstructor
public class GetDepositTransaction {
    public Transaction tx;
    public BigInteger mintValue;
    public RequestExecuteTransaction requestExecuteTransaction;
}
