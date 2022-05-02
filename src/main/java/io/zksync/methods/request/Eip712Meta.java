package io.zksync.methods.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private String feeToken;
    private BigInteger ergsPerStorage;
    private BigInteger ergsPerPubdata;
    private String withdrawToken;
    private byte[][] factoryDeps;

    public String getFeeToken() {
        return feeToken;
    }

    public String getErgsPerStorage() {
        return convert(ergsPerStorage);
    }

    public String getErgsPerPubdata() {
        return convert(ergsPerPubdata);
    }

    public String getWithdrawToken() {
        return withdrawToken;
    }

    @JsonSerialize(using = io.zksync.utils.ByteArray2Serializer.class)
    public byte[][] getFactoryDeps() {
        return factoryDeps;
    }

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null; // we don't want the field to be encoded if not present
        }
    }

    private int unsigned(byte value) {
        return value & 0xFF;
    }
}
