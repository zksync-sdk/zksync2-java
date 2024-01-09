package io.zksync.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
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
 * <p>Generated with web3j version 4.7.0.
 */
@SuppressWarnings("rawtypes")
public class NonceHolder extends Contract {
  public static final String BINARY = "Bin file was not provided";

  public static final String FUNC_GETACCOUNTNONCE = "getAccountNonce";

  public static final String FUNC_GETDEPLOYMENTNONCE = "getDeploymentNonce";

  public static final String FUNC_GETRAWNONCE = "getRawNonce";

  public static final String FUNC_INCREMENTDEPLOYMENTNONCE = "incrementDeploymentNonce";

  public static final String FUNC_INCREMENTNONCE = "incrementNonce";

  public static final String FUNC_INCREMENTNONCEIFEQUALS = "incrementNonceIfEquals";

  @Deprecated
  protected NonceHolder(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected NonceHolder(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected NonceHolder(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected NonceHolder(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public RemoteFunctionCall<BigInteger> getAccountNonce() {
    final Function function =
        new Function(
            FUNC_GETACCOUNTNONCE,
            Arrays.<Type>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<BigInteger> getDeploymentNonce(String _address) {
    final Function function =
        new Function(
            FUNC_GETDEPLOYMENTNONCE,
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<BigInteger> getRawNonce(String _address) {
    final Function function =
        new Function(
            FUNC_GETRAWNONCE,
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<TransactionReceipt> incrementDeploymentNonce(String _address) {
    final Function function =
        new Function(
            FUNC_INCREMENTDEPLOYMENTNONCE,
            Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> incrementNonce() {
    final Function function =
        new Function(
            FUNC_INCREMENTNONCE, Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> incrementNonceIfEquals(BigInteger _expectedNonce) {
    final Function function =
        new Function(
            FUNC_INCREMENTNONCEIFEQUALS,
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_expectedNonce)),
            Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  @Deprecated
  public static NonceHolder load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new NonceHolder(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static NonceHolder load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      BigInteger gasPrice,
      BigInteger gasLimit) {
    return new NonceHolder(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static NonceHolder load(
      String contractAddress,
      Web3j web3j,
      Credentials credentials,
      ContractGasProvider contractGasProvider) {
    return new NonceHolder(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static NonceHolder load(
      String contractAddress,
      Web3j web3j,
      TransactionManager transactionManager,
      ContractGasProvider contractGasProvider) {
    return new NonceHolder(contractAddress, web3j, transactionManager, contractGasProvider);
  }
}
