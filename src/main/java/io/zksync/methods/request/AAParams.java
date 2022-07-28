package io.zksync.methods.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.zksync.utils.ByteArraySerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AAParams {
    private String from;
    private byte[] signature;

    public String getFrom() {
        return from;
    }

    @JsonSerialize(using = ByteArraySerializer.class)
    public byte[] getSignature() {
        return signature;
    }
}
