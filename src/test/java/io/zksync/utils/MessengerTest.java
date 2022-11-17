package io.zksync.utils;

import io.zksync.protocol.core.L2ToL1MessageProof;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class MessengerTest {

    @Test
    void getHashedMessage() {
        L2ToL1MessageProof proof = new L2ToL1MessageProof(
                Arrays.asList("0x59ac0de84b4fe20a122ef45ef667d1ebd0b7f1dbba6c3b0580d1446e98616578",
                        "0xc3d03eebfd83049991ea3d3e358b6712e7aa2e2e63dc2d4b438987cec28ac8d0",
                        "0xe3697c7f33c31a9b0f0aeb8542287d0d21e8c4cf82163d0c44c7a98aa11aa111",
                        "0x199cc5812543ddceeddd0fc82807646a4899444240db2c0d2f20c3cceb5f51fa",
                        "0xe4733f281f18ba3ea8775dd62d2fcd84011c8c938f16ea5790fd29a03bf8db89",
                        "0x1798a1fd9c8fbb818c98cff190daa7cc10b6e5ac9716b4a2649f7c2ebcef2272",
                        "0x66d7c5983afe44cf15ea8cf565b34c6c31ff0cb4dd744524f7842b942d08770d",
                        "0xb04e5ee349086985f74b73971ce9dfe76bbed95c84906c5dffd96504e1e5396c"),
                1,
                "0x35e7dfc84dc8d27ce2015f1243280f42a9e7bb00c465fa2156081dadc26403ed"
        );

        Address sender = new Address("0x7f0a50087b9426a9787c0ee458315ba73352ff74");
        byte[] message = "Some L2->L1 message".getBytes(StandardCharsets.UTF_8);
        BigInteger txNumber = BigInteger.ZERO;

        byte[] result = Messenger.getHashedMessage(sender, message, txNumber);

        int id = proof.getId();

        for (String e : proof.getProof()) {
            byte[] bytes = (id & 1) == 0 ?
                ArrayUtils.addAll(result, Numeric.hexStringToByteArray(e)) :
            ArrayUtils.addAll(Numeric.hexStringToByteArray(e), result);

            result = Hash.sha3(bytes);
            id /= 2;
        }

        Assertions.assertArrayEquals(Numeric.hexStringToByteArray(proof.getRoot()), result);
    }

    @Test
    void verifyMessage() {
        L2ToL1MessageProof proof = new L2ToL1MessageProof(
                Arrays.asList("0x59ac0de84b4fe20a122ef45ef667d1ebd0b7f1dbba6c3b0580d1446e98616578",
                        "0xc3d03eebfd83049991ea3d3e358b6712e7aa2e2e63dc2d4b438987cec28ac8d0",
                        "0xe3697c7f33c31a9b0f0aeb8542287d0d21e8c4cf82163d0c44c7a98aa11aa111",
                        "0x199cc5812543ddceeddd0fc82807646a4899444240db2c0d2f20c3cceb5f51fa",
                        "0xe4733f281f18ba3ea8775dd62d2fcd84011c8c938f16ea5790fd29a03bf8db89",
                        "0x1798a1fd9c8fbb818c98cff190daa7cc10b6e5ac9716b4a2649f7c2ebcef2272",
                        "0x66d7c5983afe44cf15ea8cf565b34c6c31ff0cb4dd744524f7842b942d08770d",
                        "0xb04e5ee349086985f74b73971ce9dfe76bbed95c84906c5dffd96504e1e5396c"),
                1,
                "0x35e7dfc84dc8d27ce2015f1243280f42a9e7bb00c465fa2156081dadc26403ed"
        );

        Address sender = new Address("0x7f0a50087b9426a9787c0ee458315ba73352ff74");
        byte[] message = "Some L2->L1 message".getBytes(StandardCharsets.UTF_8);
        BigInteger txNumber = BigInteger.ZERO;

        byte[] hashedMessage = Messenger.getHashedMessage(sender, message, txNumber);

        boolean result = Messenger.verifyMessage(proof, hashedMessage);

        Assertions.assertTrue(result);
    }
}