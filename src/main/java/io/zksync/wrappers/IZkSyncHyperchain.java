package io.zksync.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.StaticArray4;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint192;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.abi.datatypes.reflection.Parameterized;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class IZkSyncHyperchain extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ACCEPTADMIN = "acceptAdmin";

    public static final String FUNC_BASETOKENGASPRICEMULTIPLIERDENOMINATOR = "baseTokenGasPriceMultiplierDenominator";

    public static final String FUNC_BASETOKENGASPRICEMULTIPLIERNOMINATOR = "baseTokenGasPriceMultiplierNominator";

    public static final String FUNC_BRIDGEHUBREQUESTL2TRANSACTION = "bridgehubRequestL2Transaction";

    public static final String FUNC_CHANGEFEEPARAMS = "changeFeeParams";

    public static final String FUNC_COMMITBATCHES = "commitBatches";

    public static final String FUNC_COMMITBATCHESSHAREDBRIDGE = "commitBatchesSharedBridge";

    public static final String FUNC_EXECUTEBATCHES = "executeBatches";

    public static final String FUNC_EXECUTEBATCHESSHAREDBRIDGE = "executeBatchesSharedBridge";

    public static final String FUNC_EXECUTEUPGRADE = "executeUpgrade";

    public static final String FUNC_FACETADDRESS = "facetAddress";

    public static final String FUNC_FACETADDRESSES = "facetAddresses";

    public static final String FUNC_FACETFUNCTIONSELECTORS = "facetFunctionSelectors";

    public static final String FUNC_FACETS = "facets";

    public static final String FUNC_FINALIZEETHWITHDRAWAL = "finalizeEthWithdrawal";

    public static final String FUNC_FREEZEDIAMOND = "freezeDiamond";

    public static final String FUNC_GETADMIN = "getAdmin";

    public static final String FUNC_GETBASETOKEN = "getBaseToken";

    public static final String FUNC_GETBASETOKENBRIDGE = "getBaseTokenBridge";

    public static final String FUNC_GETBRIDGEHUB = "getBridgehub";

    public static final String FUNC_GETFIRSTUNPROCESSEDPRIORITYTX = "getFirstUnprocessedPriorityTx";

    public static final String FUNC_GETL2BOOTLOADERBYTECODEHASH = "getL2BootloaderBytecodeHash";

    public static final String FUNC_GETL2DEFAULTACCOUNTBYTECODEHASH = "getL2DefaultAccountBytecodeHash";

    public static final String FUNC_GETL2SYSTEMCONTRACTSUPGRADEBATCHNUMBER = "getL2SystemContractsUpgradeBatchNumber";

    public static final String FUNC_GETL2SYSTEMCONTRACTSUPGRADETXHASH = "getL2SystemContractsUpgradeTxHash";

    public static final String FUNC_GETNAME = "getName";

    public static final String FUNC_GETPENDINGADMIN = "getPendingAdmin";

    public static final String FUNC_GETPRIORITYQUEUESIZE = "getPriorityQueueSize";

    public static final String FUNC_GETPRIORITYTXMAXGASLIMIT = "getPriorityTxMaxGasLimit";

    public static final String FUNC_GETPROTOCOLVERSION = "getProtocolVersion";

    public static final String FUNC_GETPUBDATAPRICINGMODE = "getPubdataPricingMode";

    public static final String FUNC_GETSEMVERPROTOCOLVERSION = "getSemverProtocolVersion";

    public static final String FUNC_GETSTATETRANSITIONMANAGER = "getStateTransitionManager";

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

    public static final String FUNC_PROVEBATCHESSHAREDBRIDGE = "proveBatchesSharedBridge";

    public static final String FUNC_PROVEL1TOL2TRANSACTIONSTATUS = "proveL1ToL2TransactionStatus";

    public static final String FUNC_PROVEL2LOGINCLUSION = "proveL2LogInclusion";

    public static final String FUNC_PROVEL2MESSAGEINCLUSION = "proveL2MessageInclusion";

    public static final String FUNC_REQUESTL2TRANSACTION = "requestL2Transaction";

    public static final String FUNC_REVERTBATCHES = "revertBatches";

    public static final String FUNC_REVERTBATCHESSHAREDBRIDGE = "revertBatchesSharedBridge";

    public static final String FUNC_SETPENDINGADMIN = "setPendingAdmin";

    public static final String FUNC_SETPORTERAVAILABILITY = "setPorterAvailability";

    public static final String FUNC_SETPRIORITYTXMAXGASLIMIT = "setPriorityTxMaxGasLimit";

    public static final String FUNC_SETPUBDATAPRICINGMODE = "setPubdataPricingMode";

    public static final String FUNC_SETTOKENMULTIPLIER = "setTokenMultiplier";

    public static final String FUNC_SETTRANSACTIONFILTERER = "setTransactionFilterer";

    public static final String FUNC_SETVALIDATOR = "setValidator";

    public static final String FUNC_STOREDBATCHHASH = "storedBatchHash";

    public static final String FUNC_TRANSFERETHTOSHAREDBRIDGE = "transferEthToSharedBridge";

    public static final String FUNC_UNFREEZEDIAMOND = "unfreezeDiamond";

    public static final String FUNC_UPGRADECHAINFROMVERSION = "upgradeChainFromVersion";

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

    public static final Event EXECUTEUPGRADE_EVENT = new Event("ExecuteUpgrade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DiamondCutData>() {}));
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

    public static final Event NEWBASETOKENMULTIPLIER_EVENT = new Event("NewBaseTokenMultiplier", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {}, new TypeReference<Uint128>() {}, new TypeReference<Uint128>() {}, new TypeReference<Uint128>() {}));
    ;

    public static final Event NEWFEEPARAMS_EVENT = new Event("NewFeeParams", 
            Arrays.<TypeReference<?>>asList(new TypeReference<FeeParams>() {}, new TypeReference<FeeParams>() {}));
    ;

    public static final Event NEWPENDINGADMIN_EVENT = new Event("NewPendingAdmin", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWPRIORITYREQUEST_EVENT = new Event("NewPriorityRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}, new TypeReference<L2CanonicalTransaction>() {}, new TypeReference<DynamicArray<DynamicBytes>>() {}));
    ;

    public static final Event NEWPRIORITYTXMAXGASLIMIT_EVENT = new Event("NewPriorityTxMaxGasLimit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NEWTRANSACTIONFILTERER_EVENT = new Event("NewTransactionFilterer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event PROPOSETRANSPARENTUPGRADE_EVENT = new Event("ProposeTransparentUpgrade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DiamondCutData>() {}, new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event UNFREEZE_EVENT = new Event("Unfreeze", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event VALIDATORSTATUSUPDATE_EVENT = new Event("ValidatorStatusUpdate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    public static final Event VALIDIUMMODESTATUSUPDATE_EVENT = new Event("ValidiumModeStatusUpdate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
    ;

    @Deprecated
    protected IZkSyncHyperchain(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IZkSyncHyperchain(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IZkSyncHyperchain(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IZkSyncHyperchain(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BlockCommitEventResponse> getBlockCommitEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKCOMMIT_EVENT, transactionReceipt);
        ArrayList<BlockCommitEventResponse> responses = new ArrayList<BlockCommitEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockCommitEventResponse typedResponse = new BlockCommitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlockCommitEventResponse getBlockCommitEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKCOMMIT_EVENT, log);
        BlockCommitEventResponse typedResponse = new BlockCommitEventResponse();
        typedResponse.log = log;
        typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<BlockCommitEventResponse> blockCommitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlockCommitEventFromLog(log));
    }

    public Flowable<BlockCommitEventResponse> blockCommitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKCOMMIT_EVENT));
        return blockCommitEventFlowable(filter);
    }

    public static List<BlockExecutionEventResponse> getBlockExecutionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKEXECUTION_EVENT, transactionReceipt);
        ArrayList<BlockExecutionEventResponse> responses = new ArrayList<BlockExecutionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockExecutionEventResponse typedResponse = new BlockExecutionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlockExecutionEventResponse getBlockExecutionEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKEXECUTION_EVENT, log);
        BlockExecutionEventResponse typedResponse = new BlockExecutionEventResponse();
        typedResponse.log = log;
        typedResponse.batchNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.batchHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<BlockExecutionEventResponse> blockExecutionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlockExecutionEventFromLog(log));
    }

    public Flowable<BlockExecutionEventResponse> blockExecutionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKEXECUTION_EVENT));
        return blockExecutionEventFlowable(filter);
    }

    public static List<BlocksRevertEventResponse> getBlocksRevertEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKSREVERT_EVENT, transactionReceipt);
        ArrayList<BlocksRevertEventResponse> responses = new ArrayList<BlocksRevertEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlocksRevertEventResponse typedResponse = new BlocksRevertEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.totalBatchesCommitted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.totalBatchesVerified = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.totalBatchesExecuted = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlocksRevertEventResponse getBlocksRevertEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKSREVERT_EVENT, log);
        BlocksRevertEventResponse typedResponse = new BlocksRevertEventResponse();
        typedResponse.log = log;
        typedResponse.totalBatchesCommitted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.totalBatchesVerified = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.totalBatchesExecuted = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<BlocksRevertEventResponse> blocksRevertEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlocksRevertEventFromLog(log));
    }

    public Flowable<BlocksRevertEventResponse> blocksRevertEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKSREVERT_EVENT));
        return blocksRevertEventFlowable(filter);
    }

    public static List<BlocksVerificationEventResponse> getBlocksVerificationEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKSVERIFICATION_EVENT, transactionReceipt);
        ArrayList<BlocksVerificationEventResponse> responses = new ArrayList<BlocksVerificationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlocksVerificationEventResponse typedResponse = new BlocksVerificationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.currentLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlocksVerificationEventResponse getBlocksVerificationEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKSVERIFICATION_EVENT, log);
        BlocksVerificationEventResponse typedResponse = new BlocksVerificationEventResponse();
        typedResponse.log = log;
        typedResponse.previousLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.currentLastVerifiedBatch = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<BlocksVerificationEventResponse> blocksVerificationEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBlocksVerificationEventFromLog(log));
    }

    public Flowable<BlocksVerificationEventResponse> blocksVerificationEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKSVERIFICATION_EVENT));
        return blocksVerificationEventFlowable(filter);
    }

    public static List<ExecuteUpgradeEventResponse> getExecuteUpgradeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EXECUTEUPGRADE_EVENT, transactionReceipt);
        ArrayList<ExecuteUpgradeEventResponse> responses = new ArrayList<ExecuteUpgradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExecuteUpgradeEventResponse typedResponse = new ExecuteUpgradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.diamondCut = (DiamondCutData) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ExecuteUpgradeEventResponse getExecuteUpgradeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EXECUTEUPGRADE_EVENT, log);
        ExecuteUpgradeEventResponse typedResponse = new ExecuteUpgradeEventResponse();
        typedResponse.log = log;
        typedResponse.diamondCut = (DiamondCutData) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<ExecuteUpgradeEventResponse> executeUpgradeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getExecuteUpgradeEventFromLog(log));
    }

    public Flowable<ExecuteUpgradeEventResponse> executeUpgradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXECUTEUPGRADE_EVENT));
        return executeUpgradeEventFlowable(filter);
    }

    public static List<FreezeEventResponse> getFreezeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FREEZE_EVENT, transactionReceipt);
        ArrayList<FreezeEventResponse> responses = new ArrayList<FreezeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FreezeEventResponse typedResponse = new FreezeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FreezeEventResponse getFreezeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FREEZE_EVENT, log);
        FreezeEventResponse typedResponse = new FreezeEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<FreezeEventResponse> freezeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFreezeEventFromLog(log));
    }

    public Flowable<FreezeEventResponse> freezeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FREEZE_EVENT));
        return freezeEventFlowable(filter);
    }

    public static List<IsPorterAvailableStatusUpdateEventResponse> getIsPorterAvailableStatusUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ISPORTERAVAILABLESTATUSUPDATE_EVENT, transactionReceipt);
        ArrayList<IsPorterAvailableStatusUpdateEventResponse> responses = new ArrayList<IsPorterAvailableStatusUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            IsPorterAvailableStatusUpdateEventResponse typedResponse = new IsPorterAvailableStatusUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.isPorterAvailable = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static IsPorterAvailableStatusUpdateEventResponse getIsPorterAvailableStatusUpdateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ISPORTERAVAILABLESTATUSUPDATE_EVENT, log);
        IsPorterAvailableStatusUpdateEventResponse typedResponse = new IsPorterAvailableStatusUpdateEventResponse();
        typedResponse.log = log;
        typedResponse.isPorterAvailable = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<IsPorterAvailableStatusUpdateEventResponse> isPorterAvailableStatusUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getIsPorterAvailableStatusUpdateEventFromLog(log));
    }

    public Flowable<IsPorterAvailableStatusUpdateEventResponse> isPorterAvailableStatusUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISPORTERAVAILABLESTATUSUPDATE_EVENT));
        return isPorterAvailableStatusUpdateEventFlowable(filter);
    }

    public static List<NewAdminEventResponse> getNewAdminEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWADMIN_EVENT, transactionReceipt);
        ArrayList<NewAdminEventResponse> responses = new ArrayList<NewAdminEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewAdminEventResponse typedResponse = new NewAdminEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewAdminEventResponse getNewAdminEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWADMIN_EVENT, log);
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

    public static List<NewBaseTokenMultiplierEventResponse> getNewBaseTokenMultiplierEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWBASETOKENMULTIPLIER_EVENT, transactionReceipt);
        ArrayList<NewBaseTokenMultiplierEventResponse> responses = new ArrayList<NewBaseTokenMultiplierEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewBaseTokenMultiplierEventResponse typedResponse = new NewBaseTokenMultiplierEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldNominator = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.oldDenominator = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.newNominator = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.newDenominator = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewBaseTokenMultiplierEventResponse getNewBaseTokenMultiplierEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWBASETOKENMULTIPLIER_EVENT, log);
        NewBaseTokenMultiplierEventResponse typedResponse = new NewBaseTokenMultiplierEventResponse();
        typedResponse.log = log;
        typedResponse.oldNominator = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.oldDenominator = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.newNominator = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.newDenominator = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<NewBaseTokenMultiplierEventResponse> newBaseTokenMultiplierEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewBaseTokenMultiplierEventFromLog(log));
    }

    public Flowable<NewBaseTokenMultiplierEventResponse> newBaseTokenMultiplierEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWBASETOKENMULTIPLIER_EVENT));
        return newBaseTokenMultiplierEventFlowable(filter);
    }

    public static List<NewFeeParamsEventResponse> getNewFeeParamsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWFEEPARAMS_EVENT, transactionReceipt);
        ArrayList<NewFeeParamsEventResponse> responses = new ArrayList<NewFeeParamsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewFeeParamsEventResponse typedResponse = new NewFeeParamsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldFeeParams = (FeeParams) eventValues.getNonIndexedValues().get(0);
            typedResponse.newFeeParams = (FeeParams) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewFeeParamsEventResponse getNewFeeParamsEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWFEEPARAMS_EVENT, log);
        NewFeeParamsEventResponse typedResponse = new NewFeeParamsEventResponse();
        typedResponse.log = log;
        typedResponse.oldFeeParams = (FeeParams) eventValues.getNonIndexedValues().get(0);
        typedResponse.newFeeParams = (FeeParams) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<NewFeeParamsEventResponse> newFeeParamsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewFeeParamsEventFromLog(log));
    }

    public Flowable<NewFeeParamsEventResponse> newFeeParamsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWFEEPARAMS_EVENT));
        return newFeeParamsEventFlowable(filter);
    }

    public static List<NewPendingAdminEventResponse> getNewPendingAdminEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPENDINGADMIN_EVENT, transactionReceipt);
        ArrayList<NewPendingAdminEventResponse> responses = new ArrayList<NewPendingAdminEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPendingAdminEventResponse typedResponse = new NewPendingAdminEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldPendingAdmin = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newPendingAdmin = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewPendingAdminEventResponse getNewPendingAdminEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPENDINGADMIN_EVENT, log);
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

    public static List<NewPriorityRequestEventResponse> getNewPriorityRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPRIORITYREQUEST_EVENT, transactionReceipt);
        ArrayList<NewPriorityRequestEventResponse> responses = new ArrayList<NewPriorityRequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPriorityRequestEventResponse typedResponse = new NewPriorityRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.txId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.txHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.expirationTimestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.transaction = (L2CanonicalTransaction) eventValues.getNonIndexedValues().get(3);
            typedResponse.factoryDeps = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(4)).getNativeValueCopy();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewPriorityRequestEventResponse getNewPriorityRequestEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPRIORITYREQUEST_EVENT, log);
        NewPriorityRequestEventResponse typedResponse = new NewPriorityRequestEventResponse();
        typedResponse.log = log;
        typedResponse.txId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.txHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.expirationTimestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.transaction = (L2CanonicalTransaction) eventValues.getNonIndexedValues().get(3);
        typedResponse.factoryDeps = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(4)).getNativeValueCopy();
        return typedResponse;
    }

    public Flowable<NewPriorityRequestEventResponse> newPriorityRequestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPriorityRequestEventFromLog(log));
    }

    public Flowable<NewPriorityRequestEventResponse> newPriorityRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYREQUEST_EVENT));
        return newPriorityRequestEventFlowable(filter);
    }

    public static List<NewPriorityTxMaxGasLimitEventResponse> getNewPriorityTxMaxGasLimitEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPRIORITYTXMAXGASLIMIT_EVENT, transactionReceipt);
        ArrayList<NewPriorityTxMaxGasLimitEventResponse> responses = new ArrayList<NewPriorityTxMaxGasLimitEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPriorityTxMaxGasLimitEventResponse typedResponse = new NewPriorityTxMaxGasLimitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewPriorityTxMaxGasLimitEventResponse getNewPriorityTxMaxGasLimitEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPRIORITYTXMAXGASLIMIT_EVENT, log);
        NewPriorityTxMaxGasLimitEventResponse typedResponse = new NewPriorityTxMaxGasLimitEventResponse();
        typedResponse.log = log;
        typedResponse.oldPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newPriorityTxMaxGasLimit = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewPriorityTxMaxGasLimitEventResponse> newPriorityTxMaxGasLimitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPriorityTxMaxGasLimitEventFromLog(log));
    }

    public Flowable<NewPriorityTxMaxGasLimitEventResponse> newPriorityTxMaxGasLimitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYTXMAXGASLIMIT_EVENT));
        return newPriorityTxMaxGasLimitEventFlowable(filter);
    }

    public static List<NewTransactionFiltererEventResponse> getNewTransactionFiltererEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWTRANSACTIONFILTERER_EVENT, transactionReceipt);
        ArrayList<NewTransactionFiltererEventResponse> responses = new ArrayList<NewTransactionFiltererEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewTransactionFiltererEventResponse typedResponse = new NewTransactionFiltererEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldTransactionFilterer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newTransactionFilterer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewTransactionFiltererEventResponse getNewTransactionFiltererEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWTRANSACTIONFILTERER_EVENT, log);
        NewTransactionFiltererEventResponse typedResponse = new NewTransactionFiltererEventResponse();
        typedResponse.log = log;
        typedResponse.oldTransactionFilterer = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newTransactionFilterer = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewTransactionFiltererEventResponse> newTransactionFiltererEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewTransactionFiltererEventFromLog(log));
    }

    public Flowable<NewTransactionFiltererEventResponse> newTransactionFiltererEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWTRANSACTIONFILTERER_EVENT));
        return newTransactionFiltererEventFlowable(filter);
    }

    public static List<ProposeTransparentUpgradeEventResponse> getProposeTransparentUpgradeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PROPOSETRANSPARENTUPGRADE_EVENT, transactionReceipt);
        ArrayList<ProposeTransparentUpgradeEventResponse> responses = new ArrayList<ProposeTransparentUpgradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProposeTransparentUpgradeEventResponse typedResponse = new ProposeTransparentUpgradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.diamondCut = (DiamondCutData) eventValues.getNonIndexedValues().get(0);
            typedResponse.proposalSalt = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ProposeTransparentUpgradeEventResponse getProposeTransparentUpgradeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PROPOSETRANSPARENTUPGRADE_EVENT, log);
        ProposeTransparentUpgradeEventResponse typedResponse = new ProposeTransparentUpgradeEventResponse();
        typedResponse.log = log;
        typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.diamondCut = (DiamondCutData) eventValues.getNonIndexedValues().get(0);
        typedResponse.proposalSalt = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ProposeTransparentUpgradeEventResponse> proposeTransparentUpgradeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getProposeTransparentUpgradeEventFromLog(log));
    }

    public Flowable<ProposeTransparentUpgradeEventResponse> proposeTransparentUpgradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROPOSETRANSPARENTUPGRADE_EVENT));
        return proposeTransparentUpgradeEventFlowable(filter);
    }

    public static List<UnfreezeEventResponse> getUnfreezeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UNFREEZE_EVENT, transactionReceipt);
        ArrayList<UnfreezeEventResponse> responses = new ArrayList<UnfreezeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnfreezeEventResponse typedResponse = new UnfreezeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UnfreezeEventResponse getUnfreezeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UNFREEZE_EVENT, log);
        UnfreezeEventResponse typedResponse = new UnfreezeEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<UnfreezeEventResponse> unfreezeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUnfreezeEventFromLog(log));
    }

    public Flowable<UnfreezeEventResponse> unfreezeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNFREEZE_EVENT));
        return unfreezeEventFlowable(filter);
    }

    public static List<ValidatorStatusUpdateEventResponse> getValidatorStatusUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALIDATORSTATUSUPDATE_EVENT, transactionReceipt);
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

    public static ValidatorStatusUpdateEventResponse getValidatorStatusUpdateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALIDATORSTATUSUPDATE_EVENT, log);
        ValidatorStatusUpdateEventResponse typedResponse = new ValidatorStatusUpdateEventResponse();
        typedResponse.log = log;
        typedResponse.validatorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.isActive = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ValidatorStatusUpdateEventResponse> validatorStatusUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValidatorStatusUpdateEventFromLog(log));
    }

    public Flowable<ValidatorStatusUpdateEventResponse> validatorStatusUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALIDATORSTATUSUPDATE_EVENT));
        return validatorStatusUpdateEventFlowable(filter);
    }

    public static List<ValidiumModeStatusUpdateEventResponse> getValidiumModeStatusUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALIDIUMMODESTATUSUPDATE_EVENT, transactionReceipt);
        ArrayList<ValidiumModeStatusUpdateEventResponse> responses = new ArrayList<ValidiumModeStatusUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValidiumModeStatusUpdateEventResponse typedResponse = new ValidiumModeStatusUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.validiumMode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ValidiumModeStatusUpdateEventResponse getValidiumModeStatusUpdateEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALIDIUMMODESTATUSUPDATE_EVENT, log);
        ValidiumModeStatusUpdateEventResponse typedResponse = new ValidiumModeStatusUpdateEventResponse();
        typedResponse.log = log;
        typedResponse.validiumMode = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ValidiumModeStatusUpdateEventResponse> validiumModeStatusUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValidiumModeStatusUpdateEventFromLog(log));
    }

    public Flowable<ValidiumModeStatusUpdateEventResponse> validiumModeStatusUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALIDIUMMODESTATUSUPDATE_EVENT));
        return validiumModeStatusUpdateEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> acceptAdmin() {
        final Function function = new Function(
                FUNC_ACCEPTADMIN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> baseTokenGasPriceMultiplierDenominator() {
        final Function function = new Function(FUNC_BASETOKENGASPRICEMULTIPLIERDENOMINATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> baseTokenGasPriceMultiplierNominator() {
        final Function function = new Function(FUNC_BASETOKENGASPRICEMULTIPLIERNOMINATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint128>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> bridgehubRequestL2Transaction(BridgehubL2TransactionRequest _request) {
        final Function function = new Function(
                FUNC_BRIDGEHUBREQUESTL2TRANSACTION, 
                Arrays.<Type>asList(_request), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> changeFeeParams(FeeParams _newFeeParams) {
        final Function function = new Function(
                FUNC_CHANGEFEEPARAMS, 
                Arrays.<Type>asList(_newFeeParams), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> commitBatches(StoredBatchInfo _lastCommittedBatchData, List<CommitBatchInfo> _newBatchesData) {
        final Function function = new Function(
                FUNC_COMMITBATCHES, 
                Arrays.<Type>asList(_lastCommittedBatchData, 
                new org.web3j.abi.datatypes.DynamicArray<CommitBatchInfo>(CommitBatchInfo.class, _newBatchesData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> commitBatchesSharedBridge(BigInteger _chainId, StoredBatchInfo _lastCommittedBatchData, List<CommitBatchInfo> _newBatchesData) {
        final Function function = new Function(
                FUNC_COMMITBATCHESSHAREDBRIDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_chainId), 
                _lastCommittedBatchData, 
                new org.web3j.abi.datatypes.DynamicArray<CommitBatchInfo>(CommitBatchInfo.class, _newBatchesData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeBatches(List<StoredBatchInfo> _batchesData) {
        final Function function = new Function(
                FUNC_EXECUTEBATCHES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<StoredBatchInfo>(StoredBatchInfo.class, _batchesData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeBatchesSharedBridge(BigInteger _chainId, List<StoredBatchInfo> _batchesData) {
        final Function function = new Function(
                FUNC_EXECUTEBATCHESSHAREDBRIDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_chainId), 
                new org.web3j.abi.datatypes.DynamicArray<StoredBatchInfo>(StoredBatchInfo.class, _batchesData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeUpgrade(DiamondCutData _diamondCut) {
        final Function function = new Function(
                FUNC_EXECUTEUPGRADE, 
                Arrays.<Type>asList(_diamondCut), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> facetAddress(byte[] _selector) {
        final Function function = new Function(FUNC_FACETADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(_selector)), 
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _facet)), 
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
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Facet>>() {}));
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_l2BatchNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex), 
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new org.web3j.abi.datatypes.DynamicBytes(_message), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
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

    public RemoteFunctionCall<String> getAdmin() {
        final Function function = new Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getBaseToken() {
        final Function function = new Function(FUNC_GETBASETOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getBaseTokenBridge() {
        final Function function = new Function(FUNC_GETBASETOKENBRIDGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getBridgehub() {
        final Function function = new Function(FUNC_GETBRIDGEHUB, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getFirstUnprocessedPriorityTx() {
        final Function function = new Function(FUNC_GETFIRSTUNPROCESSEDPRIORITYTX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<String> getPendingAdmin() {
        final Function function = new Function(FUNC_GETPENDINGADMIN, 
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

    public RemoteFunctionCall<BigInteger> getPubdataPricingMode() {
        final Function function = new Function(FUNC_GETPUBDATAPRICINGMODE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>> getSemverProtocolVersion() {
        final Function function = new Function(FUNC_GETSEMVERPROTOCOLVERSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint32>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> getStateTransitionManager() {
        final Function function = new Function(FUNC_GETSTATETRANSITIONMANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<VerifierParams> getVerifierParams() {
        final Function function = new Function(FUNC_GETVERIFIERPARAMS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<VerifierParams>() {}));
        return executeRemoteCallSingleValueReturn(function, VerifierParams.class);
    }

    public RemoteFunctionCall<Boolean> isDiamondStorageFrozen() {
        final Function function = new Function(FUNC_ISDIAMONDSTORAGEFROZEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isEthWithdrawalFinalized(BigInteger _l2BatchNumber, BigInteger _l2MessageIndex) {
        final Function function = new Function(FUNC_ISETHWITHDRAWALFINALIZED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_l2BatchNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isFacetFreezable(String _facet) {
        final Function function = new Function(FUNC_ISFACETFREEZABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _facet)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isFunctionFreezable(byte[] _selector) {
        final Function function = new Function(FUNC_ISFUNCTIONFREEZABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(_selector)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isValidator(String _address) {
        final Function function = new Function(FUNC_ISVALIDATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> l2LogsRootHash(BigInteger _batchNumber) {
        final Function function = new Function(FUNC_L2LOGSROOTHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> l2TransactionBaseCost(BigInteger _gasPrice, BigInteger _l2GasLimit, BigInteger _l2GasPerPubdataByteLimit) {
        final Function function = new Function(FUNC_L2TRANSACTIONBASECOST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_gasPrice), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2GasLimit), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2GasPerPubdataByteLimit)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<PriorityOperation> priorityQueueFrontOperation() {
        final Function function = new Function(FUNC_PRIORITYQUEUEFRONTOPERATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<PriorityOperation>() {}));
        return executeRemoteCallSingleValueReturn(function, PriorityOperation.class);
    }

    public RemoteFunctionCall<TransactionReceipt> proveBatches(StoredBatchInfo _prevBatch, List<StoredBatchInfo> _committedBatches, ProofInput _proof) {
        final Function function = new Function(
                FUNC_PROVEBATCHES, 
                Arrays.<Type>asList(_prevBatch, 
                new org.web3j.abi.datatypes.DynamicArray<StoredBatchInfo>(StoredBatchInfo.class, _committedBatches), 
                _proof), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> proveBatchesSharedBridge(BigInteger _chainId, StoredBatchInfo _prevBatch, List<StoredBatchInfo> _committedBatches, ProofInput _proof) {
        final Function function = new Function(
                FUNC_PROVEBATCHESSHAREDBRIDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_chainId), 
                _prevBatch, 
                new org.web3j.abi.datatypes.DynamicArray<StoredBatchInfo>(StoredBatchInfo.class, _committedBatches), 
                _proof), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> proveL1ToL2TransactionStatus(byte[] _l2TxHash, BigInteger _l2BatchNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBatch, List<byte[]> _merkleProof, BigInteger _status) {
        final Function function = new Function(FUNC_PROVEL1TOL2TRANSACTIONSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_l2TxHash), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2BatchNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex), 
                new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBatch), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_merkleProof, org.web3j.abi.datatypes.generated.Bytes32.class)), 
                new org.web3j.abi.datatypes.generated.Uint8(_status)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> proveL2LogInclusion(BigInteger _batchNumber, BigInteger _index, L2Log _log, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2LOGINCLUSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_index), 
                _log, 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> proveL2MessageInclusion(BigInteger _batchNumber, BigInteger _index, L2Message _message, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2MESSAGEINCLUSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchNumber), 
                new org.web3j.abi.datatypes.generated.Uint256(_index), 
                _message, 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> requestL2Transaction(String _contractL2, BigInteger _l2Value, byte[] _calldata, BigInteger _l2GasLimit, BigInteger _l2GasPerPubdataByteLimit, List<byte[]> _factoryDeps, String _refundRecipient, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REQUESTL2TRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractL2), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2Value), 
                new org.web3j.abi.datatypes.DynamicBytes(_calldata), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2GasLimit), 
                new org.web3j.abi.datatypes.generated.Uint256(_l2GasPerPubdataByteLimit), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_factoryDeps, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.Address(160, _refundRecipient)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> revertBatches(BigInteger _newLastBatch) {
        final Function function = new Function(
                FUNC_REVERTBATCHES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_newLastBatch)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revertBatchesSharedBridge(BigInteger _chainId, BigInteger _newLastBatch) {
        final Function function = new Function(
                FUNC_REVERTBATCHESSHAREDBRIDGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_chainId), 
                new org.web3j.abi.datatypes.generated.Uint256(_newLastBatch)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPendingAdmin(String _newPendingAdmin) {
        final Function function = new Function(
                FUNC_SETPENDINGADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _newPendingAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPorterAvailability(Boolean _zkPorterIsAvailable) {
        final Function function = new Function(
                FUNC_SETPORTERAVAILABILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_zkPorterIsAvailable)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPriorityTxMaxGasLimit(BigInteger _newPriorityTxMaxGasLimit) {
        final Function function = new Function(
                FUNC_SETPRIORITYTXMAXGASLIMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_newPriorityTxMaxGasLimit)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPubdataPricingMode(BigInteger _pricingMode) {
        final Function function = new Function(
                FUNC_SETPUBDATAPRICINGMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(_pricingMode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTokenMultiplier(BigInteger _nominator, BigInteger _denominator) {
        final Function function = new Function(
                FUNC_SETTOKENMULTIPLIER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint128(_nominator), 
                new org.web3j.abi.datatypes.generated.Uint128(_denominator)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTransactionFilterer(String _transactionFilterer) {
        final Function function = new Function(
                FUNC_SETTRANSACTIONFILTERER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _transactionFilterer)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setValidator(String _validator, Boolean _active) {
        final Function function = new Function(
                FUNC_SETVALIDATOR, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _validator), 
                new org.web3j.abi.datatypes.Bool(_active)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> storedBatchHash(BigInteger _batchNumber) {
        final Function function = new Function(FUNC_STOREDBATCHHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_batchNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferEthToSharedBridge() {
        final Function function = new Function(
                FUNC_TRANSFERETHTOSHAREDBRIDGE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unfreezeDiamond() {
        final Function function = new Function(
                FUNC_UNFREEZEDIAMOND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeChainFromVersion(BigInteger _protocolVersion, DiamondCutData _cutData) {
        final Function function = new Function(
                FUNC_UPGRADECHAINFROMVERSION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_protocolVersion), 
                _cutData), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IZkSyncHyperchain load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IZkSyncHyperchain(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IZkSyncHyperchain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IZkSyncHyperchain(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IZkSyncHyperchain load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IZkSyncHyperchain(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IZkSyncHyperchain load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IZkSyncHyperchain(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class FacetCut extends DynamicStruct {
        public String facet;

        public BigInteger action;

        public Boolean isFreezable;

        public List<byte[]> selectors;

        public FacetCut(String facet, BigInteger action, Boolean isFreezable, List<byte[]> selectors) {
            super(new org.web3j.abi.datatypes.Address(160, facet), 
                    new org.web3j.abi.datatypes.generated.Uint8(action), 
                    new org.web3j.abi.datatypes.Bool(isFreezable), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes4>(
                            org.web3j.abi.datatypes.generated.Bytes4.class,
                            org.web3j.abi.Utils.typeMap(selectors, org.web3j.abi.datatypes.generated.Bytes4.class)));
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

    public static class FeeParams extends StaticStruct {
        public BigInteger pubdataPricingMode;

        public BigInteger batchOverheadL1Gas;

        public BigInteger maxPubdataPerBatch;

        public BigInteger maxL2GasPerBatch;

        public BigInteger priorityTxMaxPubdata;

        public BigInteger minimalL2GasPrice;

        public FeeParams(BigInteger pubdataPricingMode, BigInteger batchOverheadL1Gas, BigInteger maxPubdataPerBatch, BigInteger maxL2GasPerBatch, BigInteger priorityTxMaxPubdata, BigInteger minimalL2GasPrice) {
            super(new org.web3j.abi.datatypes.generated.Uint8(pubdataPricingMode), 
                    new org.web3j.abi.datatypes.generated.Uint32(batchOverheadL1Gas), 
                    new org.web3j.abi.datatypes.generated.Uint32(maxPubdataPerBatch), 
                    new org.web3j.abi.datatypes.generated.Uint32(maxL2GasPerBatch), 
                    new org.web3j.abi.datatypes.generated.Uint32(priorityTxMaxPubdata), 
                    new org.web3j.abi.datatypes.generated.Uint64(minimalL2GasPrice));
            this.pubdataPricingMode = pubdataPricingMode;
            this.batchOverheadL1Gas = batchOverheadL1Gas;
            this.maxPubdataPerBatch = maxPubdataPerBatch;
            this.maxL2GasPerBatch = maxL2GasPerBatch;
            this.priorityTxMaxPubdata = priorityTxMaxPubdata;
            this.minimalL2GasPrice = minimalL2GasPrice;
        }

        public FeeParams(Uint8 pubdataPricingMode, Uint32 batchOverheadL1Gas, Uint32 maxPubdataPerBatch, Uint32 maxL2GasPerBatch, Uint32 priorityTxMaxPubdata, Uint64 minimalL2GasPrice) {
            super(pubdataPricingMode, batchOverheadL1Gas, maxPubdataPerBatch, maxL2GasPerBatch, priorityTxMaxPubdata, minimalL2GasPrice);
            this.pubdataPricingMode = pubdataPricingMode.getValue();
            this.batchOverheadL1Gas = batchOverheadL1Gas.getValue();
            this.maxPubdataPerBatch = maxPubdataPerBatch.getValue();
            this.maxL2GasPerBatch = maxL2GasPerBatch.getValue();
            this.priorityTxMaxPubdata = priorityTxMaxPubdata.getValue();
            this.minimalL2GasPrice = minimalL2GasPrice.getValue();
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
            super(new org.web3j.abi.datatypes.generated.Uint256(txType), 
                    new org.web3j.abi.datatypes.generated.Uint256(from), 
                    new org.web3j.abi.datatypes.generated.Uint256(to), 
                    new org.web3j.abi.datatypes.generated.Uint256(gasLimit), 
                    new org.web3j.abi.datatypes.generated.Uint256(gasPerPubdataByteLimit), 
                    new org.web3j.abi.datatypes.generated.Uint256(maxFeePerGas), 
                    new org.web3j.abi.datatypes.generated.Uint256(maxPriorityFeePerGas), 
                    new org.web3j.abi.datatypes.generated.Uint256(paymaster), 
                    new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                    new org.web3j.abi.datatypes.generated.Uint256(value), 
                    new org.web3j.abi.datatypes.generated.StaticArray4<org.web3j.abi.datatypes.generated.Uint256>(
                            org.web3j.abi.datatypes.generated.Uint256.class,
                            org.web3j.abi.Utils.typeMap(reserved, org.web3j.abi.datatypes.generated.Uint256.class)), 
                    new org.web3j.abi.datatypes.DynamicBytes(data), 
                    new org.web3j.abi.datatypes.DynamicBytes(signature), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                            org.web3j.abi.datatypes.generated.Uint256.class,
                            org.web3j.abi.Utils.typeMap(factoryDeps, org.web3j.abi.datatypes.generated.Uint256.class)), 
                    new org.web3j.abi.datatypes.DynamicBytes(paymasterInput), 
                    new org.web3j.abi.datatypes.DynamicBytes(reservedDynamic));
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

    public static class BridgehubL2TransactionRequest extends DynamicStruct {
        public String sender;

        public String contractL2;

        public BigInteger mintValue;

        public BigInteger l2Value;

        public byte[] l2Calldata;

        public BigInteger l2GasLimit;

        public BigInteger l2GasPerPubdataByteLimit;

        public List<byte[]> factoryDeps;

        public String refundRecipient;

        public BridgehubL2TransactionRequest(String sender, String contractL2, BigInteger mintValue, BigInteger l2Value, byte[] l2Calldata, BigInteger l2GasLimit, BigInteger l2GasPerPubdataByteLimit, List<byte[]> factoryDeps, String refundRecipient) {
            super(new org.web3j.abi.datatypes.Address(160, sender), 
                    new org.web3j.abi.datatypes.Address(160, contractL2), 
                    new org.web3j.abi.datatypes.generated.Uint256(mintValue), 
                    new org.web3j.abi.datatypes.generated.Uint256(l2Value), 
                    new org.web3j.abi.datatypes.DynamicBytes(l2Calldata), 
                    new org.web3j.abi.datatypes.generated.Uint256(l2GasLimit), 
                    new org.web3j.abi.datatypes.generated.Uint256(l2GasPerPubdataByteLimit), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                            org.web3j.abi.datatypes.DynamicBytes.class,
                            org.web3j.abi.Utils.typeMap(factoryDeps, org.web3j.abi.datatypes.DynamicBytes.class)), 
                    new org.web3j.abi.datatypes.Address(160, refundRecipient));
            this.sender = sender;
            this.contractL2 = contractL2;
            this.mintValue = mintValue;
            this.l2Value = l2Value;
            this.l2Calldata = l2Calldata;
            this.l2GasLimit = l2GasLimit;
            this.l2GasPerPubdataByteLimit = l2GasPerPubdataByteLimit;
            this.factoryDeps = factoryDeps;
            this.refundRecipient = refundRecipient;
        }

        public BridgehubL2TransactionRequest(Address sender, Address contractL2, Uint256 mintValue, Uint256 l2Value, DynamicBytes l2Calldata, Uint256 l2GasLimit, Uint256 l2GasPerPubdataByteLimit, DynamicArray<DynamicBytes> factoryDeps, Address refundRecipient) {
            super(sender, contractL2, mintValue, l2Value, l2Calldata, l2GasLimit, l2GasPerPubdataByteLimit, factoryDeps, refundRecipient);
            this.sender = sender.getValue();
            this.contractL2 = contractL2.getValue();
            this.mintValue = mintValue.getValue();
            this.l2Value = l2Value.getValue();
            this.l2Calldata = l2Calldata.getValue();
            this.l2GasLimit = l2GasLimit.getValue();
            this.l2GasPerPubdataByteLimit = l2GasPerPubdataByteLimit.getValue();
            this.factoryDeps = factoryDeps.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.refundRecipient = refundRecipient.getValue();
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
            super(new org.web3j.abi.datatypes.generated.Uint64(batchNumber), 
                    new org.web3j.abi.datatypes.generated.Bytes32(batchHash), 
                    new org.web3j.abi.datatypes.generated.Uint64(indexRepeatedStorageChanges), 
                    new org.web3j.abi.datatypes.generated.Uint256(numberOfLayer1Txs), 
                    new org.web3j.abi.datatypes.generated.Bytes32(priorityOperationsHash), 
                    new org.web3j.abi.datatypes.generated.Bytes32(l2LogsTreeRoot), 
                    new org.web3j.abi.datatypes.generated.Uint256(timestamp), 
                    new org.web3j.abi.datatypes.generated.Bytes32(commitment));
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

        public byte[] pubdataCommitments;

        public CommitBatchInfo(BigInteger batchNumber, BigInteger timestamp, BigInteger indexRepeatedStorageChanges, byte[] newStateRoot, BigInteger numberOfLayer1Txs, byte[] priorityOperationsHash, byte[] bootloaderHeapInitialContentsHash, byte[] eventsQueueStateHash, byte[] systemLogs, byte[] pubdataCommitments) {
            super(new org.web3j.abi.datatypes.generated.Uint64(batchNumber), 
                    new org.web3j.abi.datatypes.generated.Uint64(timestamp), 
                    new org.web3j.abi.datatypes.generated.Uint64(indexRepeatedStorageChanges), 
                    new org.web3j.abi.datatypes.generated.Bytes32(newStateRoot), 
                    new org.web3j.abi.datatypes.generated.Uint256(numberOfLayer1Txs), 
                    new org.web3j.abi.datatypes.generated.Bytes32(priorityOperationsHash), 
                    new org.web3j.abi.datatypes.generated.Bytes32(bootloaderHeapInitialContentsHash), 
                    new org.web3j.abi.datatypes.generated.Bytes32(eventsQueueStateHash), 
                    new org.web3j.abi.datatypes.DynamicBytes(systemLogs), 
                    new org.web3j.abi.datatypes.DynamicBytes(pubdataCommitments));
            this.batchNumber = batchNumber;
            this.timestamp = timestamp;
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges;
            this.newStateRoot = newStateRoot;
            this.numberOfLayer1Txs = numberOfLayer1Txs;
            this.priorityOperationsHash = priorityOperationsHash;
            this.bootloaderHeapInitialContentsHash = bootloaderHeapInitialContentsHash;
            this.eventsQueueStateHash = eventsQueueStateHash;
            this.systemLogs = systemLogs;
            this.pubdataCommitments = pubdataCommitments;
        }

        public CommitBatchInfo(Uint64 batchNumber, Uint64 timestamp, Uint64 indexRepeatedStorageChanges, Bytes32 newStateRoot, Uint256 numberOfLayer1Txs, Bytes32 priorityOperationsHash, Bytes32 bootloaderHeapInitialContentsHash, Bytes32 eventsQueueStateHash, DynamicBytes systemLogs, DynamicBytes pubdataCommitments) {
            super(batchNumber, timestamp, indexRepeatedStorageChanges, newStateRoot, numberOfLayer1Txs, priorityOperationsHash, bootloaderHeapInitialContentsHash, eventsQueueStateHash, systemLogs, pubdataCommitments);
            this.batchNumber = batchNumber.getValue();
            this.timestamp = timestamp.getValue();
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges.getValue();
            this.newStateRoot = newStateRoot.getValue();
            this.numberOfLayer1Txs = numberOfLayer1Txs.getValue();
            this.priorityOperationsHash = priorityOperationsHash.getValue();
            this.bootloaderHeapInitialContentsHash = bootloaderHeapInitialContentsHash.getValue();
            this.eventsQueueStateHash = eventsQueueStateHash.getValue();
            this.systemLogs = systemLogs.getValue();
            this.pubdataCommitments = pubdataCommitments.getValue();
        }
    }

    public static class Facet extends DynamicStruct {
        public String addr;

        public List<byte[]> selectors;

        public Facet(String addr, List<byte[]> selectors) {
            super(new org.web3j.abi.datatypes.Address(160, addr), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes4>(
                            org.web3j.abi.datatypes.generated.Bytes4.class,
                            org.web3j.abi.Utils.typeMap(selectors, org.web3j.abi.datatypes.generated.Bytes4.class)));
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
            super(new org.web3j.abi.datatypes.generated.Bytes32(recursionNodeLevelVkHash), 
                    new org.web3j.abi.datatypes.generated.Bytes32(recursionLeafLevelVkHash), 
                    new org.web3j.abi.datatypes.generated.Bytes32(recursionCircuitsSetVksHash));
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
            super(new org.web3j.abi.datatypes.generated.Bytes32(canonicalTxHash), 
                    new org.web3j.abi.datatypes.generated.Uint64(expirationTimestamp), 
                    new org.web3j.abi.datatypes.generated.Uint192(layer2Tip));
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
            super(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                            org.web3j.abi.datatypes.generated.Uint256.class,
                            org.web3j.abi.Utils.typeMap(recursiveAggregationInput, org.web3j.abi.datatypes.generated.Uint256.class)), 
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                            org.web3j.abi.datatypes.generated.Uint256.class,
                            org.web3j.abi.Utils.typeMap(serializedProof, org.web3j.abi.datatypes.generated.Uint256.class)));
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
            super(new org.web3j.abi.datatypes.generated.Uint8(l2ShardId), 
                    new org.web3j.abi.datatypes.Bool(isService), 
                    new org.web3j.abi.datatypes.generated.Uint16(txNumberInBatch), 
                    new org.web3j.abi.datatypes.Address(160, sender), 
                    new org.web3j.abi.datatypes.generated.Bytes32(key), 
                    new org.web3j.abi.datatypes.generated.Bytes32(value));
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
            super(new org.web3j.abi.datatypes.generated.Uint16(txNumberInBatch), 
                    new org.web3j.abi.datatypes.Address(160, sender), 
                    new org.web3j.abi.datatypes.DynamicBytes(data));
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
        public List<FacetCut> facetCuts;

        public String initAddress;

        public byte[] initCalldata;

        public DiamondCutData(List<FacetCut> facetCuts, String initAddress, byte[] initCalldata) {
            super(new org.web3j.abi.datatypes.DynamicArray<FacetCut>(FacetCut.class, facetCuts), 
                    new org.web3j.abi.datatypes.Address(160, initAddress), 
                    new org.web3j.abi.datatypes.DynamicBytes(initCalldata));
            this.facetCuts = facetCuts;
            this.initAddress = initAddress;
            this.initCalldata = initCalldata;
        }

        public DiamondCutData(@Parameterized(type = FacetCut.class) DynamicArray<FacetCut> facetCuts, Address initAddress, DynamicBytes initCalldata) {
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

    public static class ExecuteUpgradeEventResponse extends BaseEventResponse {
        public DiamondCutData diamondCut;
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

    public static class NewBaseTokenMultiplierEventResponse extends BaseEventResponse {
        public BigInteger oldNominator;

        public BigInteger oldDenominator;

        public BigInteger newNominator;

        public BigInteger newDenominator;
    }

    public static class NewFeeParamsEventResponse extends BaseEventResponse {
        public FeeParams oldFeeParams;

        public FeeParams newFeeParams;
    }

    public static class NewPendingAdminEventResponse extends BaseEventResponse {
        public String oldPendingAdmin;

        public String newPendingAdmin;
    }

    public static class NewPriorityRequestEventResponse extends BaseEventResponse {
        public BigInteger txId;

        public byte[] txHash;

        public BigInteger expirationTimestamp;

        public L2CanonicalTransaction transaction;

        public List<byte[]> factoryDeps;
    }

    public static class NewPriorityTxMaxGasLimitEventResponse extends BaseEventResponse {
        public BigInteger oldPriorityTxMaxGasLimit;

        public BigInteger newPriorityTxMaxGasLimit;
    }

    public static class NewTransactionFiltererEventResponse extends BaseEventResponse {
        public String oldTransactionFilterer;

        public String newTransactionFilterer;
    }

    public static class ProposeTransparentUpgradeEventResponse extends BaseEventResponse {
        public BigInteger proposalId;

        public DiamondCutData diamondCut;

        public byte[] proposalSalt;
    }

    public static class UnfreezeEventResponse extends BaseEventResponse {
    }

    public static class ValidatorStatusUpdateEventResponse extends BaseEventResponse {
        public String validatorAddress;

        public Boolean isActive;
    }

    public static class ValidiumModeStatusUpdateEventResponse extends BaseEventResponse {
        public BigInteger validiumMode;
    }
}
