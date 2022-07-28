package io.zksync.crypto.signer;

import java.util.concurrent.CompletableFuture;

import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Structurable;

public interface EthSigner {

    static final String MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";
    static final String MESSAGE_EIP712_PREFIX = "\u0019\u0001";
    static final byte[] EIP1271_SUCCESS_VALUE = Numeric.hexStringToByteArray("0x1626ba7e");
    
    /**
     * Get wallet address
     * 
     * @return Address in hex string
     */
    String getAddress();

    /**
     * Get EIP712 domain
     * 
     * @return Eip712 domain
     */
    CompletableFuture<Eip712Domain> getDomain();

    /**
     * Signs typed struct using ethereum private key by EIP-712 signature standard.
     * 
     * @param <S> - EIP712 structure
     * @param domain - EIP712 domain
     * @param typedData - Object implementing EIP712 structure standard
     * @return Signature object
     */
    <S extends Structurable> CompletableFuture<String> signTypedData(Eip712Domain domain, S typedData);

    /**
     * Verify typed EIP-712 struct standard.
     * 
     * @param <S> - EIP712 structure
     * @param domain - EIP712 domain
     * @param typedData - Object implementing EIP712 structure standard
     * @param signature - Signature of the EIP-712 structures
     * @return true on verification success
     */
    <S extends Structurable> CompletableFuture<Boolean> verifyTypedData(Eip712Domain domain, S typedData, String signature);

    /**
     * Sign raw message
     * 
     * @param message - Message to sign
     * @return Signature object
     */
    CompletableFuture<String> signMessage(byte[] message);

    /**
     * Sign raw message
     * 
     * @param message - Message to sign
     * @param addPrefix - If true then add secure prefix (https://eips.ethereum.org/EIPS/eip-712)
     * @return Signature object
     */
    CompletableFuture<String> signMessage(byte[] message, boolean addPrefix);

    /**
     * Verify signature with raw message
     * 
     * @param signature - Signature object
     * @param message - Message to verify
     * @return true on verification success
     */
    CompletableFuture<Boolean> verifySignature(String signature, byte[] message);

    /**
     * Verify signature with raw message
     * 
     * @param signature - Signature object
     * @param message - Message to verify
     * @param prefixed - If true then add secure prefix (https://eips.ethereum.org/EIPS/eip-712)
     * @return true on verification success
     */
    CompletableFuture<Boolean> verifySignature(String signature, byte[] message, boolean prefixed);

    static byte[] getEthereumMessagePrefix(int messageLength) {
        return MESSAGE_PREFIX.concat(String.valueOf(messageLength)).getBytes();
    }

    static byte[] getEthereumMessageHash(byte[] message) {
        byte[] prefix = getEthereumMessagePrefix(message.length);

        byte[] result = new byte[prefix.length + message.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(message, 0, result, prefix.length, message.length);

        return Hash.sha3(result);
    }

}
