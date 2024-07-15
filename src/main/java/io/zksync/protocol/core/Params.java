package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Params {
    private String recursionNodeLevelVkHash;
    private String recursionLeafLevelVkHash;
    private String recursionCircuitsSetVksHash;
}
