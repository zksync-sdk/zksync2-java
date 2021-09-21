package io.zksync.protocol.core;

public enum ZkSyncNetwork {
    Mainnnet,
    Localhost,
    Unknown;

    public byte getChainId() {
        switch (this) {
            case Mainnnet: return 1;
            case Localhost: return 42;
            default: throw new IllegalArgumentException("Unknown chain ID");
        }
    }
}
