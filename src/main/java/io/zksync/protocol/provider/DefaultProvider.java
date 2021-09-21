package io.zksync.protocol.provider;

import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.StateType;
import io.zksync.protocol.core.domain.block.BlockInfo;
import io.zksync.protocol.core.domain.contract.ContractAddress;
import io.zksync.protocol.core.domain.events.EventsDetails;
import io.zksync.protocol.core.domain.fee.TransactionFeeDetails;
import io.zksync.protocol.core.domain.fee.TransactionFeeRequest;
import io.zksync.protocol.core.domain.state.AccountState;
import io.zksync.protocol.core.domain.state.Status;
import io.zksync.protocol.core.domain.token.Token;
import io.zksync.protocol.core.domain.transaction.SignedTransaction;
import io.zksync.protocol.core.domain.transaction.TransactionReceipt;
import io.zksync.protocol.transport.ZkSyncTransport;
import io.zksync.protocol.transport.response.ZksAccountState;
import io.zksync.protocol.transport.response.ZksAccountType;
import io.zksync.protocol.transport.response.ZksBalance;
import io.zksync.protocol.transport.response.ZksBlockInfo;
import io.zksync.protocol.transport.response.ZksBlockInfoList;
import io.zksync.protocol.transport.response.ZksContractAddress;
import io.zksync.protocol.transport.response.ZksEvents;
import io.zksync.protocol.transport.response.ZksGetConfirmationsForEthOpAmount;
import io.zksync.protocol.transport.response.ZksNonce;
import io.zksync.protocol.transport.response.ZksSentTransaction;
import io.zksync.protocol.transport.response.ZksStatus;
import io.zksync.protocol.transport.response.ZksStorage;
import io.zksync.protocol.transport.response.ZksTokenPrice;
import io.zksync.protocol.transport.response.ZksTokens;
import io.zksync.protocol.transport.response.ZksTransactionDetails;
import io.zksync.protocol.transport.response.ZksTransactionDetailsList;
import io.zksync.protocol.transport.response.ZksTransactionFeeDetails;
import io.zksync.transaction.Transaction;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultProvider implements Provider {

    private final ZkSyncTransport transport;

    private Collection<Token> tokens;

    @Override
    public AccountState getState(String accountAddress) {
        final AccountState response = transport.send("get_account_state", Collections.singletonList(accountAddress),
                ZksAccountState.class);

        return response;
    }

    @Override
    public <T extends Transaction> TransactionFeeDetails getTransactionFee(TransactionFeeRequest<T> estimateReq,
            String tokenAddress) {
        TransactionFeeDetails response = transport.send("get_tx_fee",
            Arrays.asList(estimateReq, tokenAddress),
            ZksTransactionFeeDetails.class);
        return response;
    }

    @Override
    public Collection<Token> getTokens() {
        if (this.tokens == null) {
            this.updateTokenSet();
        }

        return this.tokens;
    }

    @Override
    public <T extends Transaction> String submitTx(SignedTransaction<T> tx) {
        final String responseBody = transport.send("tx_submit", Collections.singletonList(tx),
                ZksSentTransaction.class);

        return responseBody;
    }

    @Override
    public ContractAddress contractAddress() {
        final ContractAddress contractAddress = transport.send("get_contract_addresses", Collections.emptyList(),
                ZksContractAddress.class);

        return contractAddress;
    }

    @Override
    public TransactionReceipt getTransactionReceipt(String txHash) {
        final TransactionReceipt response = transport.send("tx_info", Collections.singletonList(txHash),
                ZksTransactionDetails.class);

        return response;
    }

    @Override
    public BigInteger getConfirmationsForEthOpAmount() {
        final BigInteger response = transport.send("get_confirmations_for_eth_op_amount", Collections.emptyList(),
                ZksGetConfirmationsForEthOpAmount.class);

        return response;
    }

    @Override
    public String getEthTransactionForWithdrawal(String zkSyncWithdrawalHash) {
        final String response = transport.send("get_eth_tx_for_withdrawal", Collections.singletonList(zkSyncWithdrawalHash),
                ZksSentTransaction.class);

        return response;
    }

    public void updateTokenSet() {
        final Collection<Token> response = transport.send("get_tokens", Collections.emptyList(), ZksTokens.class);
        this.tokens = response;
    }

    @Override
    public Collection<Token> getFeeEliglibleTokens() {
        final Collection<Token> response = transport.send("get_fee_eliglible_tokens", Collections.emptyList(), ZksTokens.class);

        return response;
    }

    @Override
    public BigDecimal getTokenPrice(String tokenAddress) {
        final BigDecimal response = transport.send("get_token_price", Collections.singletonList(tokenAddress),
                ZksTokenPrice.class);

        return response;
    }

    @Override
    public TransactionReceipt getPriorityOpReceipt(Integer serialId) {
        final TransactionReceipt response = transport.send("priority_op_info", Collections.singletonList(serialId), ZksTransactionDetails.class);

        return response;
    }

    @Override
    public Optional<AccountType> getAccountType(String accountAddress) {
        final AccountType accountType = transport.send("get_account_type", Collections.singletonList(accountAddress), ZksAccountType.class);

        return Optional.ofNullable(accountType);
    }

    @Override
    public BigInteger getBalance(String accountAddress, String tokenAddress, StateType stateType) {
        final BigInteger response = transport.send("get_balance", Arrays.asList(accountAddress, tokenAddress, stateType), ZksBalance.class);

        return response;
    }

    @Override
    public BigInteger getStorage(String writerAddress, String destinationAddress, String key, StateType stateType) {
        final BigInteger response = transport.send("get_storage", Arrays.asList(writerAddress, destinationAddress, key, stateType), ZksStorage.class);

        return response;
    }

    @Override
    public EventsDetails getEvents(Integer blockNumber, String address, Collection<String> topics) {
        final EventsDetails response = transport.send("get_events", Arrays.asList(blockNumber, address, topics), ZksEvents.class);

        return response;
    }

    @Override
    public Integer getNonce(String accountAddress) {
        final Integer response = transport.send("get_nonce", Arrays.asList(accountAddress), ZksNonce.class);

        return response;
    }

    @Override
    public Status getStatus() {
        final Status response = transport.send("get_status", Collections.emptyList(), ZksStatus.class);

        return response;
    }

    @Override
    public Collection<BlockInfo> getBlocks(Integer from, Integer limit) {
        final List<BlockInfo> response = transport.send("get_blocks", Arrays.asList(from, limit), ZksBlockInfoList.class);

        return response;
    }

    @Override
    public Collection<BlockInfo> getLastBlocks(Integer to, Integer limit) {
        final List<BlockInfo> response = transport.send("get_last_blocks", Arrays.asList(to, limit), ZksBlockInfoList.class);

        return response;
    }

    @Override
    public Optional<BlockInfo> searchBlock(String search) {
        final BlockInfo blockInfo = transport.send("search_block", Collections.singletonList(search), ZksBlockInfo.class);

        return Optional.ofNullable(blockInfo);
    }

    @Override
    public Collection<TransactionReceipt> getBlockTransactions(Integer blockNumber) {
        final List<TransactionReceipt> response = transport.send("get_block_txs", Collections.singletonList(blockNumber), ZksTransactionDetailsList.class);

        return response;
    }

    @Override
    public Collection<TransactionReceipt> getAccountTransactions(String address, Integer from, Integer limit) {
        final List<TransactionReceipt> response = transport.send("get_account_txs", Arrays.asList(address, from, limit), ZksTransactionDetailsList.class);
        
        return response;
    }
}
