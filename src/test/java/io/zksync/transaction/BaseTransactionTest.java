package io.zksync.transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Iterator;

import io.zksync.transaction.fee.Fee;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import io.zksync.protocol.core.Token;

public class BaseTransactionTest {
    protected static final Token FEE_TOKEN = Token.createETH();
    protected static final Credentials SENDER = Credentials.create(ECKeyPair.create(BigInteger.ONE));
    protected static final Fee FEE;
    protected static final Integer NONCE = 42;

    static {
        FEE = new Fee(
                new Uint256(BigInteger.valueOf(123)),
                new Uint256(BigInteger.valueOf(123)),
                new Address(FEE_TOKEN.getAddress()),
                new Uint256(BigInteger.valueOf(123)),
                new Uint256(BigInteger.valueOf(123))
        );
    }

    public void assertSerializeToEIP712(Iterator<Pair<String, Type<?>>> base) {
        {
            Pair<String, Type<?>> t2 = base.next();
            assertEquals("initiatorAddress", t2.getKey());
            assertEquals(new Address(SENDER.getAddress()), t2.getValue());
        }
        {
            Pair<String, Type<?>> t2 = base.next();
            assertEquals("feeToken", t2.getKey());
            assertEquals(new Address(FEE_TOKEN.getAddress()), t2.getValue());
        }
        {
            Pair<String, Type<?>> t2 = base.next();
            assertEquals("ergsLimit", t2.getKey());
            assertEquals(FEE.getErgsLimit(), t2.getValue());
        }
        {
            Pair<String, Type<?>> t2 = base.next();
            assertEquals("ergsPriceLimit", t2.getKey());
            assertEquals(FEE.getErgsPriceLimit(), t2.getValue());
        }
        {
            Pair<String, Type<?>> t2 = base.next();
            assertEquals("ergsPerPubdataLimit", t2.getKey());
            assertEquals(FEE.getErgsPerPubdataLimit(), t2.getValue());
        }
        {
            Pair<String, Type<?>> t2 = base.next();
            assertEquals("ergsPerStorageLimit", t2.getKey());
            assertEquals(FEE.getErgsPerStorageLimit(), t2.getValue());
        }
        {
            Pair<String, Type<?>> t2 = base.next();
            assertEquals("nonce", t2.getKey());
            assertEquals(new Uint32(NONCE), t2.getValue());
        }
    }
}
