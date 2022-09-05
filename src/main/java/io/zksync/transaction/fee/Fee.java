package io.zksync.transaction.fee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class Fee {

    @JsonIgnore
    private Uint256 ergsLimit;

    @JsonIgnore
    private Uint256 maxFeePerErg;

    @JsonIgnore
    private Uint256 maxPriorityFeePerErg;

    @JsonIgnore
    private Uint256 ergsPerPubdataLimit;

    public Fee() {
        this.ergsLimit = Uint256.DEFAULT;
        this.maxFeePerErg = Uint256.DEFAULT;
        this.maxPriorityFeePerErg = Uint256.DEFAULT;
        this.ergsPerPubdataLimit = Uint256.DEFAULT;
    }

    public BigInteger getErgsLimitNumber() {
        return ergsLimit.getValue();
    }

    public void setErgsLimit(BigInteger ergsLimit) {
        this.ergsLimit = new Uint256(ergsLimit);
    }

    @JsonSetter("ergs_limit")
    public void setErgsLimit(String ergsLimit) {
        this.ergsLimit = new Uint256(Numeric.toBigInt(ergsLimit));
    }

    public BigInteger getErgsPriceLimitNumber() {
        return maxFeePerErg.getValue();
    }

    public void setMaxFeePerErg(BigInteger maxFeePerErg) {
        this.maxFeePerErg = new Uint256(maxFeePerErg);
    }

    @JsonSetter("max_fee_per_erg")
    public void setMaxFeePerErg(String ergsPriceLimit) {
        this.maxFeePerErg = new Uint256(Numeric.toBigInt(ergsPriceLimit));
    }

    public BigInteger getMaxPriorityFeePerErgNumber() {
        return maxPriorityFeePerErg.getValue();
    }

    public void setMaxPriorityFeePerErg(BigInteger maxPriorityFeePerErg) {
        this.maxPriorityFeePerErg = new Uint256(maxPriorityFeePerErg);
    }

    @JsonSetter("max_priority_fee_per_erg")
    public void setMaxPriorityFeePerErg(String maxPriorityFeePerErg) {
        this.maxPriorityFeePerErg = new Uint256(Numeric.toBigInt(maxPriorityFeePerErg));
    }

    public BigInteger getErgsPerPubdataLimitNumber() {
        return ergsPerPubdataLimit.getValue();
    }

    public void setErgsPerPubdataLimit(BigInteger ergsPerPubdataLimit) {
        this.ergsPerPubdataLimit = new Uint256(ergsPerPubdataLimit);
    }

    @JsonSetter("ergs_per_pubdata_limit")
    public void setErgsPerPubdataLimit(String ergsPerPubdataLimit) {
        this.ergsPerPubdataLimit = new Uint256(Numeric.toBigInt(ergsPerPubdataLimit));
    }
}
