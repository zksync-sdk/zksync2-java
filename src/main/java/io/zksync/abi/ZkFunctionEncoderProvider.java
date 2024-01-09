package io.zksync.abi;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.spi.FunctionEncoderProvider;

public class ZkFunctionEncoderProvider implements FunctionEncoderProvider {

  @Override
  public FunctionEncoder get() {
    return new ZkFunctionEncoder();
  }
}
