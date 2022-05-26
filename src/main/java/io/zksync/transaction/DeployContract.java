package io.zksync.transaction;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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
public class DeployContract extends Transaction {

    public static final String DEPLOY_CONTRACT_TYPE = "DeployContract";

    private byte[] mainContractHash;
    private byte[] calldata;
    private byte[][] factoryDeps;

    public DeployContract(byte[] bytecode, byte[] calldata, Address initiatorAddress, Fee fee, Uint32 nonce) {
        super(initiatorAddress, fee, nonce);

        this.mainContractHash = Hash.sha3(bytecode);
        this.factoryDeps = new byte[][] { bytecode };
        if (calldata != null) {
            this.calldata = calldata;
        } else {
            this.calldata = new byte[32];
            this.calldata[8] = 1;
        }
    }

    public DeployContract(byte[] bytecode, Address initiatorAddress, Fee fee, Uint32 nonce) {
        this(bytecode, null, initiatorAddress, fee, nonce);
    }

    public DeployContract(byte[] bytecode, byte[] calldata, String initiatorAddress, Fee fee, BigInteger nonce) {
        this(bytecode, calldata, new Address(initiatorAddress), fee, new Uint32(nonce));
    }

    public DeployContract(String bytecode, String calldata, String initiatorAddress, Fee fee, BigInteger nonce) {
        this(
            Numeric.hexStringToByteArray(bytecode),
            Numeric.hexStringToByteArray(calldata),
            new Address(initiatorAddress),
            fee,
            new Uint32(nonce)
        );
    }

    public DeployContract(String bytecode, String initiatorAddress, Fee fee, BigInteger nonce) {
        this(
            Numeric.hexStringToByteArray(bytecode),
            new Address(initiatorAddress),
            fee,
            new Uint32(nonce)
        );
    }

    @Override
    public String getType() {
        return DEPLOY_CONTRACT_TYPE;
    }

    public byte[] getInput() {
        ByteBuffer input = ByteBuffer.allocate(mainContractHash.length + calldata.length);
        input.put(mainContractHash);
        input.put(calldata);
        return input.array();
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> base = super.eip712types();
        List<Pair<String, Type<?>>> result = new ArrayList<Pair<String, Type<?>>>(base.size() + 3);

        result.add(Pair.of("bytecodeHash", new Uint256(Numeric.toBigInt(mainContractHash))));
        result.add(Pair.of("calldataHash", new Uint256(Numeric.toBigInt(Hash.sha3(calldata)))));
        result.addAll(base);

        return result;
    }
    
}
