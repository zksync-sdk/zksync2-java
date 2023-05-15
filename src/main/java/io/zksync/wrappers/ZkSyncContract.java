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
    public static final String BINARY = "0x";

    public static final String FUNC_ACCEPTGOVERNOR = "acceptGovernor";

    public static final String FUNC_CANCELUPGRADEPROPOSAL = "cancelUpgradeProposal";

    public static final String FUNC_COMMITBLOCKS = "commitBlocks";

    public static final String FUNC_EXECUTEBLOCKS = "executeBlocks";

    public static final String FUNC_EXECUTEUPGRADE = "executeUpgrade";

    public static final String FUNC_FACETADDRESS = "facetAddress";

    public static final String FUNC_FACETADDRESSES = "facetAddresses";

    public static final String FUNC_FACETFUNCTIONSELECTORS = "facetFunctionSelectors";

    public static final String FUNC_FACETS = "facets";

    public static final String FUNC_FINALIZEETHWITHDRAWAL = "finalizeEthWithdrawal";

    public static final String FUNC_FREEZEDIAMOND = "freezeDiamond";

    public static final String FUNC_GETCURRENTPROPOSALID = "getCurrentProposalId";

    public static final String FUNC_GETFIRSTUNPROCESSEDPRIORITYTX = "getFirstUnprocessedPriorityTx";

    public static final String FUNC_GETGOVERNOR = "getGovernor";

    public static final String FUNC_GETL2BOOTLOADERBYTECODEHASH = "getL2BootloaderBytecodeHash";

    public static final String FUNC_GETL2DEFAULTACCOUNTBYTECODEHASH = "getL2DefaultAccountBytecodeHash";

    public static final String FUNC_GETPENDINGGOVERNOR = "getPendingGovernor";

    public static final String FUNC_GETPRIORITYQUEUESIZE = "getPriorityQueueSize";

    public static final String FUNC_GETPROPOSEDUPGRADEHASH = "getProposedUpgradeHash";

    public static final String FUNC_GETPROPOSEDUPGRADETIMESTAMP = "getProposedUpgradeTimestamp";

    public static final String FUNC_GETSECURITYCOUNCIL = "getSecurityCouncil";

    public static final String FUNC_GETTOTALBLOCKSCOMMITTED = "getTotalBlocksCommitted";

    public static final String FUNC_GETTOTALBLOCKSEXECUTED = "getTotalBlocksExecuted";

    public static final String FUNC_GETTOTALBLOCKSVERIFIED = "getTotalBlocksVerified";

    public static final String FUNC_GETTOTALPRIORITYTXS = "getTotalPriorityTxs";

    public static final String FUNC_GETUPGRADEPROPOSALSTATE = "getUpgradeProposalState";

    public static final String FUNC_GETVERIFIER = "getVerifier";

    public static final String FUNC_GETVERIFIERPARAMS = "getVerifierParams";

    public static final String FUNC_GETPRIORITYTXMAXGASLIMIT = "getpriorityTxMaxGasLimit";

    public static final String FUNC_ISAPPROVEDBYSECURITYCOUNCIL = "isApprovedBySecurityCouncil";

    public static final String FUNC_ISDIAMONDSTORAGEFROZEN = "isDiamondStorageFrozen";

    public static final String FUNC_ISETHWITHDRAWALFINALIZED = "isEthWithdrawalFinalized";

    public static final String FUNC_ISFACETFREEZABLE = "isFacetFreezable";

    public static final String FUNC_ISFUNCTIONFREEZABLE = "isFunctionFreezable";

    public static final String FUNC_ISVALIDATOR = "isValidator";

    public static final String FUNC_L2LOGSROOTHASH = "l2LogsRootHash";

    public static final String FUNC_L2TRANSACTIONBASECOST = "l2TransactionBaseCost";

    public static final String FUNC_PRIORITYQUEUEFRONTOPERATION = "priorityQueueFrontOperation";

    public static final String FUNC_PROPOSESHADOWUPGRADE = "proposeShadowUpgrade";

    public static final String FUNC_PROPOSETRANSPARENTUPGRADE = "proposeTransparentUpgrade";

    public static final String FUNC_PROVEBLOCKS = "proveBlocks";

    public static final String FUNC_PROVEL1TOL2TRANSACTIONSTATUS = "proveL1ToL2TransactionStatus";

    public static final String FUNC_PROVEL2LOGINCLUSION = "proveL2LogInclusion";

    public static final String FUNC_PROVEL2MESSAGEINCLUSION = "proveL2MessageInclusion";

    public static final String FUNC_REQUESTL2TRANSACTION = "requestL2Transaction";

    public static final String FUNC_REVERTBLOCKS = "revertBlocks";

    public static final String FUNC_SECURITYCOUNCILUPGRADEAPPROVE = "securityCouncilUpgradeApprove";

    public static final String FUNC_SERIALIZEL2TRANSACTION = "serializeL2Transaction";

    public static final String FUNC_SETL2BOOTLOADERBYTECODEHASH = "setL2BootloaderBytecodeHash";

    public static final String FUNC_SETL2DEFAULTACCOUNTBYTECODEHASH = "setL2DefaultAccountBytecodeHash";

    public static final String FUNC_SETPENDINGGOVERNOR = "setPendingGovernor";

    public static final String FUNC_SETPORTERAVAILABILITY = "setPorterAvailability";

    public static final String FUNC_SETPRIORITYTXMAXGASLIMIT = "setPriorityTxMaxGasLimit";

    public static final String FUNC_SETVALIDATOR = "setValidator";

    public static final String FUNC_SETVERIFIER = "setVerifier";

    public static final String FUNC_SETVERIFIERPARAMS = "setVerifierParams";

    public static final String FUNC_STOREDBLOCKHASH = "storedBlockHash";

    public static final String FUNC_UNFREEZEDIAMOND = "unfreezeDiamond";

    public static final String FUNC_UPGRADEPROPOSALHASH = "upgradeProposalHash";

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

    public static final Event CANCELUPGRADEPROPOSAL_EVENT = new Event("CancelUpgradeProposal",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event ETHWITHDRAWALFINALIZED_EVENT = new Event("EthWithdrawalFinalized",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event EXECUTEUPGRADE_EVENT = new Event("ExecuteUpgrade",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event FREEZE_EVENT = new Event("Freeze",
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event ISPORTERAVAILABLESTATUSUPDATE_EVENT = new Event("IsPorterAvailableStatusUpdate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    public static final Event NEWGOVERNOR_EVENT = new Event("NewGovernor",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWL2BOOTLOADERBYTECODEHASH_EVENT = new Event("NewL2BootloaderBytecodeHash",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event NEWL2DEFAULTACCOUNTBYTECODEHASH_EVENT = new Event("NewL2DefaultAccountBytecodeHash",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event NEWPENDINGGOVERNOR_EVENT = new Event("NewPendingGovernor",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWPRIORITYREQUEST_EVENT = new Event("NewPriorityRequest",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}, new TypeReference<L2CanonicalTransaction>() {}, new TypeReference<DynamicArray<DynamicBytes>>() {}));
    ;

    public static final Event NEWPRIORITYTXMAXGASLIMIT_EVENT = new Event("NewPriorityTxMaxGasLimit",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NEWVERIFIER_EVENT = new Event("NewVerifier",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event NEWVERIFIERPARAMS_EVENT = new Event("NewVerifierParams",
            Arrays.<TypeReference<?>>asList(new TypeReference<VerifierParams>() {}, new TypeReference<VerifierParams>() {}));
    ;

    public static final Event PROPOSESHADOWUPGRADE_EVENT = new Event("ProposeShadowUpgrade",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event PROPOSETRANSPARENTUPGRADE_EVENT = new Event("ProposeTransparentUpgrade",
            Arrays.<TypeReference<?>>asList(new TypeReference<DiamondCutData>() {}, new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event SECURITYCOUNCILUPGRADEAPPROVE_EVENT = new Event("SecurityCouncilUpgradeApprove",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event UNFREEZE_EVENT = new Event("Unfreeze",
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event VALIDATORSTATUSUPDATE_EVENT = new Event("ValidatorStatusUpdate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

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

    public static List<BlockCommitEventResponse> getBlockCommitEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BLOCKCOMMIT_EVENT, transactionReceipt);
        ArrayList<BlockCommitEventResponse> responses = new ArrayList<BlockCommitEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockCommitEventResponse typedResponse = new BlockCommitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.blockHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlockCommitEventResponse getBlockCommitEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKCOMMIT_EVENT, log);
        BlockCommitEventResponse typedResponse = new BlockCommitEventResponse();
        typedResponse.log = log;
        typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.blockHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
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
            typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.blockHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.commitment = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlockExecutionEventResponse getBlockExecutionEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKEXECUTION_EVENT, log);
        BlockExecutionEventResponse typedResponse = new BlockExecutionEventResponse();
        typedResponse.log = log;
        typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.blockHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
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
            typedResponse.totalBlocksCommitted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.totalBlocksVerified = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.totalBlocksExecuted = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlocksRevertEventResponse getBlocksRevertEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKSREVERT_EVENT, log);
        BlocksRevertEventResponse typedResponse = new BlocksRevertEventResponse();
        typedResponse.log = log;
        typedResponse.totalBlocksCommitted = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.totalBlocksVerified = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.totalBlocksExecuted = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
            typedResponse.previousLastVerifiedBlock = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.currentLastVerifiedBlock = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BlocksVerificationEventResponse getBlocksVerificationEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BLOCKSVERIFICATION_EVENT, log);
        BlocksVerificationEventResponse typedResponse = new BlocksVerificationEventResponse();
        typedResponse.log = log;
        typedResponse.previousLastVerifiedBlock = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.currentLastVerifiedBlock = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
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

    public static List<CancelUpgradeProposalEventResponse> getCancelUpgradeProposalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CANCELUPGRADEPROPOSAL_EVENT, transactionReceipt);
        ArrayList<CancelUpgradeProposalEventResponse> responses = new ArrayList<CancelUpgradeProposalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancelUpgradeProposalEventResponse typedResponse = new CancelUpgradeProposalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CancelUpgradeProposalEventResponse getCancelUpgradeProposalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CANCELUPGRADEPROPOSAL_EVENT, log);
        CancelUpgradeProposalEventResponse typedResponse = new CancelUpgradeProposalEventResponse();
        typedResponse.log = log;
        typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<CancelUpgradeProposalEventResponse> cancelUpgradeProposalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCancelUpgradeProposalEventFromLog(log));
    }

    public Flowable<CancelUpgradeProposalEventResponse> cancelUpgradeProposalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCELUPGRADEPROPOSAL_EVENT));
        return cancelUpgradeProposalEventFlowable(filter);
    }

    public static List<EthWithdrawalFinalizedEventResponse> getEthWithdrawalFinalizedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ETHWITHDRAWALFINALIZED_EVENT, transactionReceipt);
        ArrayList<EthWithdrawalFinalizedEventResponse> responses = new ArrayList<EthWithdrawalFinalizedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EthWithdrawalFinalizedEventResponse typedResponse = new EthWithdrawalFinalizedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EthWithdrawalFinalizedEventResponse getEthWithdrawalFinalizedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ETHWITHDRAWALFINALIZED_EVENT, log);
        EthWithdrawalFinalizedEventResponse typedResponse = new EthWithdrawalFinalizedEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<EthWithdrawalFinalizedEventResponse> ethWithdrawalFinalizedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEthWithdrawalFinalizedEventFromLog(log));
    }

    public Flowable<EthWithdrawalFinalizedEventResponse> ethWithdrawalFinalizedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ETHWITHDRAWALFINALIZED_EVENT));
        return ethWithdrawalFinalizedEventFlowable(filter);
    }

    public static List<ExecuteUpgradeEventResponse> getExecuteUpgradeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EXECUTEUPGRADE_EVENT, transactionReceipt);
        ArrayList<ExecuteUpgradeEventResponse> responses = new ArrayList<ExecuteUpgradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExecuteUpgradeEventResponse typedResponse = new ExecuteUpgradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.proposalSalt = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ExecuteUpgradeEventResponse getExecuteUpgradeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EXECUTEUPGRADE_EVENT, log);
        ExecuteUpgradeEventResponse typedResponse = new ExecuteUpgradeEventResponse();
        typedResponse.log = log;
        typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.proposalSalt = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
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

    public static List<NewGovernorEventResponse> getNewGovernorEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWGOVERNOR_EVENT, transactionReceipt);
        ArrayList<NewGovernorEventResponse> responses = new ArrayList<NewGovernorEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewGovernorEventResponse typedResponse = new NewGovernorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewGovernorEventResponse getNewGovernorEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWGOVERNOR_EVENT, log);
        NewGovernorEventResponse typedResponse = new NewGovernorEventResponse();
        typedResponse.log = log;
        typedResponse.oldGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewGovernorEventResponse> newGovernorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewGovernorEventFromLog(log));
    }

    public Flowable<NewGovernorEventResponse> newGovernorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWGOVERNOR_EVENT));
        return newGovernorEventFlowable(filter);
    }

    public static List<NewL2BootloaderBytecodeHashEventResponse> getNewL2BootloaderBytecodeHashEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWL2BOOTLOADERBYTECODEHASH_EVENT, transactionReceipt);
        ArrayList<NewL2BootloaderBytecodeHashEventResponse> responses = new ArrayList<NewL2BootloaderBytecodeHashEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewL2BootloaderBytecodeHashEventResponse typedResponse = new NewL2BootloaderBytecodeHashEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousBytecodeHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newBytecodeHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewL2BootloaderBytecodeHashEventResponse getNewL2BootloaderBytecodeHashEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWL2BOOTLOADERBYTECODEHASH_EVENT, log);
        NewL2BootloaderBytecodeHashEventResponse typedResponse = new NewL2BootloaderBytecodeHashEventResponse();
        typedResponse.log = log;
        typedResponse.previousBytecodeHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newBytecodeHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewL2BootloaderBytecodeHashEventResponse> newL2BootloaderBytecodeHashEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewL2BootloaderBytecodeHashEventFromLog(log));
    }

    public Flowable<NewL2BootloaderBytecodeHashEventResponse> newL2BootloaderBytecodeHashEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWL2BOOTLOADERBYTECODEHASH_EVENT));
        return newL2BootloaderBytecodeHashEventFlowable(filter);
    }

    public static List<NewL2DefaultAccountBytecodeHashEventResponse> getNewL2DefaultAccountBytecodeHashEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWL2DEFAULTACCOUNTBYTECODEHASH_EVENT, transactionReceipt);
        ArrayList<NewL2DefaultAccountBytecodeHashEventResponse> responses = new ArrayList<NewL2DefaultAccountBytecodeHashEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewL2DefaultAccountBytecodeHashEventResponse typedResponse = new NewL2DefaultAccountBytecodeHashEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousBytecodeHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newBytecodeHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewL2DefaultAccountBytecodeHashEventResponse getNewL2DefaultAccountBytecodeHashEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWL2DEFAULTACCOUNTBYTECODEHASH_EVENT, log);
        NewL2DefaultAccountBytecodeHashEventResponse typedResponse = new NewL2DefaultAccountBytecodeHashEventResponse();
        typedResponse.log = log;
        typedResponse.previousBytecodeHash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newBytecodeHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewL2DefaultAccountBytecodeHashEventResponse> newL2DefaultAccountBytecodeHashEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewL2DefaultAccountBytecodeHashEventFromLog(log));
    }

    public Flowable<NewL2DefaultAccountBytecodeHashEventResponse> newL2DefaultAccountBytecodeHashEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWL2DEFAULTACCOUNTBYTECODEHASH_EVENT));
        return newL2DefaultAccountBytecodeHashEventFlowable(filter);
    }

    public static List<NewPendingGovernorEventResponse> getNewPendingGovernorEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWPENDINGGOVERNOR_EVENT, transactionReceipt);
        ArrayList<NewPendingGovernorEventResponse> responses = new ArrayList<NewPendingGovernorEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPendingGovernorEventResponse typedResponse = new NewPendingGovernorEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldPendingGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newPendingGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewPendingGovernorEventResponse getNewPendingGovernorEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWPENDINGGOVERNOR_EVENT, log);
        NewPendingGovernorEventResponse typedResponse = new NewPendingGovernorEventResponse();
        typedResponse.log = log;
        typedResponse.oldPendingGovernor = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newPendingGovernor = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewPendingGovernorEventResponse> newPendingGovernorEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewPendingGovernorEventFromLog(log));
    }

    public Flowable<NewPendingGovernorEventResponse> newPendingGovernorEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPENDINGGOVERNOR_EVENT));
        return newPendingGovernorEventFlowable(filter);
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

    public static List<NewVerifierEventResponse> getNewVerifierEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWVERIFIER_EVENT, transactionReceipt);
        ArrayList<NewVerifierEventResponse> responses = new ArrayList<NewVerifierEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewVerifierEventResponse typedResponse = new NewVerifierEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldVerifier = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newVerifier = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewVerifierEventResponse getNewVerifierEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWVERIFIER_EVENT, log);
        NewVerifierEventResponse typedResponse = new NewVerifierEventResponse();
        typedResponse.log = log;
        typedResponse.oldVerifier = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newVerifier = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NewVerifierEventResponse> newVerifierEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewVerifierEventFromLog(log));
    }

    public Flowable<NewVerifierEventResponse> newVerifierEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWVERIFIER_EVENT));
        return newVerifierEventFlowable(filter);
    }

    public static List<NewVerifierParamsEventResponse> getNewVerifierParamsEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEWVERIFIERPARAMS_EVENT, transactionReceipt);
        ArrayList<NewVerifierParamsEventResponse> responses = new ArrayList<NewVerifierParamsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewVerifierParamsEventResponse typedResponse = new NewVerifierParamsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldVerifierParams = (VerifierParams) eventValues.getNonIndexedValues().get(0);
            typedResponse.newVerifierParams = (VerifierParams) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NewVerifierParamsEventResponse getNewVerifierParamsEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEWVERIFIERPARAMS_EVENT, log);
        NewVerifierParamsEventResponse typedResponse = new NewVerifierParamsEventResponse();
        typedResponse.log = log;
        typedResponse.oldVerifierParams = (VerifierParams) eventValues.getNonIndexedValues().get(0);
        typedResponse.newVerifierParams = (VerifierParams) eventValues.getNonIndexedValues().get(1);
        return typedResponse;
    }

    public Flowable<NewVerifierParamsEventResponse> newVerifierParamsEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNewVerifierParamsEventFromLog(log));
    }

    public Flowable<NewVerifierParamsEventResponse> newVerifierParamsEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWVERIFIERPARAMS_EVENT));
        return newVerifierParamsEventFlowable(filter);
    }

    public static List<ProposeShadowUpgradeEventResponse> getProposeShadowUpgradeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PROPOSESHADOWUPGRADE_EVENT, transactionReceipt);
        ArrayList<ProposeShadowUpgradeEventResponse> responses = new ArrayList<ProposeShadowUpgradeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProposeShadowUpgradeEventResponse typedResponse = new ProposeShadowUpgradeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ProposeShadowUpgradeEventResponse getProposeShadowUpgradeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PROPOSESHADOWUPGRADE_EVENT, log);
        ProposeShadowUpgradeEventResponse typedResponse = new ProposeShadowUpgradeEventResponse();
        typedResponse.log = log;
        typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ProposeShadowUpgradeEventResponse> proposeShadowUpgradeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getProposeShadowUpgradeEventFromLog(log));
    }

    public Flowable<ProposeShadowUpgradeEventResponse> proposeShadowUpgradeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROPOSESHADOWUPGRADE_EVENT));
        return proposeShadowUpgradeEventFlowable(filter);
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

    public static List<SecurityCouncilUpgradeApproveEventResponse> getSecurityCouncilUpgradeApproveEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SECURITYCOUNCILUPGRADEAPPROVE_EVENT, transactionReceipt);
        ArrayList<SecurityCouncilUpgradeApproveEventResponse> responses = new ArrayList<SecurityCouncilUpgradeApproveEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SecurityCouncilUpgradeApproveEventResponse typedResponse = new SecurityCouncilUpgradeApproveEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SecurityCouncilUpgradeApproveEventResponse getSecurityCouncilUpgradeApproveEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SECURITYCOUNCILUPGRADEAPPROVE_EVENT, log);
        SecurityCouncilUpgradeApproveEventResponse typedResponse = new SecurityCouncilUpgradeApproveEventResponse();
        typedResponse.log = log;
        typedResponse.proposalId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.proposalHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<SecurityCouncilUpgradeApproveEventResponse> securityCouncilUpgradeApproveEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSecurityCouncilUpgradeApproveEventFromLog(log));
    }

    public Flowable<SecurityCouncilUpgradeApproveEventResponse> securityCouncilUpgradeApproveEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SECURITYCOUNCILUPGRADEAPPROVE_EVENT));
        return securityCouncilUpgradeApproveEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> acceptGovernor() {
        final Function function = new Function(
                FUNC_ACCEPTGOVERNOR,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelUpgradeProposal(byte[] _proposedUpgradeHash) {
        final Function function = new Function(
                FUNC_CANCELUPGRADEPROPOSAL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_proposedUpgradeHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> commitBlocks(StoredBlockInfo _lastCommittedBlockData, List<CommitBlockInfo> _newBlocksData) {
        final Function function = new Function(
                FUNC_COMMITBLOCKS,
                Arrays.<Type>asList(_lastCommittedBlockData,
                        new org.web3j.abi.datatypes.DynamicArray<CommitBlockInfo>(CommitBlockInfo.class, _newBlocksData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeBlocks(List<StoredBlockInfo> _blocksData) {
        final Function function = new Function(
                FUNC_EXECUTEBLOCKS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<StoredBlockInfo>(StoredBlockInfo.class, _blocksData)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeUpgrade(DiamondCutData _diamondCut, byte[] _proposalSalt) {
        final Function function = new Function(
                FUNC_EXECUTEUPGRADE,
                Arrays.<Type>asList(_diamondCut,
                        new org.web3j.abi.datatypes.generated.Bytes32(_proposalSalt)),
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_facet)),
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

    public RemoteFunctionCall<TransactionReceipt> finalizeEthWithdrawal(BigInteger _l2BlockNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBlock, byte[] _message, List<byte[]> _merkleProof) {
        final Function function = new Function(
                FUNC_FINALIZEETHWITHDRAWAL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex),
                        new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBlock),
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

    public RemoteFunctionCall<BigInteger> getCurrentProposalId() {
        final Function function = new Function(FUNC_GETCURRENTPROPOSALID,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<byte[]> getProposedUpgradeHash() {
        final Function function = new Function(FUNC_GETPROPOSEDUPGRADEHASH,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getProposedUpgradeTimestamp() {
        final Function function = new Function(FUNC_GETPROPOSEDUPGRADETIMESTAMP,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getSecurityCouncil() {
        final Function function = new Function(FUNC_GETSECURITYCOUNCIL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBlocksCommitted() {
        final Function function = new Function(FUNC_GETTOTALBLOCKSCOMMITTED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBlocksExecuted() {
        final Function function = new Function(FUNC_GETTOTALBLOCKSEXECUTED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalBlocksVerified() {
        final Function function = new Function(FUNC_GETTOTALBLOCKSVERIFIED,
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

    public RemoteFunctionCall<BigInteger> getUpgradeProposalState() {
        final Function function = new Function(FUNC_GETUPGRADEPROPOSALSTATE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
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

    public RemoteFunctionCall<BigInteger> getpriorityTxMaxGasLimit() {
        final Function function = new Function(FUNC_GETPRIORITYTXMAXGASLIMIT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isApprovedBySecurityCouncil() {
        final Function function = new Function(FUNC_ISAPPROVEDBYSECURITYCOUNCIL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isDiamondStorageFrozen() {
        final Function function = new Function(FUNC_ISDIAMONDSTORAGEFROZEN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isEthWithdrawalFinalized(BigInteger _l2BlockNumber, BigInteger _l2MessageIndex) {
        final Function function = new Function(FUNC_ISETHWITHDRAWALFINALIZED,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isFacetFreezable(String _facet) {
        final Function function = new Function(FUNC_ISFACETFREEZABLE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_facet)),
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> l2LogsRootHash(BigInteger _blockNumber) {
        final Function function = new Function(FUNC_L2LOGSROOTHASH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)),
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

    public RemoteFunctionCall<TransactionReceipt> proposeShadowUpgrade(byte[] _proposalHash, BigInteger _proposalId) {
        final Function function = new Function(
                FUNC_PROPOSESHADOWUPGRADE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_proposalHash),
                        new org.web3j.abi.datatypes.generated.Uint40(_proposalId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> proposeTransparentUpgrade(DiamondCutData _diamondCut, BigInteger _proposalId) {
        final Function function = new Function(
                FUNC_PROPOSETRANSPARENTUPGRADE,
                Arrays.<Type>asList(_diamondCut,
                        new org.web3j.abi.datatypes.generated.Uint40(_proposalId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> proveBlocks(StoredBlockInfo _prevBlock, List<StoredBlockInfo> _committedBlocks, ProofInput _proof) {
        final Function function = new Function(
                FUNC_PROVEBLOCKS,
                Arrays.<Type>asList(_prevBlock,
                        new org.web3j.abi.datatypes.DynamicArray<StoredBlockInfo>(StoredBlockInfo.class, _committedBlocks),
                        _proof),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> proveL1ToL2TransactionStatus(byte[] _l2TxHash, BigInteger _l2BlockNumber, BigInteger _l2MessageIndex, BigInteger _l2TxNumberInBlock, List<byte[]> _merkleProof, BigInteger _status) {
        final Function function = new Function(FUNC_PROVEL1TOL2TRANSACTIONSTATUS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_l2TxHash),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2BlockNumber),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2MessageIndex),
                        new org.web3j.abi.datatypes.generated.Uint16(_l2TxNumberInBlock),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                                org.web3j.abi.datatypes.generated.Bytes32.class,
                                org.web3j.abi.Utils.typeMap(_merkleProof, org.web3j.abi.datatypes.generated.Bytes32.class)),
                        new org.web3j.abi.datatypes.generated.Uint8(_status)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> proveL2LogInclusion(BigInteger _blockNumber, BigInteger _index, L2Log _log, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2LOGINCLUSION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockNumber),
                        new org.web3j.abi.datatypes.generated.Uint256(_index),
                        _log,
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                                org.web3j.abi.datatypes.generated.Bytes32.class,
                                org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.generated.Bytes32.class))),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> proveL2MessageInclusion(BigInteger _blockNumber, BigInteger _index, L2Message _message, List<byte[]> _proof) {
        final Function function = new Function(FUNC_PROVEL2MESSAGEINCLUSION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockNumber),
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_contractL2),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2Value),
                        new org.web3j.abi.datatypes.DynamicBytes(_calldata),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2GasLimit),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2GasPerPubdataByteLimit),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                                org.web3j.abi.datatypes.DynamicBytes.class,
                                org.web3j.abi.Utils.typeMap(_factoryDeps, org.web3j.abi.datatypes.DynamicBytes.class)),
                        new org.web3j.abi.datatypes.Address(_refundRecipient)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> revertBlocks(BigInteger _newLastBlock) {
        final Function function = new Function(
                FUNC_REVERTBLOCKS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_newLastBlock)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> securityCouncilUpgradeApprove(byte[] _upgradeProposalHash) {
        final Function function = new Function(
                FUNC_SECURITYCOUNCILUPGRADEAPPROVE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_upgradeProposalHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<L2CanonicalTransaction> serializeL2Transaction(BigInteger _txId, BigInteger _l2Value, String _sender, String _contractAddressL2, byte[] _calldata, BigInteger _l2GasLimit, BigInteger _l2GasPerPubdataByteLimit, List<byte[]> _factoryDeps, BigInteger _toMint, String _refundRecipient) {
        final Function function = new Function(FUNC_SERIALIZEL2TRANSACTION,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_txId),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2Value),
                        new org.web3j.abi.datatypes.Address(_sender),
                        new org.web3j.abi.datatypes.Address(_contractAddressL2),
                        new org.web3j.abi.datatypes.DynamicBytes(_calldata),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2GasLimit),
                        new org.web3j.abi.datatypes.generated.Uint256(_l2GasPerPubdataByteLimit),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                                org.web3j.abi.datatypes.DynamicBytes.class,
                                org.web3j.abi.Utils.typeMap(_factoryDeps, org.web3j.abi.datatypes.DynamicBytes.class)),
                        new org.web3j.abi.datatypes.generated.Uint256(_toMint),
                        new org.web3j.abi.datatypes.Address(_refundRecipient)),
                Arrays.<TypeReference<?>>asList(new TypeReference<L2CanonicalTransaction>() {}));
        return executeRemoteCallSingleValueReturn(function, L2CanonicalTransaction.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setL2BootloaderBytecodeHash(byte[] _l2BootloaderBytecodeHash) {
        final Function function = new Function(
                FUNC_SETL2BOOTLOADERBYTECODEHASH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_l2BootloaderBytecodeHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setL2DefaultAccountBytecodeHash(byte[] _l2DefaultAccountBytecodeHash) {
        final Function function = new Function(
                FUNC_SETL2DEFAULTACCOUNTBYTECODEHASH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_l2DefaultAccountBytecodeHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPendingGovernor(String _newPendingGovernor) {
        final Function function = new Function(
                FUNC_SETPENDINGGOVERNOR,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newPendingGovernor)),
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

    public RemoteFunctionCall<TransactionReceipt> setValidator(String _validator, Boolean _active) {
        final Function function = new Function(
                FUNC_SETVALIDATOR,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_validator),
                        new org.web3j.abi.datatypes.Bool(_active)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVerifier(String _newVerifier) {
        final Function function = new Function(
                FUNC_SETVERIFIER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_newVerifier)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVerifierParams(VerifierParams _newVerifierParams) {
        final Function function = new Function(
                FUNC_SETVERIFIERPARAMS,
                Arrays.<Type>asList(_newVerifierParams),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> storedBlockHash(BigInteger _blockNumber) {
        final Function function = new Function(FUNC_STOREDBLOCKHASH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_blockNumber)),
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

    public RemoteFunctionCall<byte[]> upgradeProposalHash(DiamondCutData _diamondCut, BigInteger _proposalId, byte[] _salt) {
        final Function function = new Function(FUNC_UPGRADEPROPOSALHASH,
                Arrays.<Type>asList(_diamondCut,
                        new org.web3j.abi.datatypes.generated.Uint256(_proposalId),
                        new org.web3j.abi.datatypes.generated.Bytes32(_salt)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
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

    public static RemoteCall<ZkSyncContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ZkSyncContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ZkSyncContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ZkSyncContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ZkSyncContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ZkSyncContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ZkSyncContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ZkSyncContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
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

    public static class FacetCut extends DynamicStruct {
        public String facet;

        public BigInteger action;

        public Boolean isFreezable;

        public List<byte[]> selectors;

        public FacetCut(String facet, BigInteger action, Boolean isFreezable, List<byte[]> selectors) {
            super(new org.web3j.abi.datatypes.Address(facet),
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

    public static class StoredBlockInfo extends StaticStruct {
        public BigInteger blockNumber;

        public byte[] blockHash;

        public BigInteger indexRepeatedStorageChanges;

        public BigInteger numberOfLayer1Txs;

        public byte[] priorityOperationsHash;

        public byte[] l2LogsTreeRoot;

        public BigInteger timestamp;

        public byte[] commitment;

        public StoredBlockInfo(BigInteger blockNumber, byte[] blockHash, BigInteger indexRepeatedStorageChanges, BigInteger numberOfLayer1Txs, byte[] priorityOperationsHash, byte[] l2LogsTreeRoot, BigInteger timestamp, byte[] commitment) {
            super(new org.web3j.abi.datatypes.generated.Uint64(blockNumber),
                    new org.web3j.abi.datatypes.generated.Bytes32(blockHash),
                    new org.web3j.abi.datatypes.generated.Uint64(indexRepeatedStorageChanges),
                    new org.web3j.abi.datatypes.generated.Uint256(numberOfLayer1Txs),
                    new org.web3j.abi.datatypes.generated.Bytes32(priorityOperationsHash),
                    new org.web3j.abi.datatypes.generated.Bytes32(l2LogsTreeRoot),
                    new org.web3j.abi.datatypes.generated.Uint256(timestamp),
                    new org.web3j.abi.datatypes.generated.Bytes32(commitment));
            this.blockNumber = blockNumber;
            this.blockHash = blockHash;
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges;
            this.numberOfLayer1Txs = numberOfLayer1Txs;
            this.priorityOperationsHash = priorityOperationsHash;
            this.l2LogsTreeRoot = l2LogsTreeRoot;
            this.timestamp = timestamp;
            this.commitment = commitment;
        }

        public StoredBlockInfo(Uint64 blockNumber, Bytes32 blockHash, Uint64 indexRepeatedStorageChanges, Uint256 numberOfLayer1Txs, Bytes32 priorityOperationsHash, Bytes32 l2LogsTreeRoot, Uint256 timestamp, Bytes32 commitment) {
            super(blockNumber, blockHash, indexRepeatedStorageChanges, numberOfLayer1Txs, priorityOperationsHash, l2LogsTreeRoot, timestamp, commitment);
            this.blockNumber = blockNumber.getValue();
            this.blockHash = blockHash.getValue();
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges.getValue();
            this.numberOfLayer1Txs = numberOfLayer1Txs.getValue();
            this.priorityOperationsHash = priorityOperationsHash.getValue();
            this.l2LogsTreeRoot = l2LogsTreeRoot.getValue();
            this.timestamp = timestamp.getValue();
            this.commitment = commitment.getValue();
        }
    }

    public static class CommitBlockInfo extends DynamicStruct {
        public BigInteger blockNumber;

        public BigInteger timestamp;

        public BigInteger indexRepeatedStorageChanges;

        public byte[] newStateRoot;

        public BigInteger numberOfLayer1Txs;

        public byte[] l2LogsTreeRoot;

        public byte[] priorityOperationsHash;

        public byte[] initialStorageChanges;

        public byte[] repeatedStorageChanges;

        public byte[] l2Logs;

        public List<byte[]> l2ArbitraryLengthMessages;

        public List<byte[]> factoryDeps;

        public CommitBlockInfo(BigInteger blockNumber, BigInteger timestamp, BigInteger indexRepeatedStorageChanges, byte[] newStateRoot, BigInteger numberOfLayer1Txs, byte[] l2LogsTreeRoot, byte[] priorityOperationsHash, byte[] initialStorageChanges, byte[] repeatedStorageChanges, byte[] l2Logs, List<byte[]> l2ArbitraryLengthMessages, List<byte[]> factoryDeps) {
            super(new org.web3j.abi.datatypes.generated.Uint64(blockNumber),
                    new org.web3j.abi.datatypes.generated.Uint64(timestamp),
                    new org.web3j.abi.datatypes.generated.Uint64(indexRepeatedStorageChanges),
                    new org.web3j.abi.datatypes.generated.Bytes32(newStateRoot),
                    new org.web3j.abi.datatypes.generated.Uint256(numberOfLayer1Txs),
                    new org.web3j.abi.datatypes.generated.Bytes32(l2LogsTreeRoot),
                    new org.web3j.abi.datatypes.generated.Bytes32(priorityOperationsHash),
                    new org.web3j.abi.datatypes.DynamicBytes(initialStorageChanges),
                    new org.web3j.abi.datatypes.DynamicBytes(repeatedStorageChanges),
                    new org.web3j.abi.datatypes.DynamicBytes(l2Logs),
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                            org.web3j.abi.datatypes.DynamicBytes.class,
                            org.web3j.abi.Utils.typeMap(l2ArbitraryLengthMessages, org.web3j.abi.datatypes.DynamicBytes.class)),
                    new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                            org.web3j.abi.datatypes.DynamicBytes.class,
                            org.web3j.abi.Utils.typeMap(factoryDeps, org.web3j.abi.datatypes.DynamicBytes.class)));
            this.blockNumber = blockNumber;
            this.timestamp = timestamp;
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges;
            this.newStateRoot = newStateRoot;
            this.numberOfLayer1Txs = numberOfLayer1Txs;
            this.l2LogsTreeRoot = l2LogsTreeRoot;
            this.priorityOperationsHash = priorityOperationsHash;
            this.initialStorageChanges = initialStorageChanges;
            this.repeatedStorageChanges = repeatedStorageChanges;
            this.l2Logs = l2Logs;
            this.l2ArbitraryLengthMessages = l2ArbitraryLengthMessages;
            this.factoryDeps = factoryDeps;
        }

        public CommitBlockInfo(Uint64 blockNumber, Uint64 timestamp, Uint64 indexRepeatedStorageChanges, Bytes32 newStateRoot, Uint256 numberOfLayer1Txs, Bytes32 l2LogsTreeRoot, Bytes32 priorityOperationsHash, DynamicBytes initialStorageChanges, DynamicBytes repeatedStorageChanges, DynamicBytes l2Logs, DynamicArray<DynamicBytes> l2ArbitraryLengthMessages, DynamicArray<DynamicBytes> factoryDeps) {
            super(blockNumber, timestamp, indexRepeatedStorageChanges, newStateRoot, numberOfLayer1Txs, l2LogsTreeRoot, priorityOperationsHash, initialStorageChanges, repeatedStorageChanges, l2Logs, l2ArbitraryLengthMessages, factoryDeps);
            this.blockNumber = blockNumber.getValue();
            this.timestamp = timestamp.getValue();
            this.indexRepeatedStorageChanges = indexRepeatedStorageChanges.getValue();
            this.newStateRoot = newStateRoot.getValue();
            this.numberOfLayer1Txs = numberOfLayer1Txs.getValue();
            this.l2LogsTreeRoot = l2LogsTreeRoot.getValue();
            this.priorityOperationsHash = priorityOperationsHash.getValue();
            this.initialStorageChanges = initialStorageChanges.getValue();
            this.repeatedStorageChanges = repeatedStorageChanges.getValue();
            this.l2Logs = l2Logs.getValue();
            this.l2ArbitraryLengthMessages = l2ArbitraryLengthMessages.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
            this.factoryDeps = factoryDeps.getValue().stream().map(v -> v.getValue()).collect(Collectors.toList());
        }
    }

    public static class Facet extends DynamicStruct {
        public String addr;

        public List<byte[]> selectors;

        public Facet(String addr, List<byte[]> selectors) {
            super(new org.web3j.abi.datatypes.Address(addr),
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

        public BigInteger txNumberInBlock;

        public String sender;

        public byte[] key;

        public byte[] value;

        public L2Log(BigInteger l2ShardId, Boolean isService, BigInteger txNumberInBlock, String sender, byte[] key, byte[] value) {
            super(new org.web3j.abi.datatypes.generated.Uint8(l2ShardId),
                    new org.web3j.abi.datatypes.Bool(isService),
                    new org.web3j.abi.datatypes.generated.Uint16(txNumberInBlock),
                    new org.web3j.abi.datatypes.Address(sender),
                    new org.web3j.abi.datatypes.generated.Bytes32(key),
                    new org.web3j.abi.datatypes.generated.Bytes32(value));
            this.l2ShardId = l2ShardId;
            this.isService = isService;
            this.txNumberInBlock = txNumberInBlock;
            this.sender = sender;
            this.key = key;
            this.value = value;
        }

        public L2Log(Uint8 l2ShardId, Bool isService, Uint16 txNumberInBlock, Address sender, Bytes32 key, Bytes32 value) {
            super(l2ShardId, isService, txNumberInBlock, sender, key, value);
            this.l2ShardId = l2ShardId.getValue();
            this.isService = isService.getValue();
            this.txNumberInBlock = txNumberInBlock.getValue();
            this.sender = sender.getValue();
            this.key = key.getValue();
            this.value = value.getValue();
        }
    }

    public static class L2Message extends DynamicStruct {
        public BigInteger txNumberInBlock;

        public String sender;

        public byte[] data;

        public L2Message(BigInteger txNumberInBlock, String sender, byte[] data) {
            super(new org.web3j.abi.datatypes.generated.Uint16(txNumberInBlock),
                    new org.web3j.abi.datatypes.Address(sender),
                    new org.web3j.abi.datatypes.DynamicBytes(data));
            this.txNumberInBlock = txNumberInBlock;
            this.sender = sender;
            this.data = data;
        }

        public L2Message(Uint16 txNumberInBlock, Address sender, DynamicBytes data) {
            super(txNumberInBlock, sender, data);
            this.txNumberInBlock = txNumberInBlock.getValue();
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
                    new org.web3j.abi.datatypes.Address(initAddress),
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
        public BigInteger blockNumber;

        public byte[] blockHash;

        public byte[] commitment;
    }

    public static class BlockExecutionEventResponse extends BaseEventResponse {
        public BigInteger blockNumber;

        public byte[] blockHash;

        public byte[] commitment;
    }

    public static class BlocksRevertEventResponse extends BaseEventResponse {
        public BigInteger totalBlocksCommitted;

        public BigInteger totalBlocksVerified;

        public BigInteger totalBlocksExecuted;
    }

    public static class BlocksVerificationEventResponse extends BaseEventResponse {
        public BigInteger previousLastVerifiedBlock;

        public BigInteger currentLastVerifiedBlock;
    }

    public static class CancelUpgradeProposalEventResponse extends BaseEventResponse {
        public BigInteger proposalId;

        public byte[] proposalHash;
    }

    public static class EthWithdrawalFinalizedEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger amount;
    }

    public static class ExecuteUpgradeEventResponse extends BaseEventResponse {
        public BigInteger proposalId;

        public byte[] proposalHash;

        public byte[] proposalSalt;
    }

    public static class FreezeEventResponse extends BaseEventResponse {
    }

    public static class IsPorterAvailableStatusUpdateEventResponse extends BaseEventResponse {
        public Boolean isPorterAvailable;
    }

    public static class NewGovernorEventResponse extends BaseEventResponse {
        public String oldGovernor;

        public String newGovernor;
    }

    public static class NewL2BootloaderBytecodeHashEventResponse extends BaseEventResponse {
        public byte[] previousBytecodeHash;

        public byte[] newBytecodeHash;
    }

    public static class NewL2DefaultAccountBytecodeHashEventResponse extends BaseEventResponse {
        public byte[] previousBytecodeHash;

        public byte[] newBytecodeHash;
    }

    public static class NewPendingGovernorEventResponse extends BaseEventResponse {
        public String oldPendingGovernor;

        public String newPendingGovernor;
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

    public static class NewVerifierEventResponse extends BaseEventResponse {
        public String oldVerifier;

        public String newVerifier;
    }

    public static class NewVerifierParamsEventResponse extends BaseEventResponse {
        public VerifierParams oldVerifierParams;

        public VerifierParams newVerifierParams;
    }

    public static class ProposeShadowUpgradeEventResponse extends BaseEventResponse {
        public BigInteger proposalId;

        public byte[] proposalHash;
    }

    public static class ProposeTransparentUpgradeEventResponse extends BaseEventResponse {
        public BigInteger proposalId;

        public DiamondCutData diamondCut;

        public byte[] proposalSalt;
    }

    public static class SecurityCouncilUpgradeApproveEventResponse extends BaseEventResponse {
        public BigInteger proposalId;

        public byte[] proposalHash;
    }

    public static class UnfreezeEventResponse extends BaseEventResponse {
    }

    public static class ValidatorStatusUpdateEventResponse extends BaseEventResponse {
        public String validatorAddress;

        public Boolean isActive;
    }
}
