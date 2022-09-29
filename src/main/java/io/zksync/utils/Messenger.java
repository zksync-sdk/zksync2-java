package io.zksync.utils;

import io.zksync.protocol.core.L2ToL1MessageProof;
import org.apache.commons.lang3.ArrayUtils;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class Messenger {

    public static byte[] getHashedMessage(Address sender, byte[] message, BigInteger txNumberInBlock) {
        byte[] messageHash = Hash.sha3(message);
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write(0);
            output.write(1);
            output.write(Numeric.toBytesPadded(txNumberInBlock, 2));
            output.write(Numeric.hexStringToByteArray(ZkSyncAddresses.MESSENGER_ADDRESS));
            output.write(Numeric.toBytesPadded(sender.toUint().getValue(), 32));
            output.write(messageHash);

            return Hash.sha3(output.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyMessage(L2ToL1MessageProof proof, byte[] messageHash) {
        int id = proof.getId();

        byte[] result = messageHash;
        for (String e : proof.getProof()) {
            byte[] bytes = (id & 1) == 0 ?
                    ArrayUtils.addAll(result, Numeric.hexStringToByteArray(e)) :
                    ArrayUtils.addAll(Numeric.hexStringToByteArray(e), result);

            result = Hash.sha3(bytes);
            id /= 2;
        }

        return Arrays.equals(result, Numeric.hexStringToByteArray(proof.getRoot()));
    }

}
