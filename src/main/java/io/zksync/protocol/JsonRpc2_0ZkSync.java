package io.zksync.protocol;

import java.util.Arrays;
import java.util.Collections;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import io.zksync.methods.request.ZksEstimateFeeRequest;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.methods.response.ZksMainContract;

public class JsonRpc2_0ZkSync extends JsonRpc2_0Web3j implements ZkSync {

    public JsonRpc2_0ZkSync(Web3jService web3jService) {
        super(web3jService);
    }

    @Override
    public Request<?, ZksEstimateFee> zksEstimateFee(ZksEstimateFeeRequest transaction) {
        return new Request<>(
                "zks_estimateFee", Arrays.asList(transaction), web3jService, ZksEstimateFee.class);
    }

    @Override
    public Request<?, ZksMainContract> zksMainContract() {
        return new Request<>("zks_getMainContract", Collections.emptyList(), web3jService, ZksMainContract.class);
    }

    @Override
    public Request<?, EthGetBalance> ethGetBalance(String address, DefaultBlockParameter defaultBlockParameter,
            String tokenAddress) {
        return new Request<>(
            "eth_getBalance",
            Arrays.asList(address, defaultBlockParameter.getValue(), tokenAddress),
            web3jService,
            EthGetBalance.class);
    }
    
}
