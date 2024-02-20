package io.zksync.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
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
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_FINALIZEDEPOSIT = "finalizeDeposit";

    public static final String FUNC_L1BRIDGE = "l1Bridge";

    public static final String FUNC_L1TOKENADDRESS = "l1TokenAddress";

    public static final String FUNC_L2TOKENADDRESS = "l2TokenAddress";

    public static final String FUNC_WITHDRAW = "withdraw";

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

    public RemoteFunctionCall<TransactionReceipt> finalizeDeposit(String _l1Sender, String _l2Receiver, String _l1Token, BigInteger _amount, byte[] _data, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_FINALIZEDEPOSIT, 
                Arrays.<Type>asList(new Address(160, _l1Sender),
                new Address(160, _l2Receiver),
                new Address(160, _l1Token),
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> l1Bridge() {
        final Function function = new Function(FUNC_L1BRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l1TokenAddress(String _l2Token) {
        final Function function = new Function(FUNC_L1TOKENADDRESS, 
                Arrays.<Type>asList(new Address(160, _l2Token)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l2TokenAddress(String _l1Token) {
        final Function function = new Function(FUNC_L2TOKENADDRESS, 
                Arrays.<Type>asList(new Address(160, _l1Token)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public String encodeWithdraw(String _l1Receiver, String _l2Token, BigInteger _amount) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new Address(160, _l1Receiver),
                        new Address(160, _l2Token),
                        new org.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String _l1Receiver, String _l2Token, BigInteger _amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new Address(160, _l1Receiver),
                new Address(160, _l2Token),
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
}
