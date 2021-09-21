package io.zksync.protocol;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import io.zksync.methods.request.ZksEstimateFeeRequest;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.methods.response.ZksMainContract;

public interface ZkSync extends Web3j {
    static ZkSync build(Web3jService web3jService) {
        return new JsonRpc2_0ZkSync(web3jService);
    }

    Request<?, ZksEstimateFee> zksEstimateFee(ZksEstimateFeeRequest transaction);

    Request<?, ZksMainContract> zksMainContract();
    
    Request<?, EthGetBalance> ethGetBalance(
        String address, DefaultBlockParameter defaultBlockParameter, String tokenAddress);

}
