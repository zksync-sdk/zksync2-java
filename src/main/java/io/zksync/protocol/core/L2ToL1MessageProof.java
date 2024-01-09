package io.zksync.protocol.core;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class L2ToL1MessageProof {
  private List<String> proof;
  private Integer id;
  private String root;
}
