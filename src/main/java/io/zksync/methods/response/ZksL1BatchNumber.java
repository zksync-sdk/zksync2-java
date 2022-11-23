package io.zksync.methods.response;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class ZksL1BatchNumber extends Response<String> {

    public BigInteger getL1BatchNumber() {
        return Numeric.decodeQuantity(getResult());
    }


}
