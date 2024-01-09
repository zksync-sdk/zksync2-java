package io.zksync.protocol.core.debug;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VmExecutionStep {
  private String contractAddress;
  private Long memoryPageIndex;
  private Long childMemoryIndex;
  private Short pc;
  private List<String> setFlags;
  private List<BigInteger> registers;
  private Map<Byte, MemoryDirection> registerInteractions;
  private Short sp;
  private List<MemoryInteraction> memoryInteractions;
  private String error;
}
