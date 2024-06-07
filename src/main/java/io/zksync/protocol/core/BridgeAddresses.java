package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BridgeAddresses {
    public String l1Erc20DefaultBridge;
    public String l2Erc20DefaultBridge;
    public String l1WethBridge;
    public String l2WethBridge;
    public String l1SharedDefaultBridge;
    public String l2SharedDefaultBridge;
}
