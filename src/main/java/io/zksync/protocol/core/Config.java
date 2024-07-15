package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
@AllArgsConstructor
@Getter
public class Config {
    /**
     * Minimal gas price on L2.
     */
    private BigInteger minimalL2GasPrice;
    /**
     * Compute overhead part in fee calculation.
     */
    private BigInteger computeOverheadPart;
    /**
     * Public data overhead part in fee calculation.
     */
    private BigInteger pubdataOverheadPart;
    /**
     * Overhead in L1 gas for a batch of transactions.
     */
    private BigInteger batchOverheadL1Gas;
    /**
     * Maximum gas allowed per batch.
     */
    private BigInteger maxGasPerBatch;
    /**
     * Maximum amount of public data allowed per batch.
     */
    private BigInteger maxPubdataPerBatch;
}
