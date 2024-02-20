package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint64;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchDetails {
    private BaseSystemContractsHashes  baseSystemContractsHashes;
    private String commitTxHash;
    private Date committedAt;
    private String executeTxHash;
    private Date executedAt;
    private Uint64 l1GasPrice;
    private Uint l1TxCount;
    private Uint l2FairGasPrice;
    private Uint l2TxCount;
    private Uint number;
    private String proveTxHash;
    private Date provenAt;
    private String rootHash;
    private String status;
    private Uint timestamp;
}
