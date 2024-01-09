package io.zksync.transaction;

import static org.junit.jupiter.api.Assertions.*;

import io.zksync.protocol.core.Token;
import java.math.BigInteger;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

public class BaseTransactionTest {
  protected static final Token FEE_TOKEN = Token.createETH();
  protected static final Credentials SENDER = Credentials.create(ECKeyPair.create(BigInteger.ONE));
  protected static final Integer NONCE = 42;
}
