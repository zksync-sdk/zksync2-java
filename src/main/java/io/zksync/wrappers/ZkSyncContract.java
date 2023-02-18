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
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.abi.datatypes.generated.Uint96;
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
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class ZkSyncContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ACTIVATEPRIORITYMODE = "activatePriorityMode";

    public static final String FUNC_ADDCUSTOMTOKEN = "addCustomToken";

    public static final String FUNC_ADDTOKEN = "addToken";

    public static final String FUNC_ADDTOKENBASECOST = "addTokenBaseCost";

    public static final String FUNC_APPROVEEMERGENCYDIAMONDCUTASSECURITYCOUNCILMEMBER = "approveEmergencyDiamondCutAsSecurityCouncilMember";

    public static final String FUNC_CANCELDIAMONDCUTPROPOSAL = "cancelDiamondCutProposal";

    public static final String FUNC_CHANGEGOVERNOR = "changeGovernor";

    public static final String FUNC_DEPLOYCONTRACTBASECOST = "deployContractBaseCost";

    public static final String FUNC_DEPOSITBASECOST = "depositBaseCost";

    public static final String FUNC_DEPOSITERC20 = "depositERC20";

    public static final String FUNC_DEPOSITETH = "depositETH";

    public static final String FUNC_EMERGENCYFREEZEDIAMOND = "emergencyFreezeDiamond";

    public static final String FUNC_EXECUTEBASECOST = "executeBaseCost";

    public static final String FUNC_GETGOVERNOR = "getGovernor";

    public static final String FUNC_GETPENDINGBALANCE = "getPendingBalance";

    public static final String FUNC_GETTOTALBLOCKSCOMMITTED = "getTotalBlocksCommitted";

    public static final String FUNC_GETTOTALBLOCKSEXECUTED = "getTotalBlocksExecuted";

    public static final String FUNC_GETTOTALBLOCKSVERIFIED = "getTotalBlocksVerified";

    public static final String FUNC_GETTOTALPRIORITYREQUESTS = "getTotalPriorityRequests";

    public static final String FUNC_GETVERIFIER = "getVerifier";

    public static final String FUNC_ISVALIDATOR = "isValidator";

    public static final String FUNC_MOVEPRIORITYOPSFROMBUFFERTOMAINQUEUE = "movePriorityOpsFromBufferToMainQueue";

    public static final String FUNC_PLACEBIDFORBLOCKSPROCESSINGAUCTION = "placeBidForBlocksProcessingAuction";

    public static final String FUNC_REQUESTDEPLOYCONTRACT = "requestDeployContract";

    public static final String FUNC_REQUESTEXECUTE = "requestExecute";

    public static final String FUNC_REQUESTWITHDRAW = "requestWithdraw";

    public static final String FUNC_REVERTBLOCKS = "revertBlocks";

    public static final String FUNC_SETVALIDATOR = "setValidator";

    public static final String FUNC_UNFREEZEDIAMOND = "unfreezeDiamond";

    public static final String FUNC_UPDATEPRIORITYMODESUBEPOCH = "updatePriorityModeSubEpoch";

    public static final String FUNC_WITHDRAWBASECOST = "withdrawBaseCost";

    public static final String FUNC_WITHDRAWPENDINGBALANCE = "withdrawPendingBalance";

    public static final Event BLOCKCOMMIT_EVENT = new Event("BlockCommit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>(true) {}));
    ;

    public static final Event BLOCKEXECUTION_EVENT = new Event("BlockExecution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>(true) {}));
    ;

    public static final Event BLOCKSREVERT_EVENT = new Event("BlocksRevert", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Uint32>() {}));
    ;

    public static final Event DIAMONDCUTPROPOSALCANCELATION_EVENT = new Event("DiamondCutProposalCancelation", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event EMERGENCYDIAMONDCUTAPPROVED_EVENT = new Event("EmergencyDiamondCutApproved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event EMERGENCYFREEZE_EVENT = new Event("EmergencyFreeze", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event MOVEPRIORITYOPERATIONSFROMBUFFERTOHEAP_EVENT = new Event("MovePriorityOperationsFromBufferToHeap", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<DynamicArray<Uint64>>() {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event NEWGOVERNOR_EVENT = new Event("NewGovernor", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event NEWPRIORITYMODEAUCTIONBID_EVENT = new Event("NewPriorityModeAuctionBid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Address>() {}, new TypeReference<Uint96>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NEWPRIORITYMODESUBEPOCH_EVENT = new Event("NewPriorityModeSubEpoch", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}, new TypeReference<Uint128>() {}));
    ;

    public static final Event NEWPRIORITYREQUEST_EVENT = new Event("NewPriorityRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event PRIORITYMODEACTIVATED_EVENT = new Event("PriorityModeActivated", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event UNFREEZE_EVENT = new Event("Unfreeze", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event VALIDATORSTATUSUPDATE_EVENT = new Event("ValidatorStatusUpdate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event WITHDRAWPENDINGBALANCE_EVENT = new Event("WithdrawPendingBalance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected ZkSyncContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ZkSyncContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ZkSyncContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ZkSyncContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<BlockCommitEventResponse> getBlockCommitEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BLOCKCOMMIT_EVENT, transactionReceipt);
        ArrayList<BlockCommitEventResponse> responses = new ArrayList<BlockCommitEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockCommitEventResponse typedResponse = new BlockCommitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BlockCommitEventResponse> blockCommitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BlockCommitEventResponse>() {
            @Override
            public BlockCommitEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BLOCKCOMMIT_EVENT, log);
                BlockCommitEventResponse typedResponse = new BlockCommitEventResponse();
                typedResponse.log = log;
                typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BlockCommitEventResponse> blockCommitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKCOMMIT_EVENT));
        return blockCommitEventFlowable(filter);
    }

    public List<BlockExecutionEventResponse> getBlockExecutionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BLOCKEXECUTION_EVENT, transactionReceipt);
        ArrayList<BlockExecutionEventResponse> responses = new ArrayList<BlockExecutionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockExecutionEventResponse typedResponse = new BlockExecutionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BlockExecutionEventResponse> blockExecutionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BlockExecutionEventResponse>() {
            @Override
            public BlockExecutionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BLOCKEXECUTION_EVENT, log);
                BlockExecutionEventResponse typedResponse = new BlockExecutionEventResponse();
                typedResponse.log = log;
                typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BlockExecutionEventResponse> blockExecutionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKEXECUTION_EVENT));
        return blockExecutionEventFlowable(filter);
    }

    public List<BlocksRevertEventResponse> getBlocksRevertEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BLOCKSREVERT_EVENT, transactionReceipt);
        ArrayList<BlocksRevertEventResponse> responses = new ArrayList<BlocksRevertEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlocksRevertEventResponse typedResponse = new BlocksRevertEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.totalBlocksVerified = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.totalBlocksCommitted = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BlocksRevertEventResponse> blocksRevertEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BlocksRevertEventResponse>() {
            @Override
            public BlocksRevertEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BLOCKSREVERT_EVENT, log);
                BlocksRevertEventResponse typedResponse = new BlocksRevertEventResponse();
                typedResponse.log = log;
                typedResponse.totalBlocksVerified = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.totalBlocksCommitted = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BlocksRevertEventResponse> blocksRevertEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKSREVERT_EVENT));
        return blocksRevertEventFlowable(filter);
    }

    public List<DiamondCutProposalCancelationEventResponse> getDiamondCutProposalCancelationEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DIAMONDCUTPROPOSALCANCELATION_EVENT, transactionReceipt);
        ArrayList<DiamondCutProposalCancelationEventResponse> responses = new ArrayList<DiamondCutProposalCancelationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DiamondCutProposalCancelationEventResponse typedResponse = new DiamondCutProposalCancelationEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DiamondCutProposalCancelationEventResponse> diamondCutProposalCancelationEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DiamondCutProposalCancelationEventResponse>() {
            @Override
            public DiamondCutProposalCancelationEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DIAMONDCUTPROPOSALCANCELATION_EVENT, log);
                DiamondCutProposalCancelationEventResponse typedResponse = new DiamondCutProposalCancelationEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<DiamondCutProposalCancelationEventResponse> diamondCutProposalCancelationEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DIAMONDCUTPROPOSALCANCELATION_EVENT));
        return diamondCutProposalCancelationEventFlowable(filter);
    }

    public List<EmergencyDiamondCutApprovedEventResponse> getEmergencyDiamondCutApprovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EMERGENCYDIAMONDCUTAPPROVED_EVENT, transactionReceipt);
        ArrayList<EmergencyDiamondCutApprovedEventResponse> responses = new ArrayList<EmergencyDiamondCutApprovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmergencyDiamondCutApprovedEventResponse typedResponse = new EmergencyDiamondCutApprovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EmergencyDiamondCutApprovedEventResponse> emergencyDiamondCutApprovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, EmergencyDiamondCutApprovedEventResponse>() {
            @Override
            public EmergencyDiamondCutApprovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EMERGENCYDIAMONDCUTAPPROVED_EVENT, log);
                EmergencyDiamondCutApprovedEventResponse typedResponse = new EmergencyDiamondCutApprovedEventResponse();
                typedResponse.log = log;
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<EmergencyDiamondCutApprovedEventResponse> emergencyDiamondCutApprovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMERGENCYDIAMONDCUTAPPROVED_EVENT));
        return emergencyDiamondCutApprovedEventFlowable(filter);
    }

    public List<EmergencyFreezeEventResponse> getEmergencyFreezeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EMERGENCYFREEZE_EVENT, transactionReceipt);
        ArrayList<EmergencyFreezeEventResponse> responses = new ArrayList<EmergencyFreezeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmergencyFreezeEventResponse typedResponse = new EmergencyFreezeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EmergencyFreezeEventResponse> emergencyFreezeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, EmergencyFreezeEventResponse>() {
            @Override
            public EmergencyFreezeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EMERGENCYFREEZE_EVENT, log);
                EmergencyFreezeEventResponse typedResponse = new EmergencyFreezeEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<EmergencyFreezeEventResponse> emergencyFreezeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMERGENCYFREEZE_EVENT));
        return emergencyFreezeEventFlowable(filter);
    }

    public List<MovePriorityOperationsFromBufferToHeapEventResponse> getMovePriorityOperationsFromBufferToHeapEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MOVEPRIORITYOPERATIONSFROMBUFFERTOHEAP_EVENT, transactionReceipt);
        ArrayList<MovePriorityOperationsFromBufferToHeapEventResponse> responses = new ArrayList<MovePriorityOperationsFromBufferToHeapEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MovePriorityOperationsFromBufferToHeapEventResponse typedResponse = new MovePriorityOperationsFromBufferToHeapEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.expirationBlock = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.operationIDs = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.opTree = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MovePriorityOperationsFromBufferToHeapEventResponse> movePriorityOperationsFromBufferToHeapEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MovePriorityOperationsFromBufferToHeapEventResponse>() {
            @Override
            public MovePriorityOperationsFromBufferToHeapEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MOVEPRIORITYOPERATIONSFROMBUFFERTOHEAP_EVENT, log);
                MovePriorityOperationsFromBufferToHeapEventResponse typedResponse = new MovePriorityOperationsFromBufferToHeapEventResponse();
                typedResponse.log = log;
                typedResponse.expirationBlock = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.operationIDs = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.opTree = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MovePriorityOperationsFromBufferToHeapEventResponse> movePriorityOperationsFromBufferToHeapEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MOVEPRIORITYOPERATIONSFROMBUFFERTOHEAP_EVENT));
        return movePriorityOperationsFromBufferToHeapEventFlowable(filter);
    }

    public List<NewGovernorEventResponse> getNewGovernorEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWGOVERNOR_EVENT, transactionReceipt);
        ArrayList<NewGovernorEventResponse> responses = new ArrayList<NewGovernorEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewGovernorEventResponse typedResponse = new NewGovernorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newGovernor = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewGovernorEventResponse> newGovernorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewGovernorEventResponse>() {
            @Override
            public NewGovernorEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWGOVERNOR_EVENT, log);
                NewGovernorEventResponse typedResponse = new NewGovernorEventResponse();
                typedResponse.log = log;
                typedResponse.newGovernor = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewGovernorEventResponse> newGovernorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWGOVERNOR_EVENT));
        return newGovernorEventFlowable(filter);
    }

    public List<NewPriorityModeAuctionBidEventResponse> getNewPriorityModeAuctionBidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPRIORITYMODEAUCTIONBID_EVENT, transactionReceipt);
        ArrayList<NewPriorityModeAuctionBidEventResponse> responses = new ArrayList<NewPriorityModeAuctionBidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPriorityModeAuctionBidEventResponse typedResponse = new NewPriorityModeAuctionBidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.opTree = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.bidAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.complexity = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewPriorityModeAuctionBidEventResponse> newPriorityModeAuctionBidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewPriorityModeAuctionBidEventResponse>() {
            @Override
            public NewPriorityModeAuctionBidEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWPRIORITYMODEAUCTIONBID_EVENT, log);
                NewPriorityModeAuctionBidEventResponse typedResponse = new NewPriorityModeAuctionBidEventResponse();
                typedResponse.log = log;
                typedResponse.opTree = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.bidAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.complexity = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewPriorityModeAuctionBidEventResponse> newPriorityModeAuctionBidEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYMODEAUCTIONBID_EVENT));
        return newPriorityModeAuctionBidEventFlowable(filter);
    }

    public List<NewPriorityModeSubEpochEventResponse> getNewPriorityModeSubEpochEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPRIORITYMODESUBEPOCH_EVENT, transactionReceipt);
        ArrayList<NewPriorityModeSubEpochEventResponse> responses = new ArrayList<NewPriorityModeSubEpochEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPriorityModeSubEpochEventResponse typedResponse = new NewPriorityModeSubEpochEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.subEpoch = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.subEpochEndTimestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewPriorityModeSubEpochEventResponse> newPriorityModeSubEpochEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewPriorityModeSubEpochEventResponse>() {
            @Override
            public NewPriorityModeSubEpochEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWPRIORITYMODESUBEPOCH_EVENT, log);
                NewPriorityModeSubEpochEventResponse typedResponse = new NewPriorityModeSubEpochEventResponse();
                typedResponse.log = log;
                typedResponse.subEpoch = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.subEpochEndTimestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewPriorityModeSubEpochEventResponse> newPriorityModeSubEpochEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYMODESUBEPOCH_EVENT));
        return newPriorityModeSubEpochEventFlowable(filter);
    }

    public List<NewPriorityRequestEventResponse> getNewPriorityRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPRIORITYREQUEST_EVENT, transactionReceipt);
        ArrayList<NewPriorityRequestEventResponse> responses = new ArrayList<NewPriorityRequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPriorityRequestEventResponse typedResponse = new NewPriorityRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serialId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.opMetadata = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewPriorityRequestEventResponse> newPriorityRequestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewPriorityRequestEventResponse>() {
            @Override
            public NewPriorityRequestEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWPRIORITYREQUEST_EVENT, log);
                NewPriorityRequestEventResponse typedResponse = new NewPriorityRequestEventResponse();
                typedResponse.log = log;
                typedResponse.serialId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.opMetadata = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewPriorityRequestEventResponse> newPriorityRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYREQUEST_EVENT));
        return newPriorityRequestEventFlowable(filter);
    }

    public List<PriorityModeActivatedEventResponse> getPriorityModeActivatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PRIORITYMODEACTIVATED_EVENT, transactionReceipt);
        ArrayList<PriorityModeActivatedEventResponse> responses = new ArrayList<PriorityModeActivatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PriorityModeActivatedEventResponse typedResponse = new PriorityModeActivatedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PriorityModeActivatedEventResponse> priorityModeActivatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PriorityModeActivatedEventResponse>() {
            @Override
            public PriorityModeActivatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PRIORITYMODEACTIVATED_EVENT, log);
                PriorityModeActivatedEventResponse typedResponse = new PriorityModeActivatedEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<PriorityModeActivatedEventResponse> priorityModeActivatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRIORITYMODEACTIVATED_EVENT));
        return priorityModeActivatedEventFlowable(filter);
    }

    public List<UnfreezeEventResponse> getUnfreezeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UNFREEZE_EVENT, transactionReceipt);
        ArrayList<UnfreezeEventResponse> responses = new ArrayList<UnfreezeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnfreezeEventResponse typedResponse = new UnfreezeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UnfreezeEventResponse> unfreezeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UnfreezeEventResponse>() {
            @Override
            public UnfreezeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UNFREEZE_EVENT, log);
                UnfreezeEventResponse typedResponse = new UnfreezeEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<UnfreezeEventResponse> unfreezeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNFREEZE_EVENT));
        return unfreezeEventFlowable(filter);
    }

    public List<ValidatorStatusUpdateEventResponse> getValidatorStatusUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VALIDATORSTATUSUPDATE_EVENT, transactionReceipt);
        ArrayList<ValidatorStatusUpdateEventResponse> responses = new ArrayList<ValidatorStatusUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValidatorStatusUpdateEventResponse typedResponse = new ValidatorStatusUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.validatorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.isActive = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ValidatorStatusUpdateEventResponse> validatorStatusUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ValidatorStatusUpdateEventResponse>() {
            @Override
            public ValidatorStatusUpdateEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VALIDATORSTATUSUPDATE_EVENT, log);
                ValidatorStatusUpdateEventResponse typedResponse = new ValidatorStatusUpdateEventResponse();
                typedResponse.log = log;
                typedResponse.validatorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.isActive = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ValidatorStatusUpdateEventResponse> validatorStatusUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALIDATORSTATUSUPDATE_EVENT));
        return validatorStatusUpdateEventFlowable(filter);
    }

    public List<WithdrawPendingBalanceEventResponse> getWithdrawPendingBalanceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWPENDINGBALANCE_EVENT, transactionReceipt);
        ArrayList<WithdrawPendingBalanceEventResponse> responses = new ArrayList<WithdrawPendingBalanceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawPendingBalanceEventResponse typedResponse = new WithdrawPendingBalanceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawPendingBalanceEventResponse> withdrawPendingBalanceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawPendingBalanceEventResponse>() {
            @Override
            public WithdrawPendingBalanceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWPENDINGBALANCE_EVENT, log);
                WithdrawPendingBalanceEventResponse typedResponse = new WithdrawPendingBalanceEventResponse();
                typedResponse.log = log;
                typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawPendingBalanceEventResponse> withdrawPendingBalanceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWPENDINGBALANCE_EVENT));
        return withdrawPendingBalanceEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> activatePriorityMode(BigInteger _ethExpirationBlock) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVATEPRIORITYMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_ethExpirationBlock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addCustomToken(String _token, String _name, String _symbol, BigInteger _decimals, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCUSTOMTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_symbol), 
                new org.web3j.abi.datatypes.generated.Uint8(_decimals), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addToken(String _token, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> addTokenBaseCost(BigInteger _gasPrice, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADDTOKENBASECOST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_gasPrice), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approveEmergencyDiamondCutAsSecurityCouncilMember(byte[] _diamondCutHash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVEEMERGENCYDIAMONDCUTASSECURITYCOUNCILMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_diamondCutHash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelDiamondCutProposal() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELDIAMONDCUTPROPOSAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeGovernor(String _newGovernor) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEGOVERNOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _newGovernor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> deployContractBaseCost(BigInteger _gasPrice, BigInteger _gasLimit, BigInteger _bytecodeLength, BigInteger _calldataLength, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEPLOYCONTRACTBASECOST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_gasPrice), 
                new org.web3j.abi.datatypes.generated.Uint256(_gasLimit),
                new org.web3j.abi.datatypes.generated.Uint32(_bytecodeLength), 
                new org.web3j.abi.datatypes.generated.Uint32(_calldataLength), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> depositBaseCost(BigInteger _gasPrice, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DEPOSITBASECOST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_gasPrice), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> depositERC20(String _token, BigInteger _amount, String _zkSyncAddress, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITERC20, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.Address(160, _zkSyncAddress), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositETH(BigInteger _amount, String _zkSyncAddress, BigInteger _queueType, BigInteger _opTree, BigInteger _value) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITETH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.Address(160, _zkSyncAddress), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, _value);
    }

    public RemoteFunctionCall<TransactionReceipt> emergencyFreezeDiamond() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EMERGENCYFREEZEDIAMOND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> executeBaseCost(BigInteger _gasPrice, BigInteger _gasLimit, BigInteger _calldataLength, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EXECUTEBASECOST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_gasPrice), 
                new org.web3j.abi.datatypes.generated.Uint256(_gasLimit),
                new org.web3j.abi.datatypes.generated.Uint32(_calldataLength), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getGovernor() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETGOVERNOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getPendingBalance(String _address, String _token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPENDINGBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address), 
                new org.web3j.abi.datatypes.Address(160, _token)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBlocksCommitted() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOTALBLOCKSCOMMITTED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBlocksExecuted() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOTALBLOCKSEXECUTED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBlocksVerified() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOTALBLOCKSVERIFIED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalPriorityRequests() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETTOTALPRIORITYREQUESTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getVerifier() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVERIFIER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isValidator(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISVALIDATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> movePriorityOpsFromBufferToMainQueue(BigInteger _nOpsToMove, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MOVEPRIORITYOPSFROMBUFFERTOMAINQUEUE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_nOpsToMove), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> placeBidForBlocksProcessingAuction(BigInteger _complexityRoot, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PLACEBIDFORBLOCKSPROCESSINGAUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint112(_complexityRoot), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestDeployContract(byte[] _bytecode, byte[] _calldata, BigInteger _gasLimit, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTDEPLOYCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_bytecode), 
                new org.web3j.abi.datatypes.DynamicBytes(_calldata), 
                new org.web3j.abi.datatypes.generated.Uint256(_gasLimit),
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestExecute(String _contractAddressL2, byte[] _calldata, BigInteger _gasLimit, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTEXECUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddressL2), 
                new org.web3j.abi.datatypes.DynamicBytes(_calldata), 
                new org.web3j.abi.datatypes.generated.Uint256(_gasLimit),
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestWithdraw(String _token, BigInteger _amount, String _to, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTWITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revertBlocks(BigInteger _blocksToRevert) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVERTBLOCKS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(_blocksToRevert)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValidator(String _validator, Boolean _active) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETVALIDATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _validator), 
                new org.web3j.abi.datatypes.Bool(_active)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unfreezeDiamond() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNFREEZEDIAMOND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updatePriorityModeSubEpoch() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEPRIORITYMODESUBEPOCH, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> withdrawBaseCost(BigInteger _gasPrice, BigInteger _queueType, BigInteger _opTree) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_WITHDRAWBASECOST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_gasPrice), 
                new org.web3j.abi.datatypes.generated.Uint8(_queueType), 
                new org.web3j.abi.datatypes.generated.Uint8(_opTree)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawPendingBalance(String _owner, String _token, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWPENDINGBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ZkSyncContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ZkSyncContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ZkSyncContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ZkSyncContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ZkSyncContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ZkSyncContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ZkSyncContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ZkSyncContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public TransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public ContractGasProvider getGasProvider() {
        return this.gasProvider;
    }

    public static class BlockCommitEventResponse extends BaseEventResponse {
        public BigInteger blockNumber;
    }

    public static class BlockExecutionEventResponse extends BaseEventResponse {
        public BigInteger blockNumber;
    }

    public static class BlocksRevertEventResponse extends BaseEventResponse {
        public BigInteger totalBlocksVerified;

        public BigInteger totalBlocksCommitted;
    }

    public static class DiamondCutProposalCancelationEventResponse extends BaseEventResponse {
    }

    public static class EmergencyDiamondCutApprovedEventResponse extends BaseEventResponse {
        public String _address;
    }

    public static class EmergencyFreezeEventResponse extends BaseEventResponse {
    }

    public static class MovePriorityOperationsFromBufferToHeapEventResponse extends BaseEventResponse {
        public BigInteger expirationBlock;

        public List<BigInteger> operationIDs;

        public BigInteger opTree;
    }

    public static class NewGovernorEventResponse extends BaseEventResponse {
        public String newGovernor;
    }

    public static class NewPriorityModeAuctionBidEventResponse extends BaseEventResponse {
        public BigInteger opTree;

        public String sender;

        public BigInteger bidAmount;

        public BigInteger complexity;
    }

    public static class NewPriorityModeSubEpochEventResponse extends BaseEventResponse {
        public BigInteger subEpoch;

        public BigInteger subEpochEndTimestamp;
    }

    public static class NewPriorityRequestEventResponse extends BaseEventResponse {
        public BigInteger serialId;

        public byte[] opMetadata;
    }

    public static class PriorityModeActivatedEventResponse extends BaseEventResponse {
    }

    public static class UnfreezeEventResponse extends BaseEventResponse {
    }

    public static class ValidatorStatusUpdateEventResponse extends BaseEventResponse {
        public String validatorAddress;

        public Boolean isActive;
    }

    public static class WithdrawPendingBalanceEventResponse extends BaseEventResponse {
        public String zkSyncTokenAddress;

        public BigInteger amount;
    }
}
