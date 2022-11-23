package io.zksync.methods.response;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Assertions;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class ZksL1BatchBlockRange extends Response<List<String>> {

    public BigInteger getL1MinBlockNumber() {
        List<String> result = getResult();
        Assertions.verifyPrecondition(result.size() == 2, "");

        return Numeric.decodeQuantity(result.get(0));
    }

    public BigInteger getL1MaxBlockNumber() {
        List<String> result = getResult();
        Assertions.verifyPrecondition(result.size() == 2, "");

        return Numeric.decodeQuantity(result.get(1));
    }

}
