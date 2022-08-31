package io.zksync.protocol.provider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import io.zksync.wrappers.*;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert.Unit;

import io.zksync.protocol.core.Token;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultEthereumProvider implements EthereumProvider {

    private static final BigInteger MAX_APPROVE_AMOUNT = BigInteger.valueOf(2).pow(256).subtract(BigInteger.ONE);
    private static final BigInteger DEFAULT_THRESHOLD = BigInteger.valueOf(2).pow(255);
    private static final ContractGasProvider DEFAULT_GAS_PROVIDER = new StaticGasProvider(BigInteger.ZERO,
            BigInteger.ZERO);

    private final Web3j web3j;
    private final TransactionManager transactionManager;
    private final ContractGasProvider gasProvider;
    private final ZkSyncContract contract;
    private final IL1Bridge l1ERC20Bridge;
    private final IL1Bridge l1EthBridge;

    public CompletableFuture<BigInteger> getGasPrice() {
        return web3j.ethGasPrice()
                .sendAsync()
                .thenApply(EthGasPrice::getGasPrice);
    }

    @Override
    public CompletableFuture<TransactionReceipt> approveDeposits(Token token, Optional<BigInteger> limit) {
        ERC20 tokenContract = ERC20.load(token.getL1Address(), web3j, transactionManager,
                gasProvider);
        return tokenContract.approve(l1ERC20BridgeAddress(), limit.orElse(MAX_APPROVE_AMOUNT)).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> transfer(Token token, BigInteger amount, String to) {
        if (token.isETH()) {
            Transfer transfer = new Transfer(web3j, transactionManager);
            return transfer.sendFunds(to, new BigDecimal(amount), Unit.WEI).sendAsync();
        } else {
            ERC20 tokenContract = ERC20.load(token.getL1Address(), web3j, transactionManager,
                    gasProvider);
            return tokenContract.transfer(to, amount).sendAsync();
        }
    }

    public CompletableFuture<BigInteger> getDepositBaseCost(@Nullable BigInteger gasPrice) {
        if (gasPrice != null) {
            return contract.depositBaseCost(gasPrice, PriorityQueueType.Deque.getValue(), PriorityOpTree.Full.getValue()).sendAsync();
        } else {
            return getGasPrice()
                    .thenCompose(g -> contract.depositBaseCost(g, PriorityQueueType.Deque.getValue(), PriorityOpTree.Full.getValue()).sendAsync());
        }
    }

    @Override
    public CompletableFuture<TransactionReceipt> deposit(Token token, BigInteger amount, String userAddress) {
        BigInteger tips = BigInteger.ZERO;
        if (token.isETH()) {
            return l1EthBridge.deposit(userAddress, Address.DEFAULT.getValue(), amount, amount.add(tips)).sendAsync();
        } else {
            return l1ERC20Bridge.deposit(userAddress, token.getL1Address(), amount, tips).sendAsync();
        }
    }

    @Override
    public CompletableFuture<TransactionReceipt> withdraw(Token token, BigInteger amount, String userAddress) {
//        return contract.requestWithdraw(token.getAddress(), amount, userAddress, PriorityQueueType.Deque.getValue(), PriorityOpTree.Full.getValue()).sendAsync();
        throw new UnsupportedOperationException();
    }

    @Override
    public CompletableFuture<Boolean> isDepositApproved(Token token, String to, Optional<BigInteger> threshold) {
        ERC20 tokenContract = ERC20.load(token.getL1Address(), web3j, transactionManager,
                DEFAULT_GAS_PROVIDER);
        return tokenContract.allowance(to, l1ERC20BridgeAddress()).sendAsync()
                .thenApply(allowance -> allowance.compareTo(threshold.orElse(DEFAULT_THRESHOLD)) >= 0);
    }

    @Override
    public String l1ERC20BridgeAddress() {
        return l1ERC20Bridge.getContractAddress();
    }

    @Override
    public String l1EthBridgeAddress() {
        return l1EthBridge.getContractAddress();
    }
}
