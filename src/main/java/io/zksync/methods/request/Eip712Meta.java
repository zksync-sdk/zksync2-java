package io.zksync.methods.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private BigInteger ergsPerPubdata;
    private BigInteger ergsPerStorage;
    private byte[][] factoryDeps;
    private AAParams aaParams;

    private PaymasterParams paymasterParams;

    public String getFeeToken() {
        return feeToken;
    }

    public String getErgsPerPubdata() {
        return convert(ergsPerPubdata);
    }

    @JsonIgnore
    public BigInteger getErgsPerPubdataNumber() {
        return ergsPerPubdata;
    }

    public String getErgsPerStorage() {
        return convert(ergsPerStorage);
    }

    @JsonIgnore
    public BigInteger getErgsPerStorageNumber() {
        return ergsPerStorage;
    }

    @JsonSerialize(using = io.zksync.utils.ByteArray2Serializer.class)
    public byte[][] getFactoryDeps() {
        return factoryDeps;
    }

    public AAParams getAaParams() {
        return aaParams;
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
