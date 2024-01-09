package io.zksync.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.PaymasterParams;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkSyncNetwork;
import io.zksync.transaction.type.Transaction712;
import java.math.BigInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.web3j.utils.Numeric;

public class Transaction712Test extends BaseTransactionTest {

  private static final Token FEE = BaseTransactionTest.FEE_TOKEN;
  private static final String SENDER = "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf";
  private static final String RECEIVER = "0x000102030405060708090a0b0c0d0e0f10111213";
  private static final BigInteger VALUE = new BigInteger(Numeric.hexStringToByteArray("0x1"));
  private static final BigInteger GAS_PRICE =
      new BigInteger(Numeric.hexStringToByteArray("0xee6b280"));
  private static final BigInteger GAS_LIMIT =
      new BigInteger(Numeric.hexStringToByteArray("0x1ece8d"));
  private static final BigInteger MAX_PRIORITY_FEE_PER_GAS =
      new BigInteger(Numeric.hexStringToByteArray("0x0"));

  @Test
  public void testEncodeToEIP712TypeString() {
    Transaction712 transactionRequest = buildTransaction();
    String result = Eip712Encoder.encodeType(transactionRequest.intoEip712Struct());

    assertEquals(
        "Transaction(uint256 txType,uint256 from,uint256 to,uint256 gasLimit,uint256 gasPerPubdataByteLimit,uint256 maxFeePerGas,uint256 maxPriorityFeePerGas,uint256 paymaster,uint256 nonce,uint256 value,bytes data,bytes32[] factoryDeps,bytes paymasterInput)",
        result);
  }

  @Test
  @Disabled
  public void testSerializeToEIP712EncodedValue() {
    Transaction712 transactionRequest = buildTransaction();
    byte[] encoded = Eip712Encoder.encodeValue(transactionRequest.intoEip712Struct()).getValue();

    assertEquals(
        "0x2360af215549f2e44413f5a6eb25ecf40590c231e24a70b23a942f995814dc77",
        Numeric.toHexString(encoded));
  }

  @Test
  @Disabled
  public void testSerializeToEIP712Message() {
    Transaction712 transactionRequest = buildTransaction();
    byte[] encoded =
        Eip712Encoder.typedDataToSignedBytes(
            Eip712Domain.defaultDomain(ZkSyncNetwork.Localhost), transactionRequest);

    assertEquals(
        "0x2506074540188226a81a8dc006ab311c06b680232d39699d348e8ec83c81388b",
        Numeric.toHexString(encoded));
  }

  private Transaction712 buildTransaction() {
    return new Transaction712(
        280,
        GAS_LIMIT,
        new BigInteger(Numeric.hexStringToByteArray("0x1ece8d")),
        RECEIVER,
        VALUE,
        "0x",
        MAX_PRIORITY_FEE_PER_GAS,
        GAS_PRICE,
        SENDER,
        new Eip712Meta(
            new BigInteger(Numeric.hexStringToByteArray("0x1bb1")),
            null,
            null,
            new PaymasterParams()));
  }
}
