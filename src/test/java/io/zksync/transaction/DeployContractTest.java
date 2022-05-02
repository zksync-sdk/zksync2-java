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

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.helper.CounterContract;
import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.ZkSyncNetwork;

public class DeployContractTest extends BaseTransactionTest {

    private static final byte[] BYTECODE = CounterContract.getCode();
    private static final Function FUNC = CounterContract.encodeIncrement(BigInteger.valueOf(123123));

    @Test
    public void testSerializeToEIP712() {
        DeployContract deployContract = buildDeployContract();
        List<Pair<String, Type<?>>> types = deployContract.eip712types();
        Iterator<Pair<String, Type<?>>> t = types.iterator();

        {
            Pair<String, Type<?>> t2 = t.next();
            assertEquals("accountType", t2.getKey());
            assertEquals(AccountType.ZkRollup.getType(), t2.getValue());
        }
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
        {
            Pair<String, Type<?>> t2 = t.next();
            assertEquals("padding", t2.getKey());
            assertEquals(Uint256.DEFAULT, t2.getValue());
        }
    }

    @Test
    public void testEncodeToEIP712TypeString() {
        DeployContract deployContract = buildDeployContract();
        String result = Eip712Encoder.encodeType(deployContract.intoEip712Struct());

        assertEquals(
                "DeployContract(uint8 accountType,uint256 bytecodeHash,uint256 calldataHash,address initiatorAddress,address feeToken,uint256 fee,uint32 nonce,uint64 validFrom,uint64 validUntil,uint256 padding)",
                result);
    }

    @Test
    public void testSerializeToEIP712EncodedValue() {
        DeployContract deployContract = buildDeployContract();
        byte[] encoded = Eip712Encoder.encodeValue(deployContract.intoEip712Struct()).getValue();

        assertEquals("0x313228dd2323576aea6cc8cd056558ebca835cc1843433106a76ec75cbda6cc5",
                Numeric.toHexString(encoded));
    }

    @Test
    public void testSerializeToEIP712Message() {
        DeployContract deployContract = buildDeployContract();
        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
                deployContract);

        assertEquals("0xe7b596fde369e5a2e71a1b021b895dac3481b4b68eb4038a7553c6c41b5aa203",
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
