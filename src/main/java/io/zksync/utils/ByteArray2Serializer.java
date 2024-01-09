package io.zksync.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class ByteArray2Serializer extends JsonSerializer<byte[][]> {

  @Override
  public void serialize(byte[][] bytes, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeStartArray();

    for (byte[] inner : bytes) {
      generator.writeStartArray();
      for (byte b : inner) {
        generator.writeNumber(unsigned(b));
      }
      generator.writeEndArray();
    }

    generator.writeEndArray();
  }

  private static int unsigned(byte b) {
    return b & 0xFF;
  }
}
