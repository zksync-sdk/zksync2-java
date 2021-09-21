package io.zksync.transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.utils.Numeric;

import io.zksync.crypto.eip712.Structurable;
import io.zksync.protocol.core.TimeRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "type")
@JsonSubTypes({ 
  @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = DeployContract.class, name = "DeployContract"), 
  @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = MigrateToPorter.class, name = "MigrateToPorter"),
  @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = Transfer.class, name = "Transfer"),
  @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = Withdraw.class, name = "Withdraw"),
  @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = Execute.class, name = "Execute"),
})
public abstract class Transaction implements Structurable {

    @JsonIgnore
    private Address initiatorAddress;

    @JsonIgnore
    private Address feeToken;

    @JsonIgnore
    private Uint256 fee;

    @JsonIgnore
    private Uint32 nonce;

    private TimeRange validIn;

    @Override
    public abstract String getType();

    @JsonGetter("initiatorAddress")
    public String getInitiatorAddressString() {
        return initiatorAddress.getValue();
    }

    @JsonGetter("feeToken")
    public String getFeeTokenString() {
        return feeToken.getValue();
    }

    @JsonGetter("fee")
    public BigInteger getFeeNumber() {
        return fee.getValue();
    }

    @JsonGetter("nonce")
    public BigInteger getNonceNumber() {
        return nonce.getValue();
    }

    @JsonSetter("initiatorAddress")
    public void setInitiatorAddress(String initiatorAddress) {
        this.initiatorAddress = new Address(initiatorAddress);
    }

    @JsonSetter("feeToken")
    public void setFeeToken(String feeToken) {
        this.feeToken = new Address(feeToken);
    }

    @JsonSetter("fee")
    public void setFee(String feeHex) {
        this.fee = new Uint256(Numeric.toBigInt(feeHex));
    }

    public void setFee(BigInteger fee) {
        this.fee = new Uint256(fee);
    }

    @JsonSetter("nonce")
    public void setNonce(Integer nonce) {
        this.nonce = new Uint32(nonce);
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        return new ArrayList<Pair<String, Type<?>>>() {{
            add(Pair.of("initiatorAddress", initiatorAddress));
            add(Pair.of("feeToken", feeToken));
            add(Pair.of("fee", fee));
            add(Pair.of("nonce", nonce));
            add(Pair.of("validFrom", validIn.getFrom()));
            add(Pair.of("validUntil", validIn.getUntil()));
        }};
    }
}
