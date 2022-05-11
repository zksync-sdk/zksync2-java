package io.zksync.transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.helper.CounterContract;
import io.zksync.protocol.core.ZkSyncNetwork;

public class DeployContractTest extends BaseTransactionTest {

    private static final byte[] BYTECODE = Numeric.hexStringToByteArray(CounterContract.BINARY);
    private static final Function FUNC = CounterContract.encodeIncrement(BigInteger.valueOf(123123));

    @Test
    public void testSerializeToEIP712() {
        DeployContract deployContract = buildDeployContract();
        List<Pair<String, Type<?>>> types = deployContract.eip712types();
        Iterator<Pair<String, Type<?>>> t = types.iterator();

        {
            Pair<String, Type<?>> t2 = t.next();
            assertEquals("bytecodeHash", t2.getKey());
            assertEquals(new Uint256(Numeric.toBigInt(Hash.sha3(BYTECODE))), t2.getValue());
        }
        {
            Pair<String, Type<?>> t2 = t.next();
            assertEquals("calldataHash", t2.getKey());
            assertEquals(new Uint256(Numeric.toBigInt(Hash.sha3(FunctionEncoder.encode(FUNC)))), t2.getValue());
        }
        super.assertSerializeToEIP712(t);
    }

    @Test
    public void testEncodeToEIP712TypeString() {
        DeployContract deployContract = buildDeployContract();
        String result = Eip712Encoder.encodeType(deployContract.intoEip712Struct());

        assertEquals(
                "DeployContract(uint256 bytecodeHash,uint256 calldataHash,address initiatorAddress,address feeToken,uint256 ergsLimit,uint256 ergsPriceLimit,uint256 ergsPerPubdataLimit,uint256 ergsPerStorageLimit,uint32 nonce)",
                result);
    }

    @Test
    public void testSerializeToEIP712EncodedValue() {
        DeployContract deployContract = buildDeployContract();
        byte[] encoded = Eip712Encoder.encodeValue(deployContract.intoEip712Struct()).getValue();

        assertEquals("0x3f8401016a22a03fc94a264aac3994bd55fe43ba79973349f1aa597671745a25",
                Numeric.toHexString(encoded));
    }

    @Test
    public void testSerializeToEIP712Message() {
        DeployContract deployContract = buildDeployContract();
        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
                deployContract);

        assertEquals("0xd3cd685f73dae929805486fc733a339835f5d06cb10dea6301d1d1108db6e5c4",
                Numeric.toHexString(encoded));
    }

    private DeployContract buildDeployContract() {
        return new DeployContract(
                Numeric.toHexString(BYTECODE),
                FunctionEncoder.encode(FUNC),
                SENDER.getAddress(),
                FEE,
                BigInteger.valueOf(NONCE));
    }
}
