package io.zksync.methods.response;

import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class ZkTransactionReceipt extends TransactionReceipt {

    private String l1BatchNumber;
    private String l1BatchTxIndex;

    public ZkTransactionReceipt() {
    }
    public ZkTransactionReceipt(String transactionHash, String transactionIndex, String blockHash, String blockNumber, String cumulativeGasUsed, String gasUsed, String contractAddress, String root, String status, String from, String to, List<Log> logs, String logsBloom, String revertReason, String type, String effectiveGasPrice, String l1BatchNumber, String l1BatchTxIndex) {
        super(transactionHash, transactionIndex, blockHash, blockNumber, cumulativeGasUsed, gasUsed, contractAddress, root, status, from, to, logs, logsBloom, revertReason, type, effectiveGasPrice);
        this.l1BatchNumber = l1BatchNumber;
        this.l1BatchTxIndex = l1BatchTxIndex;
    }

    public String getL1BatchNumberRaw() {
        return l1BatchNumber;
    }

    public BigInteger getL1BatchNumber() {
        return Numeric.decodeQuantity(l1BatchNumber);
    }


    public void setL1BatchNumber(String l1BatchNumber) {
        this.l1BatchNumber = l1BatchNumber;
    }

    public String getL1BatchTxIndexRaw() {
        return l1BatchTxIndex;
    }

    public BigInteger getL1BatchTxIndex() {
        return Numeric.decodeQuantity(l1BatchTxIndex);
    }

    public void setL1BatchTxIndex(String l1BatchTxIndex) {
        this.l1BatchTxIndex = l1BatchTxIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZkTransactionReceipt)) return false;
        if (!super.equals(o)) return false;
        ZkTransactionReceipt that = (ZkTransactionReceipt) o;
        return l1BatchNumber.equals(that.l1BatchNumber) && l1BatchTxIndex.equals(that.l1BatchTxIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), l1BatchNumber, l1BatchTxIndex);
    }

    @Override
    public String toString() {
        return "ZkTransactionReceipt{" +
                "l1BatchNumber='" + l1BatchNumber + '\'' +
                ", l1BatchTxIndex='" + l1BatchTxIndex + '\'' +
                "} " + super.toString();
    }
}
