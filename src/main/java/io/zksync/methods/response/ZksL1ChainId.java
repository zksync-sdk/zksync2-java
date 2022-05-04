package io.zksync.methods.response;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class ZksL1ChainId extends Response<String> {
    public BigInteger getChainId() {
        return Numeric.decodeQuantity(getResult());
    }
}
