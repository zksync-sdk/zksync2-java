package io.zksync.wrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import org.web3j.protocol.core.RemoteCall;
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
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class IL1Bridge extends Contract {
    public static final String BINARY = "0x";

    public static final String FUNC_CLAIMFAILEDDEPOSIT = "claimFailedDeposit";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_FINALIZEWITHDRAWAL = "finalizeWithdrawal";

    public static final String FUNC_ISWITHDRAWALFINALIZED = "isWithdrawalFinalized";

    public static final String FUNC_L2TOKENADDRESS = "l2TokenAddress";

    public static final Event CLAIMEDFAILEDDEPOSIT_EVENT = new Event("ClaimedFailedDeposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEPOSITINITIATED_EVENT = new Event("DepositInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWALFINALIZED_EVENT = new Event("WithdrawalFinalized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected IL1Bridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IL1Bridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IL1Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IL1Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ClaimedFailedDepositEventResponse> getClaimedFailedDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CLAIMEDFAILEDDEPOSIT_EVENT, transactionReceipt);
        ArrayList<ClaimedFailedDepositEventResponse> responses = new ArrayList<ClaimedFailedDepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ClaimedFailedDepositEventResponse typedResponse = new ClaimedFailedDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ClaimedFailedDepositEventResponse> claimedFailedDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ClaimedFailedDepositEventResponse>() {
            @Override
            public ClaimedFailedDepositEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CLAIMEDFAILEDDEPOSIT_EVENT, log);
                ClaimedFailedDepositEventResponse typedResponse = new ClaimedFailedDepositEventResponse();
                typedResponse.log = log;
                typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ClaimedFailedDepositEventResponse> claimedFailedDepositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMEDFAILEDDEPOSIT_EVENT));
        return claimedFailedDepositEventFlowable(filter);
    }

    public List<DepositInitiatedEventResponse> getDepositInitiatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSITINITIATED_EVENT, transactionReceipt);
        ArrayList<DepositInitiatedEventResponse> responses = new ArrayList<DepositInitiatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositInitiatedEventResponse typedResponse = new DepositInitiatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.l1Token = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositInitiatedEventResponse> depositInitiatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DepositInitiatedEventResponse>() {
            @Override
            public DepositInitiatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSITINITIATED_EVENT, log);
                DepositInitiatedEventResponse typedResponse = new DepositInitiatedEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.l1Token = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositInitiatedEventResponse> depositInitiatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSITINITIATED_EVENT));
        return depositInitiatedEventFlowable(filter);
    }

    public List<WithdrawalFinalizedEventResponse> getWithdrawalFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWALFINALIZED_EVENT, transactionReceipt);
        ArrayList<WithdrawalFinalizedEventResponse> responses = new ArrayList<WithdrawalFinalizedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawalFinalizedEventResponse typedResponse = new WithdrawalFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawalFinalizedEventResponse> withdrawalFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawalFinalizedEventResponse>() {
            @Override
            public WithdrawalFinalizedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWALFINALIZED_EVENT, log);
                WithdrawalFinalizedEventResponse typedResponse = new WithdrawalFinalizedEventResponse();
                typedResponse.log = log;
                typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawalFinalizedEventResponse> withdrawalFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWALFINALIZED_EVENT));
        return withdrawalFinalizedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> claimFailedDeposit(String _depositSender, String _l1Token, byte[] _l2TxHash, BigInteger _l2BlockNumber, BigInteger _l2MessageIndex, List<byte[]> _merkleProof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMFAILEDDEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_depositSender), 
                new org.web3j.abi.datatypes.Address(_l1Token), 
                new org.web3j.abi.datatypes.generated.Bytes32(_l2TxHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(String _l2Receiver, String _l1Token, BigInteger _amount, BigInteger _amountValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l2Receiver), 
                new org.web3j.abi.datatypes.Address(_l1Token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, _amountValue);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeWithdrawal(BigInteger _l2BlockNumber, BigInteger _l2MessageIndex, byte[] _message, List<byte[]> _merkleProof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FINALIZEWITHDRAWAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isWithdrawalFinalized(BigInteger _l2BlockNumber, BigInteger _l2MessageIndex) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISWITHDRAWALFINALIZED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> l2TokenAddress(String _l1Token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_L2TOKENADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_l1Token)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static IL1Bridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1Bridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IL1Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1Bridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IL1Bridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IL1Bridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IL1Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IL1Bridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IL1Bridge> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IL1Bridge.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IL1Bridge> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IL1Bridge.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<IL1Bridge> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IL1Bridge.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<IL1Bridge> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IL1Bridge.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class ClaimedFailedDepositEventResponse extends BaseEventResponse {
        public String to;

        public String l1Token;

        public BigInteger amount;
    }

    public static class DepositInitiatedEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public String l1Token;

        public BigInteger amount;
    }

    public static class WithdrawalFinalizedEventResponse extends BaseEventResponse {
        public String to;

        public String l1Token;

        public BigInteger amount;
    }
}
