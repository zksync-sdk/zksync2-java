package io.zksync.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransactionStatus {
  @JsonProperty("pending")
  PENDING,
  @JsonProperty("included")
  INCLUDED,
  @JsonProperty("verified")
  VERIFIED,
  @JsonProperty("failed")
  FAILED
}
