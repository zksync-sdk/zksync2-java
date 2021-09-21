package io.zksync.transaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import io.zksync.abi.TransactionEncoder;
import io.zksync.abi.ZkFunctionEncoder;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.TimeRange;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;

public class ZkContract extends Contract {

    protected ZkTransactionFeeProvider txFeeProvider;
    protected TimeRange timeRange = new TimeRange();

    protected ZkContract(String contractBinary, String contractAddress, ZkSync zksync,
            TransactionManager transactionManager, ZkTransactionFeeProvider feeProvider) {
        super(contractBinary, contractAddress, zksync, transactionManager, new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO));
        this.txFeeProvider = feeProvider;
    }

    protected ZkContract(String contractAddress, ZkSync zksync,
            TransactionManager transactionManager, ZkTransactionFeeProvider feeProvider) {
        this("", contractAddress, zksync, transactionManager, feeProvider);
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    @Override
    protected TransactionReceipt executeTransaction(Function function) throws IOException, TransactionException {
        return super.executeTransaction(function);
    }

    @Override
    protected <T extends Type> T executeCallSingleValueReturn(Function function) throws IOException {
        Function encoded = TransactionEncoder.encodeToFunction(packContractFunctionForCall(function));
        return super.executeCallSingleValueReturn(new Function(
            encoded.getName(),
            encoded.getInputParameters(),
            Arrays.<TypeReference<?>>asList(function.getOutputParameters().toArray(new TypeReference<?>[0]))
        ));
    }

    private Execute packContractFunctionForCall(Function function) {
        String encodedFunction = Numeric.toHexString(ZkFunctionEncoder.encodeCalldata(function));
        Execute zkExecute = new Execute(
            this.getContractAddress(),
            encodedFunction,
            this.transactionManager.getFromAddress(),
            this.txFeeProvider.getFeeToken().getAddress(),
            BigInteger.ZERO,
            0,
            timeRange
        );

        return zkExecute;
    }

    private DeployContract packDeployContract(AccountType accountType) {
        DeployContract zkDeploy = new DeployContract(
            accountType,
            this.contractBinary,
            this.transactionManager.getFromAddress(),
            this.txFeeProvider.getFeeToken().getAddress(),
            BigInteger.ZERO,
            0,
            new TimeRange()
        );

        return zkDeploy;
    }
    
}
