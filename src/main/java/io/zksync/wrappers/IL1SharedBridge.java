package io.zksync.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
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
public class IL1SharedBridge extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_BRIDGEHUB = "bridgehub";

    public static final String FUNC_BRIDGEHUBCONFIRML2TRANSACTION = "bridgehubConfirmL2Transaction";

    public static final String FUNC_BRIDGEHUBDEPOSIT = "bridgehubDeposit";

    public static final String FUNC_BRIDGEHUBDEPOSITBASETOKEN = "bridgehubDepositBaseToken";

    public static final String FUNC_CLAIMFAILEDDEPOSIT = "claimFailedDeposit";

    public static final String FUNC_CLAIMFAILEDDEPOSITLEGACYERC20BRIDGE = "claimFailedDepositLegacyErc20Bridge";

    public static final String FUNC_DEPOSITHAPPENED = "depositHappened";

    public static final String FUNC_DEPOSITLEGACYERC20BRIDGE = "depositLegacyErc20Bridge";

    public static final String FUNC_FINALIZEWITHDRAWAL = "finalizeWithdrawal";

    public static final String FUNC_FINALIZEWITHDRAWALLEGACYERC20BRIDGE = "finalizeWithdrawalLegacyErc20Bridge";

    public static final String FUNC_ISWITHDRAWALFINALIZED = "isWithdrawalFinalized";

    public static final String FUNC_L1WETHADDRESS = "l1WethAddress";

    public static final String FUNC_L2BRIDGEADDRESS = "l2BridgeAddress";

    public static final String FUNC_LEGACYBRIDGE = "legacyBridge";

    public static final String FUNC_RECEIVEETH = "receiveEth";

    public static final String FUNC_SETERAFIRSTPOSTUPGRADEBATCH = "setEraFirstPostUpgradeBatch";

    public static final Event BRIDGEHUBDEPOSITBASETOKENINITIATED_EVENT = new Event("BridgehubDepositBaseTokenInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BRIDGEHUBDEPOSITFINALIZED_EVENT = new Event("BridgehubDepositFinalized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event BRIDGEHUBDEPOSITINITIATED_EVENT = new Event("BridgehubDepositInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CLAIMEDFAILEDDEPOSITSHAREDBRIDGE_EVENT = new Event("ClaimedFailedDepositSharedBridge", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LEGACYDEPOSITINITIATED_EVENT = new Event("LegacyDepositInitiated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWALFINALIZEDSHAREDBRIDGE_EVENT = new Event("WithdrawalFinalizedSharedBridge", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected IL1SharedBridge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IL1SharedBridge(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IL1SharedBridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IL1SharedBridge(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BridgehubDepositBaseTokenInitiatedEventResponse> getBridgehubDepositBaseTokenInitiatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BRIDGEHUBDEPOSITBASETOKENINITIATED_EVENT, transactionReceipt);
        ArrayList<BridgehubDepositBaseTokenInitiatedEventResponse> responses = new ArrayList<BridgehubDepositBaseTokenInitiatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            BridgehubDepositBaseTokenInitiatedEventResponse typedResponse = new BridgehubDepositBaseTokenInitiatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BridgehubDepositBaseTokenInitiatedEventResponse getBridgehubDepositBaseTokenInitiatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BRIDGEHUBDEPOSITBASETOKENINITIATED_EVENT, log);
        BridgehubDepositBaseTokenInitiatedEventResponse typedResponse = new BridgehubDepositBaseTokenInitiatedEventResponse();
        typedResponse.log = log;
        typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<BridgehubDepositBaseTokenInitiatedEventResponse> bridgehubDepositBaseTokenInitiatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBridgehubDepositBaseTokenInitiatedEventFromLog(log));
    }

    public Flowable<BridgehubDepositBaseTokenInitiatedEventResponse> bridgehubDepositBaseTokenInitiatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BRIDGEHUBDEPOSITBASETOKENINITIATED_EVENT));
        return bridgehubDepositBaseTokenInitiatedEventFlowable(filter);
    }

    public static List<BridgehubDepositFinalizedEventResponse> getBridgehubDepositFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BRIDGEHUBDEPOSITFINALIZED_EVENT, transactionReceipt);
        ArrayList<BridgehubDepositFinalizedEventResponse> responses = new ArrayList<BridgehubDepositFinalizedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            BridgehubDepositFinalizedEventResponse typedResponse = new BridgehubDepositFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.txDataHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.l2DepositTxHash = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BridgehubDepositFinalizedEventResponse getBridgehubDepositFinalizedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BRIDGEHUBDEPOSITFINALIZED_EVENT, log);
        BridgehubDepositFinalizedEventResponse typedResponse = new BridgehubDepositFinalizedEventResponse();
        typedResponse.log = log;
        typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.txDataHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.l2DepositTxHash = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<BridgehubDepositFinalizedEventResponse> bridgehubDepositFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBridgehubDepositFinalizedEventFromLog(log));
    }

    public Flowable<BridgehubDepositFinalizedEventResponse> bridgehubDepositFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BRIDGEHUBDEPOSITFINALIZED_EVENT));
        return bridgehubDepositFinalizedEventFlowable(filter);
    }

    public static List<BridgehubDepositInitiatedEventResponse> getBridgehubDepositInitiatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BRIDGEHUBDEPOSITINITIATED_EVENT, transactionReceipt);
        ArrayList<BridgehubDepositInitiatedEventResponse> responses = new ArrayList<BridgehubDepositInitiatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            BridgehubDepositInitiatedEventResponse typedResponse = new BridgehubDepositInitiatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.txDataHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BridgehubDepositInitiatedEventResponse getBridgehubDepositInitiatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BRIDGEHUBDEPOSITINITIATED_EVENT, log);
        BridgehubDepositInitiatedEventResponse typedResponse = new BridgehubDepositInitiatedEventResponse();
        typedResponse.log = log;
        typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.txDataHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.from = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<BridgehubDepositInitiatedEventResponse> bridgehubDepositInitiatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBridgehubDepositInitiatedEventFromLog(log));
    }

    public Flowable<BridgehubDepositInitiatedEventResponse> bridgehubDepositInitiatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BRIDGEHUBDEPOSITINITIATED_EVENT));
        return bridgehubDepositInitiatedEventFlowable(filter);
    }

    public static List<ClaimedFailedDepositSharedBridgeEventResponse> getClaimedFailedDepositSharedBridgeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMEDFAILEDDEPOSITSHAREDBRIDGE_EVENT, transactionReceipt);
        ArrayList<ClaimedFailedDepositSharedBridgeEventResponse> responses = new ArrayList<ClaimedFailedDepositSharedBridgeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ClaimedFailedDepositSharedBridgeEventResponse typedResponse = new ClaimedFailedDepositSharedBridgeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.l1Token = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ClaimedFailedDepositSharedBridgeEventResponse getClaimedFailedDepositSharedBridgeEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMEDFAILEDDEPOSITSHAREDBRIDGE_EVENT, log);
        ClaimedFailedDepositSharedBridgeEventResponse typedResponse = new ClaimedFailedDepositSharedBridgeEventResponse();
        typedResponse.log = log;
        typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.l1Token = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ClaimedFailedDepositSharedBridgeEventResponse> claimedFailedDepositSharedBridgeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimedFailedDepositSharedBridgeEventFromLog(log));
    }

    public Flowable<ClaimedFailedDepositSharedBridgeEventResponse> claimedFailedDepositSharedBridgeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMEDFAILEDDEPOSITSHAREDBRIDGE_EVENT));
        return claimedFailedDepositSharedBridgeEventFlowable(filter);
    }

    public static List<LegacyDepositInitiatedEventResponse> getLegacyDepositInitiatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LEGACYDEPOSITINITIATED_EVENT, transactionReceipt);
        ArrayList<LegacyDepositInitiatedEventResponse> responses = new ArrayList<LegacyDepositInitiatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LegacyDepositInitiatedEventResponse typedResponse = new LegacyDepositInitiatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.l2DepositTxHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static LegacyDepositInitiatedEventResponse getLegacyDepositInitiatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LEGACYDEPOSITINITIATED_EVENT, log);
        LegacyDepositInitiatedEventResponse typedResponse = new LegacyDepositInitiatedEventResponse();
        typedResponse.log = log;
        typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.l2DepositTxHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.from = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.l1Token = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<LegacyDepositInitiatedEventResponse> legacyDepositInitiatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getLegacyDepositInitiatedEventFromLog(log));
    }

    public Flowable<LegacyDepositInitiatedEventResponse> legacyDepositInitiatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LEGACYDEPOSITINITIATED_EVENT));
        return legacyDepositInitiatedEventFlowable(filter);
    }

    public static List<WithdrawalFinalizedSharedBridgeEventResponse> getWithdrawalFinalizedSharedBridgeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAWALFINALIZEDSHAREDBRIDGE_EVENT, transactionReceipt);
        ArrayList<WithdrawalFinalizedSharedBridgeEventResponse> responses = new ArrayList<WithdrawalFinalizedSharedBridgeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            WithdrawalFinalizedSharedBridgeEventResponse typedResponse = new WithdrawalFinalizedSharedBridgeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.l1Token = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static WithdrawalFinalizedSharedBridgeEventResponse getWithdrawalFinalizedSharedBridgeEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(WITHDRAWALFINALIZEDSHAREDBRIDGE_EVENT, log);
        WithdrawalFinalizedSharedBridgeEventResponse typedResponse = new WithdrawalFinalizedSharedBridgeEventResponse();
        typedResponse.log = log;
        typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.l1Token = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<WithdrawalFinalizedSharedBridgeEventResponse> withdrawalFinalizedSharedBridgeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getWithdrawalFinalizedSharedBridgeEventFromLog(log));
    }

    public Flowable<WithdrawalFinalizedSharedBridgeEventResponse> withdrawalFinalizedSharedBridgeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWALFINALIZEDSHAREDBRIDGE_EVENT));
        return withdrawalFinalizedSharedBridgeEventFlowable(filter);
    }

    public RemoteFunctionCall<String> bridgehub() {
        final Function function = new Function(FUNC_BRIDGEHUB, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> bridgehubConfirmL2Transaction(BigInteger _chainId, byte[] _txDataHash, byte[] _txHash) {
        final Function function = new Function(
                FUNC_BRIDGEHUBCONFIRML2TRANSACTION, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Bytes32(_txDataHash),
                new Bytes32(_txHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> bridgehubDeposit(BigInteger _chainId, String _prevMsgSender, BigInteger _l2Value, byte[] _data, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BRIDGEHUBDEPOSIT, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Address(160, _prevMsgSender),
                new Uint256(_l2Value),
                new DynamicBytes(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> bridgehubDepositBaseToken(BigInteger _chainId, String _prevMsgSender, String _l1Token, BigInteger _amount, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BRIDGEHUBDEPOSITBASETOKEN, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Address(160, _prevMsgSender),
                new Address(160, _l1Token),
                new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> claimFailedDeposit(BigInteger _chainId, String _depositSender, String _l1Token, BigInteger _amount, byte[] _l2TxHash, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_CLAIMFAILEDDEPOSIT, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Address(160, _depositSender),
                new Address(160, _l1Token),
                new Uint256(_amount),
                new Bytes32(_l2TxHash),
                new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex),
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> claimFailedDepositLegacyErc20Bridge(String _depositSender, String _l1Token, BigInteger _amount, byte[] _l2TxHash, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_CLAIMFAILEDDEPOSITLEGACYERC20BRIDGE, 
                Arrays.<Type>asList(new Address(160, _depositSender),
                new Address(160, _l1Token),
                new Uint256(_amount),
                new Bytes32(_l2TxHash),
                new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex),
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> depositHappened(BigInteger _chainId, byte[] _l2TxHash) {
        final Function function = new Function(FUNC_DEPOSITHAPPENED, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Bytes32(_l2TxHash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> depositLegacyErc20Bridge(String _msgSender, String _l2Receiver, String _l1Token, BigInteger _amount, BigInteger _l2TxGasLimit, BigInteger _l2TxGasPerPubdataByte, String _refundRecipient, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DEPOSITLEGACYERC20BRIDGE, 
                Arrays.<Type>asList(new Address(160, _msgSender),
                new Address(160, _l2Receiver),
                new Address(160, _l1Token),
                new Uint256(_amount),
                new Uint256(_l2TxGasLimit),
                new Uint256(_l2TxGasPerPubdataByte),
                new Address(160, _refundRecipient)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeWithdrawal(BigInteger _chainId, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, byte[] _message, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_FINALIZEWITHDRAWAL, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex),
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new DynamicBytes(_message),
                new DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeWithdrawalLegacyErc20Bridge(BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, byte[] _message, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_FINALIZEWITHDRAWALLEGACYERC20BRIDGE, 
                Arrays.<Type>asList(new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex),
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new DynamicBytes(_message),
                new DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isWithdrawalFinalized(BigInteger _chainId, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex) {
        final Function function = new Function(FUNC_ISWITHDRAWALFINALIZED, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> l1WethAddress() {
        final Function function = new Function(FUNC_L1WETHADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> l2BridgeAddress(BigInteger _chainId) {
        final Function function = new Function(FUNC_L2BRIDGEADDRESS, 
                Arrays.<Type>asList(new Uint256(_chainId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> legacyBridge() {
        final Function function = new Function(FUNC_LEGACYBRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> receiveEth(BigInteger _chainId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_RECEIVEETH, 
                Arrays.<Type>asList(new Uint256(_chainId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> setEraFirstPostUpgradeBatch(BigInteger _eraFirstPostUpgradeBatch) {
        final Function function = new Function(
                FUNC_SETERAFIRSTPOSTUPGRADEBATCH, 
                Arrays.<Type>asList(new Uint256(_eraFirstPostUpgradeBatch)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IL1SharedBridge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1SharedBridge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IL1SharedBridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1SharedBridge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IL1SharedBridge load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IL1SharedBridge(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IL1SharedBridge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IL1SharedBridge(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class L2TransactionRequestTwoBridgesInner extends DynamicStruct {
        public byte[] magicValue;

        public String l2Contract;

        public byte[] l2Calldata;

        public List<byte[]> factoryDeps;

        public byte[] txDataHash;

        public L2TransactionRequestTwoBridgesInner(byte[] magicValue, String l2Contract, byte[] l2Calldata, List<byte[]> factoryDeps, byte[] txDataHash) {
            super(new Bytes32(magicValue),
                    new Address(160, l2Contract),
                    new DynamicBytes(l2Calldata),
                    new DynamicArray<DynamicBytes>(
                            DynamicBytes.class,
                            org.web3j.abi.Utils.typeMap(factoryDeps, DynamicBytes.class)),
                    new Bytes32(txDataHash));
            this.magicValue = magicValue;
            this.l2Contract = l2Contract;
            this.l2Calldata = l2Calldata;
            this.factoryDeps = factoryDeps;
            this.txDataHash = txDataHash;
        }

        public L2TransactionRequestTwoBridgesInner(Bytes32 magicValue, Address l2Contract, DynamicBytes l2Calldata, DynamicArray<DynamicBytes> factoryDeps, Bytes32 txDataHash) {
            super(magicValue, l2Contract, l2Calldata, factoryDeps, txDataHash);
            this.magicValue = magicValue.getValue();
            this.l2Contract = l2Contract.getValue();
            this.l2Calldata = l2Calldata.getValue();
            this.factoryDeps = factoryDeps.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.txDataHash = txDataHash.getValue();
        }
    }

    public static class BridgehubDepositBaseTokenInitiatedEventResponse extends BaseEventResponse {
        public BigInteger chainId;

        public String from;

        public String l1Token;

        public BigInteger amount;
    }

    public static class BridgehubDepositFinalizedEventResponse extends BaseEventResponse {
        public BigInteger chainId;

        public byte[] txDataHash;

        public byte[] l2DepositTxHash;
    }

    public static class BridgehubDepositInitiatedEventResponse extends BaseEventResponse {
        public BigInteger chainId;

        public byte[] txDataHash;

        public String from;

        public String to;

        public String l1Token;

        public BigInteger amount;
    }

    public static class ClaimedFailedDepositSharedBridgeEventResponse extends BaseEventResponse {
        public BigInteger chainId;

        public String to;

        public String l1Token;

        public BigInteger amount;
    }

    public static class LegacyDepositInitiatedEventResponse extends BaseEventResponse {
        public BigInteger chainId;

        public byte[] l2DepositTxHash;

        public String from;

        public String to;

        public String l1Token;

        public BigInteger amount;
    }

    public static class WithdrawalFinalizedSharedBridgeEventResponse extends BaseEventResponse {
        public BigInteger chainId;

        public String to;

        public String l1Token;

        public BigInteger amount;
    }
}
