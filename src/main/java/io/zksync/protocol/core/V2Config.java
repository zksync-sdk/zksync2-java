package io.zksync.protocol.core;

import java.math.BigInteger;

public class V2Config {
    /**
     * Settings related to transaction fee computation.
     */
    private Config config;
    /**
     * Current L1 gas price.
     */
    private BigInteger l1GasPrice;
    /**
     * Price of storing public data on L1.
     */
    private BigInteger l1PubdataPrice;
}

