package io.zksync.protocol.core.debug;

import java.util.List;
import java.util.Map;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VmDebugTrace {
  private List<VmExecutionStep> steps;
  private Map<String, ContractSourceDebugInfo> sources;
}
