package io.zksync.wrappers;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class IEthToken extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROMTO = "transferFromTo";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_WITHDRAWWITHMESSAGE = "withdrawWithMessage";

    public static final Event MINT_EVENT = new Event("Mint", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWAL_EVENT = new Event("Withdrawal", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWALWITHMESSAGE_EVENT = new Event("WithdrawalWithMessage", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected IEthToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IEthToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IEthToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IEthToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<MintEventResponse> getMintEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINT_EVENT, transactionReceipt);
        ArrayList<MintEventResponse> responses = new ArrayList<MintEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MintEventResponse typedResponse = new MintEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MintEventResponse> mintEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, MintEventResponse>() {
            @Override
            public MintEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MINT_EVENT, log);
                MintEventResponse typedResponse = new MintEventResponse();
                typedResponse.log = log;
                typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MintEventResponse> mintEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINT_EVENT));
        return mintEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public static List<WithdrawalEventResponse> getWithdrawalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAWAL_EVENT, transactionReceipt);
        ArrayList<WithdrawalEventResponse> responses = new ArrayList<WithdrawalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawalEventResponse typedResponse = new WithdrawalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._l2Sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._l1Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse._l2Sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._l1Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawalEventResponse> withdrawalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWAL_EVENT));
        return withdrawalEventFlowable(filter);
    }

    public static List<WithdrawalWithMessageEventResponse> getWithdrawalWithMessageEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(WITHDRAWALWITHMESSAGE_EVENT, transactionReceipt);
        ArrayList<WithdrawalWithMessageEventResponse> responses = new ArrayList<WithdrawalWithMessageEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawalWithMessageEventResponse typedResponse = new WithdrawalWithMessageEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._l2Sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._l1Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._additionalData = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawalWithMessageEventResponse> withdrawalWithMessageEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawalWithMessageEventResponse>() {
            @Override
            public WithdrawalWithMessageEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWALWITHMESSAGE_EVENT, log);
                WithdrawalWithMessageEventResponse typedResponse = new WithdrawalWithMessageEventResponse();
                typedResponse.log = log;
                typedResponse._l2Sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._l1Receiver = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._additionalData = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawalWithMessageEventResponse> withdrawalWithMessageEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWALWITHMESSAGE_EVENT));
        return withdrawalWithMessageEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String _account, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFromTo(String _from, String _to, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFERFROMTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public String encodeWithdraw(String _l1Receiver, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _l1Receiver)),
                Collections.<TypeReference<?>>emptyList());
        return FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String _l1Receiver, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _l1Receiver)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawWithMessage(String _l1Receiver, byte[] _additionalData, BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWWITHMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _l1Receiver), 
                new org.web3j.abi.datatypes.DynamicBytes(_additionalData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    @Deprecated
    public static IEthToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IEthToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IEthToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IEthToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IEthToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IEthToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IEthToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IEthToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class MintEventResponse extends BaseEventResponse {
        public String account;

        public BigInteger amount;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class WithdrawalEventResponse extends BaseEventResponse {
        public String _l2Sender;

        public String _l1Receiver;

        public BigInteger _amount;
    }

    public static class WithdrawalWithMessageEventResponse extends BaseEventResponse {
        public String _l2Sender;

        public String _l1Receiver;

        public BigInteger _amount;

        public byte[] _additionalData;
    }
}
