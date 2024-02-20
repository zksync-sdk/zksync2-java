package io.zksync.transaction.type;

import io.zksync.methods.request.PaymasterParams;
import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
public class TransferTransaction {
    public String to;
    public BigInteger amount;
    public String from;
    public String tokenAddress;
    public BigInteger gasPerPubData;
    public PaymasterParams paymasterParams;
    public TransactionOptions options;

    public TransferTransaction(String to, BigInteger amount, String from) {
        this.to = to;
        this.amount = amount;
        this.from = from;
    }

    public TransferTransaction(String to, BigInteger amount, String from, String tokenAddress, PaymasterParams paymasterParams) {
        this.to = to;
        this.amount = amount;
        this.from = from;
        this.tokenAddress = tokenAddress;
        this.paymasterParams = paymasterParams;
    }

    public TransferTransaction(String to, BigInteger amount, String from, String tokenAddress) {
        this.to = to;
        this.amount = amount;
        this.from = from;
        this.tokenAddress = tokenAddress;
    }

    public TransferTransaction(String to, BigInteger amount, String from, PaymasterParams paymasterParams) {
        this.to = to;
        this.amount = amount;
        this.from = from;
        this.paymasterParams = paymasterParams;
    }

    public TransferTransaction(String to, BigInteger amount, PaymasterParams paymasterParams) {
        this.to = to;
        this.amount = amount;
        this.paymasterParams = paymasterParams;
    }

    public TransferTransaction(String to, BigInteger amount) {
        this.to = to;
        this.amount = amount;
    }
}
