package io.zksync.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ByteArraySerializer extends JsonSerializer<byte[]> {

    @Override
    public void serialize(byte[] bytes, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartArray();

        for (byte b : bytes) {
            generator.writeNumber(unsigned(b));
        }

        generator.writeEndArray();

    }

    private static int unsigned(byte b) {
        return b & 0xFF;
    }

}