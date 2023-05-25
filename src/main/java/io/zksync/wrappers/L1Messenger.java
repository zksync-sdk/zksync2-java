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
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class L1Messenger extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_SENDTOL1 = "sendToL1";

    public static final Event L1MESSAGESENT_EVENT = new Event("L1MessageSent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected L1Messenger(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected L1Messenger(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected L1Messenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected L1Messenger(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<L1MessageSentEventResponse> getL1MessageSentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(L1MESSAGESENT_EVENT, transactionReceipt);
        ArrayList<L1MessageSentEventResponse> responses = new ArrayList<L1MessageSentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            L1MessageSentEventResponse typedResponse = new L1MessageSentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._hash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._message = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<L1MessageSentEventResponse> l1MessageSentEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, L1MessageSentEventResponse>() {
            @Override
            public L1MessageSentEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(L1MESSAGESENT_EVENT, log);
                L1MessageSentEventResponse typedResponse = new L1MessageSentEventResponse();
                typedResponse.log = log;
                typedResponse._sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._hash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._message = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<L1MessageSentEventResponse> l1MessageSentEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(L1MESSAGESENT_EVENT));
        return l1MessageSentEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> sendToL1(byte[] _message) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SENDTOL1, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_message)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static L1Messenger load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new L1Messenger(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static L1Messenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new L1Messenger(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static L1Messenger load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new L1Messenger(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static L1Messenger load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new L1Messenger(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class L1MessageSentEventResponse extends BaseEventResponse {
        public String _sender;

        public byte[] _hash;

        public byte[] _message;
    }
}
