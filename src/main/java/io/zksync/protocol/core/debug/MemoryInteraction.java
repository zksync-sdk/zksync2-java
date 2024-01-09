package io.zksync.protocol.core.debug;

import java.math.BigInteger;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemoryInteraction {
  private String memoryType;
  private Long page;
  private Short address;
  private BigInteger value;
  private MemoryDirection direction;
}
