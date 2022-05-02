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

    @Test
    public void testSerializeToEIP712() {
        TransactionRequest transactionRequest = buildTransactionRequest();
        byte[] data = Eip712Encoder.encodeValue(transactionRequest.intoEip712Struct()).getValue();
    }

    //8b73c3c69bb8fe3d512ecc4cf759cc79239f7b179b0ffacaa9a75d522b39400f19b453ce45aaaaf3a300f5a9ec95869b4f28ab10430b572ee218c3a6a5e07d6fad7c5bef027816a800da1736444fb58a807ef4c9603b7848673f7e3a68eb14a5000000000000000000000000000000000000000000000000000000000000010e0000000000000000000000000000000000000000000000000000000000000000
    //8b73c3c69bb8fe3d512ecc4cf759cc79239f7b179b0ffacaa9a75d522b39400f19b453ce45aaaaf3a300f5a9ec95869b4f28ab10430b572ee218c3a6a5e07d6fad7c5bef027816a800da1736444fb58a807ef4c9603b7848673f7e3a68eb14a5000000000000000000000000000000000000000000000000000000000000010e0000000000000000000000000000000000000000000000000000000000000000

    // 85282a348d768575ed6f7147283a37d044357f1284eecb6c23ac3e4b0946a249 // Type hash      85282a348d768575ed6f7147283a37d044357f1284eecb6c23ac3e4b0946a249
    // 000000000000000000000000eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee // To             000000000000000000000000eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
    // 0000000000000000000000000000000000000000000000000000000000000000 // Nonce          0000000000000000000000000000000000000000000000000000000000000000
    // 0000000000000000000000000000000000000000000000000000000000000000 // Value          0000000000000000000000000000000000000000000000000000000000000000
    // e828b75183666777252cc46b0b9f23a794906079ac241174241eed15bf35cb47 // Data           e828b75183666777252cc46b0b9f23a794906079ac241174241eed15bf35cb47
    // 0000000000000000000000000000000000000000000000000000000000006f9c // GasPrice
    // 0000000000000000000000000000000000000000000000000000000000002940 // GasLimit
    // 0000000000000000000000000000000000000000000000000000000000000000 // ErgsPerStorage
    // 0000000000000000000000000000000000000000000000000000000000000000 // ErgsPerPubdata
    // 000000000000000000000000eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee // Fee token
    // 0000000000000000000000000000000000000000000000000000000000000000 // Withdraw token
    // 0000000000000000000000000000000000000000000000000000000000006f9c000000000000000000000000000000000000000000000000000000000000294000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee0000000000000000000000000000000000000000000000000000000000000000

    @Test
    public void testEncodeToEIP712TypeString() {
        TransactionRequest transactionRequest = buildTransactionRequest();
        String result = Eip712Encoder.encodeType(transactionRequest.intoEip712Struct());

        assertEquals(
                "TransactionRequest(address to,uint256 nonce,uint256 value,bytes data,uint256 gasPrice,uint256 gasLimit,uint256 ergsPerStorage,uint256 ergsPerPubdata,address feeToken,address withdrawToken)",
                result);
    }

    @Test
    public void testSerializeToEIP712EncodedValue() {
        Withdraw withdraw = buildWithdraw();
        byte[] encoded = Eip712Encoder.encodeValue(withdraw.intoEip712Struct()).getValue();

        assertEquals("0x3a99876e01820f4d66c90ed96be2dc0c1f44b0d7d490e2af4a7ab498c2bcb1ad",
                Numeric.toHexString(encoded));
    }

    @Test
    public void testSerializeToEIP712Message() {
        Withdraw withdraw = buildWithdraw();
        byte[] encoded = Eip712Encoder.typedDataToSignedBytes(Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost),
                withdraw);

        assertEquals("0xb7735ab5994e9baec8aed00895c54574a513ad7d2b96965cee0ad66ff0e405f0",
                Numeric.toHexString(encoded));
    }

    private TransactionRequest buildTransactionRequest() {
        return new TransactionRequest(buildWithdraw());
    }

    private Withdraw buildWithdraw() {
        return new Withdraw(
                WITHDRAW_TOKEN.getAddress(),
                RECEIVER,
                Convert.toWei("1", Unit.ETHER).toBigInteger(),
                SENDER.getAddress(),
                FEE,
                BigInteger.valueOf(NONCE));
    }
}
