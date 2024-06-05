package io.zksync.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
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
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class IPaymasterFlow extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_APPROVALBASED = "approvalBased";

    public static final String FUNC_GENERAL = "general";

    @Deprecated
    protected IPaymasterFlow(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IPaymasterFlow(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IPaymasterFlow(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IPaymasterFlow(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> approvalBased(String _token, BigInteger _minAllowance, byte[] _innerInput) {
        final Function function = new Function(
                FUNC_APPROVALBASED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_minAllowance), 
                new org.web3j.abi.datatypes.DynamicBytes(_innerInput)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> general(byte[] input) {
        final Function function = new Function(
                FUNC_GENERAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(input)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IPaymasterFlow load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IPaymasterFlow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IPaymasterFlow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IPaymasterFlow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IPaymasterFlow load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IPaymasterFlow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IPaymasterFlow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IPaymasterFlow(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
