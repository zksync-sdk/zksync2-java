package io.zksync.protocol;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
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

public interface ZkSync extends Web3j {
    static ZkSync build(Web3jService web3jService) {
        return new JsonRpc2_0ZkSync(web3jService);
    }

    Request<?, ZksEstimateFee> zksEstimateFee(ZksEstimateFeeRequest transaction);

    Request<?, ZksMainContract> zksMainContract();

    Request<?, EthSendRawTransaction> zksGetL1WithdrawalTx(String transactionHash);

    Request<?, ZksAccountType> zksGetAccountType(String address);

    Request<?, ZksTransactions> zksGetAccountTransactions(String address, Integer before, Short limit);

    Request<?, ZksTokens> zksGetConfirmedTokens(Integer from, Short limit);

    Request<?, ZksIsTokenLiquid> zksIsTokenLiquid(String tokenAddress);

    Request<?, ZksTokenPrice> zksGetTokenPrice(String tokenAddress);
    
    // Request<?, > zksSetContractDebugInfo();
    // Request<?, > zksGetContractDebugInfo();
    // Request<?, > zksGetTransactionTrace();
    
    Request<?, EthGetBalance> ethGetBalance(
        String address, DefaultBlockParameter defaultBlockParameter, String tokenAddress);

}
