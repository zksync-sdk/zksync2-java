package io.zksync.protocol.core.debug;

import java.util.Map;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractSourceDebugInfo {
  private String assemblyCode;
  private Map<Long, Long> pcLineMapping;
}
