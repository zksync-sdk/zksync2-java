package io.zksync.methods.request;

import org.web3j.crypto.RawTransaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ZksEstimateFeeRequest {
    
    private String data;

    public static ZksEstimateFeeRequest fromRawTransaction(RawTransaction transaction) {
        return new ZksEstimateFeeRequest("0x" + transaction.getData());
    }

}
