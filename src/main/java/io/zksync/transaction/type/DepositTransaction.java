package io.zksync.transaction.type;

import io.zksync.wrappers.IL1Bridge;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
public class DepositTransaction {
    public String tokenAddress;
    public BigInteger amount;
    public String to;
    public BigInteger l2GasLimit;
    public String bridgeAddress;
    public byte[] customBridgeData;
    public BigInteger gasPerPubdataByte;
    public BigInteger operatorTip;
    public String refoundRecepient;
    public Boolean approveERC20;
    public Boolean approveBaseERC20;
    public TransactionOptions options;
    public TransactionOptions approveOptions;
    public TransactionOptions approveBaseOptions;

    public DepositTransaction(String tokenAddress, BigInteger amount, @Nullable String to, @Nullable BigInteger l2GasLimit, @Nullable String bridgeAddress, @Nullable byte[] customBridgeData, @Nullable BigInteger gasPerPubdataByte, @Nullable BigInteger operatorTip, @Nullable String refoundRecepient, @Nullable Boolean approveERC20, @Nullable TransactionOptions options) {
        this.tokenAddress = tokenAddress;
        this.amount = amount;
        this.to = to;
        this.l2GasLimit = l2GasLimit;
        this.bridgeAddress = bridgeAddress;
        this.customBridgeData = customBridgeData;
        this.gasPerPubdataByte = gasPerPubdataByte;
        this.operatorTip = operatorTip;
        this.refoundRecepient = refoundRecepient;
        this.approveERC20 = approveERC20;
        this.options = options;
    }

    public DepositTransaction(String tokenAddress, BigInteger amount, Boolean approveBaseERC20) {
        this.tokenAddress = tokenAddress;
        this.amount = amount;
        this.approveBaseERC20 = approveBaseERC20;
    }

    public DepositTransaction(String tokenAddress, BigInteger amount, Boolean approveBaseERC20, Boolean approveERC20) {
        this.tokenAddress = tokenAddress;
        this.amount = amount;
        this.approveBaseERC20 = approveBaseERC20;
        this.approveERC20 = approveERC20;
    }

    public DepositTransaction(String tokenAddress, BigInteger amount) {
        this.tokenAddress = tokenAddress;
        this.amount = amount;
    }

    public RequestExecuteTransaction toRequestExecute(String mainContractAddress){
        return new RequestExecuteTransaction(
                l2GasLimit,
                mainContractAddress,
                Numeric.hexStringToByteArray("0x"),
                amount,
                null,
                operatorTip,
                gasPerPubdataByte,
                refoundRecepient,
                options);
    }

    public RequestExecuteTransaction toRequestExecute(String mainContractAddress, BigInteger mintValue){
        return new RequestExecuteTransaction(
                l2GasLimit,
                mainContractAddress,
                Numeric.hexStringToByteArray("0x"),
                amount,
                mintValue,
                null,
                operatorTip,
                gasPerPubdataByte,
                refoundRecepient,
                options);
    }

    public Transaction toTx(String from, String calldata){
        return new Transaction(from, options.getNonce(), options.getGasPrice(), BigInteger.ZERO, bridgeAddress, options.getValue(), calldata, options.getChainId().longValue(), options.getMaxPriorityFeePerGas(), options.getMaxFeePerGas());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DepositTransaction that = (DepositTransaction) o;
        return Objects.equals(tokenAddress, that.tokenAddress) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(to.toLowerCase(), that.to.toLowerCase()) &&
                Objects.equals(l2GasLimit, that.l2GasLimit) &&
                Objects.equals(bridgeAddress, that.bridgeAddress) &&
                Arrays.equals(customBridgeData, that.customBridgeData) &&
                Objects.equals(gasPerPubdataByte, that.gasPerPubdataByte) &&
                Objects.equals(operatorTip, that.operatorTip) &&
                Objects.equals(refoundRecepient.toLowerCase(), that.refoundRecepient.toLowerCase()) &&
                Objects.equals(approveERC20, that.approveERC20) &&
                Objects.equals(options.getNonce(), that.options.getNonce()) &&
                Objects.equals(options.getValue(), that.options.getValue()) &&
                Objects.equals(options.getGasPrice(), that.options.getGasPrice()) &&
                Objects.equals(options.getMaxFeePerGas(), that.options.getMaxFeePerGas()) &&
                Objects.equals(options.getMaxPriorityFeePerGas(), that.options.getMaxPriorityFeePerGas()) &&
                Objects.equals(options.getGasLimit(), that.options.getGasLimit()) &&
                Objects.equals(options.getChainId(), that.options.getChainId());
    }
}
