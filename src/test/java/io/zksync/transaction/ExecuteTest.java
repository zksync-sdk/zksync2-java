package io.zksync.transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.helper.CounterContract;
import io.zksync.protocol.core.ZkSyncNetwork;

public class ExecuteTest extends BaseTransactionTest {

    private static final Function FUNC = CounterContract.encodeIncrement(BigInteger.valueOf(123123));
    private static final String CONTRACT_ADDRESS = "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC";

    @Test
    public void testSerializeToEIP712() {
        Execute execute = buildExecute();
        List<Pair<String, Type<?>>> types = execute.eip712types();
        Iterator<Pair<String, Type<?>>> t = types.iterator();

        {
            Pair<String, Type<?>> t2 = t.next();
            assertEquals("contractAddress", t2.getKey());
            assertEquals(new Address(CONTRACT_ADDRESS), t2.getValue());
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
        Execute execute = buildExecute();
        String result = Eip712Encoder.encodeType(execute.intoEip712Struct());

        assertEquals(
                "Execute(address contractAddress,uint256 calldataHash,address initiatorAddress,address feeToken,uint256 ergsLimit,uint256 ergsPriceLimit,uint256 ergsPerPubdataLimit,uint256 ergsPerStorageLimit,uint32 nonce)",
                result);
    }

    @Test
    public void testSerializeToEIP712EncodedValue() {
        Execute execute = buildExecute();
        byte[] encoded = Eip712Encoder.encodeValue(execute.intoEip712Struct()).getValue();

        assertEquals("0xb1cda52df83b03210d19ccc7792d1998d6879c70734e40fa0a8e0288d7d28789",
                Numeric.toHexString(encoded));
    }

    @Test
    public void testSerializeToEIP712Message() {
        Execute execute = buildExecute();
        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
                execute);

        assertEquals("0x54a536300508ab44aeeddd75a13cfc641cb07d84acd2564438a48e03a905be75",
                Numeric.toHexString(encoded));
    }

    private Execute buildExecute() {
        return new Execute(
                CONTRACT_ADDRESS,
                FunctionEncoder.encode(FUNC),
                SENDER.getAddress(),
                FEE,
                BigInteger.valueOf(NONCE));
    }
}
