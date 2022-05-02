package io.zksync.transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import io.zksync.transaction.fee.Fee;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.utils.Numeric;

import io.zksync.protocol.core.TimeRange;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Withdraw extends Transaction {

    public static final String WITHDRAW_TYPE = "Withdraw";

    @JsonIgnore
    private Address token;

    @JsonIgnore
    private Address to;

    @JsonIgnore
    private Uint256 amount;

    public Withdraw(Address token, Address to, Uint256 amount, Address initiatorAddress, Fee fee, Uint32 nonce) {
        super(initiatorAddress, fee, nonce);

        this.token = token;
        this.to = to;
        this.amount = amount;
    }

    public Withdraw(String token, String to, BigInteger amount, String initiatorAddress, Fee fee, BigInteger nonce) {
        this(
            new Address(token),
            new Address(to),
            new Uint256(amount),
            new Address(initiatorAddress),
            fee,
            new Uint32(nonce)
        );
    }

    @JsonGetter("token")
    public String getTokenString() {
        return token.getValue();
    }

    @JsonGetter("to")
    public String getToString() {
        return to.getValue();
    }

    @JsonGetter("amount")
    public BigInteger getAmountNumber() {
        return amount.getValue();
    }

    @JsonSetter("token")
    public void setToken(String token) {
        this.token = new Address(token);
    }

    @JsonSetter("to")
    public void setTo(String to) {
        this.to = new Address(to);
    }

    @JsonSetter("amount")
    public void setAmount(String amountHex) {
        this.amount = new Uint256(Numeric.toBigInt(amountHex));
    }

    @Override
    public String getType() {
        return WITHDRAW_TYPE;
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> base = super.eip712types();
        List<Pair<String, Type<?>>> result = new ArrayList<Pair<String, Type<?>>>(base.size() + 3);

        result.add(Pair.of("to", to));
        result.add(Pair.of("token", token));
        result.add(Pair.of("amount", amount));
        result.addAll(base);

        return result;
    }
    
}
