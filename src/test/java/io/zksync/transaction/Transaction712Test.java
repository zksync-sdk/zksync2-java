package io.zksync.transaction;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.helper.CounterContract;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.PaymasterParams;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkSyncNetwork;
import io.zksync.transaction.type.Transaction712;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Transaction712Test extends BaseTransactionTest {

    private static final Token FEE = BaseTransactionTest.FEE_TOKEN;
    private static final String SENDER = "0x1234512345123451234512345123451234512345";
    private static final String RECEIVER = "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC";

    @Test
    public void testEncodeToEIP712TypeString() {
        Transaction712 transactionRequest = buildTransaction();
        String result = Eip712Encoder.encodeType(transactionRequest.intoEip712Struct());

        assertEquals(
                "Transaction(uint8 txType,uint256 to,uint256 value,bytes data,uint256 ergsLimit,uint256 ergsPerPubdataByteLimit,uint256 ergsPrice,uint256 nonce)",
                result);
    }

    @Test
    public void testSerializeToEIP712EncodedValue() {
        Transaction712 transactionRequest = buildTransaction();
        byte[] encoded = Eip712Encoder.encodeValue(transactionRequest.intoEip712Struct()).getValue();

        assertEquals("0x8c1349f7824b3ccfd732378410f53504e02853633d851c900f11eea6a393b3d8",
                Numeric.toHexString(encoded));
    }

    @Test
    public void testSerializeToEIP712Message() {
        Transaction712 transactionRequest = buildTransaction();
        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
                transactionRequest);

        assertEquals("0x3ca537c84fa3d7af9312ce299e2e43f70c9a12f35132882487886e698dde126d",
                Numeric.toHexString(encoded));
    }

    private Transaction712 buildTransaction() {
        return new Transaction712(
                42,
                BigInteger.valueOf(NONCE),
                BigInteger.valueOf(54321),
                RECEIVER,
                BigInteger.ZERO,
                FunctionEncoder.encode(CounterContract.encodeIncrement(BigInteger.valueOf(42))),
                BigInteger.ZERO,
                BigInteger.ZERO,
                SENDER,
                new Eip712Meta(
                        BigInteger.ZERO,
                        null,
                        null,
                        new PaymasterParams()
                )
        );
    }
}
