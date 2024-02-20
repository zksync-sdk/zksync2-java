package io.zksync.transaction.type;

import io.zksync.methods.request.PaymasterParams;
import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
public class WithdrawTransaction {
    public String tokenAddress;
    public BigInteger amount;
    public String to;
    public String from;
    public String bridgeAddress;
    public byte[] customBridgeData;
    public PaymasterParams paymasterParams;
    public TransactionOptions options;

    public WithdrawTransaction(String tokenAddress, BigInteger amount, String from) {
        this.tokenAddress = tokenAddress;
        this.amount = amount;
        this.from = from;
    }
    public WithdrawTransaction(String tokenAddress, BigInteger amount, String to, PaymasterParams paymasterParams) {
        this.tokenAddress = tokenAddress;
        this.amount = amount;
        this.to = to;
        this.paymasterParams = paymasterParams;
    }

    public WithdrawTransaction(String tokenAddress, BigInteger amount, PaymasterParams paymasterParams) {
        this.tokenAddress = tokenAddress;
        this.amount = amount;
        this.paymasterParams = paymasterParams;
    }
}
