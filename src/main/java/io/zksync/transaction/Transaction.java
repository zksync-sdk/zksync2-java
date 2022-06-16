package io.zksync.transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.zksync.transaction.fee.Fee;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint32;

import io.zksync.crypto.eip712.Structurable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = DeployContract.class, name = DeployContract.DEPLOY_CONTRACT_TYPE),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = Withdraw.class, name = Withdraw.WITHDRAW_TYPE),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = Execute.class, name = Execute.EXECUTE_TYPE),
})
public abstract class Transaction {

    @JsonIgnore
    private Address initiatorAddress;

    private Fee fee;

    @JsonIgnore
    private Uint32 nonce;

    public abstract String getType();

    @JsonGetter("initiatorAddress")
    public String getInitiatorAddressString() {
        return initiatorAddress.getValue();
    }

    @JsonGetter("nonce")
    public BigInteger getNonceNumber() {
        return nonce.getValue();
    }

    @JsonSetter("initiatorAddress")
    public void setInitiatorAddress(String initiatorAddress) {
        this.initiatorAddress = new Address(initiatorAddress);
    }

    @JsonSetter("nonce")
    public void setNonce(Integer nonce) {
        this.nonce = new Uint32(nonce);
    }
}
