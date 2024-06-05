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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class IL1ERC20Bridge extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_CLAIMFAILEDDEPOSIT = "claimFailedDeposit";

    public static final String FUNC_deposit = "deposit";

    public static final String FUNC_DEPOSITAMOUNT = "depositAmount";

    public static final String FUNC_FINALIZEWITHDRAWAL = "finalizeWithdrawal";

    public static final String FUNC_ISWITHDRAWALFINALIZED = "isWithdrawalFinalized";

    public static final String FUNC_L2BRIDGE = "l2Bridge";

    public static final String FUNC_L2TOKENADDRESS = "l2TokenAddress";

    public static final String FUNC_L2TOKENBEACON = "l2TokenBeacon";

    public static final String FUNC_SHAREDBRIDGE = "sharedBridge";

    public static final String FUNC_TRANSFERTOKENTOSHAREDBRIDGE = "transferTokenToSharedBridge";

    public static final Event CLAIMEDFAILEDDEPOSIT_EVENT = new Event("ClaimedFailedDeposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEPOSITINITIATED_EVENT = new Event("DepositInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWALFINALIZED_EVENT = new Event("WithdrawalFinalized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected IL1ERC20Bridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IL1ERC20Bridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IL1ERC20Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IL1ERC20Bridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ClaimedFailedDepositEventResponse> getClaimedFailedDepositEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMEDFAILEDDEPOSIT_EVENT, transactionReceipt);
        ArrayList<ClaimedFailedDepositEventResponse> responses = new ArrayList<ClaimedFailedDepositEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClaimedFailedDepositEventResponse typedResponse = new ClaimedFailedDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ClaimedFailedDepositEventResponse getClaimedFailedDepositEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMEDFAILEDDEPOSIT_EVENT, log);
        ClaimedFailedDepositEventResponse typedResponse = new ClaimedFailedDepositEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ClaimedFailedDepositEventResponse> claimedFailedDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimedFailedDepositEventFromLog(log));
    }

    public Flowable<ClaimedFailedDepositEventResponse> claimedFailedDepositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMEDFAILEDDEPOSIT_EVENT));
        return claimedFailedDepositEventFlowable(filter);
    }

    public static List<DepositInitiatedEventResponse> getDepositInitiatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DEPOSITINITIATED_EVENT, transactionReceipt);
        ArrayList<DepositInitiatedEventResponse> responses = new ArrayList<DepositInitiatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DepositInitiatedEventResponse typedResponse = new DepositInitiatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.l2DepositTxHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DepositInitiatedEventResponse getDepositInitiatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DEPOSITINITIATED_EVENT, log);
        DepositInitiatedEventResponse typedResponse = new DepositInitiatedEventResponse();
        typedResponse.log = log;
        typedResponse.l2DepositTxHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DepositInitiatedEventResponse> depositInitiatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDepositInitiatedEventFromLog(log));
    }

    public Flowable<DepositInitiatedEventResponse> depositInitiatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSITINITIATED_EVENT));
        return depositInitiatedEventFlowable(filter);
    }

    public static List<WithdrawalFinalizedEventResponse> getWithdrawalFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAWALFINALIZED_EVENT, transactionReceipt);
        ArrayList<WithdrawalFinalizedEventResponse> responses = new ArrayList<WithdrawalFinalizedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WithdrawalFinalizedEventResponse typedResponse = new WithdrawalFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WithdrawalFinalizedEventResponse getWithdrawalFinalizedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WITHDRAWALFINALIZED_EVENT, log);
        WithdrawalFinalizedEventResponse typedResponse = new WithdrawalFinalizedEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.l1Token = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<WithdrawalFinalizedEventResponse> withdrawalFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getWithdrawalFinalizedEventFromLog(log));
    }

    public Flowable<WithdrawalFinalizedEventResponse> withdrawalFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWALFINALIZED_EVENT));
        return withdrawalFinalizedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> claimFailedDeposit(String _depositSender, String _l1Token, byte[] _l2TxHash, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_CLAIMFAILEDDEPOSIT, 
                Arrays.<Type>asList(new Address(160, _depositSender),
                new Address(160, _l1Token),
                new Bytes32(_l2TxHash),
                new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex),
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new org.web3j.abi.datatypes.DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(String _l2Receiver, String _l1Token, BigInteger _amount, BigInteger _l2TxGasLimit, BigInteger _l2TxGasPerPubdataByte, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_deposit, 
                Arrays.<Type>asList(new Address(160, _l2Receiver),
                new Address(160, _l1Token),
                new Uint256(_amount),
                new Uint256(_l2TxGasLimit),
                new Uint256(_l2TxGasPerPubdataByte)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(String _l2Receiver, String _l1Token, BigInteger _amount, BigInteger _l2TxGasLimit, BigInteger _l2TxGasPerPubdataByte, String _refundRecipient, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_deposit, 
                Arrays.<Type>asList(new Address(160, _l2Receiver),
                new Address(160, _l1Token),
                new Uint256(_amount),
                new Uint256(_l2TxGasLimit),
                new Uint256(_l2TxGasPerPubdataByte),
                new Address(160, _refundRecipient)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> depositAmount(String _account, String _l1Token, byte[] _depositL2TxHash) {
        final Function function = new Function(
                FUNC_DEPOSITAMOUNT, 
                Arrays.<Type>asList(new Address(160, _account),
                new Address(160, _l1Token),
                new Bytes32(_depositL2TxHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeWithdrawal(BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, byte[] _message, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_FINALIZEWITHDRAWAL, 
                Arrays.<Type>asList(new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex),
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isWithdrawalFinalized(BigInteger _l2BatchNumber, BigInteger _l2MessageIndex) {
        final Function function = new Function(FUNC_ISWITHDRAWALFINALIZED, 
                Arrays.<Type>asList(new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> l2Bridge() {
        final Function function = new Function(FUNC_L2BRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l2TokenAddress(String _l1Token) {
        final Function function = new Function(FUNC_L2TOKENADDRESS, 
                Arrays.<Type>asList(new Address(160, _l1Token)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l2TokenBeacon() {
        final Function function = new Function(FUNC_L2TOKENBEACON, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> sharedBridge() {
        final Function function = new Function(FUNC_SHAREDBRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferTokenToSharedBridge(String _token, BigInteger _amount) {
        final Function function = new Function(
                FUNC_TRANSFERTOKENTOSHAREDBRIDGE, 
                Arrays.<Type>asList(new Address(160, _token),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IL1ERC20Bridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1ERC20Bridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IL1ERC20Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1ERC20Bridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IL1ERC20Bridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IL1ERC20Bridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IL1ERC20Bridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IL1ERC20Bridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class ClaimedFailedDepositEventResponse extends BaseEventResponse {
        public String to;

        public String l1Token;

        public BigInteger amount;
    }

    public static class DepositInitiatedEventResponse extends BaseEventResponse {
        public byte[] l2DepositTxHash;

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
