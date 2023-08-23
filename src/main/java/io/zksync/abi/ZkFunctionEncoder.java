package io.zksync.abi;

import org.apache.commons.lang3.ArrayUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import static org.web3j.abi.Utils.staticStructNestedPublicFieldsFlatList;

public class ZkFunctionEncoder extends FunctionEncoder {

    private static final int ABI_OFFSET_CALL_RETURN_DATA = 8;
    private static final int ABI_OFFSET_RETURN_DATA_SIZE = 1;
    private static final int ABI_OFFSET_CALLDATA_SIZE = 0;

    private static final int ABI_MEMORY_DATA_OFFSET = 1;
    private static final int ABI_MEMORY_HEADER_OFFSET = 0;
    private static final int ABI_OFFSET_ENTRY_HASH = 7;
    private static final int FIELD_SIZE = 32;
    
    @Override
    @SuppressWarnings("rawtypes")
    public String encodeFunction(final Function function) {
        final List<Type> parameters = function.getInputParameters();

        final String methodSignature = buildMethodSignature(function.getName(), parameters);
        final String methodId = buildMethodId(methodSignature);

        final StringBuilder result = new StringBuilder();
        result.append(methodId);

        return encodeParameters(parameters, result);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public String encodeParameters(final List<Type> parameters) {
        return encodeParameters(parameters, new StringBuilder());
    }

    @Override
    protected String encodeWithSelector(String methodId, List<Type> parameters) {
        final StringBuilder result = new StringBuilder(methodId);

        return encodeParameters(parameters, result);
    }

    @Override
    protected String encodePackedParameters(List<Type> parameters) {
        final StringBuilder result = new StringBuilder();
        for (Type parameter : parameters) {
            result.append(TypeEncoder.encodePacked(parameter));
        }
        return result.toString();
    }

    public static byte[] encodeConstructor(byte[] calldata) {
        int size = calldata.length;

        if (size % 32 != 0) {
            int offset = 32 - size % 32;
            int newLength = size + offset;
            calldata = Arrays.copyOf(calldata, newLength);
        }

        byte[] result = new byte[ABI_MEMORY_DATA_OFFSET * FIELD_SIZE + calldata.length];

        int calldataOffset = ABI_MEMORY_HEADER_OFFSET * FIELD_SIZE;
        byte[] calldataSize = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(size).array();

        System.arraycopy(calldataSize, 0, result, calldataOffset, 4);

        int constructorDataOffset = 8;
        result[constructorDataOffset] |= 0b00000001;

        for (int i = 0; i < calldata.length; i += FIELD_SIZE) {
            ArrayUtils.reverse(calldata, i, Math.min(calldata.length, i + FIELD_SIZE));
        }
        System.arraycopy(calldata, 0, result, FIELD_SIZE, calldata.length);

        return result;
    }

    public static byte[] encodeCalldata(Function function) {
        final byte[] calldata = Numeric.hexStringToByteArray(FunctionEncoder.encode(function));
        final int calldataSize = function.getInputParameters().size();
        final int returndataSize = function.getOutputParameters().size();
        final int size = (ABI_OFFSET_CALL_RETURN_DATA + calldataSize) * FIELD_SIZE;
        final int calldataSizeOffset = ABI_OFFSET_CALLDATA_SIZE * FIELD_SIZE;
        final ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(calldataSizeOffset, calldataSize);

        final int returndataSizeOffset = ABI_OFFSET_RETURN_DATA_SIZE * FIELD_SIZE;
        buffer.putInt(returndataSizeOffset, returndataSize);

        final int constructorCalllOffset = ABI_OFFSET_ENTRY_HASH * FIELD_SIZE;
        buffer.put(constructorCalllOffset, (byte) 0);

        final int entryHashOffset = (ABI_OFFSET_ENTRY_HASH + 1) * FIELD_SIZE - 4;
        final byte[] selector = ArrayUtils.subarray(calldata, 0, 4);
        ArrayUtils.reverse(selector);
        for (int i = 0; i < 4; i++) {
            buffer.put(entryHashOffset + i, selector[i]);
        }

        int calldataOffset = ABI_OFFSET_CALL_RETURN_DATA * FIELD_SIZE;
        for (Type<?> value : function.getInputParameters()) {
            final String result = TypeEncoder.encode(value);
            final byte[] encoded = Numeric.hexStringToByteArray(result);
            ArrayUtils.reverse(encoded);
            for (int i = 0; i < encoded.length; i++) {
                buffer.put(calldataOffset + i, encoded[i]);
            }
            calldataOffset += FIELD_SIZE;
        }

        return buffer.array();

    }

    @SuppressWarnings("rawtypes")
    private static String encodeParameters(
            final List<Type> parameters, final StringBuilder result) {

        int dynamicDataOffset = getLength(parameters) * Type.MAX_BYTE_LENGTH;
        final StringBuilder dynamicData = new StringBuilder();

        for (Type parameter : parameters) {
            final String encodedValue = ZkTypeEncoder.encode(parameter);

            if (isDynamic(parameter)) {
                final String encodedDataOffset =
                        TypeEncoder.encode(new Uint(BigInteger.valueOf(dynamicDataOffset)));
                result.append(encodedDataOffset);
                dynamicData.append(encodedValue);
                dynamicDataOffset += encodedValue.length() >> 1;
            } else {
                result.append(encodedValue);
            }
        }
        result.append(dynamicData);

        return result.toString();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static int getLength(final List<Type> parameters) {
        int count = 0;
        for (final Type type : parameters) {
            if (type instanceof StaticArray
                    && StaticStruct.class.isAssignableFrom(
                            ((StaticArray) type).getComponentType())) {
                count +=
                        staticStructNestedPublicFieldsFlatList(
                                                ((StaticArray) type).getComponentType())
                                        .size()
                                * ((StaticArray) type).getValue().size();
            } else if (type instanceof StaticArray
                    && DynamicStruct.class.isAssignableFrom(
                            ((StaticArray) type).getComponentType())) {
                count++;
            } else if(type instanceof StaticStruct) {
                count += getLength(((StaticStruct) type).getValue());
            } else if (type instanceof StaticArray) {
                count += ((StaticArray) type).getValue().size();
            } else {
                count++;
            }
        }
        return count;
    }

    @SuppressWarnings("rawtypes")
    static boolean isDynamic(Type parameter) {
        return parameter instanceof DynamicBytes
                || parameter instanceof Utf8String
                || parameter instanceof DynamicArray
                || (parameter instanceof StaticArray
                        && DynamicStruct.class.isAssignableFrom(
                                ((StaticArray) parameter).getComponentType()));
    }
}
