package io.zksync.protocol.core;

import org.web3j.abi.datatypes.Uint;

import java.util.Date;

public class BlockDetails {
    private String commitTxHash;
    private Date committedAt;
    private String executeTxHash;
    private Date executedAt;
    private Uint l1TxCount;
    private Uint l2TxCount;
    private Uint number;
    private String proveTxHash;
    private Date provenAt;
    private String rootHash;
    private String status;
    private Uint timestamp;
}
