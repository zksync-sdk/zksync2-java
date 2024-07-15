package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;
@AllArgsConstructor
@Getter
public class TransactionWithDetailedOutput {
    /** Transaction hash. */
    private String transactionHash;
    /** Storage slots. */
    private List<StorageLog> storageLogs;
    /** Generated events. */
    private List<Event> events;
}

