package io.zksync.protocol.provider;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import io.zksync.protocol.core.BridgeAddresses;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.wrappers.L1ERC20Bridge;
import io.zksync.wrappers.L1EthBridge;
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
     * Send deposit transaction to ZkSync contract. For ERC20 token must be approved before. @see EthereumProvider.approveDepodits
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
     * Get ZkSync Bridge for ERC20 smart-contract address in Ethereum blockchain
     *
     * @return Contract address in hex string
     */
    String l1ERC20BridgeAddress();

    /**
     * Get ZkSync Bridge for Eth smart-contract address in Ethereum blockchain
     *
     * @return Contract address in hex string
     */
    String l1EthBridgeAddress();

    static CompletableFuture<EthereumProvider> load(ZkSync zksync, Web3j ethereum, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        return zksync.zksGetBridgeContracts()
            .sendAsync()
            .thenApply(response -> {
                if (!response.hasError()) {
                    BridgeAddresses bridgeAddresses = response.getResult();
                    L1ERC20Bridge erc20Bridge = L1ERC20Bridge.load(bridgeAddresses.getL1Erc20DefaultBridge(), ethereum, transactionManager, gasProvider);
                    L1EthBridge ethBridge = L1EthBridge.load(bridgeAddresses.getL1EthDefaultBridge(), ethereum, transactionManager, gasProvider);
                    return new DefaultEthereumProvider(ethereum, transactionManager, gasProvider, null, erc20Bridge, ethBridge);
                } else {
                    throw new JsonRpcResponseException(response);
                }
            });
    }

}
