package io.zksync.wrappers;

import java.math.BigInteger;

public enum PriorityOpTree {
  Full(0),
  Rollup(1);

  private final long type;

  PriorityOpTree(long type) {
    this.type = type;
  }

  public BigInteger getValue() {
    return BigInteger.valueOf(type);
  }
}
