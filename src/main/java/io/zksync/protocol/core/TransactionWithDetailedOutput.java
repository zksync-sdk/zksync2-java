package io.zksync.protocol.core;

import java.math.BigInteger;
import java.util.List;

public class TransactionWithDetailedOutput {
    /** Transaction hash. */
    private String transactionHash;
    /** Storage slots. */
    private List<StorageLog> storageLogs;
    /** Generated events. */
    private List<Event> events;
}

