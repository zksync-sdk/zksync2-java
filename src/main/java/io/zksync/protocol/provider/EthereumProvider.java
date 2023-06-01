package io.zksync.protocol.provider;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import io.zksync.methods.response.ZksMainContract;
import io.zksync.protocol.core.BridgeAddresses;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.wrappers.IL1Bridge;
import io.zksync.wrappers.ZkSyncContract;
import org.jetbrains.annotations.Nullable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;


public interface EthereumProvider {

    /**
     * Send approve transaction to token contract.
     *
     * @param token - Token object supported by ZkSync
     * @param limit - Maximum amount to approve for ZkSync contract
     * @return CompletableFuture for waiting for transaction mine
     */
    CompletableFuture<TransactionReceipt> approveDeposits(Token token, Optional<BigInteger> limit);

    /**
     * Send transfer transaction. This is the regular transfer of ERC20 token
     *
     * @param token - Token object supported by ZkSync
     * @param amount - Amount tokens to transfer
     * @param to - Address of receiver tokens
     * @return CompletableFuture for waiting for transaction mine
     */
    CompletableFuture<TransactionReceipt> transfer(Token token, BigInteger amount, String to);

    /**
     * Get base cost for L2 transaction.
     * @param gasLimit - Gas limit for L2 transaction
     * @param gasPerPubdataByte - Gas per pubdata byte
     * @param gasPrice - Gas price for L2 transaction
     * @return CompletableFuture for waiting for transaction mine
     */
    CompletableFuture<BigInteger> getBaseCost(BigInteger gasLimit, BigInteger gasPerPubdataByte, @Nullable BigInteger gasPrice);

/**
     * Send request execute transaction to ZkSync contract.
     *
     * @param contractAddress - Address of contract to call
     * @param l2Value - Value to send to contract
     * @param calldata - Calldata to send to contract
     * @param gasLimit - Gas limit for L2 transaction
     * @param factoryDeps - Factory dependencies
     * @param operatorTips - Tips for operator on L2 that executes deposit
     * @param gasPrice - Gas price for L2 transaction
     * @param refundRecipient - Address of L2 receiver refund in ZkSync
     * @return CompletableFuture for waiting for transaction mine
     */
    CompletableFuture<TransactionReceipt> requestExecute(String contractAddress, BigInteger l2Value, byte[] calldata, BigInteger gasLimit, @Nullable byte[][] factoryDeps, @Nullable BigInteger operatorTips, @Nullable BigInteger gasPrice, String refundRecipient);

    /**
     * Send deposit transaction to ZkSync contract. For ERC20 token must be approved before. @see EthereumProvider.approveDepodits
     *
     * @param token - Token object supported by ZkSync
     * @param amount - Amount tokens to transfer
     * @param operatorTips - Tips for operator on L2 that executes deposit
     * @param userAddress - Address of L2 receiver deposit in ZkSync
     * @return CompletableFuture for waiting for transaction mine
     */

    CompletableFuture<TransactionReceipt> deposit(Token token, BigInteger amount, BigInteger operatorTips, String userAddress);

    /**
     * Send withdraw transaction to ZkSync contract.
     *
     * @param token - Token object supported by ZkSync
     * @param amount - Amount tokens to transfer
     * @param userAddress - Address of L1 receiver withdraw in ZkSync
     * @return CompletableFuture for waiting for transaction mine
     */
    CompletableFuture<TransactionReceipt> withdraw(Token token, BigInteger amount, String userAddress);
    TransactionReceipt finalizeWithdraw(String txHash, int index) throws Exception;

    /**
     * Check if deposit is approved in enough amount
     *
     * @param token - Token object supported by ZkSync
     * @param to - Address of the account who can deposit tokens from yours
     * @param threshold - Minimum threshold of approved tokens
     * @return CompletableFuture of blockchain smart-contract call
     */
    CompletableFuture<Boolean> isDepositApproved(Token token, String to, Optional<BigInteger> threshold);

    /**
     * Get ZkSync Bridge for ERC20 smart-contract address in Ethereum blockchain
     *
     * @return Contract address in hex string
     */
    String l1ERC20BridgeAddress();

    static CompletableFuture<EthereumProvider> load(ZkSync zksync, Web3j ethereum, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        return CompletableFuture.supplyAsync(() -> {
            BridgeAddresses bridgeAddresses = zksync.zksGetBridgeContracts().sendAsync().join().getResult();
            String mainContract = zksync.zksMainContract().sendAsync().join().getResult();
            IL1Bridge erc20Bridge = IL1Bridge.load(bridgeAddresses.getL1Erc20DefaultBridge(), ethereum, transactionManager, gasProvider);
            ZkSyncContract zkSyncContract = ZkSyncContract.load(mainContract, ethereum, transactionManager, gasProvider);
            return new DefaultEthereumProvider(ethereum,zksync, transactionManager, gasProvider, zkSyncContract, erc20Bridge);
        });
    }

}
