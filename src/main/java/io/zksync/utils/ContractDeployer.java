package io.zksync.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.utils.Assertions;
import org.web3j.utils.Numeric;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class ContractDeployer {

    private static final BigInteger MAX_BYTECODE_SIZE = BigInteger.valueOf(2).pow(16);
    public static final String CREATE2_PREFIX = Hash.sha3String("zksyncCreate2");

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

    public static byte[] hashBytecode(byte[] bytecode) {
        byte[] bytecodeHash = Hash.sha256(bytecode);

        BigInteger length = BigInteger.valueOf(bytecode.length / 32);
        if (length.compareTo(MAX_BYTECODE_SIZE) > 0) {
            throw new IllegalArgumentException("Bytecode length must be less than 2^16 bytes");
        }

        byte[] bytecodeLength = Numeric.toBytesPadded(length, 2);

        System.arraycopy(bytecodeLength, 0, bytecodeHash, 0, bytecodeLength.length);

        return bytecodeHash;
    }

    public static Function encodeCreate2(byte[] bytecode) {
        return encodeCreate2(bytecode, new byte[32]);
    }

    public static Function encodeCreate2(byte[] bytecode, byte[] salt) {
        Assertions.verifyPrecondition(salt.length == 32, "Salt length must be 32 bytes");
        byte[] bytecodeHash = hashBytecode(bytecode);

        return new Function(
            "create2",
                Arrays.asList(new Bytes32(salt), new Bytes32(bytecodeHash), new Uint256(0), DynamicBytes.DEFAULT),
                Collections.emptyList()
        );
    }

    public static Function encodeCreate(byte[] bytecode) {
        byte[] bytecodeHash = hashBytecode(bytecode);

        return new Function(
                "create",
                Arrays.asList(new Bytes32(new byte[] {}), new Bytes32(bytecodeHash), new Uint256(0), DynamicBytes.DEFAULT),
                Collections.emptyList()
        );
    }

}
