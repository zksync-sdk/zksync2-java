package io.zksync.protocol.core;

public enum ZkSyncNetwork {
    Mainnet,
    Localhost,
    Unknown;

    public long getChainId() {
        switch (this) {
            case Mainnet: return 1;
            case Localhost: return 42;
            default: throw new IllegalArgumentException("Unknown chain ID");
        }
    }
}
