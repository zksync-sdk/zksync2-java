package io.zksync.transaction.type;

import java.math.BigInteger;

public class TransactionOptions {
    private BigInteger nonce;
    private BigInteger value;
    private BigInteger gasPrice;
    private BigInteger maxFeePerGas;
    private BigInteger maxPriorityFeePerGas;
    private BigInteger gasLimit;
    private BigInteger chainId;

    public TransactionOptions(BigInteger nonce, BigInteger value, BigInteger gasPrice, BigInteger maxFeePerGas, BigInteger maxPriorityFeePerGas, BigInteger gasLimit, BigInteger chainId) {
        this.nonce = nonce;
        this.value = value;
        this.gasPrice = gasPrice;
        this.maxFeePerGas = maxFeePerGas;
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
        this.gasLimit = gasLimit;
        this.chainId = chainId;
    }
    public TransactionOptions(){
        this(null, null, null, null, null, null, null);
    }

    public BigInteger getChainId() {
        return chainId;
    }

    public void setChainId(BigInteger chainId) {
        this.chainId = chainId;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getMaxFeePerGas() {
        return maxFeePerGas;
    }

    public void setMaxFeePerGas(BigInteger maxFeePerGas) {
        this.maxFeePerGas = maxFeePerGas;
    }

    public BigInteger getMaxPriorityFeePerGas() {
        return maxPriorityFeePerGas;
    }

    public void setMaxPriorityFeePerGas(BigInteger maxPriorityFeePerGas) {
        this.maxPriorityFeePerGas = maxPriorityFeePerGas;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }
}
