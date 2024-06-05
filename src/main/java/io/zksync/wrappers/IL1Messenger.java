package io.zksync.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint16;
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
public class IL1Messenger extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_REQUESTBYTECODEL1PUBLICATION = "requestBytecodeL1Publication";

    public static final String FUNC_SENDL2TOL1LOG = "sendL2ToL1Log";

    public static final String FUNC_SENDTOL1 = "sendToL1";

    public static final Event BYTECODEL1PUBLICATIONREQUESTED_EVENT = new Event("BytecodeL1PublicationRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event L1MESSAGESENT_EVENT = new Event("L1MessageSent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event L2TOL1LOGSENT_EVENT = new Event("L2ToL1LogSent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<L2ToL1Log>() {}));
    ;

    @Deprecated
    protected IL1Messenger(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IL1Messenger(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IL1Messenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IL1Messenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BytecodeL1PublicationRequestedEventResponse> getBytecodeL1PublicationRequestedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BYTECODEL1PUBLICATIONREQUESTED_EVENT, transactionReceipt);
        ArrayList<BytecodeL1PublicationRequestedEventResponse> responses = new ArrayList<BytecodeL1PublicationRequestedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            BytecodeL1PublicationRequestedEventResponse typedResponse = new BytecodeL1PublicationRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._bytecodeHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BytecodeL1PublicationRequestedEventResponse getBytecodeL1PublicationRequestedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BYTECODEL1PUBLICATIONREQUESTED_EVENT, log);
        BytecodeL1PublicationRequestedEventResponse typedResponse = new BytecodeL1PublicationRequestedEventResponse();
        typedResponse.log = log;
        typedResponse._bytecodeHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<BytecodeL1PublicationRequestedEventResponse> bytecodeL1PublicationRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBytecodeL1PublicationRequestedEventFromLog(log));
    }

    public Flowable<BytecodeL1PublicationRequestedEventResponse> bytecodeL1PublicationRequestedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BYTECODEL1PUBLICATIONREQUESTED_EVENT));
        return bytecodeL1PublicationRequestedEventFlowable(filter);
    }

    public static List<L1MessageSentEventResponse> getL1MessageSentEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(L1MESSAGESENT_EVENT, transactionReceipt);
        ArrayList<L1MessageSentEventResponse> responses = new ArrayList<L1MessageSentEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            L1MessageSentEventResponse typedResponse = new L1MessageSentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._hash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._message = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static L1MessageSentEventResponse getL1MessageSentEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(L1MESSAGESENT_EVENT, log);
        L1MessageSentEventResponse typedResponse = new L1MessageSentEventResponse();
        typedResponse.log = log;
        typedResponse._sender = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._hash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse._message = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<L1MessageSentEventResponse> l1MessageSentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getL1MessageSentEventFromLog(log));
    }

    public Flowable<L1MessageSentEventResponse> l1MessageSentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(L1MESSAGESENT_EVENT));
        return l1MessageSentEventFlowable(filter);
    }

    public static List<L2ToL1LogSentEventResponse> getL2ToL1LogSentEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(L2TOL1LOGSENT_EVENT, transactionReceipt);
        ArrayList<L2ToL1LogSentEventResponse> responses = new ArrayList<L2ToL1LogSentEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            L2ToL1LogSentEventResponse typedResponse = new L2ToL1LogSentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._l2log = (L2ToL1Log) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public static L2ToL1LogSentEventResponse getL2ToL1LogSentEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(L2TOL1LOGSENT_EVENT, log);
        L2ToL1LogSentEventResponse typedResponse = new L2ToL1LogSentEventResponse();
        typedResponse.log = log;
        typedResponse._l2log = (L2ToL1Log) eventValues.getNonIndexedValues().get(0);
        return typedResponse;
    }

    public Flowable<L2ToL1LogSentEventResponse> l2ToL1LogSentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getL2ToL1LogSentEventFromLog(log));
    }

    public Flowable<L2ToL1LogSentEventResponse> l2ToL1LogSentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(L2TOL1LOGSENT_EVENT));
        return l2ToL1LogSentEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> requestBytecodeL1Publication(byte[] _bytecodeHash) {
        final Function function = new Function(
                FUNC_REQUESTBYTECODEL1PUBLICATION, 
                Arrays.<Type>asList(new Bytes32(_bytecodeHash)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sendL2ToL1Log(Boolean _isService, byte[] _key, byte[] _value) {
        final Function function = new Function(
                FUNC_SENDL2TOL1LOG, 
                Arrays.<Type>asList(new Bool(_isService),
                new Bytes32(_key),
                new Bytes32(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String encodeSendToL1(byte[] _message) {
        final Function function = new Function(
                FUNC_SENDTOL1,
                Arrays.<Type>asList(new DynamicBytes(_message)),
                Collections.<TypeReference<?>>emptyList());
        return FunctionEncoder.encode(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sendToL1(byte[] _message) {
        final Function function = new Function(
                FUNC_SENDTOL1, 
                Arrays.<Type>asList(new DynamicBytes(_message)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IL1Messenger load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1Messenger(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IL1Messenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IL1Messenger(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IL1Messenger load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IL1Messenger(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IL1Messenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IL1Messenger(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class L2ToL1Log extends StaticStruct {
        public BigInteger l2ShardId;

        public Boolean isService;

        public BigInteger txNumberInBlock;

        public String sender;

        public byte[] key;

        public byte[] value;

        public L2ToL1Log(BigInteger l2ShardId, Boolean isService, BigInteger txNumberInBlock, String sender, byte[] key, byte[] value) {
            super(new Uint8(l2ShardId),
                    new Bool(isService),
                    new Uint16(txNumberInBlock),
                    new Address(160, sender),
                    new Bytes32(key),
                    new Bytes32(value));
            this.l2ShardId = l2ShardId;
            this.isService = isService;
            this.txNumberInBlock = txNumberInBlock;
            this.sender = sender;
            this.key = key;
            this.value = value;
        }

        public L2ToL1Log(Uint8 l2ShardId, Bool isService, Uint16 txNumberInBlock, Address sender, Bytes32 key, Bytes32 value) {
            super(l2ShardId, isService, txNumberInBlock, sender, key, value);
            this.l2ShardId = l2ShardId.getValue();
            this.isService = isService.getValue();
            this.txNumberInBlock = txNumberInBlock.getValue();
            this.sender = sender.getValue();
            this.key = key.getValue();
            this.value = value.getValue();
        }
    }

    public static class BytecodeL1PublicationRequestedEventResponse extends BaseEventResponse {
        public byte[] _bytecodeHash;
    }

    public static class L1MessageSentEventResponse extends BaseEventResponse {
        public String _sender;

        public byte[] _hash;

        public byte[] _message;
    }

    public static class L2ToL1LogSentEventResponse extends BaseEventResponse {
        public L2ToL1Log _l2log;
    }
}
