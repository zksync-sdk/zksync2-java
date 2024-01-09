package io.zksync.wrappers;

import java.math.BigInteger;

public enum PriorityQueueType {
  Deque(0),
  HeapBuffer(1),
  Heap(2);

  private final long type;

  PriorityQueueType(long type) {
    this.type = type;
  }

  public BigInteger getValue() {
    return BigInteger.valueOf(type);
  }
}
