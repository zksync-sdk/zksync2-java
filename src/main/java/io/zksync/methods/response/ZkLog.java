package io.zksync.methods.response;

import org.web3j.protocol.core.methods.response.Log;

import java.util.List;
import java.util.Objects;

public class ZkLog extends Log {
    private String l1BatchNumber;
    private String transactionLogIndex;

    public String getL1BatchNumber() {
        return l1BatchNumber;
    }

    public void setL1BatchNumber(String l1BatchNumber) {
        this.l1BatchNumber = l1BatchNumber;
    }

    public ZkLog(String l1BatchNumber) {
        this.l1BatchNumber = l1BatchNumber;
    }

    public ZkLog(boolean removed, String logIndex, String transactionIndex, String transactionHash, String blockHash, String blockNumber, String address, String data, String type, List<String> topics, String l1BatchNumber) {
        super(removed, logIndex, transactionIndex, transactionHash, blockHash, blockNumber, address, data, type, topics);
        this.l1BatchNumber = l1BatchNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZkLog)) return false;
        if (!super.equals(o)) return false;
        ZkLog zkLog = (ZkLog) o;
        return l1BatchNumber.equals(zkLog.l1BatchNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), l1BatchNumber);
    }
}
