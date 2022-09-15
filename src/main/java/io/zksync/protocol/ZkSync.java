package io.zksync.protocol;

import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.*;
import org.jetbrains.annotations.Nullable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthEstimateGas;

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
     * Get list of the tokens supported by ZkSync.
     * The tokens are returned in alphabetical order by their symbol, so basically, the token id is its position in an alphabetically sorted array of tokens.
     * 
     * @param from  Offset of tokens
     * @param limit Limit of amount of tokens to return
     * @return Prepared get confirmed tokens request
     */
    Request<?, ZksTokens> zksGetConfirmedTokens(Integer from, Short limit);

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

    Request<?, ZksContractDebugInfo> zksGetContractDebugInfo(String contractAddress);

    Request<?, ZksTransactionTrace> zksGetTransactionTrace(String transactionHash);

    /**
     * Get all known balances for the given account.
     *
     * @param address Wallet address
     * @return List of all balances where account has non-zero value
     */
    Request<?, ZksAccountBalances> zksGetAllAccountBalances(String address);

    /**
     * Get default bridges addresses deployed on L1 and L2.
     *
     * @return Prepared get bridge contract request
     */
    Request<?, ZksBridgeAddresses> zksGetBridgeContracts();

    /**
     * Get the proof for the message sent via the L1Messenger system contract.
     *
     * @param block The number of the block where the message was emitted
     * @param sender The sender of the message (i.e. the account that called the L1Messenger system contract)
     * @param message The keccak256 hash of the message that was sent
     * @param l2LogPosition The index in the block of the event that was emitted by the L1Messenger when submitting this message. If it is omitted, the proof for the first message with such content will be returned
     * @return Prepared get proof for message request
     */
    Request<?, ZksMessageProof> zksGetL2ToL1MsgProof(Integer block, String sender, String message, @Nullable Long l2LogPosition);

    /**
     * Generates and returns an estimate of how much gas is necessary to allow the transaction to complete. The transaction will not be added to the blockchain.
     *
     * @param transaction Transaction data for estimation
     * @return Prepared estimate gas request
     */
    Request<?, EthEstimateGas> ethEstimateGas(Transaction transaction);

    /**
     * Get the address of the testnet paymaster: the paymaster that is available on testnets and enables paying fees in ERC-20 compatible tokens.
     *
     * @return Prepared get paymaster request
     */
    Request<?, ZksTestnetPaymasterAddress> zksGetTestnetPaymaster();
}
