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
}
