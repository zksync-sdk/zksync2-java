package io.zksync.transaction;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkSyncNetwork;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionRequestTest extends BaseTransactionTest {

    private static final Token WITHDRAW_TOKEN = BaseTransactionTest.FEE_TOKEN;
    private static final String RECEIVER = "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC";

//    @Test
//    public void testEncodeToEIP712TypeString() {
//        TransactionRequest transactionRequest = buildTransactionRequest();
//        String result = Eip712Encoder.encodeType(transactionRequest.intoEip712Struct());
//
//        assertEquals(
//                "TransactionRequest(address to,uint256 nonce,uint256 value,bytes data,uint256 gasPrice,uint256 gasLimit,uint256 ergsPerStorage,uint256 ergsPerPubdata,address feeToken,address withdrawToken)",
//                result);
//    }
//
//    @Test
//    public void testSerializeToEIP712EncodedValue() {
//        TransactionRequest transactionRequest = buildTransactionRequest();
//        byte[] encoded = Eip712Encoder.encodeValue(transactionRequest.intoEip712Struct()).getValue();
//
//        assertEquals("0xaa6ad134be006ef5c644699e8bed3cc8ba1e7e3959ac16bca67fe966d1933d3b",
//                Numeric.toHexString(encoded));
//    }
//
//    @Test
//    public void testSerializeToEIP712Message() {
//        TransactionRequest transactionRequest = buildTransactionRequest();
//        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
//                transactionRequest);
//
//        assertEquals("0x920215621a903192bf987305c42025dc6a4376ba01a97dd93dc327758fa407aa",
//                Numeric.toHexString(encoded));
//    }
//
//    private TransactionRequest buildTransactionRequest() {
//        return new TransactionRequest(buildWithdraw());
//    }
//
//    private Withdraw buildWithdraw() {
//        return new Withdraw(
//                WITHDRAW_TOKEN.getL2Address(),
//                RECEIVER,
//                Convert.toWei("1", Unit.ETHER).toBigInteger(),
//                SENDER.getAddress(),
//                FEE,
//                BigInteger.valueOf(NONCE));
//    }
}
