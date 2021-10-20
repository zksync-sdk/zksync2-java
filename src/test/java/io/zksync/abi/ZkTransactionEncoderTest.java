package io.zksync.abi;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import io.zksync.protocol.core.TimeRange;
import io.zksync.protocol.core.domain.token.Token;
import io.zksync.transaction.Transfer;

public class ZkTransactionEncoderTest {

    private static final Token ETH = Token.createETH();

    Credentials credentials;

    @Before
    public void setUp() {
        this.credentials = Credentials.create(ECKeyPair.create(BigInteger.ONE));
    }

    @Test
    public void testEncodeTransfer() {
        Transfer zkTransfer = new Transfer(
            ETH.getAddress(),
            this.credentials.getAddress(),
            Convert.toWei("1", Unit.ETHER).toBigInteger(),
            this.credentials.getAddress(),
            ETH.getAddress(),
            BigInteger.ZERO,
            0,
            new TimeRange()
        );

        // final String result = Numeric.toHexString()
    }
}
