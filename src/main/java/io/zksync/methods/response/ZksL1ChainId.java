package io.zksync.methods.response;

import java.math.BigInteger;
import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

public class ZksL1ChainId extends Response<String> {
  public BigInteger getChainId() {
    return Numeric.decodeQuantity(getResult());
  }
}
