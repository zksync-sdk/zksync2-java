package io.zksync.protocol;

import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.*;
import io.zksync.transaction.type.TransferTransaction;
import io.zksync.transaction.type.WithdrawTransaction;
import org.jetbrains.annotations.Nullable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

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
     * Get the proof for the message sent via the IL1Messenger system contract.
     *
     * @param block         The number of the block where the message was emitted
     * @param sender        The sender of the message (i.e. the account that called the IL1Messenger system contract)
     * @param message       The keccak256 hash of the message that was sent
     * @param l2LogPosition The index in the block of the event that was emitted by the IL1Messenger when submitting this message. If it is omitted, the proof for the first message with such content will be returned
     * @return Prepared get proof for message request
     */
    Request<?, ZksMessageProof> zksGetL2ToL1MsgProof(Integer block, String sender, String message, @Nullable Long l2LogPosition);

    Request<?, ZksMessageProof> zksGetL2ToL1LogProof(String txHash, int index);
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

    /**
     * Get transaction receipt.
     * The same as eth_getTransactionReceipt but with additional fields belong to L2ToL1 messenger
     * See {@link io.zksync.utils.Messenger}
     *
     * @param transactionHash Hash of the executed transaction hash with sent message
     * @return Prepared get transaction receipt request
     */
    Request<?, ZksGetTransactionReceipt> zksGetTransactionReceipt(String transactionHash);

    /**
     * Get transaction details.
     *
     * @param transactionHash Hash of the executed transaction hash with sent message
     * @return Prepared get transaction details request
     */
    Request<?, ZksGetTransactionDetails> zksGetTransactionDetails(String transactionHash);

    /**
     * Get transaction.
     *
     * @param transactionHash Hash of the executed transaction hash with sent message
     * @return Prepared get transaction request
     */
    Request<?, ZksGetTransactionByHash> zksGetTransactionByHash(String transactionHash);

    /**
     * Get logs.
     *
     * @param ethFilter the filter options
     * @return Prepared get transaction request
     */
    Request<?, ZksGetLogs> zksGetLogs(EthFilter ethFilter);

    /**
     * Get block by hash.
     *
     * @param blockHash Hash of a block
     * @param returnFullTransactionObjects  If true it returns the full transaction objects,
     * if false only the hashes of the transactions
     * @return Prepared get transaction receipt request
     */
    Request<?, ZksBlock> zksGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects);

    /**
     * Get block by number.
     *
     * @param defaultBlockParameter integer of a block number, or the string "earliest",
     * "latest" or "pending", as in the default block parameter.
     * @param returnFullTransactionObjects If true it returns the full transaction objects,
     * if false only the hashes of the transactions.
     * @return Prepared get transaction receipt request
     */
    Request<?, ZksBlock> zksGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects);

    Request<?, EthEstimateGas> estimateL1ToL2Execute(String contractAddress, byte[] calldata, String caller, @Nullable BigInteger l2GasLimit, @Nullable BigInteger l2Value, @Nullable byte[][] factoryDeps, @Nullable BigInteger operatorTip, @Nullable BigInteger gasPerPubDataByte, @Nullable String refoundRecepient);

    Transaction getWithdrawTransaction(WithdrawTransaction tx, ContractGasProvider gasProvider, TransactionManager transactionManager) throws Exception;

    Transaction getTransferTransaction(TransferTransaction tx, TransactionManager transactionManager, ContractGasProvider gasProvider);

    Request<?, ZksGetTransactionByHash> getL2TransactionFromPriorityOp(TransactionReceipt receipt) throws InterruptedException;

    String getL2HashFromPriorityOp(TransactionReceipt receipt, String contractAddress);

    Request<?, ZksL1BatchNumber> getL1BatchNumber();

    Request<?, ZksStorageProof> getProof(String address, String[] keys, BigInteger l1BatchNumber);

    Request<?, ZksBlockRange> getL1BatchBlockRange(BigInteger l1BatchNumber);

    Request<?, ZksBatchDetails> getL1BatchDetails(BigInteger number);

    Request<?, ZksBlockDetails> getBlockDetails(BigInteger number);



}