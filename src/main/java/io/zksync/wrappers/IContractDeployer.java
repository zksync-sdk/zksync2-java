package io.zksync.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class IContractDeployer extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_CREATE = "create";

    public static final String FUNC_CREATE2 = "create2";

    public static final String FUNC_CREATE2ACCOUNT = "create2Account";

    public static final String FUNC_CREATEACCOUNT = "createAccount";

    public static final String FUNC_GETACCOUNTINFO = "getAccountInfo";

    public static final String FUNC_GETNEWADDRESSCREATE = "getNewAddressCreate";

    public static final String FUNC_GETNEWADDRESSCREATE2 = "getNewAddressCreate2";

    public static final String FUNC_UPDATEACCOUNTVERSION = "updateAccountVersion";

    public static final String FUNC_UPDATENONCEORDERING = "updateNonceOrdering";

    public static final Event ACCOUNTNONCEORDERINGUPDATED_EVENT = new Event("AccountNonceOrderingUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event ACCOUNTVERSIONUPDATED_EVENT = new Event("AccountVersionUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint8>() {}));
    ;

    public static final Event CONTRACTDEPLOYED_EVENT = new Event("ContractDeployed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected IContractDeployer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IContractDeployer(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IContractDeployer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IContractDeployer(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<AccountNonceOrderingUpdatedEventResponse> getAccountNonceOrderingUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACCOUNTNONCEORDERINGUPDATED_EVENT, transactionReceipt);
        ArrayList<AccountNonceOrderingUpdatedEventResponse> responses = new ArrayList<AccountNonceOrderingUpdatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AccountNonceOrderingUpdatedEventResponse typedResponse = new AccountNonceOrderingUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.nonceOrdering = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AccountNonceOrderingUpdatedEventResponse getAccountNonceOrderingUpdatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACCOUNTNONCEORDERINGUPDATED_EVENT, log);
        AccountNonceOrderingUpdatedEventResponse typedResponse = new AccountNonceOrderingUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.nonceOrdering = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AccountNonceOrderingUpdatedEventResponse> accountNonceOrderingUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAccountNonceOrderingUpdatedEventFromLog(log));
    }

    public Flowable<AccountNonceOrderingUpdatedEventResponse> accountNonceOrderingUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCOUNTNONCEORDERINGUPDATED_EVENT));
        return accountNonceOrderingUpdatedEventFlowable(filter);
    }

    public static List<AccountVersionUpdatedEventResponse> getAccountVersionUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACCOUNTVERSIONUPDATED_EVENT, transactionReceipt);
        ArrayList<AccountVersionUpdatedEventResponse> responses = new ArrayList<AccountVersionUpdatedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AccountVersionUpdatedEventResponse typedResponse = new AccountVersionUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.aaVersion = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AccountVersionUpdatedEventResponse getAccountVersionUpdatedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACCOUNTVERSIONUPDATED_EVENT, log);
        AccountVersionUpdatedEventResponse typedResponse = new AccountVersionUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.aaVersion = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AccountVersionUpdatedEventResponse> accountVersionUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAccountVersionUpdatedEventFromLog(log));
    }

    public Flowable<AccountVersionUpdatedEventResponse> accountVersionUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCOUNTVERSIONUPDATED_EVENT));
        return accountVersionUpdatedEventFlowable(filter);
    }

    public static List<ContractDeployedEventResponse> getContractDeployedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTRACTDEPLOYED_EVENT, transactionReceipt);
        ArrayList<ContractDeployedEventResponse> responses = new ArrayList<ContractDeployedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ContractDeployedEventResponse typedResponse = new ContractDeployedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.deployerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.bytecodeHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ContractDeployedEventResponse getContractDeployedEventFromLog(Log log) {
        EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONTRACTDEPLOYED_EVENT, log);
        ContractDeployedEventResponse typedResponse = new ContractDeployedEventResponse();
        typedResponse.log = log;
        typedResponse.deployerAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.bytecodeHash = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<ContractDeployedEventResponse> contractDeployedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getContractDeployedEventFromLog(log));
    }

    public Flowable<ContractDeployedEventResponse> contractDeployedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTRACTDEPLOYED_EVENT));
        return contractDeployedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> create(byte[] _salt, byte[] _bytecodeHash, byte[] _input, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATE, 
                Arrays.<Type>asList(new Bytes32(_salt),
                new Bytes32(_bytecodeHash),
                new org.web3j.abi.datatypes.DynamicBytes(_input)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> create2(byte[] _salt, byte[] _bytecodeHash, byte[] _input, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATE2, 
                Arrays.<Type>asList(new Bytes32(_salt),
                new Bytes32(_bytecodeHash),
                new org.web3j.abi.datatypes.DynamicBytes(_input)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> create2Account(byte[] _salt, byte[] _bytecodeHash, byte[] _input, BigInteger _aaVersion, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATE2ACCOUNT, 
                Arrays.<Type>asList(new Bytes32(_salt),
                new Bytes32(_bytecodeHash),
                new org.web3j.abi.datatypes.DynamicBytes(_input), 
                new Uint8(_aaVersion)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> createAccount(byte[] _salt, byte[] _bytecodeHash, byte[] _input, BigInteger _aaVersion, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATEACCOUNT, 
                Arrays.<Type>asList(new Bytes32(_salt),
                new Bytes32(_bytecodeHash),
                new org.web3j.abi.datatypes.DynamicBytes(_input), 
                new Uint8(_aaVersion)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<AccountInfo> getAccountInfo(String _address) {
        final Function function = new Function(FUNC_GETACCOUNTINFO, 
                Arrays.<Type>asList(new Address(160, _address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<AccountInfo>() {}));
        return executeRemoteCallSingleValueReturn(function, AccountInfo.class);
    }

    public RemoteFunctionCall<String> getNewAddressCreate(String _sender, BigInteger _senderNonce) {
        final Function function = new Function(FUNC_GETNEWADDRESSCREATE, 
                Arrays.<Type>asList(new Address(160, _sender),
                new org.web3j.abi.datatypes.generated.Uint256(_senderNonce)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getNewAddressCreate2(String _sender, byte[] _bytecodeHash, byte[] _salt, byte[] _input) {
        final Function function = new Function(FUNC_GETNEWADDRESSCREATE2, 
                Arrays.<Type>asList(new Address(160, _sender),
                new Bytes32(_bytecodeHash),
                new Bytes32(_salt),
                new org.web3j.abi.datatypes.DynamicBytes(_input)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateAccountVersion(BigInteger _version) {
        final Function function = new Function(
                FUNC_UPDATEACCOUNTVERSION, 
                Arrays.<Type>asList(new Uint8(_version)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateNonceOrdering(BigInteger _nonceOrdering) {
        final Function function = new Function(
                FUNC_UPDATENONCEORDERING, 
                Arrays.<Type>asList(new Uint8(_nonceOrdering)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IContractDeployer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IContractDeployer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IContractDeployer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IContractDeployer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IContractDeployer load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IContractDeployer(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IContractDeployer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IContractDeployer(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class AccountInfo extends StaticStruct {
        public BigInteger supportedAAVersion;

        public BigInteger nonceOrdering;

        public AccountInfo(BigInteger supportedAAVersion, BigInteger nonceOrdering) {
            super(new Uint8(supportedAAVersion),
                    new Uint8(nonceOrdering));
            this.supportedAAVersion = supportedAAVersion;
            this.nonceOrdering = nonceOrdering;
        }

        public AccountInfo(Uint8 supportedAAVersion, Uint8 nonceOrdering) {
            super(supportedAAVersion, nonceOrdering);
            this.supportedAAVersion = supportedAAVersion.getValue();
            this.nonceOrdering = nonceOrdering.getValue();
        }
    }

    public static class AccountNonceOrderingUpdatedEventResponse extends BaseEventResponse {
        public String accountAddress;

        public BigInteger nonceOrdering;
    }

    public static class AccountVersionUpdatedEventResponse extends BaseEventResponse {
        public String accountAddress;

        public BigInteger aaVersion;
    }

    public static class ContractDeployedEventResponse extends BaseEventResponse {
        public String deployerAddress;

        public byte[] bytecodeHash;

        public String contractAddress;
    }
}
