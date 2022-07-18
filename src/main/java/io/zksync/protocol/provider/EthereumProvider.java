package io.zksync.protocol.provider;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.wrappers.ZkSyncContract;


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
     * Send deposit transaction to ZkSync contract. For ERC20 token must be approved before. @see EthereumProvider.approveDeposits
     * 
     * @param token - Token object supported by ZkSync
     * @param amount - Amount tokens to transfer
     * @param userAddress - Address of L2 receiver deposit in ZkSync
     * @return CompletableFuture for waiting for transaction mine
     */
    CompletableFuture<TransactionReceipt> deposit(Token token, BigInteger amount, String userAddress);
    
    /**
     * Send withdraw transaction to ZkSync contract.
     * 
     * @param token - Token object supported by ZkSync
     * @param amount - Amount tokens to transfer
     * @param userAddress - Address of L1 receiver withdraw in ZkSync
     * @return CompletableFuture for waiting for transaction mine
     */
    CompletableFuture<TransactionReceipt> withdraw(Token token, BigInteger amount, String userAddress);

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
     * Get ZkSync smart-contract address in Ethereum blockchain
     * 
     * @return Contract address in hex string
     */
    String contractAddress();

    static CompletableFuture<EthereumProvider> load(ZkSync zksync, Web3j ethereum, Credentials credentials) {
        return zksync.zksMainContract()
            .sendAsync()
            .thenApply(response -> {
                if (!response.hasError()) {
                    BigInteger chainId = ethereum.ethChainId().sendAsync().join().getChainId();
                    return new DefaultEthereumProvider(ethereum, ZkSyncContract.load(response.getResult(), ethereum, new RawTransactionManager(ethereum, credentials, chainId.longValue()), new StaticGasProvider(BigInteger.ZERO, BigInteger.valueOf(8_000_000l))));
                } else {
                    return null;
                }
            });
    }

}
