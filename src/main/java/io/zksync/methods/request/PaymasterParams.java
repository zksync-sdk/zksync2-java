package io.zksync.methods.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.zksync.utils.ByteArraySerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymasterParams {
  private String paymaster;
  private byte[] paymasterInput;

  public String getPaymaster() {
    return paymaster;
  }

  @JsonSerialize(using = ByteArraySerializer.class)
  public byte[] getPaymasterInput() {
    return paymasterInput;
  }
}
