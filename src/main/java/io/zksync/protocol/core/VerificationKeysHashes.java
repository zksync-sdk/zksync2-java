package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerificationKeysHashes {
    private Params params;
    private String recursionSchedulerLevelVkHash;
}

