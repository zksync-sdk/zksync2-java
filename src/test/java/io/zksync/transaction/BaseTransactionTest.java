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
    protected static final Integer NONCE = 42;
}
