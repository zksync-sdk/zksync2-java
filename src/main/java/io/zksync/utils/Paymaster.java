package io.zksync.utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;

public class Paymaster {
  private static final String GENERAL_FUNCTION = "general";
  private static final String APPROVAL_BASED_FUNCTION = "approvalBased";

  public static Function encodeGeneral(byte[] input) {
    return new Function(
        GENERAL_FUNCTION,
        Collections.singletonList(new DynamicBytes(input)),
        Collections.emptyList());
  }

  public static Function encodeApprovalBased(
      String tokenAddress, BigInteger minimalAllowance, byte[] input) {
    return new Function(
        APPROVAL_BASED_FUNCTION,
        Arrays.asList(
            new Address(tokenAddress), new Uint256(minimalAllowance), new DynamicBytes(input)),
        Collections.emptyList());
  }
}
