package io.zksync.methods.response;

import java.util.Optional;
import org.web3j.protocol.core.Response;

public class ZksGetTransactionReceipt extends Response<ZkTransactionReceipt> {

  public Optional<ZkTransactionReceipt> getTransactionReceipt() {
    return Optional.ofNullable(getResult());
  }
}
