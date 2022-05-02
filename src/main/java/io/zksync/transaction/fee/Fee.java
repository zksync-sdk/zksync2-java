package io.zksync.transaction.fee;

import com.fasterxml.jackson.annotation.JsonGetter;
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
@NoArgsConstructor
public class Fee {

    @JsonIgnore
    private Uint256 ergsLimit;

    @JsonIgnore
    private Uint256 ergsPriceLimit;

    @JsonIgnore
    private Address feeToken;

    @JsonIgnore
    private Uint256 ergsPerStorageLimit;

    @JsonIgnore
    private Uint256 ergsPerPubdataLimit;

    public Fee(Address feeToken) {
        this.feeToken = feeToken;
        this.ergsLimit = Uint256.DEFAULT;
        this.ergsPriceLimit = Uint256.DEFAULT;
        this.ergsPerStorageLimit = Uint256.DEFAULT;
        this.ergsPerPubdataLimit = Uint256.DEFAULT;
    }

    public Fee(String feeToken) {
        this(new Address(feeToken));
    }

    public BigInteger getErgsLimitNumber() {
        return ergsLimit.getValue();
    }

    public void setErgsLimit(BigInteger ergsLimit) {
        this.ergsLimit = new Uint256(ergsLimit);
    }

    @JsonSetter("ergsLimit")
    public void setErgsLimit(String ergsLimit) {
        this.ergsLimit = new Uint256(Numeric.toBigInt(ergsLimit));
    }

    public BigInteger getErgsPriceLimitNumber() {
        return ergsPriceLimit.getValue();
    }

    public void setErgsPriceLimit(BigInteger ergsPriceLimit) {
        this.ergsPriceLimit = new Uint256(ergsPriceLimit);
    }

    @JsonSetter("ergsPriceLimit")
    public void setErgsPriceLimit(String ergsPriceLimit) {
        this.ergsPriceLimit = new Uint256(Numeric.toBigInt(ergsPriceLimit));
    }

    public String getFeeTokenString() {
        return feeToken.getValue();
    }

    @JsonSetter("feeToken")
    public void setFeeToken(String feeToken) {
        this.feeToken = new Address(feeToken);
    }

    public BigInteger getErgsPerStorageLimitNumber() {
        return ergsPerStorageLimit.getValue();
    }

    public void setErgsPerStorageLimit(BigInteger ergsPerStorageLimit) {
        this.ergsPerStorageLimit = new Uint256(ergsPerStorageLimit);
    }

    @JsonSetter("ergsPerStorageLimit")
    public void setErgsPerStorageLimit(String ergsPerStorageLimit) {
        this.ergsPerStorageLimit = new Uint256(Numeric.toBigInt(ergsPerStorageLimit));
    }

    public BigInteger getErgsPerPubdataLimitNumber() {
        return ergsPerPubdataLimit.getValue();
    }

    public void setErgsPerPubdataLimit(BigInteger ergsPerPubdataLimit) {
        this.ergsPerPubdataLimit = new Uint256(ergsPerPubdataLimit);
    }

    @JsonSetter("ergsPerPubdataLimit")
    public void setErgsPerPubdataLimit(String ergsPerPubdataLimit) {
        this.ergsPerPubdataLimit = new Uint256(Numeric.toBigInt(ergsPerPubdataLimit));
    }
}
