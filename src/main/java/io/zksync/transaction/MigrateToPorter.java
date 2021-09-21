package io.zksync.transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;

import io.zksync.protocol.core.TimeRange;
import lombok.Setter;

@Setter
public class MigrateToPorter extends Transaction {

    private Address accountAddress;

    public MigrateToPorter(Address accountAddress, Address initiatorAddress, Address feeToken, Uint256 fee, Uint32 nonce, TimeRange validIn) {
        super(initiatorAddress, feeToken, fee, nonce, validIn);

        this.accountAddress = accountAddress;
    }

    public MigrateToPorter(String accountAddress, String initiatorAddress, String feeToken, BigInteger fee, Integer nonce, TimeRange validIn) {
        this(
            new Address(accountAddress),
            new Address(initiatorAddress),
            new Address(feeToken),
            new Uint256(fee),
            new Uint32(nonce),
            validIn
        );
    }

    @JsonIgnore
    public Address getAccountAddress() {
        return accountAddress;
    }

    @JsonGetter("accountAddress")
    public String getAccountAddressString() {
        return accountAddress.getValue();
    }
    
    @Override
    public String getType() {
        return "MigrateToPorter";
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> base = super.eip712types();
        List<Pair<String, Type<?>>> result = new ArrayList<Pair<String, Type<?>>>(base.size() + 3);

        result.add(Pair.of("accountAddress", accountAddress));
        result.addAll(base);

        return result;
    }
    
}
