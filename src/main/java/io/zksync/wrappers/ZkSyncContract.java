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
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
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
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class ZkSyncContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC__TRANSFERERC20 = "_transferERC20";

    public static final String FUNC_ACTIVATEEXODUSMODE = "activateExodusMode";

    public static final String FUNC_ADDTOKEN = "addToken";

    public static final String FUNC_ADDEDTOKENS = "addedTokens";

    public static final String FUNC_CANCELOUTSTANDINGDEPOSITSFOREXODUSMODE = "cancelOutstandingDepositsForExodusMode";

    public static final String FUNC_COMMITBLOCKS = "commitBlocks";

    public static final String FUNC_DEPOSITERC20 = "depositERC20";

    public static final String FUNC_DEPOSITETH = "depositETH";

    public static final String FUNC_EXECUTEBLOCKS = "executeBlocks";

    public static final String FUNC_EXODUSMODE = "exodusMode";

    public static final String FUNC_FIRSTPRIORITYREQUESTID = "firstPriorityRequestId";

    public static final String FUNC_GETNOTICEPERIOD = "getNoticePeriod";

    public static final String FUNC_GETPENDINGBALANCE = "getPendingBalance";

    public static final String FUNC_GOVERNANCE = "governance";

    public static final String FUNC_INITIALIZE = "initialize";

    public static final String FUNC_ISREADYFORUPGRADE = "isReadyForUpgrade";

    public static final String FUNC_PERFORMEXODUS = "performExodus";

    public static final String FUNC_PERFORMEDEXODUS = "performedExodus";

    public static final String FUNC_PROVEBLOCKS = "proveBlocks";

    public static final String FUNC_REQUESTMIGRATETOPORTER = "requestMigrateToPorter";

    public static final String FUNC_REQUESTWITHDRAW = "requestWithdraw";

    public static final String FUNC_REVERTBLOCKS = "revertBlocks";

    public static final String FUNC_TOTALBLOCKSCOMMITTED = "totalBlocksCommitted";

    public static final String FUNC_TOTALBLOCKSEXECUTED = "totalBlocksExecuted";

    public static final String FUNC_TOTALBLOCKSPROVEN = "totalBlocksProven";

    public static final String FUNC_TOTALCOMMITTEDPRIORITYREQUESTS = "totalCommittedPriorityRequests";

    public static final String FUNC_TOTALPRIORITYREQUESTS = "totalPriorityRequests";

    public static final String FUNC_UPGRADE = "upgrade";

    public static final String FUNC_UPGRADECANCELED = "upgradeCanceled";

    public static final String FUNC_UPGRADEFINISHES = "upgradeFinishes";

    public static final String FUNC_UPGRADENOTICEPERIODSTARTED = "upgradeNoticePeriodStarted";

    public static final String FUNC_UPGRADEPREPARATIONSTARTED = "upgradePreparationStarted";

    public static final String FUNC_VERIFIER = "verifier";

    public static final String FUNC_WITHDRAWPENDINGBALANCE = "withdrawPendingBalance";

    public static final Event BLOCKCOMMIT_EVENT = new Event("BlockCommit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>(true) {}));
    ;

    public static final Event BLOCKVERIFICATION_EVENT = new Event("BlockVerification", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>(true) {}));
    ;

    public static final Event BLOCKSREVERT_EVENT = new Event("BlocksRevert", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Uint32>() {}));
    ;

    public static final Event DEPOSIT_EVENT = new Event("Deposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DEPOSITCOMMIT_EVENT = new Event("DepositCommit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>(true) {}, new TypeReference<Uint32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event EXODUSMODE_EVENT = new Event("ExodusMode", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event FACTAUTH_EVENT = new Event("FactAuth", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint32>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event NEWPRIORITYREQUEST_EVENT = new Event("NewPriorityRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint8>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWCOMMIT_EVENT = new Event("WithdrawCommit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>(true) {}, new TypeReference<Uint32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWAL_EVENT = new Event("Withdrawal", 
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

    public List<BlockVerificationEventResponse> getBlockVerificationEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BLOCKVERIFICATION_EVENT, transactionReceipt);
        ArrayList<BlockVerificationEventResponse> responses = new ArrayList<BlockVerificationEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BlockVerificationEventResponse typedResponse = new BlockVerificationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<BlockVerificationEventResponse> blockVerificationEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, BlockVerificationEventResponse>() {
            @Override
            public BlockVerificationEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BLOCKVERIFICATION_EVENT, log);
                BlockVerificationEventResponse typedResponse = new BlockVerificationEventResponse();
                typedResponse.log = log;
                typedResponse.blockNumber = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<BlockVerificationEventResponse> blockVerificationEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BLOCKVERIFICATION_EVENT));
        return blockVerificationEventFlowable(filter);
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

    public List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositEventResponse> depositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DepositEventResponse>() {
            @Override
            public DepositEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSIT_EVENT, log);
                DepositEventResponse typedResponse = new DepositEventResponse();
                typedResponse.log = log;
                typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public List<DepositCommitEventResponse> getDepositCommitEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSITCOMMIT_EVENT, transactionReceipt);
        ArrayList<DepositCommitEventResponse> responses = new ArrayList<DepositCommitEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositCommitEventResponse typedResponse = new DepositCommitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.zkSyncBlockId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.accountId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositCommitEventResponse> depositCommitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DepositCommitEventResponse>() {
            @Override
            public DepositCommitEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSITCOMMIT_EVENT, log);
                DepositCommitEventResponse typedResponse = new DepositCommitEventResponse();
                typedResponse.log = log;
                typedResponse.zkSyncBlockId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.accountId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositCommitEventResponse> depositCommitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSITCOMMIT_EVENT));
        return depositCommitEventFlowable(filter);
    }

    public List<ExodusModeEventResponse> getExodusModeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EXODUSMODE_EVENT, transactionReceipt);
        ArrayList<ExodusModeEventResponse> responses = new ArrayList<ExodusModeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExodusModeEventResponse typedResponse = new ExodusModeEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ExodusModeEventResponse> exodusModeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ExodusModeEventResponse>() {
            @Override
            public ExodusModeEventResponse apply(Log log) {
                ExodusModeEventResponse typedResponse = new ExodusModeEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public Flowable<ExodusModeEventResponse> exodusModeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXODUSMODE_EVENT));
        return exodusModeEventFlowable(filter);
    }

    public List<FactAuthEventResponse> getFactAuthEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FACTAUTH_EVENT, transactionReceipt);
        ArrayList<FactAuthEventResponse> responses = new ArrayList<FactAuthEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FactAuthEventResponse typedResponse = new FactAuthEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.nonce = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fact = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<FactAuthEventResponse> factAuthEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, FactAuthEventResponse>() {
            @Override
            public FactAuthEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FACTAUTH_EVENT, log);
                FactAuthEventResponse typedResponse = new FactAuthEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.nonce = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.fact = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<FactAuthEventResponse> factAuthEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FACTAUTH_EVENT));
        return factAuthEventFlowable(filter);
    }

    public List<NewPriorityRequestEventResponse> getNewPriorityRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPRIORITYREQUEST_EVENT, transactionReceipt);
        ArrayList<NewPriorityRequestEventResponse> responses = new ArrayList<NewPriorityRequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPriorityRequestEventResponse typedResponse = new NewPriorityRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.serialId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.opType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.pubData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.expirationBlock = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
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
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.serialId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.opType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.pubData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.expirationBlock = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewPriorityRequestEventResponse> newPriorityRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPRIORITYREQUEST_EVENT));
        return newPriorityRequestEventFlowable(filter);
    }

    public List<WithdrawCommitEventResponse> getWithdrawCommitEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWCOMMIT_EVENT, transactionReceipt);
        ArrayList<WithdrawCommitEventResponse> responses = new ArrayList<WithdrawCommitEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawCommitEventResponse typedResponse = new WithdrawCommitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.zkSyncBlockId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.accountId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawCommitEventResponse> withdrawCommitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawCommitEventResponse>() {
            @Override
            public WithdrawCommitEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWCOMMIT_EVENT, log);
                WithdrawCommitEventResponse typedResponse = new WithdrawCommitEventResponse();
                typedResponse.log = log;
                typedResponse.zkSyncBlockId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.accountId = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawCommitEventResponse> withdrawCommitEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWCOMMIT_EVENT));
        return withdrawCommitEventFlowable(filter);
    }

    public List<WithdrawalEventResponse> getWithdrawalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWAL_EVENT, transactionReceipt);
        ArrayList<WithdrawalEventResponse> responses = new ArrayList<WithdrawalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawalEventResponse> withdrawalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawalEventResponse>() {
            @Override
            public WithdrawalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWAL_EVENT, log);
                WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
                typedResponse.log = log;
                typedResponse.zkSyncTokenAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawalEventResponse> withdrawalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWAL_EVENT));
        return withdrawalEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> _transferERC20(String _token, String _to, BigInteger _amount, BigInteger _maxAmount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC__TRANSFERERC20, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.generated.Uint256(_maxAmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> activateExodusMode() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ACTIVATEEXODUSMODE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addToken(String _token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> addedTokens(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADDEDTOKENS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelOutstandingDepositsForExodusMode(BigInteger _n, List<byte[]> _depositsPubdata) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCELOUTSTANDINGDEPOSITSFOREXODUSMODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(_n), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(_depositsPubdata, org.web3j.abi.datatypes.DynamicBytes.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositERC20(String _token, BigInteger _amount, String _zkSyncAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITERC20, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.Address(160, _zkSyncAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositETH(String _zkSyncAddress, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITETH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _zkSyncAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Boolean> exodusMode() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EXODUSMODE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> firstPriorityRequestId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FIRSTPRIORITYREQUESTID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getNoticePeriod() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETNOTICEPERIOD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPendingBalance(String _address, String _token) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPENDINGBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address), 
                new org.web3j.abi.datatypes.Address(160, _token)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> governance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GOVERNANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> initialize(byte[] initializationParameters) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INITIALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(initializationParameters)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isReadyForUpgrade() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISREADYFORUPGRADE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> performExodus(StoredBlockInfo _storedBlockInfo, String _owner, BigInteger _accountId, String _zkSyncTokenAddress, BigInteger _amount, List<BigInteger> _proof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PERFORMEXODUS, 
                Arrays.<Type>asList(_storedBlockInfo, 
                new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.generated.Uint32(_accountId), 
                new org.web3j.abi.datatypes.Address(160, _zkSyncTokenAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_proof, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> performedExodus(BigInteger param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PERFORMEDEXODUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> requestMigrateToPorter() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTMIGRATETOPORTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> requestWithdraw(String _token, BigInteger _amount, String _to) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REQUESTWITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.Address(160, _to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revertBlocks(List<StoredBlockInfo> _blocksToRevert) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVERTBLOCKS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<StoredBlockInfo>(StoredBlockInfo.class, _blocksToRevert)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> totalBlocksCommitted() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALBLOCKSCOMMITTED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalBlocksExecuted() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALBLOCKSEXECUTED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalBlocksProven() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALBLOCKSPROVEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalCommittedPriorityRequests() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALCOMMITTEDPRIORITYREQUESTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalPriorityRequests() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALPRIORITYREQUESTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> upgrade(byte[] upgradeParameters) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(upgradeParameters)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeCanceled() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADECANCELED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeFinishes() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADEFINISHES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradeNoticePeriodStarted() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADENOTICEPERIODSTARTED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> upgradePreparationStarted() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPGRADEPREPARATIONSTARTED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> verifier() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFIER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public static class StoredBlockInfo extends StaticStruct {
        public BigInteger blockNumber;

        public BigInteger lastProcessedPriorityOp;

        public byte[] pendingOnchainOperationsHash;

        public BigInteger timestamp;

        public byte[] stateRoot;

        public byte[] zkPorterRoot;

        public byte[] commitment;

        public StoredBlockInfo(BigInteger blockNumber, BigInteger lastProcessedPriorityOp, byte[] pendingOnchainOperationsHash, BigInteger timestamp, byte[] stateRoot, byte[] zkPorterRoot, byte[] commitment) {
            super(new org.web3j.abi.datatypes.generated.Uint32(blockNumber),new org.web3j.abi.datatypes.generated.Uint64(lastProcessedPriorityOp),new org.web3j.abi.datatypes.generated.Bytes32(pendingOnchainOperationsHash),new org.web3j.abi.datatypes.generated.Uint256(timestamp),new org.web3j.abi.datatypes.generated.Bytes32(stateRoot),new org.web3j.abi.datatypes.generated.Bytes32(zkPorterRoot),new org.web3j.abi.datatypes.generated.Bytes32(commitment));
            this.blockNumber = blockNumber;
            this.lastProcessedPriorityOp = lastProcessedPriorityOp;
            this.pendingOnchainOperationsHash = pendingOnchainOperationsHash;
            this.timestamp = timestamp;
            this.stateRoot = stateRoot;
            this.zkPorterRoot = zkPorterRoot;
            this.commitment = commitment;
        }

        public StoredBlockInfo(Uint32 blockNumber, Uint64 lastProcessedPriorityOp, Bytes32 pendingOnchainOperationsHash, Uint256 timestamp, Bytes32 stateRoot, Bytes32 zkPorterRoot, Bytes32 commitment) {
            super(blockNumber,lastProcessedPriorityOp,pendingOnchainOperationsHash,timestamp,stateRoot,zkPorterRoot,commitment);
            this.blockNumber = blockNumber.getValue();
            this.lastProcessedPriorityOp = lastProcessedPriorityOp.getValue();
            this.pendingOnchainOperationsHash = pendingOnchainOperationsHash.getValue();
            this.timestamp = timestamp.getValue();
            this.stateRoot = stateRoot.getValue();
            this.zkPorterRoot = zkPorterRoot.getValue();
            this.commitment = commitment.getValue();
        }
    }

    public static class BlockCommitEventResponse extends BaseEventResponse {
        public BigInteger blockNumber;
    }

    public static class BlockVerificationEventResponse extends BaseEventResponse {
        public BigInteger blockNumber;
    }

    public static class BlocksRevertEventResponse extends BaseEventResponse {
        public BigInteger totalBlocksVerified;

        public BigInteger totalBlocksCommitted;
    }

    public static class DepositEventResponse extends BaseEventResponse {
        public String zkSyncTokenAddress;

        public BigInteger amount;
    }

    public static class DepositCommitEventResponse extends BaseEventResponse {
        public BigInteger zkSyncBlockId;

        public BigInteger accountId;

        public String zkSyncTokenAddress;

        public String owner;

        public BigInteger amount;
    }

    public static class ExodusModeEventResponse extends BaseEventResponse {
    }

    public static class FactAuthEventResponse extends BaseEventResponse {
        public String sender;

        public BigInteger nonce;

        public byte[] fact;
    }

    public static class NewPriorityRequestEventResponse extends BaseEventResponse {
        public String sender;

        public BigInteger serialId;

        public BigInteger opType;

        public byte[] pubData;

        public BigInteger expirationBlock;
    }

    public static class WithdrawCommitEventResponse extends BaseEventResponse {
        public BigInteger zkSyncBlockId;

        public BigInteger accountId;

        public String zkSyncTokenAddress;

        public String owner;

        public BigInteger amount;
    }

    public static class WithdrawalEventResponse extends BaseEventResponse {
        public String zkSyncTokenAddress;

        public BigInteger amount;
    }
}
