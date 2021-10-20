package io.zksync.protocol;

import java.util.Arrays;
import java.util.Collections;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendRawTransaction;

import io.zksync.methods.request.ZksEstimateFeeRequest;
import io.zksync.methods.response.ZksAccountType;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.methods.response.ZksIsTokenLiquid;
import io.zksync.methods.response.ZksMainContract;
import io.zksync.methods.response.ZksTokenPrice;
import io.zksync.methods.response.ZksTokens;
import io.zksync.methods.response.ZksTransactions;

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

    @Override
    public Request<?, EthSendRawTransaction> zksGetL1WithdrawalTx(String transactionHash) {
        return new Request<>(
                "zks_getL1WithdrawalTx", Arrays.asList(transactionHash), web3jService, EthSendRawTransaction.class);
    }

    @Override
    public Request<?, ZksAccountType> zksGetAccountType(String address) {
        return new Request<>(
                "zks_getAccountType", Arrays.asList(address), web3jService, ZksAccountType.class);
    }

    @Override
    public Request<?, ZksTransactions> zksGetAccountTransactions(String address, Integer before, Short limit) {
        return new Request<>(
                "zks_getAccountTransactions", Arrays.asList(address, before, limit), web3jService, ZksTransactions.class);
    }

    @Override
    public Request<?, ZksTokens> zksGetConfirmedTokens(Integer from, Short limit) {
        return new Request<>(
                "zks_getConfirmedTokens", Arrays.asList(from, limit), web3jService, ZksTokens.class);
    }

    @Override
    public Request<?, ZksIsTokenLiquid> zksIsTokenLiquid(String tokenAddress) {
        return new Request<>(
                "zks_isTokenLiquid", Arrays.asList(tokenAddress), web3jService, ZksIsTokenLiquid.class);
    }

    @Override
    public Request<?, ZksTokenPrice> zksGetTokenPrice(String tokenAddress) {
        return new Request<>(
                "zks_getTokenPrice", Arrays.asList(tokenAddress), web3jService, ZksTokenPrice.class);
    }
    
}
