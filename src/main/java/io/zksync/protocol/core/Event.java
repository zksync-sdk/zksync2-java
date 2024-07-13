package io.zksync.protocol.core;

import java.math.BigInteger;
import java.util.List;

public class Event {
    private String address;
    private List<String> topics;
    private String data;
    private String blockHash;
    private BigInteger blockNumber;
    private BigInteger l1BatchNumber;
    private String transactionHash;
    private BigInteger transactionIndex;
    private BigInteger logIndex;
    private BigInteger transactionLogIndex;
    private String logType;
    private boolean removed;
}
