package io.zksync.crypto.signer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.junit.Before;

import io.zksync.protocol.core.ZkSyncNetwork;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.crypto.eip712.Eip712Domain;

import io.zksync.helper.eip712.Mail;

public class PrivateKeyEthSignerTest {
    
    private Credentials credentials;
    private PrivateKeyEthSigner key;
    private Eip712Domain domain;
    private Mail message;

    @Before
    public void setUp() {
        final String privateKey = Hash.sha3String("cow");

        this.credentials = Credentials.create(privateKey);
        this.key = new PrivateKeyEthSigner(credentials, ZkSyncNetwork.Mainnnet);

        Eip712Domain domain = new Eip712Domain(
            "Ether Mail",
            "1",
            ZkSyncNetwork.Mainnnet,
            "0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC"
        );

        this.domain = domain;

        this.message = new Mail();
    }

    @Test
    public void testSignTypedData() {
        final String signature = this.key.signTypedData(this.domain, this.message).join();

        assertEquals("0x4355c47d63924e8a72e509b65029052eb6c299d53a04e167c5775fd466751c9d07299936d304c153f6443dfa05f40ff007d72911b6f72307f996231605b915621c", signature);
    }

    @Test
    public void testVerifySignedTypedData() {
        final String signature = "0x4355c47d63924e8a72e509b65029052eb6c299d53a04e167c5775fd466751c9d07299936d304c153f6443dfa05f40ff007d72911b6f72307f996231605b915621c";

        boolean verified = this.key.verifyTypedData(this.domain, this.message, signature).join();

        assertTrue(verified);
    }

}
