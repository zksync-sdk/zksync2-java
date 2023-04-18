package io.zksync.crypto.signer;

import static org.junit.jupiter.api.Assertions.*;

import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.PaymasterParams;
import io.zksync.transaction.type.Transaction712;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import io.zksync.crypto.eip712.Eip712Domain;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class Transaction712SignTest {

    private static Credentials credentials;
    private static PrivateKeyEthSigner key;
    private static Eip712Domain domain;
    private static Transaction712 message;

    @BeforeAll
    public static void setUp() {
        final String privateKey = "0x0000000000000000000000000000000000000000000000000000000000000001";

        credentials = Credentials.create(privateKey);
        key = new PrivateKeyEthSigner(credentials, 280L);

        domain = Eip712Domain.defaultDomain(280L);

        message = buildTransferTransaction();
    }

    @Test
    public void testSignTypedTransferData() {
        final String signature = key.signTypedData(domain, message).join();

        assertEquals("0x4a9ff9c2a31be0ef25954a4906865953edab960ed7f509a00c86db9f70df01a904f73dbac053f119f6d90878f95c5da1f666e1dc987355fa666e82d50a56cdce1b", signature);
    }

    @Test
    public void testSignTypedExecuteData() {
        message = buildExecuteTransaction();
        final String signature = key.signTypedData(domain, message).join();
        assertEquals("0x07c9e373dba52a79d4c5d539aa73ad6f3dcd362702788fa5177f045af6f63ef638f04c295e9469249cb960146d3d9d101a13a5d1e6795f439a3cf6f222200a761b", signature);
    }


    private static Transaction712 buildTransferTransaction() {
        return new Transaction712(
                280,
                BigInteger.valueOf(0),
                Numeric.toBigInt("0x30a108"),
                "0x000102030405060708090a0b0c0d0e0f10111213",
                Numeric.toBigInt("0x1"),
                "",
                BigInteger.ZERO,
                Numeric.toBigInt("0xee6b280"),
                "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
                new Eip712Meta(
                        Numeric.toBigInt("0x3d0e"),
                        null,
                        null,
                        new PaymasterParams()
                )
        );
    }


    private static Transaction712 buildExecuteTransaction() {
        return new Transaction712(
                280,
                Numeric.toBigInt("0x0"),
                Numeric.toBigInt("0x3dbb95"),
                "0x0000000000000000000000000000000000000000",
                Numeric.toBigInt("0x0"),
                "0xa9059cbb000000000000000000000000b06b15bc1818971cbfd1b181c345a5db1ecda00d00000000000000000000000000000000000000000000000000000000312c8040",
                BigInteger.ZERO,
                Numeric.toBigInt("0xee6b280"),
                "0x7e5f4552091a69125d5dfcb7b8c2659029395bdf",
                new Eip712Meta(
                        Numeric.toBigInt("0x4e20"),
                        null,
                        null,
                        new PaymasterParams()
                )
        );
    }
}