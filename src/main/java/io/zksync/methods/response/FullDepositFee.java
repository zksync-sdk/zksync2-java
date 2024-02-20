package io.zksync.methods.response;

import java.math.BigInteger;

public class FullDepositFee {
    public BigInteger maxFeePerGas;
    public BigInteger maxPriorityFeePerGas;
    public BigInteger gasPrice;
    public BigInteger baseCost;
    public BigInteger l1GasLimit;
    public BigInteger l2GasLimit;
}
