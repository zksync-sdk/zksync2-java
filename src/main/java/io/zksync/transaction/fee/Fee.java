package io.zksync.transaction.fee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class Fee {

    @JsonIgnore
    private Uint256 gasLimit;

    @JsonIgnore
    private Uint256 maxFeePerGas;

    @JsonIgnore
    private Uint256 maxPriorityFeePerGas;

    @JsonIgnore
    private Uint256 gasPerPubdataLimit;

    public Fee() {
        this.gasLimit = Uint256.DEFAULT;
        this.maxFeePerGas = Uint256.DEFAULT;
        this.maxPriorityFeePerGas = Uint256.DEFAULT;
        this.gasPerPubdataLimit = Uint256.DEFAULT;
    }

    public Fee(BigInteger gqsLimit, BigInteger maxFeePerGas, BigInteger maxPriorityFeePerGas, BigInteger gasPerPubdataLimit) {
        this.gasLimit = new Uint256(gqsLimit);
        this.maxFeePerGas = new Uint256(maxFeePerGas);
        this.maxPriorityFeePerGas = new Uint256(maxPriorityFeePerGas);
        this.gasPerPubdataLimit = new Uint256(gasPerPubdataLimit);
    }

    public BigInteger getGasLimitNumber() {
        return gasLimit.getValue();
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = new Uint256(gasLimit);
    }

    @JsonSetter("gas_limit")
    public void setGasLimit(String gasLimit) {
        this.gasLimit = new Uint256(Numeric.toBigInt(gasLimit));
    }

    public BigInteger getGasPriceLimitNumber() {
        return maxFeePerGas.getValue();
    }

    public void setMaxFeePerGas(BigInteger maxFeePerGas) {
        this.maxFeePerGas = new Uint256(maxFeePerGas);
    }

    @JsonSetter("max_fee_per_gas")
    public void setMaxFeePerGas(String gasPriceLimit) {
        this.maxFeePerGas = new Uint256(Numeric.toBigInt(gasPriceLimit));
    }

    public BigInteger getMaxPriorityFeePerErgNumber() {
        return maxPriorityFeePerGas.getValue();
    }

    public void setMaxPriorityFeePerGas(BigInteger maxPriorityFeePerGas) {
        this.maxPriorityFeePerGas = new Uint256(maxPriorityFeePerGas);
    }

    @JsonSetter("max_priority_fee_per_erg")
    public void setMaxPriorityFeePerGas(String maxPriorityFeePerErg) {
        this.maxPriorityFeePerGas = new Uint256(Numeric.toBigInt(maxPriorityFeePerErg));
    }

    public BigInteger getGasPerPubdataLimitNumber() {
        return gasPerPubdataLimit.getValue();
    }

    public void setGasPerPubdataLimit(BigInteger gasPerPubdataLimit) {
        this.gasPerPubdataLimit = new Uint256(gasPerPubdataLimit);
    }

    @JsonSetter("gas_per_pubdata_limit")
    public void setGasPerPubdataLimit(String gasPerPubdataLimit) {
        this.gasPerPubdataLimit = new Uint256(Numeric.toBigInt(gasPerPubdataLimit));
    }
}
