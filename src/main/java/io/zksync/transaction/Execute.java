package io.zksync.transaction;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigInteger;
import java.util.ArrayList;

import io.zksync.transaction.fee.Fee;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Execute extends Transaction {

    public static final String EXECUTE_TYPE = "Execute";

    @JsonIgnore
    private Address contractAddress;
    private byte[] calldata;

    public Execute(Address contractAddress, byte[] calldata, Address initiatorAddress, Fee fee, Uint32 nonce) {
        super(initiatorAddress, fee, nonce);

        this.contractAddress = contractAddress;
        this.calldata = calldata;
    }

    public Execute(String contractAddress, String calldata, String initiatorAddress, Fee fee, BigInteger nonce) {
        this(
            new Address(contractAddress),
            Numeric.hexStringToByteArray(calldata),
            new Address(initiatorAddress),
            fee,
            new Uint32(nonce)
        );
    }

    @JsonGetter("contractAddress")
    public String getContractAddressString() {
        return contractAddress.getValue();
    }

    @JsonSetter("contractAddress")
    public void setContractAddress(String contractAddress) {
        this.contractAddress = new Address(contractAddress);
    }

    @Override
    public String getType() {
        return EXECUTE_TYPE;
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> base = super.eip712types();
        List<Pair<String, Type<?>>> result = new ArrayList<>(base.size() + 3);

        result.add(Pair.of("contractAddress", contractAddress));
        result.add(Pair.of("calldataHash", new Uint256(Numeric.toBigInt(Hash.sha3(calldata)))));
        result.addAll(base);

        return result;
    }
    
}
