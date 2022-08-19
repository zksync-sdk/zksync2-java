package io.zksync.methods.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.zksync.utils.ByteArraySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Builder
public class Eip712Meta {
    private BigInteger ergsPerPubdata;
    private byte[] customSignature;
    private byte[][] factoryDeps;

    private PaymasterParams paymasterParams;

    public String getErgsPerPubdata() {
        return convert(ergsPerPubdata);
    }

    @JsonIgnore
    public BigInteger getErgsPerPubdataNumber() {
        return ergsPerPubdata;
    }

    @JsonSerialize(using = ByteArraySerializer.class)
    public byte[] getCustomSignature() {
        return customSignature;
    }

    @JsonSerialize(using = io.zksync.utils.ByteArray2Serializer.class)
    public byte[][] getFactoryDeps() {
        return factoryDeps;
    }

    public PaymasterParams getPaymasterParams() {
        return paymasterParams;
    }

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null; // we don't want the field to be encoded if not present
        }
    }

}
