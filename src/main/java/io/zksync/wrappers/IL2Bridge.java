package io.zksync.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class IL2Bridge extends Contract {
    public static final String BINARY = "0x";

    public static final String FUNC_FINALIZEDEPOSIT = "finalizeDeposit";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_L1BRIDGE = "l1Bridge";

    public static final String FUNC_L1TOKENADDRESS = "l1TokenAddress";

    public static final String FUNC_L2TOKENADDRESS = "l2TokenAddress";

    public static final String FUNC_WITHDRAW = "withdraw";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected IL2Bridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IL2Bridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IL2Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IL2Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeDeposit(String _l1Sender, String _l2Receiver, String _l1Token, BigInteger _amount, byte[] _data) {
        final Function function = new Function(
                FUNC_FINALIZEDEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l1Sender), 
                new org.web3j.abi.datatypes.Address(_l2Receiver), 
                new org.web3j.abi.datatypes.Address(_l1Token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(String _l1Bridge, byte[] _l2TokenProxyBytecodeHash, String _governor) {
        final Function function = new Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l1Bridge), 
                new org.web3j.abi.datatypes.generated.Bytes32(_l2TokenProxyBytecodeHash), 
                new org.web3j.abi.datatypes.Address(_governor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> l1Bridge() {
        final Function function = new Function(FUNC_L1BRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l1TokenAddress(String _l2Token) {
        final Function function = new Function(FUNC_L1TOKENADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l2Token)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l2TokenAddress(String _l1Token) {
        final Function function = new Function(FUNC_L2TOKENADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l1Token)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String _l1Receiver, String _l2Token, BigInteger _amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l1Receiver), 
                new org.web3j.abi.datatypes.Address(_l2Token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IL2Bridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL2Bridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IL2Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL2Bridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IL2Bridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IL2Bridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IL2Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IL2Bridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IL2Bridge> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IL2Bridge.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IL2Bridge> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IL2Bridge.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IL2Bridge> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IL2Bridge.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IL2Bridge> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IL2Bridge.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
