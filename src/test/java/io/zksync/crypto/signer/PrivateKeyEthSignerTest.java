package io.zksync.crypto.signer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.tx.ChainIdLong;

import io.zksync.protocol.core.ZkSyncNetwork;
import io.zksync.crypto.eip712.Eip712Domain;

import io.zksync.helper.eip712.Mail;

public class PrivateKeyEthSignerTest {
    
    private static Credentials credentials;
    private static PrivateKeyEthSigner key;
    private static Eip712Domain domain;
    private static Mail message;

    @BeforeAll
    public static void setUp() {
        final String privateKey = Hash.sha3String("cow");

        credentials = Credentials.create(privateKey);
        key = new PrivateKeyEthSigner(credentials, ChainIdLong.MAINNET);

        domain = new Eip712Domain(
            "Ether Mail",
            "1",
            ZkSyncNetwork.Mainnet,
            "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"
        );

        message = new Mail();
    }

    @Test
    public void testSignTypedData() {
        final String signature = key.signTypedData(domain, message).join();

        assertEquals("0x4355c47d63924e8a72e509b65029052eb6c299d53a04e167c5775fd466751c9d07299936d304c153f6443dfa05f40ff007d72911b6f72307f996231605b915621c", signature);
    }

    @Test
    public void testVerifySignedTypedData() {
        final String signature = "0x4355c47d63924e8a72e509b65029052eb6c299d53a04e167c5775fd466751c9d07299936d304c153f6443dfa05f40ff007d72911b6f72307f996231605b915621c";

        boolean verified = key.verifyTypedData(domain, message, signature).join();

        assertTrue(verified);
    }

}
