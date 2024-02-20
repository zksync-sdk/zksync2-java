package io.zksync.transaction.type;

import io.zksync.methods.request.Eip712Meta;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class RequestExecuteTransaction{
    private BigInteger l2GasLimit;
    private String contractAddress;
    private byte[] calldata;
    private BigInteger l2Value;
    private byte[][] factoryDeps;
    private BigInteger operatorTip;
    private BigInteger gasPerPubDataByte;
    private String refoundRecepient;
    private TransactionOptions options;

    public RequestExecuteTransaction(BigInteger l2GasLimit, String contractAddress, byte[] calldata, BigInteger l2Value, byte[][] factoryDeps, BigInteger operatorTip, BigInteger gasPerPubDataByte, String refoundRecepient, TransactionOptions transactionOptions) {
        this.l2GasLimit = l2GasLimit;
        this.contractAddress = contractAddress;
        this.calldata = calldata;
        this.l2Value = l2Value;
        this.factoryDeps = factoryDeps;
        this.operatorTip = operatorTip;
        this.gasPerPubDataByte = gasPerPubDataByte;
        this.refoundRecepient = refoundRecepient;
        this.options = transactionOptions;
    }
    public Transaction toTx(String from){
        return new Transaction(from, options.getNonce(), options.getGasPrice(), BigInteger.ZERO, contractAddress, l2Value, Numeric.toHexString(calldata), options.getChainId().longValue(), options.getMaxPriorityFeePerGas(), options.getMaxFeePerGas());
    }
    public BigInteger getL2GasLimit() {
        return l2GasLimit;
    }

    public void setL2GasLimit(BigInteger l2GasLimit) {
        this.l2GasLimit = l2GasLimit;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public BigInteger getL2Value() {
        return l2Value;
    }

    public void setL2Value(BigInteger l2Value) {
        this.l2Value = l2Value;
    }

    public byte[][] getFactoryDeps() {
        return factoryDeps;
    }

    public void setFactoryDeps(byte[][] factoryDeps) {
        this.factoryDeps = factoryDeps;
    }

    public BigInteger getOperatorTip() {
        return operatorTip;
    }

    public void setOperatorTip(BigInteger operatorTip) {
        this.operatorTip = operatorTip;
    }

    public BigInteger getGasPerPubDataByte() {
        return gasPerPubDataByte;
    }

    public void setGasPerPubDataByte(BigInteger gasPerPubDataByte) {
        this.gasPerPubDataByte = gasPerPubDataByte;
    }

    public String getRefoundRecepient() {
        return refoundRecepient;
    }

    public void setRefoundRecepient(String refoundRecepient) {
        this.refoundRecepient = refoundRecepient;
    }
    public void setCalldata(byte[] calldata) {
        this.calldata = calldata;
    }
    public byte[] getCalldata() {
        return this.calldata;
    }
    public TransactionOptions getOptions() {
        return options;
    }

    public void setOptions(TransactionOptions transactionOptions) {
        this.options = transactionOptions;
    }
}
