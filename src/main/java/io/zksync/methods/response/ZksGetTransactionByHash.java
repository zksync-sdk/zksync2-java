package io.zksync.methods.response;

import org.web3j.protocol.core.Response;

import java.util.Optional;

public class ZksGetTransactionByHash extends Response<ZkTransactionByHash> {

    public Optional<ZkTransactionByHash> getTransactionByHash() {
        return Optional.ofNullable(getResult());
    }

}