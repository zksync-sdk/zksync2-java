package io.zksync.methods.response;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;

public class ZksAccountBalances extends Response<Map<String, String>> {
    public Map<String, BigInteger> getBalances() {
        return getResult().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> Numeric.decodeQuantity(entry.getValue())));
    }
}
