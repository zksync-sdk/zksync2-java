package io.zksync.wrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
public class INonceHolder extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_GETDEPLOYMENTNONCE = "getDeploymentNonce";

    public static final String FUNC_GETMINNONCE = "getMinNonce";

    public static final String FUNC_GETRAWNONCE = "getRawNonce";

    public static final String FUNC_GETVALUEUNDERNONCE = "getValueUnderNonce";

    public static final String FUNC_INCREASEMINNONCE = "increaseMinNonce";

    public static final String FUNC_INCREMENTDEPLOYMENTNONCE = "incrementDeploymentNonce";

    public static final String FUNC_INCREMENTMINNONCEIFEQUALS = "incrementMinNonceIfEquals";

    public static final String FUNC_ISNONCEUSED = "isNonceUsed";

    public static final String FUNC_SETVALUEUNDERNONCE = "setValueUnderNonce";

    public static final String FUNC_VALIDATENONCEUSAGE = "validateNonceUsage";

    public static final Event VALUESETUNDERNONCE_EVENT = new Event("ValueSetUnderNonce", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected INonceHolder(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected INonceHolder(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected INonceHolder(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected INonceHolder(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ValueSetUnderNonceEventResponse> getValueSetUnderNonceEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALUESETUNDERNONCE_EVENT, transactionReceipt);
        ArrayList<ValueSetUnderNonceEventResponse> responses = new ArrayList<ValueSetUnderNonceEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ValueSetUnderNonceEventResponse typedResponse = new ValueSetUnderNonceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.key = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ValueSetUnderNonceEventResponse> valueSetUnderNonceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ValueSetUnderNonceEventResponse>() {
            @Override
            public ValueSetUnderNonceEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(VALUESETUNDERNONCE_EVENT, log);
                ValueSetUnderNonceEventResponse typedResponse = new ValueSetUnderNonceEventResponse();
                typedResponse.log = log;
                typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.key = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ValueSetUnderNonceEventResponse> valueSetUnderNonceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUESETUNDERNONCE_EVENT));
        return valueSetUnderNonceEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> getDeploymentNonce(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDEPLOYMENTNONCE, 
                Arrays.<Type>asList(new Address(160, _address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getMinNonce(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETMINNONCE, 
                Arrays.<Type>asList(new Address(160, _address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getRawNonce(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRAWNONCE, 
                Arrays.<Type>asList(new Address(160, _address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getValueUnderNonce(BigInteger _key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVALUEUNDERNONCE, 
                Arrays.<Type>asList(new Uint256(_key)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> increaseMinNonce(BigInteger _value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCREASEMINNONCE, 
                Arrays.<Type>asList(new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> incrementDeploymentNonce(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCREMENTDEPLOYMENTNONCE, 
                Arrays.<Type>asList(new Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> incrementMinNonceIfEquals(BigInteger _expectedNonce) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCREMENTMINNONCEIFEQUALS, 
                Arrays.<Type>asList(new Uint256(_expectedNonce)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isNonceUsed(String _address, BigInteger _nonce) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISNONCEUSED, 
                Arrays.<Type>asList(new Address(160, _address),
                new Uint256(_nonce)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setValueUnderNonce(BigInteger _key, BigInteger _value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETVALUEUNDERNONCE, 
                Arrays.<Type>asList(new Uint256(_key),
                new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static INonceHolder load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new INonceHolder(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static INonceHolder load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new INonceHolder(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static INonceHolder load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new INonceHolder(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static INonceHolder load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new INonceHolder(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class ValueSetUnderNonceEventResponse extends BaseEventResponse {
        public String accountAddress;

        public BigInteger key;

        public BigInteger value;
    }
}
