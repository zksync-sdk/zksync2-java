package io.zksync.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
public class IBridgehub extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ACCEPTADMIN = "acceptAdmin";

    public static final String FUNC_ADDSTATETRANSITIONMANAGER = "addStateTransitionManager";

    public static final String FUNC_ADDTOKEN = "addToken";

    public static final String FUNC_BASETOKEN = "baseToken";

    public static final String FUNC_CREATENEWCHAIN = "createNewChain";

    public static final String FUNC_GETSTATETRANSITION = "getStateTransition";

    public static final String FUNC_L2TRANSACTIONBASECOST = "l2TransactionBaseCost";

    public static final String FUNC_PROVEL1TOL2TRANSACTIONSTATUS = "proveL1ToL2TransactionStatus";

    public static final String FUNC_PROVEL2LOGINCLUSION = "proveL2LogInclusion";

    public static final String FUNC_PROVEL2MESSAGEINCLUSION = "proveL2MessageInclusion";

    public static final String FUNC_REMOVESTATETRANSITIONMANAGER = "removeStateTransitionManager";

    public static final String FUNC_REQUESTL2TRANSACTIONDIRECT = "requestL2TransactionDirect";

    public static final String FUNC_REQUESTL2TRANSACTIONTWOBRIDGES = "requestL2TransactionTwoBridges";

    public static final String FUNC_SETPENDINGADMIN = "setPendingAdmin";

    public static final String FUNC_SETSHAREDBRIDGE = "setSharedBridge";

    public static final String FUNC_SHAREDBRIDGE = "sharedBridge";

    public static final String FUNC_STATETRANSITIONMANAGER = "stateTransitionManager";

    public static final String FUNC_STATETRANSITIONMANAGERISREGISTERED = "stateTransitionManagerIsRegistered";

    public static final String FUNC_TOKENISREGISTERED = "tokenIsRegistered";

    public static final Event NEWADMIN_EVENT = new Event("NewAdmin", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWCHAIN_EVENT = new Event("NewChain", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWPENDINGADMIN_EVENT = new Event("NewPendingAdmin", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected IBridgehub(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IBridgehub(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IBridgehub(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IBridgehub(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<NewAdminEventResponse> getNewAdminEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWADMIN_EVENT, transactionReceipt);
        ArrayList<NewAdminEventResponse> responses = new ArrayList<NewAdminEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            NewAdminEventResponse typedResponse = new NewAdminEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewAdminEventResponse getNewAdminEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWADMIN_EVENT, log);
        NewAdminEventResponse typedResponse = new NewAdminEventResponse();
        typedResponse.log = log;
        typedResponse.oldAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewAdminEventResponse> newAdminEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewAdminEventFromLog(log));
    }

    public Flowable<NewAdminEventResponse> newAdminEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWADMIN_EVENT));
        return newAdminEventFlowable(filter);
    }

    public static List<NewChainEventResponse> getNewChainEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWCHAIN_EVENT, transactionReceipt);
        ArrayList<NewChainEventResponse> responses = new ArrayList<NewChainEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            NewChainEventResponse typedResponse = new NewChainEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.chainGovernance = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.stateTransitionManager = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewChainEventResponse getNewChainEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWCHAIN_EVENT, log);
        NewChainEventResponse typedResponse = new NewChainEventResponse();
        typedResponse.log = log;
        typedResponse.chainId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.chainGovernance = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.stateTransitionManager = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<NewChainEventResponse> newChainEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewChainEventFromLog(log));
    }

    public Flowable<NewChainEventResponse> newChainEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWCHAIN_EVENT));
        return newChainEventFlowable(filter);
    }

    public static List<NewPendingAdminEventResponse> getNewPendingAdminEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPENDINGADMIN_EVENT, transactionReceipt);
        ArrayList<NewPendingAdminEventResponse> responses = new ArrayList<NewPendingAdminEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            NewPendingAdminEventResponse typedResponse = new NewPendingAdminEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldPendingAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newPendingAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewPendingAdminEventResponse getNewPendingAdminEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPENDINGADMIN_EVENT, log);
        NewPendingAdminEventResponse typedResponse = new NewPendingAdminEventResponse();
        typedResponse.log = log;
        typedResponse.oldPendingAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newPendingAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewPendingAdminEventResponse> newPendingAdminEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPendingAdminEventFromLog(log));
    }

    public Flowable<NewPendingAdminEventResponse> newPendingAdminEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPENDINGADMIN_EVENT));
        return newPendingAdminEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptAdmin() {
        final Function function = new Function(
                FUNC_ACCEPTADMIN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addStateTransitionManager(String _stateTransitionManager) {
        final Function function = new Function(
                FUNC_ADDSTATETRANSITIONMANAGER, 
                Arrays.<Type>asList(new Address(160, _stateTransitionManager)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addToken(String _token) {
        final Function function = new Function(
                FUNC_ADDTOKEN, 
                Arrays.<Type>asList(new Address(160, _token)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> baseToken(BigInteger _chainId) {
        final Function function = new Function(FUNC_BASETOKEN, 
                Arrays.<Type>asList(new Uint256(_chainId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createNewChain(BigInteger _chainId, String _stateTransitionManager, String _baseToken, BigInteger _salt, String _admin, byte[] _initData) {
        final Function function = new Function(
                FUNC_CREATENEWCHAIN, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Address(160, _stateTransitionManager),
                new Address(160, _baseToken),
                new Uint256(_salt),
                new Address(160, _admin),
                new DynamicBytes(_initData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getStateTransition(BigInteger _chainId) {
        final Function function = new Function(FUNC_GETSTATETRANSITION, 
                Arrays.<Type>asList(new Uint256(_chainId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> l2TransactionBaseCost(BigInteger _chainId, BigInteger _gasPrice, BigInteger _l2GasLimit, BigInteger _l2GasPerPubdataByteLimit) {
        final Function function = new Function(FUNC_L2TRANSACTIONBASECOST, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Uint256(_gasPrice),
                new Uint256(_l2GasLimit),
                new Uint256(_l2GasPerPubdataByteLimit)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> proveL1ToL2TransactionStatus(BigInteger _chainId, byte[] _l2TxHash, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, List<byte[]> _merkleProof, BigInteger _status) {
        final Function function = new Function(FUNC_PROVEL1TOL2TRANSACTIONSTATUS, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Bytes32(_l2TxHash),
                new Uint256(_l2BatchNumber),
                new Uint256(_l2MessageIndex),
                new Uint16(_l2TxNumberInBatch),
                new DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class)),
                new Uint8(_status)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> proveL2LogInclusion(BigInteger _chainId, BigInteger _batchNumber, BigInteger _index, L2Log _log, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2LOGINCLUSION, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Uint256(_batchNumber),
                new Uint256(_index),
                _log, 
                new DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_proof, Bytes32.class))),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> proveL2MessageInclusion(BigInteger _chainId, BigInteger _batchNumber, BigInteger _index, L2Message _message, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2MESSAGEINCLUSION, 
                Arrays.<Type>asList(new Uint256(_chainId),
                new Uint256(_batchNumber),
                new Uint256(_index),
                _message, 
                new DynamicArray<Bytes32>(
                        Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_proof, Bytes32.class))),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeStateTransitionManager(String _stateTransitionManager) {
        final Function function = new Function(
                FUNC_REMOVESTATETRANSITIONMANAGER, 
                Arrays.<Type>asList(new Address(160, _stateTransitionManager)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestL2TransactionDirect(L2TransactionRequestDirect _request, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REQUESTL2TRANSACTIONDIRECT, 
                Arrays.<Type>asList(_request), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public String encodeRequestL2TransactionTwoBridges(L2TransactionRequestTwoBridgesOuter _request) {
        final Function function = new Function(
                FUNC_REQUESTL2TRANSACTIONTWOBRIDGES,
                Arrays.<Type>asList(_request),
                Collections.<TypeReference<?>>emptyList());
        return FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestL2TransactionTwoBridges(L2TransactionRequestTwoBridgesOuter _request, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REQUESTL2TRANSACTIONTWOBRIDGES, 
                Arrays.<Type>asList(_request), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> setPendingAdmin(String _newPendingAdmin) {
        final Function function = new Function(
                FUNC_SETPENDINGADMIN, 
                Arrays.<Type>asList(new Address(160, _newPendingAdmin)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setSharedBridge(String _sharedBridge) {
        final Function function = new Function(
                FUNC_SETSHAREDBRIDGE, 
                Arrays.<Type>asList(new Address(160, _sharedBridge)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> sharedBridge() {
        final Function function = new Function(FUNC_SHAREDBRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> stateTransitionManager(BigInteger _chainId) {
        final Function function = new Function(FUNC_STATETRANSITIONMANAGER, 
                Arrays.<Type>asList(new Uint256(_chainId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> stateTransitionManagerIsRegistered(String _stateTransitionManager) {
        final Function function = new Function(FUNC_STATETRANSITIONMANAGERISREGISTERED, 
                Arrays.<Type>asList(new Address(160, _stateTransitionManager)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> tokenIsRegistered(String _baseToken) {
        final Function function = new Function(FUNC_TOKENISREGISTERED, 
                Arrays.<Type>asList(new Address(160, _baseToken)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static IBridgehub load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IBridgehub(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IBridgehub load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IBridgehub(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IBridgehub load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IBridgehub(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IBridgehub load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IBridgehub(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class L2Log extends StaticStruct {
        public BigInteger l2ShardId;

        public Boolean isService;

        public BigInteger txNumberInBatch;

        public String sender;

        public byte[] key;

        public byte[] value;

        public L2Log(BigInteger l2ShardId, Boolean isService, BigInteger txNumberInBatch, String sender, byte[] key, byte[] value) {
            super(new Uint8(l2ShardId),
                    new Bool(isService),
                    new Uint16(txNumberInBatch),
                    new Address(160, sender),
                    new Bytes32(key),
                    new Bytes32(value));
            this.l2ShardId = l2ShardId;
            this.isService = isService;
            this.txNumberInBatch = txNumberInBatch;
            this.sender = sender;
            this.key = key;
            this.value = value;
        }

        public L2Log(Uint8 l2ShardId, Bool isService, Uint16 txNumberInBatch, Address sender, Bytes32 key, Bytes32 value) {
            super(l2ShardId, isService, txNumberInBatch, sender, key, value);
            this.l2ShardId = l2ShardId.getValue();
            this.isService = isService.getValue();
            this.txNumberInBatch = txNumberInBatch.getValue();
            this.sender = sender.getValue();
            this.key = key.getValue();
            this.value = value.getValue();
        }
    }

    public static class L2Message extends DynamicStruct {
        public BigInteger txNumberInBatch;

        public String sender;

        public byte[] data;

        public L2Message(BigInteger txNumberInBatch, String sender, byte[] data) {
            super(new Uint16(txNumberInBatch),
                    new Address(160, sender),
                    new DynamicBytes(data));
            this.txNumberInBatch = txNumberInBatch;
            this.sender = sender;
            this.data = data;
        }

        public L2Message(Uint16 txNumberInBatch, Address sender, DynamicBytes data) {
            super(txNumberInBatch, sender, data);
            this.txNumberInBatch = txNumberInBatch.getValue();
            this.sender = sender.getValue();
            this.data = data.getValue();
        }
    }

    public static class L2TransactionRequestDirect extends DynamicStruct {
        public BigInteger chainId;

        public BigInteger mintValue;

        public String l2Contract;

        public BigInteger l2Value;

        public byte[] l2Calldata;

        public BigInteger l2GasLimit;

        public BigInteger l2GasPerPubdataByteLimit;

        public List<byte[]> factoryDeps;

        public String refundRecipient;

        public L2TransactionRequestDirect(BigInteger chainId, BigInteger mintValue, String l2Contract, BigInteger l2Value, byte[] l2Calldata, BigInteger l2GasLimit, BigInteger l2GasPerPubdataByteLimit, List<byte[]> factoryDeps, String refundRecipient) {
            super(new Uint256(chainId),
                    new Uint256(mintValue),
                    new Address(160, l2Contract),
                    new Uint256(l2Value),
                    new DynamicBytes(l2Calldata),
                    new Uint256(l2GasLimit),
                    new Uint256(l2GasPerPubdataByteLimit),
                    new DynamicArray<DynamicBytes>(
                            DynamicBytes.class,
                            org.web3j.abi.Utils.typeMap(factoryDeps, DynamicBytes.class)),
                    new Address(160, refundRecipient));
            this.chainId = chainId;
            this.mintValue = mintValue;
            this.l2Contract = l2Contract;
            this.l2Value = l2Value;
            this.l2Calldata = l2Calldata;
            this.l2GasLimit = l2GasLimit;
            this.l2GasPerPubdataByteLimit = l2GasPerPubdataByteLimit;
            this.factoryDeps = factoryDeps;
            this.refundRecipient = refundRecipient;
        }

        public L2TransactionRequestDirect(Uint256 chainId, Uint256 mintValue, Address l2Contract, Uint256 l2Value, DynamicBytes l2Calldata, Uint256 l2GasLimit, Uint256 l2GasPerPubdataByteLimit, DynamicArray<DynamicBytes> factoryDeps, Address refundRecipient) {
            super(chainId, mintValue, l2Contract, l2Value, l2Calldata, l2GasLimit, l2GasPerPubdataByteLimit, factoryDeps, refundRecipient);
            this.chainId = chainId.getValue();
            this.mintValue = mintValue.getValue();
            this.l2Contract = l2Contract.getValue();
            this.l2Value = l2Value.getValue();
            this.l2Calldata = l2Calldata.getValue();
            this.l2GasLimit = l2GasLimit.getValue();
            this.l2GasPerPubdataByteLimit = l2GasPerPubdataByteLimit.getValue();
            this.factoryDeps = factoryDeps.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.refundRecipient = refundRecipient.getValue();
        }
    }

    public static class L2TransactionRequestTwoBridgesOuter extends DynamicStruct {
        public BigInteger chainId;

        public BigInteger mintValue;

        public BigInteger l2Value;

        public BigInteger l2GasLimit;

        public BigInteger l2GasPerPubdataByteLimit;

        public String refundRecipient;

        public String secondBridgeAddress;

        public BigInteger secondBridgeValue;

        public byte[] secondBridgeCalldata;

        public L2TransactionRequestTwoBridgesOuter(BigInteger chainId, BigInteger mintValue, BigInteger l2Value, BigInteger l2GasLimit, BigInteger l2GasPerPubdataByteLimit, String refundRecipient, String secondBridgeAddress, BigInteger secondBridgeValue, byte[] secondBridgeCalldata) {
            super(new Uint256(chainId),
                    new Uint256(mintValue),
                    new Uint256(l2Value),
                    new Uint256(l2GasLimit),
                    new Uint256(l2GasPerPubdataByteLimit),
                    new Address(160, refundRecipient),
                    new Address(160, secondBridgeAddress),
                    new Uint256(secondBridgeValue),
                    new DynamicBytes(secondBridgeCalldata));
            this.chainId = chainId;
            this.mintValue = mintValue;
            this.l2Value = l2Value;
            this.l2GasLimit = l2GasLimit;
            this.l2GasPerPubdataByteLimit = l2GasPerPubdataByteLimit;
            this.refundRecipient = refundRecipient;
            this.secondBridgeAddress = secondBridgeAddress;
            this.secondBridgeValue = secondBridgeValue;
            this.secondBridgeCalldata = secondBridgeCalldata;
        }

        public L2TransactionRequestTwoBridgesOuter(Uint256 chainId, Uint256 mintValue, Uint256 l2Value, Uint256 l2GasLimit, Uint256 l2GasPerPubdataByteLimit, Address refundRecipient, Address secondBridgeAddress, Uint256 secondBridgeValue, DynamicBytes secondBridgeCalldata) {
            super(chainId, mintValue, l2Value, l2GasLimit, l2GasPerPubdataByteLimit, refundRecipient, secondBridgeAddress, secondBridgeValue, secondBridgeCalldata);
            this.chainId = chainId.getValue();
            this.mintValue = mintValue.getValue();
            this.l2Value = l2Value.getValue();
            this.l2GasLimit = l2GasLimit.getValue();
            this.l2GasPerPubdataByteLimit = l2GasPerPubdataByteLimit.getValue();
            this.refundRecipient = refundRecipient.getValue();
            this.secondBridgeAddress = secondBridgeAddress.getValue();
            this.secondBridgeValue = secondBridgeValue.getValue();
            this.secondBridgeCalldata = secondBridgeCalldata.getValue();
        }
    }

    public static class NewAdminEventResponse extends BaseEventResponse {
        public String oldAdmin;

        public String newAdmin;
    }

    public static class NewChainEventResponse extends BaseEventResponse {
        public BigInteger chainId;

        public String chainGovernance;

        public String stateTransitionManager;
    }

    public static class NewPendingAdminEventResponse extends BaseEventResponse {
        public String oldPendingAdmin;

        public String newPendingAdmin;
    }
}
