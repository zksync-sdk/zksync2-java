package io.zksync.crypto.eip712;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.web3j.abi.datatypes.Type;

@AllArgsConstructor
public class Eip712Struct implements Type<Structurable>, Comparable<Eip712Struct> {

  private Structurable structure;

  @Override
  public Structurable getValue() {
    return structure;
  }

  @Override
  public String getTypeAsString() {
    return structure.getTypeName();
  }

  @Override
  public int compareTo(Eip712Struct o) {
    return this.getTypeAsString().compareTo(o.getTypeAsString());
  }

  public String encodeType() {
    return structure.getTypeName()
        + "("
        + structure.eip712types().stream()
            .map((entry) -> entry.getValue().getTypeAsString() + " " + entry.getKey())
            .collect(Collectors.joining(","))
        + ")";
  }
}
