package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BridgeAddresses {
    private String l1Erc20DefaultBridge;
    private String l2Erc20DefaultBridge;
    private String l1WethBridge;
    private String l2WethBridge;
    private String l1SharedDefaultBridge;
    private String l2SharedDefaultBridge;
}
