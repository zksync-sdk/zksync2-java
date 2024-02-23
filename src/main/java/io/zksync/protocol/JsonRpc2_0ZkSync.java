package io.zksync.protocol;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.*;
import io.zksync.protocol.core.BridgeAddresses;
import io.zksync.transaction.type.TransactionOptions;
import io.zksync.transaction.type.TransferTransaction;
import io.zksync.transaction.type.WithdrawTransaction;
import io.zksync.utils.TransactionStatus;
import io.zksync.utils.ZkSyncAddresses;
import io.zksync.wrappers.ERC20;
import io.zksync.wrappers.IEthToken;
import io.zksync.wrappers.IL2Bridge;
import org.jetbrains.annotations.Nullable;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Numeric;

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
    public Request<?, ZksMessageProof> zksGetL2ToL1LogProof(String txHash, int index) {
        return new Request<>("zks_getL2ToL1LogProof", Arrays.asList(txHash, index), web3jService, ZksMessageProof.class);
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

    @Override
    public Request<?, ZksGetTransactionByHash> zksGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "eth_getTransactionByHash", Collections.singletonList(transactionHash), web3jService, ZksGetTransactionByHash.class);
    }

    @Override
    public Request<?, ZksGetLogs> zksGetLogs(EthFilter ethFilter) {
        return new Request<>(
                "eth_getLogs", Collections.singletonList(ethFilter), web3jService, ZksGetLogs.class);
    }

    @Override
    public Request<?, ZksBlock> zksGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "eth_getBlockByHash",
                Arrays.asList(blockHash, returnFullTransactionObjects),
                web3jService,
                ZksBlock.class);
    }

    @Override
    public Request<?, ZksBlock> zksGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter, boolean returnFullTransactionObjects) {
        return new Request<>(
                "eth_getBlockByNumber",
                Arrays.asList(defaultBlockParameter.getValue(), returnFullTransactionObjects),
                web3jService,
                ZksBlock.class);
    }

    public Request<?, EthEstimateGas> estimateGasL1(Transaction transaction) {
        return new Request<>(
                "zks_estimateGasL1ToL2",
                Collections.singletonList(transaction),
                web3jService,
                EthEstimateGas.class);
    }

    public Request<?, EthEstimateGas> estimateL1ToL2Execute(String contractAddress, byte[] calldata, String caller, @Nullable BigInteger l2GasLimit, @Nullable BigInteger l2Value, @Nullable byte[][] factoryDeps, @Nullable BigInteger operatorTip, @Nullable BigInteger gasPerPubDataByte, @Nullable String refoundRecepient) {
        if (gasPerPubDataByte == null){
            gasPerPubDataByte = BigInteger.valueOf(800);
        }
        Eip712Meta meta = new Eip712Meta(gasPerPubDataByte, null, factoryDeps, null);

        if (factoryDeps != null){
            meta.setFactoryDeps(factoryDeps);
        }
        Transaction tx = Transaction.createFunctionCallTransaction(caller, contractAddress, l2Value, Numeric.toHexString(calldata), gasPerPubDataByte);
        return estimateGasL1(tx);
    }

    public Transaction getWithdrawTransaction(WithdrawTransaction tx, ContractGasProvider gasProvider, TransactionManager transactionManager) throws Exception {
        if (tx.from == null && tx.to == null){
            throw new Error("Withdrawal target address is undefined!");
        }

        tx.to = tx.to == null ? tx.from : tx.to;
        tx.options = tx.options == null ? new TransactionOptions() : tx.options;

        if (tx.tokenAddress.equals(ZkSyncAddresses.ETH_ADDRESS)){
            if (tx.options.getValue() == null){
                tx.options.setValue(tx.amount);
            }

            BigInteger passedValue = tx.options.getValue();

            if (!passedValue.equals(tx.amount)) {
                throw new Exception("The tx.value is not equal to the value withdrawn!");
            }

            IEthToken ethL2Token = IEthToken.load(ZkSyncAddresses.L2_ETH_TOKEN_ADDRESS, this, transactionManager, gasProvider);
            String data = ethL2Token.encodeWithdraw(tx.to, passedValue);
            Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(50000), null, null, tx.paymasterParams);

            return new Transaction(tx.from, ZkSyncAddresses.L2_ETH_TOKEN_ADDRESS, BigInteger.ZERO, BigInteger.ZERO, tx.amount, data, meta);
        }
        if (tx.bridgeAddress == null){
            BridgeAddresses bridgeAddresses = zksGetBridgeContracts().sendAsync().join().getResult();
            IL2Bridge l2WethBridge = IL2Bridge.load(bridgeAddresses.getL2wETHBridge(), this, transactionManager, gasProvider);

            String l1WethToken = ZkSyncAddresses.ETH_ADDRESS;
            try{
                l1WethToken = l2WethBridge.l1TokenAddress(tx.tokenAddress).sendAsync().join();
            }catch (Exception e){}

            tx.bridgeAddress = !Objects.equals(l1WethToken, ZkSyncAddresses.ETH_ADDRESS) ? bridgeAddresses.getL2wETHBridge() : bridgeAddresses.getL2Erc20DefaultBridge();
        }
        IL2Bridge bridge = IL2Bridge.load(tx.bridgeAddress, this, transactionManager, gasProvider);
        String data  = bridge.encodeWithdraw(tx.to, tx.tokenAddress, tx.amount);
        Eip712Meta meta = new Eip712Meta(BigInteger.valueOf(50000), null, null, tx.paymasterParams);

        return new Transaction(tx.from, tx.bridgeAddress, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, data, meta);
    }

    public Transaction getTransferTransaction(TransferTransaction tx, TransactionManager transactionManager, ContractGasProvider gasProvider){
        tx.to = tx.to == null ? tx.from : tx.to;
        tx.options = tx.options == null ? new TransactionOptions() : tx.options;
        BigInteger gasPrice = tx.options.getGasPrice();
        if (gasPrice == null){
            gasPrice = ethGasPrice().sendAsync().join().getGasPrice();
        }
        if (tx.gasPerPubData == null){
            tx.gasPerPubData = BigInteger.valueOf(50000);
        }
        Eip712Meta meta = new Eip712Meta(tx.gasPerPubData, null, null, tx.paymasterParams);

        if (tx.tokenAddress == null || tx.tokenAddress.equals(ZkSyncAddresses.ETH_ADDRESS) ){
            return new Transaction(tx.from, tx.to, BigInteger.ZERO, gasPrice, tx.amount, "0x", meta);
        }
//        ERC20 token = ERC20.load(tx.tokenAddress, this, transactionManager, gasProvider);
        String data = ERC20.encodeTransfer(tx.to, tx.amount);
        BigInteger value = tx.options.getValue();
        if (tx.tokenAddress != null && !tx.tokenAddress .equals(ZkSyncAddresses.ETH_ADDRESS)){
            value = BigInteger.ZERO;
        }

        return new Transaction(tx.from, tx.tokenAddress, BigInteger.ZERO, gasPrice, value, data, meta);
    }

    public Request<?, ZksGetTransactionByHash> getL2TransactionFromPriorityOp(TransactionReceipt receipt) throws InterruptedException {
        String l2Hash = getL2HashFromPriorityOp(receipt, zksMainContract().sendAsync().join().getResult());

        String status = null;
        do {
            status = transactionStatus(l2Hash);
            Thread.sleep(500);
        }while(status.equalsIgnoreCase(TransactionStatus.NOT_FOUND.getValue()));

        return zksGetTransactionByHash(l2Hash);
    }
    public String transactionStatus(String txHash){
        ZkTransaction transaction = zksGetTransactionByHash(txHash).sendAsync().join().getResult();
        if (transaction == null){
            return TransactionStatus.NOT_FOUND.getValue();
        }
        if (transaction.getBlockNumber() == null){
            return TransactionStatus.PROCESSING.getValue();
        }
        ZksBlock verifiedBlock = zksGetBlockByNumber(DefaultBlockParameterName.FINALIZED, false).sendAsync().join();
        if (transaction.getBlockNumber().compareTo(verifiedBlock.getBlock().getNumber()) <= 0){
            return TransactionStatus.FINALIZED.getValue();
        }
        return TransactionStatus.COMMITTED.getValue();
    }
    public String getL2HashFromPriorityOp(TransactionReceipt receipt, String contractAddress){
        Iterator it = receipt.getLogs().iterator();
        while (it.hasNext()){
            Log log = (Log) it.next();
            if (!log.getAddress().equalsIgnoreCase(contractAddress)){
                continue;
            }

            return "0x" + log.getData().substring(66, 130);
        }
        return null;
    }

    @Override
    public Request<?, ZksL1BatchNumber> getL1BatchNumber() {
        return new Request<>(
                "zks_L1BatchNumber", Collections.emptyList(), web3jService, ZksL1BatchNumber.class);
    }

    @Override
    public Request<?, ZksStorageProof> getProof(String address, String[] keys, BigInteger l1BatchNumber) {
        return new Request<>(
                "zks_L1BatchNumber", Arrays.asList(address, keys, l1BatchNumber), web3jService, ZksStorageProof.class);
    }

    @Override
    public Request<?, ZksBlockRange> getL1BatchBlockRange(BigInteger l1BatchNumber) {
        return new Request<>(
                "zks_getL1BatchBlockRange", Collections.singletonList(l1BatchNumber), web3jService, ZksBlockRange.class);
    }

    @Override
    public Request<?, ZksBatchDetails> getL1BatchDetails(BigInteger number) {
        return new Request<>(
                "zks_getL1BatchDetails", Collections.singletonList(number), web3jService, ZksBatchDetails.class);
    }

    @Override
    public Request<?, ZksBlockDetails> getBlockDetails(BigInteger number) {
        return new Request<>(
                "zks_getBlockDetails", Collections.singletonList(number), web3jService, ZksBlockDetails.class);
    }

}
