package io.zksync.crypto.signer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.eip712.Eip712Encoder;
import io.zksync.crypto.eip712.Structurable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PrivateKeyEthSigner implements EthSigner {

    private Credentials credentials;
    private Long chainId;

    public static PrivateKeyEthSigner fromMnemonic(String mnemonic, long chainId) {
        Credentials credentials = generateCredentialsFromMnemonic(mnemonic, 0);
        return new PrivateKeyEthSigner(credentials, chainId);
    }

    public static PrivateKeyEthSigner fromMnemonic(String mnemonic, int accountIndex, long chainId) {
        Credentials credentials = generateCredentialsFromMnemonic(mnemonic, accountIndex);
        return new PrivateKeyEthSigner(credentials, chainId);
    }

    @Override
    public String getAddress() {
        return credentials.getAddress();
    }

    @Override
    public CompletableFuture<Eip712Domain> getDomain() {
        Eip712Domain domain = Eip712Domain.defaultDomain(chainId);
        return CompletableFuture.completedFuture(domain);
    }

    @Override
    public <S extends Structurable> CompletableFuture<String> signTypedData(Eip712Domain domain, S typedData) {
        return this.signMessage(Eip712Encoder.typedDataToSignedBytes(domain, typedData), false);
    }

    @Override
    public <S extends Structurable> CompletableFuture<Boolean> verifyTypedData(Eip712Domain domain, S typedData,
            String signature) {
        return this.verifySignature(signature, Eip712Encoder.typedDataToSignedBytes(domain, typedData), false);
    }

    @Override
    public CompletableFuture<String> signMessage(byte[] message) {
        return this.signMessage(message, true);
    }

    @Override
    public CompletableFuture<String> signMessage(byte[] message, boolean addPrefix) {
        Sign.SignatureData sig = addPrefix ? Sign.signPrefixedMessage(message, credentials.getEcKeyPair())
                : Sign.signMessage(message, credentials.getEcKeyPair(), false);

        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            output.write(sig.getR());
            output.write(sig.getS());
            output.write(sig.getV());
        } catch (IOException e) {
            throw new IllegalStateException("Error when creating ETH signature", e);
        }

        final String signature = Numeric.toHexString(output.toByteArray());

        return CompletableFuture.completedFuture(signature);
    }

    @Override
    public CompletableFuture<Boolean> verifySignature(String signature, byte[] message) {
        return this.verifySignature(signature, message, true);
    }

    @Override
    public CompletableFuture<Boolean> verifySignature(String signature, byte[] message, boolean prefixed) {
        byte[] messageHash = prefixed ? EthSigner.getEthereumMessageHash(message) : message;

        String address = ecrecover(Numeric.hexStringToByteArray(signature), messageHash);

        return CompletableFuture.completedFuture(address.equalsIgnoreCase(this.getAddress()));
    }

    private static String ecrecover(byte[] signature, byte[] hash) {
        ECDSASignature sig = new ECDSASignature(
                Numeric.toBigInt(Arrays.copyOfRange(signature, 0, 32)),
                Numeric.toBigInt(Arrays.copyOfRange(signature, 32, 64)));

        byte v = signature[64];

        int recId;
        if (v >= 3) {
            recId = v - 27;
        } else {
            recId = v;
        }

        BigInteger recovered = Sign.recoverFromSignature(recId, sig, hash);
        return "0x" + Keys.getAddress(recovered);
    }

    private static Credentials generateCredentialsFromMnemonic(String mnemonic, int accountIndex) {
        // m/44'/60'/0'/0 derivation path
        int[] derivationPath = { 44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT,
                0 | Bip32ECKeyPair.HARDENED_BIT, 0, accountIndex };

        // Generate a BIP32 master keypair from the mnemonic phrase
        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(
                MnemonicUtils.generateSeed(mnemonic, ""));

        // Derive the keypair using the derivation path
        Bip32ECKeyPair derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath);

        // Load the wallet for the derived keypair
        return Credentials.create(derivedKeyPair);
    }

}
