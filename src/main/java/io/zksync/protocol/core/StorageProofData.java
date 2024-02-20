package io.zksync.protocol.core;

import java.math.BigInteger;

public class StorageProofData {
    String key;
    String value;
    BigInteger index;
    String[] proof;
}
