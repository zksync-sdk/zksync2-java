package io.zksync.crypto.eip712;

import io.zksync.crypto.signer.EthSigner;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

public class Eip712Encoder {
  public static Bytes32 encodeValue(Type<?> value) {
    if (value instanceof Utf8String) {
      Utf8String s = (Utf8String) value;
      return new Bytes32(Hash.sha3(s.getValue().getBytes()));
    } else if (value instanceof Bytes32) {
      return (Bytes32) value;
    } else if (value instanceof NumericType) {
      NumericType nt = (NumericType) value;
      return new Bytes32(Numeric.toBytesPadded(nt.getValue(), 32));
    } else if (value instanceof DynamicArray) {
      List<Type<?>> members = ((DynamicArray<Type<?>>) value).getValue();
      ByteBuffer bytes = ByteBuffer.allocate(members.size() * 32);
      members.stream()
          .map(Eip712Encoder::encodeValue)
          .forEach(bytes32 -> bytes.put(bytes32.getValue()));
      return new Bytes32(Hash.sha3(bytes.array()));
    } else if (value instanceof BytesType) {
      BytesType bt = (BytesType) value;
      byte[] bytes = Hash.sha3(bt.getValue());
      return new Bytes32(bytes);
    } else if (value instanceof Address) {
      Address address = (Address) value;
      byte[] bytes = Numeric.hexStringToByteArray(address.getValue());
      byte[] result = new byte[32];
      System.arraycopy(bytes, 0, result, 12, 20);
      return new Bytes32(result);
    } else if (value instanceof Eip712Struct) {
      Eip712Struct struct = (Eip712Struct) value;
      byte[] typeHash = typeHash(struct);
      List<Pair<String, Type<?>>> members = struct.getValue().eip712types();
      ByteBuffer bytes = ByteBuffer.allocate((members.size() + 1) * 32);
      bytes.put(typeHash);
      for (Pair<String, Type<?>> member : members) {
        Bytes32 result = encodeValue(member.getValue());
        bytes.put(result.getValue());
      }
      return new Bytes32(Hash.sha3(bytes.array()));
    } else {
      throw new IllegalArgumentException(
          String.format("Unsupported ethereum type: \"%s\"", value.getTypeAsString()));
    }
  }

  public static String encodeType(Eip712Struct structure) {
    StringBuilder sb = new StringBuilder(structure.encodeType());
    dependencies(structure).forEach(value -> sb.append(value.encodeType()));

    return sb.toString();
  }

  public static byte[] typeHash(Eip712Struct structure) {
    return Hash.sha3(encodeType(structure).getBytes());
  }

  public static Set<Eip712Struct> dependencies(Eip712Struct structure) {
    final TreeSet<Eip712Struct> result = new TreeSet<>();
    for (Pair<String, Type<?>> value : structure.getValue().eip712types()) {
      if (value.getValue() instanceof Eip712Struct) {
        result.add((Eip712Struct) value.getValue());
      }
    }

    return result;
  }

  public static <S extends Structurable> byte[] typedDataToSignedBytes(
      Eip712Domain domain, S typedData) {
    final ByteArrayOutputStream output = new ByteArrayOutputStream();

    try {
      output.write(EthSigner.MESSAGE_EIP712_PREFIX.getBytes());
      output.write(Eip712Encoder.encodeValue(domain.intoEip712Struct()).getValue());
      output.write(Eip712Encoder.encodeValue(typedData.intoEip712Struct()).getValue());
    } catch (IOException e) {
      throw new IllegalStateException("Error when creating ETH signature", e);
    }

    return Hash.sha3(output.toByteArray());
  }
}
