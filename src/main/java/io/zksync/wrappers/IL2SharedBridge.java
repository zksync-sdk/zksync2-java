package io.zksync.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
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
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class IL2SharedBridge extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_FINALIZEDEPOSIT = "finalizeDeposit";

    public static final String FUNC_L1BRIDGE = "l1Bridge";

    public static final String FUNC_L1SHAREDBRIDGE = "l1SharedBridge";

    public static final String FUNC_L1TOKENADDRESS = "l1TokenAddress";

    public static final String FUNC_L2TOKENADDRESS = "l2TokenAddress";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event FINALIZEDEPOSIT_EVENT = new Event("FinalizeDeposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWALINITIATED_EVENT = new Event("WithdrawalInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected IL2SharedBridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IL2SharedBridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IL2SharedBridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IL2SharedBridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<FinalizeDepositEventResponse> getFinalizeDepositEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FINALIZEDEPOSIT_EVENT, transactionReceipt);
        ArrayList<FinalizeDepositEventResponse> responses = new ArrayList<FinalizeDepositEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            FinalizeDepositEventResponse typedResponse = new FinalizeDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.l1Sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.l2Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.l2Token = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FinalizeDepositEventResponse getFinalizeDepositEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FINALIZEDEPOSIT_EVENT, log);
        FinalizeDepositEventResponse typedResponse = new FinalizeDepositEventResponse();
        typedResponse.log = log;
        typedResponse.l1Sender = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.l2Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.l2Token = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<FinalizeDepositEventResponse> finalizeDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFinalizeDepositEventFromLog(log));
    }

    public Flowable<FinalizeDepositEventResponse> finalizeDepositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FINALIZEDEPOSIT_EVENT));
        return finalizeDepositEventFlowable(filter);
    }

    public static List<WithdrawalInitiatedEventResponse> getWithdrawalInitiatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAWALINITIATED_EVENT, transactionReceipt);
        ArrayList<WithdrawalInitiatedEventResponse> responses = new ArrayList<WithdrawalInitiatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WithdrawalInitiatedEventResponse typedResponse = new WithdrawalInitiatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.l2Sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.l1Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.l2Token = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WithdrawalInitiatedEventResponse getWithdrawalInitiatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WITHDRAWALINITIATED_EVENT, log);
        WithdrawalInitiatedEventResponse typedResponse = new WithdrawalInitiatedEventResponse();
        typedResponse.log = log;
        typedResponse.l2Sender = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.l1Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.l2Token = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<WithdrawalInitiatedEventResponse> withdrawalInitiatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getWithdrawalInitiatedEventFromLog(log));
    }

    public Flowable<WithdrawalInitiatedEventResponse> withdrawalInitiatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWALINITIATED_EVENT));
        return withdrawalInitiatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeDeposit(String _l1Sender, String _l2Receiver, String _l1Token, BigInteger _amount, byte[] _data) {
        final Function function = new Function(
                FUNC_FINALIZEDEPOSIT, 
                Arrays.<Type>asList(new Address(160, _l1Sender),
                new Address(160, _l2Receiver),
                new Address(160, _l1Token),
                new Uint256(_amount),
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> l1Bridge() {
        final Function function = new Function(FUNC_L1BRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l1SharedBridge() {
        final Function function = new Function(FUNC_L1SHAREDBRIDGE, 
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

    public RemoteFunctionCall<TransactionReceipt> withdraw(String _l1Receiver, String _l2Token, BigInteger _amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new Address(160, _l1Receiver),
                new Address(160, _l2Token),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IL2SharedBridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL2SharedBridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IL2SharedBridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL2SharedBridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IL2SharedBridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IL2SharedBridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IL2SharedBridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IL2SharedBridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class FinalizeDepositEventResponse extends BaseEventResponse {
        public String l1Sender;

        public String l2Receiver;

        public String l2Token;

        public BigInteger amount;
    }

    public static class WithdrawalInitiatedEventResponse extends BaseEventResponse {
        public String l2Sender;

        public String l1Receiver;

        public String l2Token;

        public BigInteger amount;
    }
}
