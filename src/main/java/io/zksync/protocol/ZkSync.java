package io.zksync.protocol;

import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.*;
import org.jetbrains.annotations.Nullable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendRawTransaction;

import io.zksync.protocol.core.debug.ContractSourceDebugInfo;

public interface ZkSync extends Web3j {
    static ZkSync build(Web3jService web3jService) {
        return new JsonRpc2_0ZkSync(web3jService);
    }

    /**
     * Estimate fee for the given transaction at the moment of the latest committed
     * block.
     * 
     * @param transaction Transaction data for estimation
     * @return Prepared estimate fee request
     */
    Request<?, ZksEstimateFee> zksEstimateFee(Transaction transaction);

    /**
     * Get address of main contract for current network chain.
     * 
     * @return Prepared main contract request
     */
    Request<?, ZksMainContract> zksMainContract();

    /**
     * Get hash of the withdrawal transaction in the L1 Ethereum chain.
     * 
     * @param transactionHash Hash of the withdraw transaction in L2 in hex format
     * @return Prepared get withdraw transaction hash request
     */
    Request<?, EthSendRawTransaction> zksGetL1WithdrawalTx(String transactionHash);

    /**
     * Get list of the transactions for a given account.
     * 
     * @param address Account address in hex format
     * @param before  Top offset of transactions
     * @param limit   Limit of amount of transactions to return
     * @return Prepared get account transactions request
     */
    Request<?, ZksTransactions> zksGetAccountTransactions(String address, Integer before, Short limit);

    /**
     * Get list of the tokens supported by ZkSync.
     * 
     * @param from  Offset of tokens
     * @param limit Limit of amount of tokens to return
     * @return Prepared get confirmed tokens request
     */
    Request<?, ZksTokens> zksGetConfirmedTokens(Integer from, Short limit);

    /**
     * Check if token is liquid.
     * 
     * @param tokenAddress Address of the token in hex format
     * @return Prepared is token liquid request
     */
    Request<?, ZksIsTokenLiquid> zksIsTokenLiquid(String tokenAddress);

    /**
     * Get price of the token in USD.
     * 
     * @param tokenAddress Address of the token in hex format
     * @return Prepared get token price request
     */
    Request<?, ZksTokenPrice> zksGetTokenPrice(String tokenAddress);

    /**
     * Get chain identifier of the L1 chain.
     * 
     * @return Prepared l1 chainid request
     */
    Request<?, ZksL1ChainId> zksL1ChainId();

    /**
     * Get account's balance in Token.
     * 
     * @param address               Address of the account in hex format
     * @param defaultBlockParameter Block number or block name
     * @param tokenAddress          Address of the token in hex format
     * @return Prepared get balance request
     */
    Request<?, EthGetBalance> ethGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter, String tokenAddress);

    Request<?, ZksSetContractDebugInfoResult> zksSetContractDebugInfo(String contractAddress,
            ContractSourceDebugInfo contractDebugInfo);

    Request<?, ZksContractDebugInfo> zksGetContractDebugInfo(String contractAddress);

    Request<?, ZksTransactionTrace> zksGetTransactionTrace(String transactionHash);

    Request<?, ZksAccountBalances> zksGetAllAccountBalances(String address);

    Request<?, ZksBridgeAddresses> zksGetBridgeContracts();

    Request<?, ZksMessageProof> zksGetL2ToL1MsgProof(Integer block, String sender, String message, @Nullable Long l2LogPosition);
}
