package io.zksync.transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.TimeRange;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeployContract extends Transaction {

    private AccountType accountType;
    private byte[] bytecode;
    private byte[] calldata;

    public DeployContract(AccountType accountType, byte[] bytecode, byte[] calldata, Address initiatorAddress, Address feeToken, Uint256 fee, Uint32 nonce, TimeRange validIn) {
        super(initiatorAddress, feeToken, fee, nonce, validIn);

        this.accountType = accountType;
        this.bytecode = bytecode;
        this.calldata = calldata;
    }

    public DeployContract(AccountType accountType, byte[] bytecode, Address initiatorAddress, Address feeToken, Uint256 fee, Uint32 nonce, TimeRange validIn) {
        this(accountType, bytecode, new byte[256], initiatorAddress, feeToken, fee, nonce, validIn);

        this.calldata[224] = 1;
    }

    public DeployContract(AccountType accountType, String bytecode, String calldata, String initiatorAddress, String feeToken, BigInteger fee, Integer nonce, TimeRange validIn) {
        this(
            accountType,
            Numeric.hexStringToByteArray(bytecode),
            Numeric.hexStringToByteArray(calldata),
            new Address(initiatorAddress),
            new Address(feeToken),
            new Uint256(fee),
            new Uint32(nonce),
            validIn
        );
    }

    public DeployContract(AccountType accountType, String bytecode, String initiatorAddress, String feeToken, BigInteger fee, Integer nonce, TimeRange validIn) {
        this(
            accountType,
            Numeric.hexStringToByteArray(bytecode),
            new Address(initiatorAddress),
            new Address(feeToken),
            new Uint256(fee),
            new Uint32(nonce),
            validIn
        );
    }

    @Override
    public String getType() {
        return "DeployContract";
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> base = super.eip712types();
        List<Pair<String, Type<?>>> result = new ArrayList<Pair<String, Type<?>>>(base.size() + 3);

        result.add(Pair.of("accountType", accountType.getType()));
        result.add(Pair.of("bytecodeHash", new Uint256(Numeric.toBigInt(Hash.sha3(bytecode)))));
        result.add(Pair.of("calldataHash", new Uint256(Numeric.toBigInt(Hash.sha3(calldata)))));
        result.addAll(base);
        result.add(Pair.of("padding", Uint256.DEFAULT));

        return result;
    }
    
}
