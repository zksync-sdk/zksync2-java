package io.zksync.transaction;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.helper.CounterContract;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkSyncNetwork;
import io.zksync.transaction.type.Transaction712;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionRequestTest extends BaseTransactionTest {

    private static final Token FEE = BaseTransactionTest.FEE_TOKEN;
    private static final String RECEIVER = "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC";

    @Test
    public void testEncodeToEIP712TypeString() {
        TransactionRequest transactionRequest = buildTransactionRequest();
        String result = Eip712Encoder.encodeType(transactionRequest.intoEip712Struct());

        assertEquals(
                "TransactionRequest(uint8 txType,address to,uint256 value,bytes data,address feeToken,uint256 ergsLimit,uint256 ergsPerPubdataByteLimit,uint256 ergsPrice,uint256 nonce)",
                result);
    }

    @Test
    public void testSerializeToEIP712EncodedValue() {
        TransactionRequest transactionRequest = buildTransactionRequest();
        byte[] encoded = Eip712Encoder.encodeValue(transactionRequest.intoEip712Struct()).getValue();

        assertEquals("0x1dd5e801453f3dd457e604f944b2077adce238857e751b2ca14f31c28f3f53c5",
                Numeric.toHexString(encoded));
    }

    @Test
    public void testSerializeToEIP712Message() {
        TransactionRequest transactionRequest = buildTransactionRequest();
        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
                transactionRequest);

        assertEquals("0xb777de7a05b9d5caf010c980b071d6d3ea69e87fdbfee9e6170314601cc054d1",
                Numeric.toHexString(encoded));
    }

    private TransactionRequest buildTransactionRequest() {
        return TransactionRequest.from(buildTransaction());
    }

    private Transaction712 buildTransaction() {
        return new Transaction712(
                BigInteger.valueOf(NONCE),
                BigInteger.ZERO,
                BigInteger.valueOf(54321),
                RECEIVER,
                BigInteger.ZERO,
                FunctionEncoder.encode(CounterContract.encodeIncrement(BigInteger.valueOf(42))),
                42,
                new Eip712Meta(
                        FEE.getL2Address(),
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        null,
                        null,
                        null
                )
        );
    }
}
