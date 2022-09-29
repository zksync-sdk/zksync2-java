package io.zksync.methods.response;

import org.web3j.protocol.core.Response;

import java.util.Optional;

public class ZksGetTransactionReceipt extends Response<ZkTransactionReceipt> {

    public Optional<ZkTransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(getResult());
    }

}
