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
        {
            Pair<String, Type<?>> t2 = t.next();
            assertEquals("padding", t2.getKey());
            assertEquals(Uint256.DEFAULT, t2.getValue());
        }
    }

    @Test
    public void testEncodeToEIP712TypeString() {
        Execute execute = buildExecute();
        String result = Eip712Encoder.encodeType(execute.intoEip712Struct());

        assertEquals(
                "Execute(address contractAddress,uint256 calldataHash,address initiatorAddress,address feeToken,uint256 fee,uint32 nonce,uint64 validFrom,uint64 validUntil,uint256 padding)",
                result);
    }

    @Test
    public void testSerializeToEIP712EncodedValue() {
        Execute execute = buildExecute();
        byte[] encoded = Eip712Encoder.encodeValue(execute.intoEip712Struct()).getValue();

        assertEquals("0xdb2a6569d0fe824cae6f3a1380a8e927d4d9663d467996b5b0fa13f1d7c272d1",
                Numeric.toHexString(encoded));
    }

    @Test
    public void testSerializeToEIP712Message() {
        Execute execute = buildExecute();
        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
                execute);

        assertEquals("0x2cdc84d892bc3a843451c6b938b96d57fb9efeb949b21c4644f818ae6e4386b6",
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
