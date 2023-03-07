package io.zksync.protocol;

import java.util.Arrays;
import java.util.Collections;

import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.*;
import org.jetbrains.annotations.Nullable;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthEstimateGas;

public class JsonRpc2_0ZkSync extends JsonRpc2_0Web3j implements ZkSync {

    public static final int DEFAULT_BLOCK_COMMIT_TIME = 800;

    public JsonRpc2_0ZkSync(Web3jService web3jService) {
        super(web3jService);
    }

    @Override
    public Request<?, ZksEstimateFee> zksEstimateFee(Transaction transaction) {
        return new Request<>(
                "zks_estimateFee", Collections.singletonList(transaction), web3jService, ZksEstimateFee.class);
    }

    @Override
    public Request<?, ZksMainContract> zksMainContract() {
        return new Request<>("zks_getMainContract", Collections.emptyList(), web3jService, ZksMainContract.class);
    }

    @Override
    public Request<?, ZksTokens> zksGetConfirmedTokens(Integer from, Short limit) {
        return new Request<>(
                "zks_getConfirmedTokens", Arrays.asList(from, limit), web3jService, ZksTokens.class);
    }

    @Override
    public Request<?, ZksTokenPrice> zksGetTokenPrice(String tokenAddress) {
        return new Request<>(
                "zks_getTokenPrice", Collections.singletonList(tokenAddress), web3jService, ZksTokenPrice.class);
    }

    @Override
    public Request<?, ZksL1ChainId> zksL1ChainId() {
        return new Request<>("zks_L1ChainId", Collections.emptyList(), web3jService, ZksL1ChainId.class);
    }

    @Override
    public Request<?, ZksContractDebugInfo> zksGetContractDebugInfo(String contractAddress) {
        return new Request<>(
                "zks_getContractDebugInfo", Collections.singletonList(contractAddress), web3jService, ZksContractDebugInfo.class);
    }

    @Override
    public Request<?, ZksTransactionTrace> zksGetTransactionTrace(String transactionHash) {
        return new Request<>(
                "zks_getTransactionTrace", Collections.singletonList(transactionHash), web3jService, ZksTransactionTrace.class);
    }

    @Override
    public Request<?, ZksAccountBalances> zksGetAllAccountBalances(String address) {
        return new Request<>("zks_getAllAccountBalances", Collections.singletonList(address), web3jService, ZksAccountBalances.class);
    }

    @Override
    public Request<?, ZksBridgeAddresses> zksGetBridgeContracts() {
        return new Request<>("zks_getBridgeContracts", Collections.emptyList(), web3jService, ZksBridgeAddresses.class);
    }

    @Override
    public Request<?, ZksMessageProof> zksGetL2ToL1MsgProof(Integer block, String sender, String message, @Nullable Long l2LogPosition) {
        return new Request<>("zks_getL2ToL1MsgProof", Arrays.asList(block, sender, message), web3jService, ZksMessageProof.class);
    }

    @Override
    public Request<?, EthEstimateGas> ethEstimateGas(Transaction transaction) {
        return new Request<>(
                "eth_estimateGas", Collections.singletonList(transaction), web3jService, EthEstimateGas.class);
    }

    @Override
    public Request<?, ZksTestnetPaymasterAddress> zksGetTestnetPaymaster() {
        return new Request<>("zks_getTestnetPaymaster", Collections.emptyList(), web3jService, ZksTestnetPaymasterAddress.class);
    }

    @Override
    public Request<?, ZksGetTransactionReceipt> zksGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "eth_getTransactionReceipt", Collections.singletonList(transactionHash), web3jService, ZksGetTransactionReceipt.class);
    }

    @Override
    public Request<?, ZksGetTransactionDetails> zksGetTransactionDetails(String transactionHash) {
        return new Request<>(
                "zks_getTransactionDetails", Collections.singletonList(transactionHash), web3jService, ZksGetTransactionDetails.class);
    }
}
