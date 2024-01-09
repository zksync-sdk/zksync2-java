package io.zksync.wrappers;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * Auto generated code.
 *
 * <p><strong>Do not modify!</strong>
 *
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the <a
 * href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class IL2Messenger extends Contract {
  public static final String BINARY = "0x";

  public static final String FUNC_SENDTOL1 = "sendToL1";

  protected static final HashMap<String, String> _addresses;

  static {
    _addresses = new HashMap<String, String>();
  }

  @Deprecated
  protected IL2Messenger(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected IL2Messenger(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected IL2Messenger(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected IL2Messenger(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteFunctionCall<TransactionReceipt> sendToL1(byte[] _message) {
    final Function function =
        new Function(
            FUNC_SENDTOL1,
            Collections.singletonList(new DynamicBytes(_message)),
            Collections.emptyList());
    return executeRemoteCallTransaction(function);
  }

  public static Function encodeSendToL1(byte[] message) {
    return new Function(
        FUNC_SENDTOL1,
        Collections.singletonList(new DynamicBytes(message)),
        Collections.emptyList());
  }

  @Deprecated
  public static IL2Messenger load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new IL2Messenger(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static IL2Messenger load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new IL2Messenger(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static IL2Messenger load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new IL2Messenger(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static IL2Messenger load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new IL2Messenger(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<IL2Messenger> deploy(
      Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        IL2Messenger.class, web3j, credentials, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<IL2Messenger> deploy(
      Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return deployRemoteCall(IL2Messenger.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
  }

  public static RemoteCall<IL2Messenger> deploy(
      Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return deployRemoteCall(
        IL2Messenger.class, web3j, transactionManager, contractGasProvider, BINARY, "");
  }

  @Deprecated
  public static RemoteCall<IL2Messenger> deploy(
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return deployRemoteCall(
        IL2Messenger.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
  }

  protected String getStaticDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }

  public static String getPreviouslyDeployedAddress(String networkId) {
    return _addresses.get(networkId);
  }
}
