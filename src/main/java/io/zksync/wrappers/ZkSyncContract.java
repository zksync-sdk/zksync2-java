package io.zksync.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.StaticArray4;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint192;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.abi.datatypes.reflection.Parameterized;
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
 * <p>Generated with web3j version 4.9.7.
 */
@SuppressWarnings("rawtypes")
public class ZkSyncContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ACCEPTADMIN = "acceptAdmin";

    public static final String FUNC_ACCEPTGOVERNOR = "acceptGovernor";

    public static final String FUNC_COMMITBATCHES = "commitBatches";

    public static final String FUNC_EXECUTEBATCHES = "executeBatches";

    public static final String FUNC_EXECUTEUPGRADE = "executeUpgrade";

    public static final String FUNC_FACETADDRESS = "facetAddress";

    public static final String FUNC_FACETADDRESSES = "facetAddresses";

    public static final String FUNC_FACETFUNCTIONSELECTORS = "facetFunctionSelectors";

    public static final String FUNC_FACETS = "facets";

    public static final String FUNC_FINALIZEETHWITHDRAWAL = "finalizeEthWithdrawal";

    public static final String FUNC_FREEZEDIAMOND = "freezeDiamond";

    public static final String FUNC_GETFIRSTUNPROCESSEDPRIORITYTX = "getFirstUnprocessedPriorityTx";

    public static final String FUNC_GETGOVERNOR = "getGovernor";

    public static final String FUNC_GETL2BOOTLOADERBYTECODEHASH = "getL2BootloaderBytecodeHash";

    public static final String FUNC_GETL2DEFAULTACCOUNTBYTECODEHASH = "getL2DefaultAccountBytecodeHash";

    public static final String FUNC_GETL2SYSTEMCONTRACTSUPGRADEBATCHNUMBER = "getL2SystemContractsUpgradeBatchNumber";

    public static final String FUNC_GETL2SYSTEMCONTRACTSUPGRADETXHASH = "getL2SystemContractsUpgradeTxHash";

    public static final String FUNC_GETNAME = "getName";

    public static final String FUNC_GETPENDINGGOVERNOR = "getPendingGovernor";

    public static final String FUNC_GETPRIORITYQUEUESIZE = "getPriorityQueueSize";

    public static final String FUNC_GETPRIORITYTXMAXGASLIMIT = "getPriorityTxMaxGasLimit";

    public static final String FUNC_GETPROTOCOLVERSION = "getProtocolVersion";

    public static final String FUNC_GETTOTALBATCHESCOMMITTED = "getTotalBatchesCommitted";

    public static final String FUNC_GETTOTALBATCHESEXECUTED = "getTotalBatchesExecuted";

    public static final String FUNC_GETTOTALBATCHESVERIFIED = "getTotalBatchesVerified";

    public static final String FUNC_GETTOTALPRIORITYTXS = "getTotalPriorityTxs";

    public static final String FUNC_GETVERIFIER = "getVerifier";

    public static final String FUNC_GETVERIFIERPARAMS = "getVerifierParams";

    public static final String FUNC_ISDIAMONDSTORAGEFROZEN = "isDiamondStorageFrozen";

    public static final String FUNC_ISETHWITHDRAWALFINALIZED = "isEthWithdrawalFinalized";

    public static final String FUNC_ISFACETFREEZABLE = "isFacetFreezable";

    public static final String FUNC_ISFUNCTIONFREEZABLE = "isFunctionFreezable";

    public static final String FUNC_ISVALIDATOR = "isValidator";

    public static final String FUNC_L2LOGSROOTHASH = "l2LogsRootHash";

    public static final String FUNC_L2TRANSACTIONBASECOST = "l2TransactionBaseCost";

    public static final String FUNC_PRIORITYQUEUEFRONTOPERATION = "priorityQueueFrontOperation";

    public static final String FUNC_PROVEBATCHES = "proveBatches";

    public static final String FUNC_PROVEL1TOL2TRANSACTIONSTATUS = "proveL1ToL2TransactionStatus";

    public static final String FUNC_PROVEL2LOGINCLUSION = "proveL2LogInclusion";

    public static final String FUNC_PROVEL2MESSAGEINCLUSION = "proveL2MessageInclusion";

    public static final String FUNC_REQUESTL2TRANSACTION = "requestL2Transaction";

    public static final String FUNC_REVERTBATCHES = "revertBatches";

    public static final String FUNC_SETPENDINGADMIN = "setPendingAdmin";

    public static final String FUNC_SETPENDINGGOVERNOR = "setPendingGovernor";

    public static final String FUNC_SETPORTERAVAILABILITY = "setPorterAvailability";

    public static final String FUNC_SETPRIORITYTXMAXGASLIMIT = "setPriorityTxMaxGasLimit";

    public static final String FUNC_SETVALIDATOR = "setValidator";

    public static final String FUNC_STOREDBATCHHASH = "storedBatchHash";

    public static final String FUNC_UNFREEZEDIAMOND = "unfreezeDiamond";

    public static final Event BLOCKCOMMIT_EVENT = new Event("BlockCommit",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event BLOCKEXECUTION_EVENT = new Event("BlockExecution",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event BLOCKSREVERT_EVENT = new Event("BlocksRevert",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event BLOCKSVERIFICATION_EVENT = new Event("BlocksVerification",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event ETHWITHDRAWALFINALIZED_EVENT = new Event("EthWithdrawalFinalized",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event EXECUTEUPGRADE_EVENT = new Event("ExecuteUpgrade",
            Arrays.<TypeReference<?>>asList(new TypeReference<IZkSync.DiamondCutData>() {}));
    ;

    public static final Event FREEZE_EVENT = new Event("Freeze",
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event ISPORTERAVAILABLESTATUSUPDATE_EVENT = new Event("IsPorterAvailableStatusUpdate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    public static final Event NEWADMIN_EVENT = new Event("NewAdmin",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWGOVERNOR_EVENT = new Event("NewGovernor",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWPENDINGADMIN_EVENT = new Event("NewPendingAdmin",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWPENDINGGOVERNOR_EVENT = new Event("NewPendingGovernor",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWPRIORITYREQUEST_EVENT = new Event("NewPriorityRequest",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}, new TypeReference<IZkSync.L2CanonicalTransaction>() {}, new TypeReference<DynamicArray<DynamicBytes>>() {}));
    ;

    public static final Event NEWPRIORITYTXMAXGASLIMIT_EVENT = new Event("NewPriorityTxMaxGasLimit",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNFREEZE_EVENT = new Event("Unfreeze",
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event VALIDATORSTATUSUPDATE_EVENT = new Event("ValidatorStatusUpdate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
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

    public static List<IZkSync.BlockCommitEventResponse> getBlockCommitEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKCOMMIT_EVENT, transactionReceipt);
        ArrayList<IZkSync.BlockCommitEventResponse> responses = new ArrayList<IZkSync.BlockCommitEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.BlockCommitEventResponse typedResponse = new IZkSync.BlockCommitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.BlockCommitEventResponse getBlockCommitEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKCOMMIT_EVENT, log);
        IZkSync.BlockCommitEventResponse typedResponse = new IZkSync.BlockCommitEventResponse();
        typedResponse.log = log;
        typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.BlockCommitEventResponse> blockCommitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlockCommitEventFromLog(log));
    }

    public Flowable<IZkSync.BlockCommitEventResponse> blockCommitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKCOMMIT_EVENT));
        return blockCommitEventFlowable(filter);
    }

    public static List<IZkSync.BlockExecutionEventResponse> getBlockExecutionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKEXECUTION_EVENT, transactionReceipt);
        ArrayList<IZkSync.BlockExecutionEventResponse> responses = new ArrayList<IZkSync.BlockExecutionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.BlockExecutionEventResponse typedResponse = new IZkSync.BlockExecutionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.BlockExecutionEventResponse getBlockExecutionEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKEXECUTION_EVENT, log);
        IZkSync.BlockExecutionEventResponse typedResponse = new IZkSync.BlockExecutionEventResponse();
        typedResponse.log = log;
        typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.BlockExecutionEventResponse> blockExecutionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlockExecutionEventFromLog(log));
    }

    public Flowable<IZkSync.BlockExecutionEventResponse> blockExecutionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKEXECUTION_EVENT));
        return blockExecutionEventFlowable(filter);
    }

    public static List<IZkSync.BlocksRevertEventResponse> getBlocksRevertEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKSREVERT_EVENT, transactionReceipt);
        ArrayList<IZkSync.BlocksRevertEventResponse> responses = new ArrayList<IZkSync.BlocksRevertEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.BlocksRevertEventResponse typedResponse = new IZkSync.BlocksRevertEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.totalBatchesCommitted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.totalBatchesVerified = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.totalBatchesExecuted = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.BlocksRevertEventResponse getBlocksRevertEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKSREVERT_EVENT, log);
        IZkSync.BlocksRevertEventResponse typedResponse = new IZkSync.BlocksRevertEventResponse();
        typedResponse.log = log;
        typedResponse.totalBatchesCommitted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.totalBatchesVerified = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.totalBatchesExecuted = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.BlocksRevertEventResponse> blocksRevertEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlocksRevertEventFromLog(log));
    }

    public Flowable<IZkSync.BlocksRevertEventResponse> blocksRevertEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKSREVERT_EVENT));
        return blocksRevertEventFlowable(filter);
    }

    public static List<IZkSync.BlocksVerificationEventResponse> getBlocksVerificationEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKSVERIFICATION_EVENT, transactionReceipt);
        ArrayList<IZkSync.BlocksVerificationEventResponse> responses = new ArrayList<IZkSync.BlocksVerificationEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.BlocksVerificationEventResponse typedResponse = new IZkSync.BlocksVerificationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.currentLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.BlocksVerificationEventResponse getBlocksVerificationEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKSVERIFICATION_EVENT, log);
        IZkSync.BlocksVerificationEventResponse typedResponse = new IZkSync.BlocksVerificationEventResponse();
        typedResponse.log = log;
        typedResponse.previousLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.currentLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.BlocksVerificationEventResponse> blocksVerificationEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlocksVerificationEventFromLog(log));
    }

    public Flowable<IZkSync.BlocksVerificationEventResponse> blocksVerificationEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKSVERIFICATION_EVENT));
        return blocksVerificationEventFlowable(filter);
    }

    public static List<IZkSync.EthWithdrawalFinalizedEventResponse> getEthWithdrawalFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ETHWITHDRAWALFINALIZED_EVENT, transactionReceipt);
        ArrayList<IZkSync.EthWithdrawalFinalizedEventResponse> responses = new ArrayList<IZkSync.EthWithdrawalFinalizedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.EthWithdrawalFinalizedEventResponse typedResponse = new IZkSync.EthWithdrawalFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.EthWithdrawalFinalizedEventResponse getEthWithdrawalFinalizedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ETHWITHDRAWALFINALIZED_EVENT, log);
        IZkSync.EthWithdrawalFinalizedEventResponse typedResponse = new IZkSync.EthWithdrawalFinalizedEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.EthWithdrawalFinalizedEventResponse> ethWithdrawalFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEthWithdrawalFinalizedEventFromLog(log));
    }

    public Flowable<IZkSync.EthWithdrawalFinalizedEventResponse> ethWithdrawalFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ETHWITHDRAWALFINALIZED_EVENT));
        return ethWithdrawalFinalizedEventFlowable(filter);
    }

    public static List<IZkSync.ExecuteUpgradeEventResponse> getExecuteUpgradeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EXECUTEUPGRADE_EVENT, transactionReceipt);
        ArrayList<IZkSync.ExecuteUpgradeEventResponse> responses = new ArrayList<IZkSync.ExecuteUpgradeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.ExecuteUpgradeEventResponse typedResponse = new IZkSync.ExecuteUpgradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.diamondCut = (IZkSync.DiamondCutData) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.ExecuteUpgradeEventResponse getExecuteUpgradeEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EXECUTEUPGRADE_EVENT, log);
        IZkSync.ExecuteUpgradeEventResponse typedResponse = new IZkSync.ExecuteUpgradeEventResponse();
        typedResponse.log = log;
        typedResponse.diamondCut = (IZkSync.DiamondCutData) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<IZkSync.ExecuteUpgradeEventResponse> executeUpgradeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getExecuteUpgradeEventFromLog(log));
    }

    public Flowable<IZkSync.ExecuteUpgradeEventResponse> executeUpgradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXECUTEUPGRADE_EVENT));
        return executeUpgradeEventFlowable(filter);
    }

    public static List<IZkSync.FreezeEventResponse> getFreezeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FREEZE_EVENT, transactionReceipt);
        ArrayList<IZkSync.FreezeEventResponse> responses = new ArrayList<IZkSync.FreezeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.FreezeEventResponse typedResponse = new IZkSync.FreezeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.FreezeEventResponse getFreezeEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FREEZE_EVENT, log);
        IZkSync.FreezeEventResponse typedResponse = new IZkSync.FreezeEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<IZkSync.FreezeEventResponse> freezeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFreezeEventFromLog(log));
    }

    public Flowable<IZkSync.FreezeEventResponse> freezeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FREEZE_EVENT));
        return freezeEventFlowable(filter);
    }

    public static List<IZkSync.IsPorterAvailableStatusUpdateEventResponse> getIsPorterAvailableStatusUpdateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ISPORTERAVAILABLESTATUSUPDATE_EVENT, transactionReceipt);
        ArrayList<IZkSync.IsPorterAvailableStatusUpdateEventResponse> responses = new ArrayList<IZkSync.IsPorterAvailableStatusUpdateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.IsPorterAvailableStatusUpdateEventResponse typedResponse = new IZkSync.IsPorterAvailableStatusUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.isPorterAvailable = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.IsPorterAvailableStatusUpdateEventResponse getIsPorterAvailableStatusUpdateEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ISPORTERAVAILABLESTATUSUPDATE_EVENT, log);
        IZkSync.IsPorterAvailableStatusUpdateEventResponse typedResponse = new IZkSync.IsPorterAvailableStatusUpdateEventResponse();
        typedResponse.log = log;
        typedResponse.isPorterAvailable = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.IsPorterAvailableStatusUpdateEventResponse> isPorterAvailableStatusUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getIsPorterAvailableStatusUpdateEventFromLog(log));
    }

    public Flowable<IZkSync.IsPorterAvailableStatusUpdateEventResponse> isPorterAvailableStatusUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISPORTERAVAILABLESTATUSUPDATE_EVENT));
        return isPorterAvailableStatusUpdateEventFlowable(filter);
    }

    public static List<IZkSync.NewAdminEventResponse> getNewAdminEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWADMIN_EVENT, transactionReceipt);
        ArrayList<IZkSync.NewAdminEventResponse> responses = new ArrayList<IZkSync.NewAdminEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.NewAdminEventResponse typedResponse = new IZkSync.NewAdminEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.NewAdminEventResponse getNewAdminEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWADMIN_EVENT, log);
        IZkSync.NewAdminEventResponse typedResponse = new IZkSync.NewAdminEventResponse();
        typedResponse.log = log;
        typedResponse.oldAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.NewAdminEventResponse> newAdminEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewAdminEventFromLog(log));
    }

    public Flowable<IZkSync.NewAdminEventResponse> newAdminEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWADMIN_EVENT));
        return newAdminEventFlowable(filter);
    }

    public static List<IZkSync.NewGovernorEventResponse> getNewGovernorEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWGOVERNOR_EVENT, transactionReceipt);
        ArrayList<IZkSync.NewGovernorEventResponse> responses = new ArrayList<IZkSync.NewGovernorEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.NewGovernorEventResponse typedResponse = new IZkSync.NewGovernorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.NewGovernorEventResponse getNewGovernorEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWGOVERNOR_EVENT, log);
        IZkSync.NewGovernorEventResponse typedResponse = new IZkSync.NewGovernorEventResponse();
        typedResponse.log = log;
        typedResponse.oldGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.NewGovernorEventResponse> newGovernorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewGovernorEventFromLog(log));
    }

    public Flowable<IZkSync.NewGovernorEventResponse> newGovernorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWGOVERNOR_EVENT));
        return newGovernorEventFlowable(filter);
    }

    public static List<IZkSync.NewPendingAdminEventResponse> getNewPendingAdminEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPENDINGADMIN_EVENT, transactionReceipt);
        ArrayList<IZkSync.NewPendingAdminEventResponse> responses = new ArrayList<IZkSync.NewPendingAdminEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.NewPendingAdminEventResponse typedResponse = new IZkSync.NewPendingAdminEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldPendingAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newPendingAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.NewPendingAdminEventResponse getNewPendingAdminEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPENDINGADMIN_EVENT, log);
        IZkSync.NewPendingAdminEventResponse typedResponse = new IZkSync.NewPendingAdminEventResponse();
        typedResponse.log = log;
        typedResponse.oldPendingAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newPendingAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.NewPendingAdminEventResponse> newPendingAdminEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPendingAdminEventFromLog(log));
    }

    public Flowable<IZkSync.NewPendingAdminEventResponse> newPendingAdminEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPENDINGADMIN_EVENT));
        return newPendingAdminEventFlowable(filter);
    }

    public static List<IZkSync.NewPendingGovernorEventResponse> getNewPendingGovernorEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPENDINGGOVERNOR_EVENT, transactionReceipt);
        ArrayList<IZkSync.NewPendingGovernorEventResponse> responses = new ArrayList<IZkSync.NewPendingGovernorEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.NewPendingGovernorEventResponse typedResponse = new IZkSync.NewPendingGovernorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldPendingGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newPendingGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.NewPendingGovernorEventResponse getNewPendingGovernorEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPENDINGGOVERNOR_EVENT, log);
        IZkSync.NewPendingGovernorEventResponse typedResponse = new IZkSync.NewPendingGovernorEventResponse();
        typedResponse.log = log;
        typedResponse.oldPendingGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newPendingGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.NewPendingGovernorEventResponse> newPendingGovernorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPendingGovernorEventFromLog(log));
    }

    public Flowable<IZkSync.NewPendingGovernorEventResponse> newPendingGovernorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPENDINGGOVERNOR_EVENT));
        return newPendingGovernorEventFlowable(filter);
    }

    public static List<IZkSync.NewPriorityRequestEventResponse> getNewPriorityRequestEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPRIORITYREQUEST_EVENT, transactionReceipt);
        ArrayList<IZkSync.NewPriorityRequestEventResponse> responses = new ArrayList<IZkSync.NewPriorityRequestEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.NewPriorityRequestEventResponse typedResponse = new IZkSync.NewPriorityRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.txId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.txHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.expirationTimestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.transaction = (IZkSync.L2CanonicalTransaction) eventValues.getNonIndexedValues().get(3);
            typedResponse.factoryDeps = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(4)).getNativeValueCopy();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.NewPriorityRequestEventResponse getNewPriorityRequestEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPRIORITYREQUEST_EVENT, log);
        IZkSync.NewPriorityRequestEventResponse typedResponse = new IZkSync.NewPriorityRequestEventResponse();
        typedResponse.log = log;
        typedResponse.txId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.txHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.expirationTimestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.transaction = (IZkSync.L2CanonicalTransaction) eventValues.getNonIndexedValues().get(3);
        typedResponse.factoryDeps = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(4)).getNativeValueCopy();
        return typedResponse;
    }

    public Flowable<IZkSync.NewPriorityRequestEventResponse> newPriorityRequestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPriorityRequestEventFromLog(log));
    }

    public Flowable<IZkSync.NewPriorityRequestEventResponse> newPriorityRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYREQUEST_EVENT));
        return newPriorityRequestEventFlowable(filter);
    }

    public static List<IZkSync.NewPriorityTxMaxGasLimitEventResponse> getNewPriorityTxMaxGasLimitEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPRIORITYTXMAXGASLIMIT_EVENT, transactionReceipt);
        ArrayList<IZkSync.NewPriorityTxMaxGasLimitEventResponse> responses = new ArrayList<IZkSync.NewPriorityTxMaxGasLimitEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.NewPriorityTxMaxGasLimitEventResponse typedResponse = new IZkSync.NewPriorityTxMaxGasLimitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.NewPriorityTxMaxGasLimitEventResponse getNewPriorityTxMaxGasLimitEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPRIORITYTXMAXGASLIMIT_EVENT, log);
        IZkSync.NewPriorityTxMaxGasLimitEventResponse typedResponse = new IZkSync.NewPriorityTxMaxGasLimitEventResponse();
        typedResponse.log = log;
        typedResponse.oldPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.NewPriorityTxMaxGasLimitEventResponse> newPriorityTxMaxGasLimitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPriorityTxMaxGasLimitEventFromLog(log));
    }

    public Flowable<IZkSync.NewPriorityTxMaxGasLimitEventResponse> newPriorityTxMaxGasLimitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYTXMAXGASLIMIT_EVENT));
        return newPriorityTxMaxGasLimitEventFlowable(filter);
    }

    public static List<IZkSync.UnfreezeEventResponse> getUnfreezeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UNFREEZE_EVENT, transactionReceipt);
        ArrayList<IZkSync.UnfreezeEventResponse> responses = new ArrayList<IZkSync.UnfreezeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.UnfreezeEventResponse typedResponse = new IZkSync.UnfreezeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.UnfreezeEventResponse getUnfreezeEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UNFREEZE_EVENT, log);
        IZkSync.UnfreezeEventResponse typedResponse = new IZkSync.UnfreezeEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<IZkSync.UnfreezeEventResponse> unfreezeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUnfreezeEventFromLog(log));
    }

    public Flowable<IZkSync.UnfreezeEventResponse> unfreezeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNFREEZE_EVENT));
        return unfreezeEventFlowable(filter);
    }

    public static List<IZkSync.ValidatorStatusUpdateEventResponse> getValidatorStatusUpdateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALIDATORSTATUSUPDATE_EVENT, transactionReceipt);
        ArrayList<IZkSync.ValidatorStatusUpdateEventResponse> responses = new ArrayList<IZkSync.ValidatorStatusUpdateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IZkSync.ValidatorStatusUpdateEventResponse typedResponse = new IZkSync.ValidatorStatusUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.validatorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.isActive = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IZkSync.ValidatorStatusUpdateEventResponse getValidatorStatusUpdateEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALIDATORSTATUSUPDATE_EVENT, log);
        IZkSync.ValidatorStatusUpdateEventResponse typedResponse = new IZkSync.ValidatorStatusUpdateEventResponse();
        typedResponse.log = log;
        typedResponse.validatorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.isActive = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<IZkSync.ValidatorStatusUpdateEventResponse> validatorStatusUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValidatorStatusUpdateEventFromLog(log));
    }

    public Flowable<IZkSync.ValidatorStatusUpdateEventResponse> validatorStatusUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALIDATORSTATUSUPDATE_EVENT));
        return validatorStatusUpdateEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptAdmin() {
        final Function function = new Function(
                FUNC_ACCEPTADMIN,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptGovernor() {
        final Function function = new Function(
                FUNC_ACCEPTGOVERNOR,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> commitBatches(IZkSync.StoredBatchInfo _lastCommittedBatchData, List<IZkSync.CommitBatchInfo> _newBatchesData) {
        final Function function = new Function(
                FUNC_COMMITBATCHES,
                Arrays.<Type>asList(_lastCommittedBatchData,
                        new DynamicArray<IZkSync.CommitBatchInfo>(IZkSync.CommitBatchInfo.class, _newBatchesData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeBatches(List<IZkSync.StoredBatchInfo> _batchesData) {
        final Function function = new Function(
                FUNC_EXECUTEBATCHES,
                Arrays.<Type>asList(new DynamicArray<IZkSync.StoredBatchInfo>(IZkSync.StoredBatchInfo.class, _batchesData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeUpgrade(IZkSync.DiamondCutData _diamondCut) {
        final Function function = new Function(
                FUNC_EXECUTEUPGRADE,
                Arrays.<Type>asList(_diamondCut),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> facetAddress(byte[] _selector) {
        final Function function = new Function(FUNC_FACETADDRESS,
                Arrays.<Type>asList(new Bytes4(_selector)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> facetAddresses() {
        final Function function = new Function(FUNC_FACETADDRESSES,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> facetFunctionSelectors(String _facet) {
        final Function function = new Function(FUNC_FACETFUNCTIONSELECTORS,
                Arrays.<Type>asList(new Address(160, _facet)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes4>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> facets() {
        final Function function = new Function(FUNC_FACETS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<IZkSync.Facet>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeEthWithdrawal(BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, byte[] _message, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_FINALIZEETHWITHDRAWAL,
                Arrays.<Type>asList(new Uint256(_l2BatchNumber),
                        new Uint256(_l2MessageIndex),
                        new Uint16(_l2TxNumberInBatch),
                        new DynamicBytes(_message),
                        new DynamicArray<Bytes32>(
                                Bytes32.class,
                                org.web3j.abi.Utils.typeMap(_merkleProof, Bytes32.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> freezeDiamond() {
        final Function function = new Function(
                FUNC_FREEZEDIAMOND,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getFirstUnprocessedPriorityTx() {
        final Function function = new Function(FUNC_GETFIRSTUNPROCESSEDPRIORITYTX,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getGovernor() {
        final Function function = new Function(FUNC_GETGOVERNOR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> getL2BootloaderBytecodeHash() {
        final Function function = new Function(FUNC_GETL2BOOTLOADERBYTECODEHASH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> getL2DefaultAccountBytecodeHash() {
        final Function function = new Function(FUNC_GETL2DEFAULTACCOUNTBYTECODEHASH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getL2SystemContractsUpgradeBatchNumber() {
        final Function function = new Function(FUNC_GETL2SYSTEMCONTRACTSUPGRADEBATCHNUMBER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> getL2SystemContractsUpgradeTxHash() {
        final Function function = new Function(FUNC_GETL2SYSTEMCONTRACTSUPGRADETXHASH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> getName() {
        final Function function = new Function(FUNC_GETNAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getPendingGovernor() {
        final Function function = new Function(FUNC_GETPENDINGGOVERNOR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getPriorityQueueSize() {
        final Function function = new Function(FUNC_GETPRIORITYQUEUESIZE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPriorityTxMaxGasLimit() {
        final Function function = new Function(FUNC_GETPRIORITYTXMAXGASLIMIT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getProtocolVersion() {
        final Function function = new Function(FUNC_GETPROTOCOLVERSION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBatchesCommitted() {
        final Function function = new Function(FUNC_GETTOTALBATCHESCOMMITTED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBatchesExecuted() {
        final Function function = new Function(FUNC_GETTOTALBATCHESEXECUTED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBatchesVerified() {
        final Function function = new Function(FUNC_GETTOTALBATCHESVERIFIED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalPriorityTxs() {
        final Function function = new Function(FUNC_GETTOTALPRIORITYTXS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getVerifier() {
        final Function function = new Function(FUNC_GETVERIFIER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<IZkSync.VerifierParams> getVerifierParams() {
        final Function function = new Function(FUNC_GETVERIFIERPARAMS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<IZkSync.VerifierParams>() {}));
        return executeRemoteCallSingleValueReturn(function, IZkSync.VerifierParams.class);
    }

    public RemoteFunctionCall<Boolean> isDiamondStorageFrozen() {
        final Function function = new Function(FUNC_ISDIAMONDSTORAGEFROZEN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isEthWithdrawalFinalized(BigInteger _l2BatchNumber, BigInteger _l2MessageIndex) {
        final Function function = new Function(FUNC_ISETHWITHDRAWALFINALIZED,
                Arrays.<Type>asList(new Uint256(_l2BatchNumber),
                        new Uint256(_l2MessageIndex)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isFacetFreezable(String _facet) {
        final Function function = new Function(FUNC_ISFACETFREEZABLE,
                Arrays.<Type>asList(new Address(160, _facet)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isFunctionFreezable(byte[] _selector) {
        final Function function = new Function(FUNC_ISFUNCTIONFREEZABLE,
                Arrays.<Type>asList(new Bytes4(_selector)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isValidator(String _address) {
        final Function function = new Function(FUNC_ISVALIDATOR,
                Arrays.<Type>asList(new Address(160, _address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> l2LogsRootHash(BigInteger _batchNumber) {
        final Function function = new Function(FUNC_L2LOGSROOTHASH,
                Arrays.<Type>asList(new Uint256(_batchNumber)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> l2TransactionBaseCost(BigInteger _gasPrice, BigInteger _l2GasLimit, BigInteger _l2GasPerPubdataByteLimit) {
        final Function function = new Function(FUNC_L2TRANSACTIONBASECOST,
                Arrays.<Type>asList(new Uint256(_gasPrice),
                        new Uint256(_l2GasLimit),
                        new Uint256(_l2GasPerPubdataByteLimit)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<IZkSync.PriorityOperation> priorityQueueFrontOperation() {
        final Function function = new Function(FUNC_PRIORITYQUEUEFRONTOPERATION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<IZkSync.PriorityOperation>() {}));
        return executeRemoteCallSingleValueReturn(function, IZkSync.PriorityOperation.class);
    }

    public RemoteFunctionCall<TransactionReceipt> proveBatches(IZkSync.StoredBatchInfo _prevBatch, List<IZkSync.StoredBatchInfo> _committedBatches, IZkSync.ProofInput _proof) {
        final Function function = new Function(
                FUNC_PROVEBATCHES,
                Arrays.<Type>asList(_prevBatch,
                        new DynamicArray<IZkSync.StoredBatchInfo>(IZkSync.StoredBatchInfo.class, _committedBatches),
                        _proof),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> proveL1ToL2TransactionStatus(byte[] _l2TxHash, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, List<byte[]> _merkleProof, BigInteger _status) {
        final Function function = new Function(FUNC_PROVEL1TOL2TRANSACTIONSTATUS,
                Arrays.<Type>asList(new Bytes32(_l2TxHash),
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

    public RemoteFunctionCall<Boolean> proveL2LogInclusion(BigInteger _l2BatchNumber, BigInteger _index, IZkSync.L2Log _log, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2LOGINCLUSION,
                Arrays.<Type>asList(new Uint256(_l2BatchNumber),
                        new Uint256(_index),
                        _log,
                        new DynamicArray<Bytes32>(
                                Bytes32.class,
                                org.web3j.abi.Utils.typeMap(_proof, Bytes32.class))),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> proveL2MessageInclusion(BigInteger _l2BatchNumber, BigInteger _index, IZkSync.L2Message _message, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2MESSAGEINCLUSION,
                Arrays.<Type>asList(new Uint256(_l2BatchNumber),
                        new Uint256(_index),
                        _message,
                        new DynamicArray<Bytes32>(
                                Bytes32.class,
                                org.web3j.abi.Utils.typeMap(_proof, Bytes32.class))),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> requestL2Transaction(String _contractL2, BigInteger _l2Value, byte[] _calldata, BigInteger _l2GasLimit, BigInteger _l2GasPerPubdataByteLimit, List<byte[]> _factoryDeps, String _refundRecipient, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REQUESTL2TRANSACTION,
                Arrays.<Type>asList(new Address(160, _contractL2),
                        new Uint256(_l2Value),
                        new DynamicBytes(_calldata),
                        new Uint256(_l2GasLimit),
                        new Uint256(_l2GasPerPubdataByteLimit),
                        new DynamicArray<DynamicBytes>(
                                DynamicBytes.class,
                                org.web3j.abi.Utils.typeMap(_factoryDeps, DynamicBytes.class)),
                        new Address(160, _refundRecipient)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> revertBatches(BigInteger _newLastBatch) {
        final Function function = new Function(
                FUNC_REVERTBATCHES,
                Arrays.<Type>asList(new Uint256(_newLastBatch)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPendingAdmin(String _newPendingAdmin) {
        final Function function = new Function(
                FUNC_SETPENDINGADMIN,
                Arrays.<Type>asList(new Address(160, _newPendingAdmin)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPendingGovernor(String _newPendingGovernor) {
        final Function function = new Function(
                FUNC_SETPENDINGGOVERNOR,
                Arrays.<Type>asList(new Address(160, _newPendingGovernor)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPorterAvailability(Boolean _zkPorterIsAvailable) {
        final Function function = new Function(
                FUNC_SETPORTERAVAILABILITY,
                Arrays.<Type>asList(new Bool(_zkPorterIsAvailable)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPriorityTxMaxGasLimit(BigInteger _newPriorityTxMaxGasLimit) {
        final Function function = new Function(
                FUNC_SETPRIORITYTXMAXGASLIMIT,
                Arrays.<Type>asList(new Uint256(_newPriorityTxMaxGasLimit)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValidator(String _validator, Boolean _active) {
        final Function function = new Function(
                FUNC_SETVALIDATOR,
                Arrays.<Type>asList(new Address(160, _validator),
                        new Bool(_active)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> storedBatchHash(BigInteger _batchNumber) {
        final Function function = new Function(FUNC_STOREDBATCHHASH,
                Arrays.<Type>asList(new Uint256(_batchNumber)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> unfreezeDiamond() {
        final Function function = new Function(
                FUNC_UNFREEZEDIAMOND,
                Arrays.<Type>asList(),
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

    public static class FacetCut extends DynamicStruct {
        public String facet;

        public BigInteger action;

        public Boolean isFreezable;

        public List<byte[]> selectors;

        public FacetCut(String facet, BigInteger action, Boolean isFreezable, List<byte[]> selectors) {
            super(new Address(160, facet),
                    new Uint8(action),
                    new Bool(isFreezable),
                    new DynamicArray<Bytes4>(
                            Bytes4.class,
                            org.web3j.abi.Utils.typeMap(selectors, Bytes4.class)));
            this.facet = facet;
            this.action = action;
            this.isFreezable = isFreezable;
            this.selectors = selectors;
        }

        public FacetCut(Address facet, Uint8 action, Bool isFreezable, DynamicArray<Bytes4> selectors) {
            super(facet, action, isFreezable, selectors);
            this.facet = facet.getValue();
            this.action = action.getValue();
            this.isFreezable = isFreezable.getValue();
            this.selectors = selectors.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }
    }

    public static class L2CanonicalTransaction extends DynamicStruct {
        public BigInteger txType;

        public BigInteger from;

        public BigInteger to;

        public BigInteger gasLimit;

        public BigInteger gasPerPubdataByteLimit;

        public BigInteger maxFeePerGas;

        public BigInteger maxPriorityFeePerGas;

        public BigInteger paymaster;

        public BigInteger nonce;

        public BigInteger value;

        public List<BigInteger> reserved;

        public byte[] data;

        public byte[] signature;

        public List<BigInteger> factoryDeps;

        public byte[] paymasterInput;

        public byte[] reservedDynamic;

        public L2CanonicalTransaction(BigInteger txType, BigInteger from, BigInteger to, BigInteger gasLimit, BigInteger gasPerPubdataByteLimit, BigInteger maxFeePerGas, BigInteger maxPriorityFeePerGas, BigInteger paymaster, BigInteger nonce, BigInteger value, List<BigInteger> reserved, byte[] data, byte[] signature, List<BigInteger> factoryDeps, byte[] paymasterInput, byte[] reservedDynamic) {
            super(new Uint256(txType),
                    new Uint256(from),
                    new Uint256(to),
                    new Uint256(gasLimit),
                    new Uint256(gasPerPubdataByteLimit),
                    new Uint256(maxFeePerGas),
                    new Uint256(maxPriorityFeePerGas),
                    new Uint256(paymaster),
                    new Uint256(nonce),
                    new Uint256(value),
                    new StaticArray4<Uint256>(
                            Uint256.class,
                            org.web3j.abi.Utils.typeMap(reserved, Uint256.class)),
                    new DynamicBytes(data),
                    new DynamicBytes(signature),
                    new DynamicArray<Uint256>(
                            Uint256.class,
                            org.web3j.abi.Utils.typeMap(factoryDeps, Uint256.class)),
                    new DynamicBytes(paymasterInput),
                    new DynamicBytes(reservedDynamic));
            this.txType = txType;
            this.from = from;
            this.to = to;
            this.gasLimit = gasLimit;
            this.gasPerPubdataByteLimit = gasPerPubdataByteLimit;
            this.maxFeePerGas = maxFeePerGas;
            this.maxPriorityFeePerGas = maxPriorityFeePerGas;
            this.paymaster = paymaster;
            this.nonce = nonce;
            this.value = value;
            this.reserved = reserved;
            this.data = data;
            this.signature = signature;
            this.factoryDeps = factoryDeps;
            this.paymasterInput = paymasterInput;
            this.reservedDynamic = reservedDynamic;
        }

        public L2CanonicalTransaction(Uint256 txType, Uint256 from, Uint256 to, Uint256 gasLimit, Uint256 gasPerPubdataByteLimit, Uint256 maxFeePerGas, Uint256 maxPriorityFeePerGas, Uint256 paymaster, Uint256 nonce, Uint256 value, StaticArray4<Uint256> reserved, DynamicBytes data, DynamicBytes signature, DynamicArray<Uint256> factoryDeps, DynamicBytes paymasterInput, DynamicBytes reservedDynamic) {
            super(txType, from, to, gasLimit, gasPerPubdataByteLimit, maxFeePerGas, maxPriorityFeePerGas, paymaster, nonce, value, reserved, data, signature, factoryDeps, paymasterInput, reservedDynamic);
            this.txType = txType.getValue();
            this.from = from.getValue();
            this.to = to.getValue();
            this.gasLimit = gasLimit.getValue();
            this.gasPerPubdataByteLimit = gasPerPubdataByteLimit.getValue();
            this.maxFeePerGas = maxFeePerGas.getValue();
            this.maxPriorityFeePerGas = maxPriorityFeePerGas.getValue();
            this.paymaster = paymaster.getValue();
            this.nonce = nonce.getValue();
            this.value = value.getValue();
            this.reserved = reserved.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.data = data.getValue();
            this.signature = signature.getValue();
            this.factoryDeps = factoryDeps.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.paymasterInput = paymasterInput.getValue();
            this.reservedDynamic = reservedDynamic.getValue();
        }
    }

    public static class StoredBatchInfo extends StaticStruct {
        public BigInteger batchNumber;

        public byte[] batchHash;

        public BigInteger indexRepeatedStorageChanges;

        public BigInteger numberOfLayer1Txs;

        public byte[] priorityOperationsHash;

        public byte[] l2LogsTreeRoot;

        public BigInteger timestamp;

        public byte[] commitment;

        public StoredBatchInfo(BigInteger batchNumber, byte[] batchHash, BigInteger indexRepeatedStorageChanges, BigInteger numberOfLayer1Txs, byte[] priorityOperationsHash, byte[] l2LogsTreeRoot, BigInteger timestamp, byte[] commitment) {
            super(new Uint64(batchNumber),
                    new Bytes32(batchHash),
                    new Uint64(indexRepeatedStorageChanges),
                    new Uint256(numberOfLayer1Txs),
                    new Bytes32(priorityOperationsHash),
                    new Bytes32(l2LogsTreeRoot),
                    new Uint256(timestamp),
                    new Bytes32(commitment));
            this.batchNumber = batchNumber;
            this.batchHash = batchHash;
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges;
            this.numberOfLayer1Txs = numberOfLayer1Txs;
            this.priorityOperationsHash = priorityOperationsHash;
            this.l2LogsTreeRoot = l2LogsTreeRoot;
            this.timestamp = timestamp;
            this.commitment = commitment;
        }

        public StoredBatchInfo(Uint64 batchNumber, Bytes32 batchHash, Uint64 indexRepeatedStorageChanges, Uint256 numberOfLayer1Txs, Bytes32 priorityOperationsHash, Bytes32 l2LogsTreeRoot, Uint256 timestamp, Bytes32 commitment) {
            super(batchNumber, batchHash, indexRepeatedStorageChanges, numberOfLayer1Txs, priorityOperationsHash, l2LogsTreeRoot, timestamp, commitment);
            this.batchNumber = batchNumber.getValue();
            this.batchHash = batchHash.getValue();
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges.getValue();
            this.numberOfLayer1Txs = numberOfLayer1Txs.getValue();
            this.priorityOperationsHash = priorityOperationsHash.getValue();
            this.l2LogsTreeRoot = l2LogsTreeRoot.getValue();
            this.timestamp = timestamp.getValue();
            this.commitment = commitment.getValue();
        }
    }

    public static class CommitBatchInfo extends DynamicStruct {
        public BigInteger batchNumber;

        public BigInteger timestamp;

        public BigInteger indexRepeatedStorageChanges;

        public byte[] newStateRoot;

        public BigInteger numberOfLayer1Txs;

        public byte[] priorityOperationsHash;

        public byte[] bootloaderHeapInitialContentsHash;

        public byte[] eventsQueueStateHash;

        public byte[] systemLogs;

        public byte[] totalL2ToL1Pubdata;

        public CommitBatchInfo(BigInteger batchNumber, BigInteger timestamp, BigInteger indexRepeatedStorageChanges, byte[] newStateRoot, BigInteger numberOfLayer1Txs, byte[] priorityOperationsHash, byte[] bootloaderHeapInitialContentsHash, byte[] eventsQueueStateHash, byte[] systemLogs, byte[] totalL2ToL1Pubdata) {
            super(new Uint64(batchNumber),
                    new Uint64(timestamp),
                    new Uint64(indexRepeatedStorageChanges),
                    new Bytes32(newStateRoot),
                    new Uint256(numberOfLayer1Txs),
                    new Bytes32(priorityOperationsHash),
                    new Bytes32(bootloaderHeapInitialContentsHash),
                    new Bytes32(eventsQueueStateHash),
                    new DynamicBytes(systemLogs),
                    new DynamicBytes(totalL2ToL1Pubdata));
            this.batchNumber = batchNumber;
            this.timestamp = timestamp;
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges;
            this.newStateRoot = newStateRoot;
            this.numberOfLayer1Txs = numberOfLayer1Txs;
            this.priorityOperationsHash = priorityOperationsHash;
            this.bootloaderHeapInitialContentsHash = bootloaderHeapInitialContentsHash;
            this.eventsQueueStateHash = eventsQueueStateHash;
            this.systemLogs = systemLogs;
            this.totalL2ToL1Pubdata = totalL2ToL1Pubdata;
        }

        public CommitBatchInfo(Uint64 batchNumber, Uint64 timestamp, Uint64 indexRepeatedStorageChanges, Bytes32 newStateRoot, Uint256 numberOfLayer1Txs, Bytes32 priorityOperationsHash, Bytes32 bootloaderHeapInitialContentsHash, Bytes32 eventsQueueStateHash, DynamicBytes systemLogs, DynamicBytes totalL2ToL1Pubdata) {
            super(batchNumber, timestamp, indexRepeatedStorageChanges, newStateRoot, numberOfLayer1Txs, priorityOperationsHash, bootloaderHeapInitialContentsHash, eventsQueueStateHash, systemLogs, totalL2ToL1Pubdata);
            this.batchNumber = batchNumber.getValue();
            this.timestamp = timestamp.getValue();
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges.getValue();
            this.newStateRoot = newStateRoot.getValue();
            this.numberOfLayer1Txs = numberOfLayer1Txs.getValue();
            this.priorityOperationsHash = priorityOperationsHash.getValue();
            this.bootloaderHeapInitialContentsHash = bootloaderHeapInitialContentsHash.getValue();
            this.eventsQueueStateHash = eventsQueueStateHash.getValue();
            this.systemLogs = systemLogs.getValue();
            this.totalL2ToL1Pubdata = totalL2ToL1Pubdata.getValue();
        }
    }

    public static class Facet extends DynamicStruct {
        public String addr;

        public List<byte[]> selectors;

        public Facet(String addr, List<byte[]> selectors) {
            super(new Address(160, addr),
                    new DynamicArray<Bytes4>(
                            Bytes4.class,
                            org.web3j.abi.Utils.typeMap(selectors, Bytes4.class)));
            this.addr = addr;
            this.selectors = selectors;
        }

        public Facet(Address addr, DynamicArray<Bytes4> selectors) {
            super(addr, selectors);
            this.addr = addr.getValue();
            this.selectors = selectors.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }
    }

    public static class VerifierParams extends StaticStruct {
        public byte[] recursionNodeLevelVkHash;

        public byte[] recursionLeafLevelVkHash;

        public byte[] recursionCircuitsSetVksHash;

        public VerifierParams(byte[] recursionNodeLevelVkHash, byte[] recursionLeafLevelVkHash, byte[] recursionCircuitsSetVksHash) {
            super(new Bytes32(recursionNodeLevelVkHash),
                    new Bytes32(recursionLeafLevelVkHash),
                    new Bytes32(recursionCircuitsSetVksHash));
            this.recursionNodeLevelVkHash = recursionNodeLevelVkHash;
            this.recursionLeafLevelVkHash = recursionLeafLevelVkHash;
            this.recursionCircuitsSetVksHash = recursionCircuitsSetVksHash;
        }

        public VerifierParams(Bytes32 recursionNodeLevelVkHash, Bytes32 recursionLeafLevelVkHash, Bytes32 recursionCircuitsSetVksHash) {
            super(recursionNodeLevelVkHash, recursionLeafLevelVkHash, recursionCircuitsSetVksHash);
            this.recursionNodeLevelVkHash = recursionNodeLevelVkHash.getValue();
            this.recursionLeafLevelVkHash = recursionLeafLevelVkHash.getValue();
            this.recursionCircuitsSetVksHash = recursionCircuitsSetVksHash.getValue();
        }
    }

    public static class PriorityOperation extends StaticStruct {
        public byte[] canonicalTxHash;

        public BigInteger expirationTimestamp;

        public BigInteger layer2Tip;

        public PriorityOperation(byte[] canonicalTxHash, BigInteger expirationTimestamp, BigInteger layer2Tip) {
            super(new Bytes32(canonicalTxHash),
                    new Uint64(expirationTimestamp),
                    new Uint192(layer2Tip));
            this.canonicalTxHash = canonicalTxHash;
            this.expirationTimestamp = expirationTimestamp;
            this.layer2Tip = layer2Tip;
        }

        public PriorityOperation(Bytes32 canonicalTxHash, Uint64 expirationTimestamp, Uint192 layer2Tip) {
            super(canonicalTxHash, expirationTimestamp, layer2Tip);
            this.canonicalTxHash = canonicalTxHash.getValue();
            this.expirationTimestamp = expirationTimestamp.getValue();
            this.layer2Tip = layer2Tip.getValue();
        }
    }

    public static class ProofInput extends DynamicStruct {
        public List<BigInteger> recursiveAggregationInput;

        public List<BigInteger> serializedProof;

        public ProofInput(List<BigInteger> recursiveAggregationInput, List<BigInteger> serializedProof) {
            super(new DynamicArray<Uint256>(
                            Uint256.class,
                            org.web3j.abi.Utils.typeMap(recursiveAggregationInput, Uint256.class)),
                    new DynamicArray<Uint256>(
                            Uint256.class,
                            org.web3j.abi.Utils.typeMap(serializedProof, Uint256.class)));
            this.recursiveAggregationInput = recursiveAggregationInput;
            this.serializedProof = serializedProof;
        }

        public ProofInput(DynamicArray<Uint256> recursiveAggregationInput, DynamicArray<Uint256> serializedProof) {
            super(recursiveAggregationInput, serializedProof);
            this.recursiveAggregationInput = recursiveAggregationInput.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.serializedProof = serializedProof.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }
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

    public static class DiamondCutData extends DynamicStruct {
        public List<IZkSync.FacetCut> facetCuts;

        public String initAddress;

        public byte[] initCalldata;

        public DiamondCutData(List<IZkSync.FacetCut> facetCuts, String initAddress, byte[] initCalldata) {
            super(new DynamicArray<IZkSync.FacetCut>(IZkSync.FacetCut.class, facetCuts),
                    new Address(160, initAddress),
                    new DynamicBytes(initCalldata));
            this.facetCuts = facetCuts;
            this.initAddress = initAddress;
            this.initCalldata = initCalldata;
        }

        public DiamondCutData(@Parameterized(type = IZkSync.FacetCut.class) DynamicArray<IZkSync.FacetCut> facetCuts, Address initAddress, DynamicBytes initCalldata) {
            super(facetCuts, initAddress, initCalldata);
            this.facetCuts = facetCuts.getValue();
            this.initAddress = initAddress.getValue();
            this.initCalldata = initCalldata.getValue();
        }
    }

    public static class BlockCommitEventResponse extends BaseEventResponse {
        public BigInteger batchNumber;

        public byte[] batchHash;

        public byte[] commitment;
    }

    public static class BlockExecutionEventResponse extends BaseEventResponse {
        public BigInteger batchNumber;

        public byte[] batchHash;

        public byte[] commitment;
    }

    public static class BlocksRevertEventResponse extends BaseEventResponse {
        public BigInteger totalBatchesCommitted;

        public BigInteger totalBatchesVerified;

        public BigInteger totalBatchesExecuted;
    }

    public static class BlocksVerificationEventResponse extends BaseEventResponse {
        public BigInteger previousLastVerifiedBatch;

        public BigInteger currentLastVerifiedBatch;
    }

    public static class EthWithdrawalFinalizedEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger amount;
    }

    public static class ExecuteUpgradeEventResponse extends BaseEventResponse {
        public IZkSync.DiamondCutData diamondCut;
    }

    public static class FreezeEventResponse extends BaseEventResponse {
    }

    public static class IsPorterAvailableStatusUpdateEventResponse extends BaseEventResponse {
        public Boolean isPorterAvailable;
    }

    public static class NewAdminEventResponse extends BaseEventResponse {
        public String oldAdmin;

        public String newAdmin;
    }

    public static class NewGovernorEventResponse extends BaseEventResponse {
        public String oldGovernor;

        public String newGovernor;
    }

    public static class NewPendingAdminEventResponse extends BaseEventResponse {
        public String oldPendingAdmin;

        public String newPendingAdmin;
    }

    public static class NewPendingGovernorEventResponse extends BaseEventResponse {
        public String oldPendingGovernor;

        public String newPendingGovernor;
    }

    public static class NewPriorityRequestEventResponse extends BaseEventResponse {
        public BigInteger txId;

        public byte[] txHash;

        public BigInteger expirationTimestamp;

        public IZkSync.L2CanonicalTransaction transaction;

        public List<byte[]> factoryDeps;
    }

    public static class NewPriorityTxMaxGasLimitEventResponse extends BaseEventResponse {
        public BigInteger oldPriorityTxMaxGasLimit;

        public BigInteger newPriorityTxMaxGasLimit;
    }

    public static class UnfreezeEventResponse extends BaseEventResponse {
    }

    public static class ValidatorStatusUpdateEventResponse extends BaseEventResponse {
        public String validatorAddress;

        public Boolean isActive;
    }
}
