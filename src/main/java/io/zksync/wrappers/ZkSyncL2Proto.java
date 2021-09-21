package io.zksync.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.StructType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes1;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.MigrateToPorter;
import io.zksync.transaction.Transaction;
import io.zksync.transaction.Transfer;
import io.zksync.transaction.Withdraw;

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
public class ZkSyncL2Proto extends Contract {

    public static final String ZK_SYNC_CORE_ADDRESS = "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";

    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_DEPLOYCONTRACT = "deployContract";

    public static final String FUNC_EXECUTE = "execute";

    public static final String FUNC_MIGRATETOPORTER = "migrateToPorter";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_WITHDRAW = "withdraw";

    @Deprecated
    protected ZkSyncL2Proto(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ZkSyncL2Proto(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ZkSyncL2Proto(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ZkSyncL2Proto(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> deployContract(DeployContractStruct param0, CommonData param1) {
        final Function function = new Function(
                FUNC_DEPLOYCONTRACT, 
                Arrays.<Type>asList(param0, 
                param1), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> execute(ExecuteStruct param0, CommonData param1) {
        final Function function = new Function(
                FUNC_EXECUTE, 
                Arrays.<Type>asList(param0, 
                param1), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> migrateToPorter(MigrateToPorterStruct param0, CommonData param1) {
        final Function function = new Function(
                FUNC_MIGRATETOPORTER, 
                Arrays.<Type>asList(param0, 
                param1), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(TransferStruct param0, CommonData param1) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(param0, 
                param1), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(WithdrawStruct param0, CommonData param1) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(param0, 
                param1), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ZkSyncL2Proto load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ZkSyncL2Proto(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ZkSyncL2Proto load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ZkSyncL2Proto(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ZkSyncL2Proto load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ZkSyncL2Proto(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ZkSyncL2Proto load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ZkSyncL2Proto(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static Function encodeFunction(DeployContractStruct param0, CommonData param1) {
        final Function function = new Function(
            FUNC_DEPLOYCONTRACT, 
            Arrays.<Type>asList(param0, 
            param1), 
            Collections.<TypeReference<?>>emptyList());
        return function;
    }

    public static Function encodeFunction(ExecuteStruct param0, CommonData param1) {
        final Function function = new Function(
            FUNC_EXECUTE, 
            Arrays.<Type>asList(param0, 
            param1), 
            Collections.<TypeReference<?>>emptyList());
        return function;
    }

    public static Function encodeFunction(MigrateToPorterStruct param0, CommonData param1) {
        final Function function = new Function(
            FUNC_MIGRATETOPORTER, 
            Arrays.<Type>asList(param0, 
            param1), 
            Collections.<TypeReference<?>>emptyList());
        return function;
    }

    public static Function encodeFunction(TransferStruct param0, CommonData param1) {
        final Function function = new Function(
            FUNC_TRANSFER, 
            Arrays.<Type>asList(param0, 
            param1), 
            Collections.<TypeReference<?>>emptyList());
        return function;
    }

    public static Function encodeFunction(WithdrawStruct param0, CommonData param1) {
        final Function function = new Function(
            FUNC_WITHDRAW, 
            Arrays.<Type>asList(param0, 
            param1), 
            Collections.<TypeReference<?>>emptyList());
        return function;
    }

    public static class DeployContractStruct extends DynamicStruct {
        public BigInteger accountType;

        public byte[] bytecode;

        public byte[] calldata;

        public DeployContractStruct(BigInteger accountType, byte[] bytecode, byte[] calldata) {
            super(new org.web3j.abi.datatypes.generated.Uint8(accountType),new org.web3j.abi.datatypes.DynamicBytes(bytecode),new org.web3j.abi.datatypes.DynamicBytes(calldata));
            this.accountType = accountType;
            this.bytecode = bytecode;
            this.calldata = calldata;
        }

        public DeployContractStruct(Uint8 accountType, DynamicBytes bytecode, DynamicBytes calldata) {
            super(accountType,bytecode,calldata);
            this.accountType = accountType.getValue();
            this.bytecode = bytecode.getValue();
            this.calldata = calldata.getValue();
        }

        public DeployContractStruct(DeployContract deployContract) {
            this(
                deployContract.getAccountType().getType(),
                new DynamicBytes(deployContract.getBytecode()),
                new DynamicBytes(deployContract.getCalldata())
            );
        }
    }

    public static class CommonData extends StaticStruct {
        public BigInteger nonce;

        public BigInteger validFrom;

        public BigInteger validTo;

        public String feeToken;

        public BigInteger fee;

        public String initiator;

        public byte[] signature;

        public CommonData(BigInteger nonce, BigInteger validFrom, BigInteger validTo, String feeToken, BigInteger fee, String initiator, byte[] signature) {
            super(new org.web3j.abi.datatypes.generated.Uint32(nonce),new org.web3j.abi.datatypes.generated.Uint64(validFrom),new org.web3j.abi.datatypes.generated.Uint64(validTo),new org.web3j.abi.datatypes.Address(feeToken),new org.web3j.abi.datatypes.generated.Uint256(fee),new org.web3j.abi.datatypes.Address(initiator),new StaticArray65(signature));
            this.nonce = nonce;
            this.validFrom = validFrom;
            this.validTo = validTo;
            this.feeToken = feeToken;
            this.fee = fee;
            this.initiator = initiator;
            this.signature = signature;
        }

        public CommonData(Transaction transaction, byte[] signature) {
            super(
                transaction.getNonce(),
                transaction.getValidIn().getFrom(),
                transaction.getValidIn().getUntil(),
                transaction.getFeeToken(),
                transaction.getFee(),
                transaction.getInitiatorAddress(),
                new StaticArray65(signature)
            );
            this.nonce = transaction.getNonce().getValue();
            this.validFrom = transaction.getValidIn().getFrom().getValue();
            this.validTo = transaction.getValidIn().getUntil().getValue();
            this.feeToken = transaction.getFeeToken().getValue();
            this.fee = transaction.getFee().getValue();
            this.initiator = transaction.getInitiatorAddress().getValue();
            this.signature = signature;
        }

        public CommonData(Transaction transaction) {
            this(transaction, null);
        }

        @Override
        public String getTypeAsString() {
            return super.getTypeAsString().replace("staticarray65", "bytes1[65]");
        }
    }

    public static class ExecuteStruct extends DynamicStruct {
        public String contractAddress;

        public byte[] callData;

        public ExecuteStruct(String contractAddress, byte[] callData) {
            super(new org.web3j.abi.datatypes.Address(contractAddress),new org.web3j.abi.datatypes.DynamicBytes(callData));
            this.contractAddress = contractAddress;
            this.callData = callData;
        }

        public ExecuteStruct(Address contractAddress, DynamicBytes callData) {
            super(contractAddress,callData);
            this.contractAddress = contractAddress.getValue();
            this.callData = callData.getValue();
        }

        public ExecuteStruct(Execute execute) {
            this(
                execute.getContractAddress(),
                new DynamicBytes(execute.getCalldata())
            );
        }
    }

    public static class MigrateToPorterStruct extends StaticStruct {
        public String accountAddress;

        public MigrateToPorterStruct(String accountAddress) {
            super(new org.web3j.abi.datatypes.Address(accountAddress));
            this.accountAddress = accountAddress;
        }

        public MigrateToPorterStruct(Address accountAddress) {
            super(accountAddress);
            this.accountAddress = accountAddress.getValue();
        }

        public MigrateToPorterStruct(MigrateToPorter migrateToPorter) {
            this(
                migrateToPorter.getAccountAddress()
            );
        }
    }

    public static class TransferStruct extends StaticStruct {
        public String token;

        public BigInteger amount;

        public String to;

        public TransferStruct(String token, BigInteger amount, String to) {
            super(new org.web3j.abi.datatypes.Address(token),new org.web3j.abi.datatypes.generated.Uint256(amount),new org.web3j.abi.datatypes.Address(to));
            this.token = token;
            this.amount = amount;
            this.to = to;
        }

        public TransferStruct(Address token, Uint256 amount, Address to) {
            super(token,amount,to);
            this.token = token.getValue();
            this.amount = amount.getValue();
            this.to = to.getValue();
        }

        public TransferStruct(Transfer transfer) {
            this(
                transfer.getToken(),
                transfer.getAmount(),
                transfer.getTo()
            );
        }
    }

    public static class WithdrawStruct extends StaticStruct {
        public String token;

        public BigInteger amount;

        public String to;

        public WithdrawStruct(String token, BigInteger amount, String to) {
            super(new org.web3j.abi.datatypes.Address(token),new org.web3j.abi.datatypes.generated.Uint256(amount),new org.web3j.abi.datatypes.Address(to));
            this.token = token;
            this.amount = amount;
            this.to = to;
        }

        public WithdrawStruct(Address token, Uint256 amount, Address to) {
            super(token,amount,to);
            this.token = token.getValue();
            this.amount = amount.getValue();
            this.to = to.getValue();
        }

        public WithdrawStruct(Withdraw withdraw) {
            this(
                withdraw.getToken(),
                withdraw.getAmount(),
                withdraw.getTo()
            );
        }
    }

    public static class StaticArray65 extends StaticArray<Bytes1> {

        private final Class<Bytes1> type;
        protected final List<Bytes1> value;

        public StaticArray65(List<Bytes1> values) {
            super(Bytes1.class, 0, Collections.emptyList());
            this.type = Bytes1.class;
            this.value = values;
        }

        public StaticArray65(byte[] values) {
            super(Bytes1.class, 0, Collections.emptyList());
            if (values == null || values.length == 0) {
                values = new byte[65];
            }
            this.value = Arrays.stream(ArrayUtils.toObject(values)).map(value -> new Bytes1(new byte[]{value})).collect(Collectors.toList());
            this.type = Bytes1.class;
        }

        @Override
        public int bytes32PaddedLength() {
            int length = 0;
            for (Bytes1 t : value) {
                int valueLength = t.bytes32PaddedLength();
                length += valueLength;
            }
            return length;
        }

        @Override
        public List<Bytes1> getValue() {
            return value;
        }

        public Class<Bytes1> getComponentType() {
            return type;
        }

        @Override
        public String getTypeAsString() {
            String type;
            if (StructType.class.isAssignableFrom(value.get(0).getClass())) {
                type = value.get(0).getTypeAsString();
            } else {
                type = AbiTypes.getTypeAString(getComponentType());
            }
            return type + "[" + value.size() + "]";
        }
    }
}
