package io.zksync.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Hash;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.utils.Assertions;
import org.web3j.utils.Numeric;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ContractDeployer {

    private static final BigInteger MAX_BYTECODE_SIZE = BigInteger.valueOf(2).pow(16);
    public static final String CREATE_PREFIX = Hash.sha3String("zksyncCreate");
    public static final String CREATE2_PREFIX = Hash.sha3String("zksyncCreate2");

    /**
     * Compute contract address according <a href="https://eips.ethereum.org/EIPS/eip-1014">EIP-1014</a>
     *
     * @param sender Address of a source of a transaction
     * @param bytecode Compiled bytecode of the contract
     * @param constructor Encoded constructor parameters
     * @param salt 32 bytes salt
     * @return Computed address of a contract
     */
    public static Address computeL2Create2Address(Address sender, byte[] bytecode, byte[] constructor, byte[] salt) {
        Assertions.verifyPrecondition(salt.length == 32, "Salt length must be 32 bytes");
        byte[] senderBytes = Numeric.toBytesPadded(sender.toUint().getValue(), 32);
        byte[] bytecodeHash = hashBytecode(bytecode);
        byte[] constructorHash = Hash.sha3(constructor);

        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write(Numeric.hexStringToByteArray(CREATE2_PREFIX));
            output.write(senderBytes);
            output.write(salt);
            output.write(bytecodeHash);
            output.write(constructorHash);

            byte[] result = Hash.sha3(output.toByteArray());

            return new Address(Numeric.toBigInt(ArrayUtils.subarray(result, 12, result.length)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Compute contract address
     *
     * @param sender Address of a source of a transaction
     * @param nonce Deployment nonce (see {@link io.zksync.wrappers.NonceHolder})
     * @return Computed address of a contract
     */
    public static Address computeL2CreateAddress(Address sender, BigInteger nonce) {
        byte[] senderBytes = Numeric.toBytesPadded(sender.toUint().getValue(), 32);
        byte[] nonceBytes = Numeric.toBytesPadded(nonce, 32);

        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write(Numeric.hexStringToByteArray(CREATE_PREFIX));
            output.write(senderBytes);
            output.write(nonceBytes);

            byte[] result = Hash.sha3(output.toByteArray());

            return new Address(Numeric.toBigInt(ArrayUtils.subarray(result, 12, result.length)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract correct deployed contract address from transaction receipt
     *
     * @param receipt Transaction receipt
     * @return Address of the deployed contract
     */
    public static Address extractContractAddress(TransactionReceipt receipt) {
        Event contractDeployed = new Event("ContractDeployed", Arrays.asList(new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}));

        EventValues deployedEvent = receipt.getLogs().stream()
                .map(log -> Contract.staticExtractEventParameters(contractDeployed, log))
                .filter(Objects::nonNull)
                .reduce((ignore, last) -> last).orElseThrow(() -> new IllegalArgumentException("Receipt does not have any `ContractDeployed` event"));

        return (Address) deployedEvent.getIndexedValues().get(2);
    }

    /**
     * Generates SHA-256 digest for the given bytecode.
     *
     * @param bytecode Compiled bytecode of the contract
     * @return The hash value for the given input
     */
    public static byte[] hashBytecode(byte[] bytecode) {
        byte[] bytecodeHash = Hash.sha256(bytecode);

        if (bytecode.length % 32 != 0) {
            throw new IllegalArgumentException("The bytecode length in bytes must be divisible by 32");
        }

        BigInteger length = BigInteger.valueOf(bytecode.length / 32);
        if (length.compareTo(MAX_BYTECODE_SIZE) > 0) {
            throw new IllegalArgumentException("Bytecode length must be less than 2^16 bytes");
        }

        byte[] codeHashVersion = new byte[] { 1, 0 };
        byte[] bytecodeLength = Numeric.toBytesPadded(length, 2);

        System.arraycopy(codeHashVersion, 0, bytecodeHash, 0, codeHashVersion.length);
        System.arraycopy(bytecodeLength, 0, bytecodeHash, 2, bytecodeLength.length);

        return bytecodeHash;
    }

    /**
     * Encode `create2` deployment function of default factory contract
     *
     * @param bytecode Compiled bytecode of the contract
     * @return Encoded contract function
     */
    public static Function encodeCreate2(byte[] bytecode) {
        return encodeCreate2(bytecode, new byte[] {}, new byte[32]);
    }

    /**
     * Encode `create2` deployment function of default factory contract
     *
     * @param bytecode Compiled bytecode of the contract
     * @param calldata Encoded constructor parameters
     * @return Encoded contract function
     */
    public static Function encodeCreate2(byte[] bytecode, byte[] calldata) {
        return encodeCreate2(bytecode, calldata, new byte[32]);
    }

    /**
     * Encode `create2` deployment function of default factory contract
     *
     * @param bytecode Compiled bytecode of the contract
     * @param calldata Encoded constructor parameters
     * @param salt 32 bytes salt
     * @return Encoded contract function
     */
    public static Function encodeCreate2(byte[] bytecode, byte[] calldata, byte[] salt) {
        Assertions.verifyPrecondition(salt.length == 32, "Salt length must be 32 bytes");
        byte[] bytecodeHash = hashBytecode(bytecode);

        return new Function(
            "create2",
                Arrays.asList(new Bytes32(salt), new Bytes32(bytecodeHash), new DynamicBytes(calldata)),
                Collections.emptyList()
        );
    }

    /**
     * Encode `create` deployment function of default factory contract
     *
     * @param bytecode Compiled bytecode of the contract
     * @return Encoded contract function
     */
    public static Function encodeCreate(byte[] bytecode) {
        return encodeCreate(bytecode, new byte[] {});
    }

    /**
     * Encode `create` deployment function of default factory contract
     *
     * @param bytecode Compiled bytecode of the contract
     * @param calldata Encoded constructor parameters
     * @return Encoded contract function
     */
    public static Function encodeCreate(byte[] bytecode, byte[] calldata) {
        byte[] bytecodeHash = hashBytecode(bytecode);

        return new Function(
                "create",
                Arrays.asList(new Bytes32(new byte[32]), new Bytes32(bytecodeHash), new DynamicBytes(calldata)),
                Collections.emptyList()
        );
    }

    /**
     * Encode `create2` deployment custom account function of default factory contract (see <a href="https://eips.ethereum.org/EIPS/eip-4337">EIP-4337</a>)
     *
     * @param bytecode Compiled bytecode of the Custom Account contract
     * @return Encoded contract function
     */
    public static Function encodeCreate2Account(byte[] bytecode) {
        return encodeCreate2Account(bytecode, new byte[] {}, new byte[32], AccountAbstractionVersion.Version1);
    }

    /**
     * Encode `create2` deployment custom account function of default factory contract (see <a href="https://eips.ethereum.org/EIPS/eip-4337">EIP-4337</a>)
     *
     * @param bytecode Compiled bytecode of the Custom Account contract
     * @param calldata Encoded constructor parameters
     * @return Encoded contract function
     */
    public static Function encodeCreate2Account(byte[] bytecode, byte[] calldata) {
        return encodeCreate2Account(bytecode, calldata, new byte[32], AccountAbstractionVersion.Version1);
    }

    /**
     * Encode `create2` deployment custom account function of default factory contract (see <a href="https://eips.ethereum.org/EIPS/eip-4337">EIP-4337</a>)
     *
     * @param bytecode Compiled bytecode of the Custom Account contract
     * @param calldata Encoded constructor parameters
     * @param salt 32 bytes salt
     * @return Encoded contract function
     */
    public static Function encodeCreate2Account(byte[] bytecode, byte[] calldata, byte[] salt, AccountAbstractionVersion accountAbstractionVersion) {
        Assertions.verifyPrecondition(salt.length == 32, "Salt length must be 32 bytes");
        byte[] bytecodeHash = hashBytecode(bytecode);
        return new Function(
                "create2Account",
                Arrays.asList(new Bytes32(salt), new Bytes32(bytecodeHash), new DynamicBytes(calldata), new Uint8(accountAbstractionVersion.getRawValue())),
                Collections.emptyList()
        );
    }

    /**
     * Encode `create` deployment custom account function of default factory contract (see <a href="https://eips.ethereum.org/EIPS/eip-4337">EIP-4337</a>)
     *
     * @param bytecode Compiled bytecode of the Custom Account contract
     * @return Encoded contract function
     */
    public static Function encodeCreateAccount(byte[] bytecode) {
        return encodeCreateAccount(bytecode, new byte[] {});
    }

    /**
     * Encode `create` deployment custom account function of default factory contract (see <a href="https://eips.ethereum.org/EIPS/eip-4337">EIP-4337</a>)
     *
     * @param bytecode Compiled bytecode of the Custom Account contract
     * @param calldata Encoded constructor parameters
     * @return Encoded contract function
     */
    public static Function encodeCreateAccount(byte[] bytecode, byte[] calldata) {
        byte[] bytecodeHash = hashBytecode(bytecode);

        return new Function(
                "createAccount",
                Arrays.asList(new Bytes32(new byte[32]), new Bytes32(bytecodeHash), new DynamicBytes(calldata)),
                Collections.emptyList()
        );
    }

}
