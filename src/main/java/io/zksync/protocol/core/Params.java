package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Params {
    private String recursionNodeLevelVkHash;
    private String recursionLeafLevelVkHash;
    private String recursionCircuitsSetVksHash;
}
