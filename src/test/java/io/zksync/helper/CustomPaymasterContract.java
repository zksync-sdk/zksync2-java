package io.zksync.helper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.StaticArray6;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
 * <p>Generated with web3j version 4.7.0.
 */
@SuppressWarnings("rawtypes")
public class CustomPaymasterContract extends Contract {
    public static final String BINARY;

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/customPaymasterBinary.hex")) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int count;
            byte[] bytes = new byte[16536];
            while ((count = fis.read(bytes)) != -1) {
                buffer.write(bytes, 0, count);
            }
            BINARY = buffer.toString("UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String FUNC_VALIDATEANDPAYFORPAYMASTERTRANSACTION = "validateAndPayForPaymasterTransaction";

    @Deprecated
    protected CustomPaymasterContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CustomPaymasterContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CustomPaymasterContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CustomPaymasterContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> validateAndPayForPaymasterTransaction(Transaction _transaction) {
        final Function function = new Function(
                FUNC_VALIDATEANDPAYFORPAYMASTERTRANSACTION, 
                Arrays.<Type>asList(_transaction), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CustomPaymasterContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CustomPaymasterContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CustomPaymasterContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CustomPaymasterContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CustomPaymasterContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CustomPaymasterContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CustomPaymasterContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CustomPaymasterContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CustomPaymasterContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CustomPaymasterContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CustomPaymasterContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CustomPaymasterContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CustomPaymasterContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CustomPaymasterContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CustomPaymasterContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CustomPaymasterContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    /**
     * struct Transaction {
     * 	uint8 txType;
     * 	uint256 from;
     * 	uint256 to;
     * 	uint256 ergsLimit;
     * 	uint256 ergsPerPubdataByteLimit;
     * 	uint256 ergsPrice;
     * 	uint256 paymaster;
     * 	// In the future, we might want to add some
     * 	// new fields to the struct. The `txData` struct
     * 	// is to be passed to account and any changes to its structure
     * 	// would mean a breaking change to these accounts. In order to prevent this,
     * 	// we should keep some fields as "reserved".
     * 	// It is also recommneded that their length is fixed, since
     * 	// it would allow easier proof integration (in case we will need
     * 	// some special circuit for preprocessing transactions).
     * 	uint256[6] reserved;
     * 	bytes data;
     * 	bytes signature;
     * 	bytes32[] factoryDeps;
     * 	bytes paymasterInput;
     * 	// Reserved dynamic type for the future use-case. Using it should be avoided,
     * 	// But it is still here, just in case we want to enable some additional functionality.
     * 	bytes reservedDynamic;
     * }
     */

    public static class Transaction extends DynamicStruct {
        public BigInteger txType;

        public BigInteger from;

        public BigInteger to;

        public BigInteger ergsLimit;

        public BigInteger ergsPerPubdataByteLimit;

        public BigInteger ergsPrice;

        public BigInteger paymaster;

        public List<BigInteger> reserved;

        public byte[] data;

        public byte[] signature;

        public List<byte[]> factoryDeps;

        public byte[] paymasterInput;

        public byte[] reservedDynamic;

        public Transaction(BigInteger txType, BigInteger from, BigInteger to, BigInteger ergsLimit, BigInteger ergsPerPubdataByteLimit, BigInteger ergsPrice, BigInteger paymaster, List<BigInteger> reserved, byte[] data, byte[] signature, List<byte[]> factoryDeps, byte[] paymasterInput, byte[] reservedDynamic) {
            super(new org.web3j.abi.datatypes.generated.Uint8(txType),new org.web3j.abi.datatypes.generated.Uint256(from),new org.web3j.abi.datatypes.generated.Uint256(to),new org.web3j.abi.datatypes.generated.Uint256(ergsLimit),new org.web3j.abi.datatypes.generated.Uint256(ergsPerPubdataByteLimit),new org.web3j.abi.datatypes.generated.Uint256(ergsPrice),new org.web3j.abi.datatypes.generated.Uint256(paymaster),new org.web3j.abi.datatypes.generated.StaticArray6<>(Uint256.class, reserved.stream().map(Uint256::new).collect(Collectors.toList())),new org.web3j.abi.datatypes.DynamicBytes(data),new org.web3j.abi.datatypes.DynamicBytes(signature),new org.web3j.abi.datatypes.DynamicArray<>(Bytes32.class, factoryDeps.stream().map(Bytes32::new).collect(Collectors.toList())),new org.web3j.abi.datatypes.DynamicBytes(paymasterInput),new org.web3j.abi.datatypes.DynamicBytes(reservedDynamic));
            this.txType = txType;
            this.from = from;
            this.to = to;
            this.ergsLimit = ergsLimit;
            this.ergsPerPubdataByteLimit = ergsPerPubdataByteLimit;
            this.ergsPrice = ergsPrice;
            this.paymaster = paymaster;
            this.reserved = reserved;
            this.data = data;
            this.signature = signature;
            this.factoryDeps = factoryDeps;
            this.paymasterInput = paymasterInput;
            this.reservedDynamic = reservedDynamic;
        }

        public Transaction(Uint8 txType, Uint256 from, Uint256 to, Uint256 ergsLimit, Uint256 ergsPerPubdataByteLimit, Uint256 ergsPrice, Uint256 paymaster, StaticArray6<Uint256> reserved, DynamicBytes data, DynamicBytes signature, DynamicArray<Bytes32> factoryDeps, DynamicBytes paymasterInput, DynamicBytes reservedDynamic) {
            super(txType,from,to,ergsLimit,ergsPerPubdataByteLimit,ergsPrice,paymaster,reserved,data,signature,factoryDeps,paymasterInput,reservedDynamic);
            this.txType = txType.getValue();
            this.from = from.getValue();
            this.to = to.getValue();
            this.ergsLimit = ergsLimit.getValue();
            this.ergsPerPubdataByteLimit = ergsPerPubdataByteLimit.getValue();
            this.ergsPrice = ergsPrice.getValue();
            this.paymaster = paymaster.getValue();
            this.reserved = reserved.getValue().stream().map(Uint256::getValue).collect(Collectors.toList());
            this.data = data.getValue();
            this.signature = signature.getValue();
            this.factoryDeps = factoryDeps.getValue().stream().map(Bytes32::getValue).collect(Collectors.toList());
            this.paymasterInput = paymasterInput.getValue();
            this.reservedDynamic = reservedDynamic.getValue();
        }
    }
}
