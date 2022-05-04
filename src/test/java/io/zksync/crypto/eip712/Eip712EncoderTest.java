package io.zksync.crypto.eip712;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.Before;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint128;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Numeric;

import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkSyncNetwork;
import io.zksync.helper.eip712.Mail;

public class Eip712EncoderTest {

    private Eip712Domain domain;
    private Mail message;

    @Before
    public void setUp() {
        this.message = new Mail();

        this.domain = new Eip712Domain(
            "Ether Mail",
            "1",
            ZkSyncNetwork.Mainnet,
            "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"
        );
    }

    @Test
    public void testEncodeType() {
        final String result = Eip712Encoder.encodeType(this.message.intoEip712Struct());

        assertEquals("Mail(Person from,Person to,string contents)Person(string name,address wallet)", result);
    }

    @Test
    public void testHashEncodedType() {
        final byte[] hash = Eip712Encoder.typeHash(this.message.intoEip712Struct());

        assertEquals("0xa0cedeb2dc280ba39b857546d74f5549c3a1d7bdc2dd96bf881f76108e23dac2", Numeric.toHexString(hash));
    }

    @Test
    public void testEncodeContentsValue() {
        final byte[] hash = Eip712Encoder.encodeValue(this.message.contents).getValue();

        assertEquals("0xb5aadf3154a261abdd9086fc627b61efca26ae5702701d05cd2305f7c52a2fc8", Numeric.toHexString(hash));
    }

    @Test
    public void testEncodePersonData() {
        final byte[] fromHash = Eip712Encoder.encodeValue(this.message.from.intoEip712Struct()).getValue();
        final byte[] toHash = Eip712Encoder.encodeValue(this.message.to.intoEip712Struct()).getValue();

        assertEquals("0xfc71e5fa27ff56c350aa531bc129ebdf613b772b6604664f5d8dbe21b85eb0c8", Numeric.toHexString(fromHash));
        assertEquals("0xcd54f074a4af31b4411ff6a60c9719dbd559c221c8ac3492d9d872b041d703d1", Numeric.toHexString(toHash));
    }

    @Test
    public void testEncodeMailData() {
        final byte[] data = Eip712Encoder.encodeValue(this.message.intoEip712Struct()).getValue();

        assertEquals("0xc52c0ee5d84264471806290a3f2c4cecfc5490626bf912d01f240d7a274b371e", Numeric.toHexString(data));
    }

    @Test
    public void testEncodeDomainMemberValues() {
        {
            final byte[] data = Eip712Encoder.encodeValue(this.domain.getName()).getValue();

            assertEquals("0xc70ef06638535b4881fafcac8287e210e3769ff1a8e91f1b95d6246e61e4d3c6", Numeric.toHexString(data));
        }
        {
            final byte[] data = Eip712Encoder.encodeValue(this.domain.getVersion()).getValue();

            assertEquals("0xc89efdaa54c0f20c7adf612882df0950f5a951637e0307cdcb4c672f298b8bc6", Numeric.toHexString(data));
        }
        {
            final byte[] data = Eip712Encoder.encodeValue(this.domain.getChainId()).getValue();

            assertEquals("0x0000000000000000000000000000000000000000000000000000000000000001", Numeric.toHexString(data));
        }
        {
            final byte[] data = Eip712Encoder.encodeValue(this.domain.getVerifyingContract()).getValue();

            assertEquals("0x000000000000000000000000cccccccccccccccccccccccccccccccccccccccc", Numeric.toHexString(data));
        }
    }

    @Test
    public void testEncodeDomainData() {
        final byte[] data = Eip712Encoder.encodeValue(this.domain.intoEip712Struct()).getValue();

        assertEquals("0xf2cee375fa42b42143804025fc449deafd50cc031ca257e0b194a650a912090f", Numeric.toHexString(data));
    }

    @Test
    public void testTypedDataToSignedBytes() {
        final byte[] data = Eip712Encoder.typedDataToSignedBytes(this.domain, this.message);

        assertEquals("0xbe609aee343fb3c4b28e1df9e632fca64fcfaede20f02e86244efddf30957bd2", Numeric.toHexString(data));
    }

    @Test
    public void testEncodeTypes() {
        {
            Address address = new Address("0xe1fab3efd74a77c23b426c302d96372140ff7d0c");

            byte[] result = Eip712Encoder.encodeValue(address).getValue();

            assertEquals("0x000000000000000000000000e1fab3efd74a77c23b426c302d96372140ff7d0c", Numeric.toHexString(result));
        }

        {
            Uint128 number = new Uint128(123);

            byte[] result = Eip712Encoder.encodeValue(number).getValue();

            assertEquals("0x000000000000000000000000000000000000000000000000000000000000007b", Numeric.toHexString(result));
        }
    }

//    @Test
//    public void testEncodeTransfer() {
//        Transfer zkTransfer = new Transfer(
//            ETH.getAddress(),
//            this.credentials.getAddress(),
//            Convert.toWei("1", Unit.ETHER).toBigInteger(),
//            this.credentials.getAddress(),
//            ETH.getAddress(),
//            BigInteger.ZERO,
//            1,
//            new TimeRange()
//        );
//
//        byte[] result = Eip712Encoder.encodeValue(zkTransfer.intoEip712Struct()).getValue();
//
//        assertEquals("0x997cbce617f1c5aca3616ddc2122db218d726e220fa040451d893430d7382ce5", Numeric.toHexString(result));
//    }
}
