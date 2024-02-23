package io.zksync.protocol.account;

import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.methods.response.FullDepositFee;
import io.zksync.methods.response.L2toL1Log;
import io.zksync.methods.response.ZkTransactionReceipt;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.BridgeAddresses;
import io.zksync.protocol.core.L2ToL1MessageProof;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.type.*;
import io.zksync.utils.ZkSyncAddresses;
import io.zksync.wrappers.*;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.EventValues;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.*;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;
import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_FREQUENCY;
import static io.zksync.utils.WalletUtils.*;
import static io.zksync.utils.ZkSyncAddresses.L2_ETH_TOKEN_ADDRESS;
import static io.zksync.utils.ZkSyncAddresses.MESSENGER_ADDRESS;
import static io.zksync.wrappers.IL1Messenger.L1MESSAGESENT_EVENT;

@Getter
public class WalletL1 {
    private static final BigInteger L1_TO_L2_GAS_PER_PUBDATA = BigInteger.valueOf(800);
    private static final BigInteger L1_RECOMMENDED_MIN_ETH_DEPOSIT_GAS_LIMIT = BigInteger.valueOf(200_000);
    private static final BigInteger L1_RECOMMENDED_MIN_ERC20_DEPOSIT_GAS_LIMIT = BigInteger.valueOf(400_000);
    protected final Web3j providerL1;
    protected final ZkSync providerL2;
    protected final TransactionManager transactionManager;
    protected final TransactionReceiptProcessor transactionReceiptProcessorL1;
    protected final ContractGasProvider gasProvider;
    protected final String mainContractAddress;
    protected final IZkSync contract;
    protected final EthSigner signer;
    protected final Credentials credentials;
    public WalletL1(Web3j providerL1, ZkSync providerL2, TransactionManager transactionManager, ContractGasProvider gasProvider, Credentials credentials) {
        this.providerL1 = providerL1;
        this.providerL2 = providerL2;
        this.transactionManager = transactionManager;
        this.gasProvider = gasProvider;
        this.mainContractAddress = providerL2.zksMainContract().sendAsync().join().getResult();
        this.contract = IZkSync.load(providerL2.zksMainContract().sendAsync().join().getResult(), providerL1, transactionManager, gasProvider);
        this.credentials = credentials;
        this.signer = new PrivateKeyEthSigner(credentials, providerL1.ethChainId().sendAsync().join().getChainId().longValue());
        this.transactionReceiptProcessorL1 = new PollingTransactionReceiptProcessor(providerL1, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
    }

    public WalletL1(Web3j providerL1, ZkSync providerL2, Credentials credentials){
        this(
                providerL1,
                providerL2,
                new RawTransactionManager(providerL1, credentials, providerL1.ethChainId().sendAsync().join().getChainId().longValue()),
                new StaticGasProvider(providerL1.ethGasPrice().sendAsync().join().getGasPrice(), BigInteger.valueOf(300_000L)),
                credentials);
    }

    /**
     * Get main contract wrapper
     *
     * @return IZkSync
     */
    public IZkSync getMainContract(){
        if (contract == null){
            return IZkSync.load(providerL2.zksMainContract().sendAsync().join().getResult(), providerL1, transactionManager, gasProvider);
        }
        return contract;
    }

    /**
     * Get balance of wallet in native coin (wallet address gets from {@link EthSigner})
     *
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalanceL1() {
        return getBalanceL1(signer.getAddress(), ZkSyncAddresses.ETH_ADDRESS, DefaultBlockParameterName.LATEST);
    }

    /**
     * Get balance of wallet in {@link Token} (wallet address gets from {@link EthSigner})
     *
     * @param token Token object supported by ZkSync
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalanceL1(String token) {
        return getBalanceL1(signer.getAddress(), token, DefaultBlockParameterName.LATEST);
    }

    /**
     * Get balance of wallet in {@link Token}
     *
     * @param address Address of the wallet
     * @param token Token object supported by ZkSync
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalanceL1(String address, String token) {
        return getBalanceL1(address, token, DefaultBlockParameterName.LATEST);
    }

    /**
     * Get balance of wallet by address in {@link Token} at block {@link DefaultBlockParameter}
     * also see {@link org.web3j.protocol.core.DefaultBlockParameterName}, {@link org.web3j.protocol.core.DefaultBlockParameterNumber}, {@link ZkBlockParameterName}
     *
     * @param address Wallet address
     * @param token Token object supported by ZkSync
     * @param at Block variant
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalanceL1(String address, String token, DefaultBlockParameter at) {
        if (Objects.equals(token, ZkSyncAddresses.ETH_ADDRESS)) {
            return new RemoteCall<>(() ->
                    this.providerL1.ethGetBalance(address, at).sendAsync().join().getBalance());
        } else {
            ERC20 erc20 = ERC20.load(token, this.providerL2, transactionManager, gasProvider);
            return erc20.balanceOf(address);
        }
    }
    public CompletableFuture<TransactionReceipt> approveERC20(String token, BigInteger amount) {
        return approveERC20(token, amount, null);
    }
    public CompletableFuture<TransactionReceipt> approveERC20(String token, @Nullable BigInteger amount, @Nullable String bridgeAddress) {
        if (token == ZkSyncAddresses.ETH_ADDRESS){
            throw new Error("ETH token can't be approved! The address of the token does not exist on L1.");
        }
        ERC20 tokenContract = ERC20.load(token, providerL1, transactionManager,
                gasProvider);

        if (bridgeAddress == null){
            L1BridgeContracts bridgeContracts = getL1BridgeContracts();
            String l2WethAddress = ZkSyncAddresses.ETH_ADDRESS;
            try{
                l2WethAddress = bridgeContracts.wethL1Bridge.l2TokenAddress(token).sendAsync().join();
            }catch (Exception e){}
            bridgeAddress = !Objects.equals(l2WethAddress, ZkSyncAddresses.ETH_ADDRESS) ? bridgeContracts.wethL1Bridge.getContractAddress() : bridgeContracts.erc20L1Bridge.getContractAddress();
        }

        return tokenContract.approve(bridgeAddress, amount).sendAsync();
    }

    public CompletableFuture<BigInteger> getAllowanceL1(String token){
        return getAllowanceL1(token, null);
    }

    public CompletableFuture<BigInteger> getAllowanceL1(String token, @Nullable String bridgeAddress){
        if (bridgeAddress == null){
            L1BridgeContracts bridgeContracts = getL1BridgeContracts();
            String l2WethToken = ZkSyncAddresses.ETH_ADDRESS;
            try{
                l2WethToken = bridgeContracts.wethL1Bridge.l2TokenAddress(token).sendAsync().join();
            }catch (Exception e){}
            bridgeAddress = !Objects.equals(l2WethToken, ZkSyncAddresses.ETH_ADDRESS) ? bridgeContracts.wethL1Bridge.getContractAddress() : bridgeContracts.erc20L1Bridge.getContractAddress();
        }
        ERC20 erc20 = ERC20.load(token, providerL1, transactionManager, gasProvider);
        return erc20.allowance(signer.getAddress(), bridgeAddress).sendAsync();
    }

    public String l2TokenAddress(String l1Address){
        if (ZkSyncAddresses.ETH_ADDRESS.equals(l1Address)){
            return ZkSyncAddresses.ETH_ADDRESS;
        }

        L1BridgeContracts bridgeContracts = getL1BridgeContracts();
        IL1Bridge bridge = bridgeContracts.erc20L1Bridge;
        String l2WethAddress = ZkSyncAddresses.ETH_ADDRESS;
        try{
            l2WethAddress = bridgeContracts.wethL1Bridge.l2TokenAddress(l1Address).sendAsync().join();
            if (Objects.equals(l2WethAddress, ZkSyncAddresses.ETH_ADDRESS)){
                return l2WethAddress;
            }
        }catch (Exception e){}
        return bridgeContracts.erc20L1Bridge.l2TokenAddress(l1Address).sendAsync().join();
    }

    private EthSigner getSigner() {
        return signer;
    }

    public L1BridgeContracts getL1BridgeContracts(){
        BridgeAddresses bridgeAddresses = providerL2.zksGetBridgeContracts().sendAsync().join().getResult();
        return new L1BridgeContracts(bridgeAddresses.getL1Erc20DefaultBridge(), bridgeAddresses.getL1wETHBridge(), providerL1, transactionManager, gasProvider);
    }

    public CompletableFuture<BigInteger> getBaseCost(BigInteger gasLimit) {
        return getBaseCost(gasLimit, null, null);
    }

    public CompletableFuture<BigInteger> getBaseCost(BigInteger gasLimit, @Nullable BigInteger gasPerPubdataByte, @Nullable BigInteger gasPrice) {
        if (gasPrice == null){
            gasPrice = providerL1.ethGasPrice().sendAsync().join().getGasPrice();
        }
        if (gasPerPubdataByte == null){
            gasPerPubdataByte = L1_TO_L2_GAS_PER_PUBDATA;
        }

        return contract.l2TransactionBaseCost(gasPrice, gasLimit, gasPerPubdataByte).sendAsync();
    }

    public RequestExecuteTransaction getRequestExecuteTransaction(RequestExecuteTransaction transaction){

        if (transaction.getOperatorTip() == null){
            transaction.setOperatorTip(BigInteger.valueOf(0));
        }
        if (transaction.getGasPerPubDataByte() == null){
            transaction.setGasPerPubDataByte(BigInteger.valueOf(800));
        }
        if (transaction.getRefoundRecepient() == null){
            transaction.setRefoundRecepient(signer.getAddress());
        }
        if (transaction.getL2GasLimit() == null){
            transaction.setL2GasLimit(providerL2.estimateL1ToL2Execute(transaction.getContractAddress(), transaction.getCalldata(), getSigner().getAddress(), null, transaction.getL2Value(), transaction.getFactoryDeps(), transaction.getOperatorTip(), transaction.getGasPerPubDataByte(), transaction.getRefoundRecepient()).sendAsync().join().getAmountUsed());
        }
        if (transaction.getFactoryDeps() == null)
            transaction.setFactoryDeps(new byte[1][]);
        List<byte[]> factoryDepsList = transaction.getFactoryDeps() == null ? Collections.emptyList() : Arrays.asList(transaction.getFactoryDeps());

        transaction.setOptions(insertGasPriceInTransactionOptions(transaction.getOptions(), providerL1));
        transaction.getOptions().setChainId(providerL1.ethChainId().sendAsync().join().getChainId());
        transaction.getOptions().setNonce(providerL1.ethGetTransactionCount(signer.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().join().getTransactionCount());

        BigInteger gasPriceForestimation = transaction.getOptions().getMaxFeePerGas() != null ? transaction.getOptions().getMaxFeePerGas() : transaction.getOptions().getGasPrice();

        BigInteger baseCost = getBaseCost(transaction.getL2GasLimit(), transaction.getGasPerPubDataByte(), gasPriceForestimation).join();

        if (transaction.getOptions().getValue() == null){
            transaction.getOptions().setValue(baseCost.add(transaction.getL2Value().add(transaction.getOperatorTip())));
        }

        checkBaseCost(baseCost, transaction.getOptions().getValue());

        return transaction;
    }

    public Request<?, EthSendTransaction> deposit(DepositTransaction transaction) throws IOException {
        transaction = getDepositTransaction(transaction);

        if (Objects.equals(transaction.tokenAddress, ZkSyncAddresses.ETH_ADDRESS)){
            BigInteger baseGasLimit = estimateGasRequestExecute(transaction.toRequestExecute(mainContractAddress)).sendAsync().join().getAmountUsed();
            BigInteger gasLimit = scaleGasLimit(baseGasLimit);

            if (transaction.options.getGasLimit() == null)
                transaction.options.setGasLimit(gasLimit);
            return requestExecute(transaction.toRequestExecute(mainContractAddress));
        }
        L1BridgeContracts bridgeContracts = getL1BridgeContracts();
        IL1Bridge bridge = bridgeContracts.erc20L1Bridge;
        if (transaction.bridgeAddress == null){
            String l2WethAddress = ZkSyncAddresses.ETH_ADDRESS;
            try{
                l2WethAddress = bridgeContracts.wethL1Bridge.l2TokenAddress(transaction.tokenAddress).sendAsync().join();
                bridge = bridgeContracts.wethL1Bridge;
            }catch (Exception e){}
            transaction.bridgeAddress = !Objects.equals(l2WethAddress, ZkSyncAddresses.ETH_ADDRESS) ? bridgeContracts.wethL1Bridge.getContractAddress() : bridgeContracts.erc20L1Bridge.getContractAddress();
        }
        if (transaction.approveERC20 != null && transaction.approveERC20){
            BigInteger allowance = getAllowanceL1(transaction.tokenAddress, transaction.bridgeAddress).join();
            if (transaction.amount.compareTo(allowance) > 0){
                approveERC20(transaction.tokenAddress, transaction.amount, transaction.bridgeAddress).join();
            }
        }

        String data = bridge.encodeDeposit(signer.getAddress(), transaction.tokenAddress, transaction.amount, transaction.l2GasLimit, transaction.gasPerPubdataByte, transaction.refoundRecepient, transaction.options.getValue());
        Transaction a = transaction.toTx(signer.getAddress(), data);
        BigInteger baseGasLimit = providerL1.ethEstimateGas(a).sendAsync().join().getAmountUsed();
        BigInteger gasLimit = scaleGasLimit(baseGasLimit);
        RawTransaction tx = RawTransaction.createTransaction(transaction.options.getChainId().longValue(), transaction.options.getNonce(), gasLimit, bridge.getContractAddress(), transaction.options.getValue(), data, transaction.options.getMaxPriorityFeePerGas(), transaction.options.getMaxFeePerGas());
        byte[] message = TransactionEncoder.signMessage(tx, credentials);
        return providerL1.ethSendRawTransaction(Numeric.toHexString(message));
    }

    public BigInteger estimateGasDeposit(DepositTransaction transaction){
        transaction = getDepositTransaction(transaction);
        BigInteger baseGasLimit = BigInteger.ZERO;
        if (Objects.equals(transaction.tokenAddress, ZkSyncAddresses.ETH_ADDRESS)){
            String address = providerL2.zksMainContract().sendAsync().join().getResult();
            baseGasLimit = estimateGasRequestExecute(new RequestExecuteTransaction(transaction.l2GasLimit, address, transaction.customBridgeData, transaction.amount, null, transaction.operatorTip, transaction.gasPerPubdataByte, transaction.refoundRecepient, transaction.options)).sendAsync().join().getAmountUsed();
        } else {
            L1BridgeContracts l1BridgeContracts = getL1BridgeContracts();
            String l2WethToken = ZkSyncAddresses.ETH_ADDRESS;
            try {
                l2WethToken = l1BridgeContracts.wethL1Bridge.l2TokenAddress(transaction.tokenAddress).sendAsync().join();
            } catch (Exception e) {}

            IL1Bridge l1Bridge = !Objects.equals(l2WethToken, ZkSyncAddresses.ETH_ADDRESS) ? l1BridgeContracts.wethL1Bridge : l1BridgeContracts.erc20L1Bridge;
            baseGasLimit = providerL1.ethEstimateGas(transaction.toFunctionCallTx(signer.getAddress(), l1Bridge)).sendAsync().join().getAmountUsed();

        }
        return scaleGasLimit(baseGasLimit);
    }

    public FullDepositFee getFullRequiredDepositFee(DepositTransaction transaction){
        L1BridgeContracts l1BridgeContracts = getL1BridgeContracts();
        transaction.amount = BigInteger.valueOf(1);

        transaction.options = insertGasPriceInTransactionOptions(transaction.options, providerL1);

        BigInteger gasPriceForEstimation = transaction.options.getMaxFeePerGas() != null ? transaction.options.getMaxFeePerGas() : transaction.options.getGasPrice();

        transaction.to = transaction.to == null ? signer.getAddress() : transaction.to;
        transaction.gasPerPubdataByte = transaction.gasPerPubdataByte == null ? L1_TO_L2_GAS_PER_PUBDATA : transaction.gasPerPubdataByte;
        if (transaction.bridgeAddress != null){
            String customBridgeData = transaction.customBridgeData == null ?
                    (transaction.bridgeAddress.equals(l1BridgeContracts.wethL1Bridge.getContractAddress()) ? "0x" :
                            getERC20DefaultBridgeData(transaction.tokenAddress, providerL1, credentials, gasProvider))
                    : Numeric.toHexString(transaction.customBridgeData);

        } else {
            transaction.l2GasLimit = estimateDefaultBridgeDepositL2Gas(
                    transaction.tokenAddress, transaction.amount, transaction.to, providerL2, getL1BridgeContracts(), providerL1, credentials,gasProvider, signer.getAddress(), transaction.gasPerPubdataByte);
        }

        BigInteger baseCost = contract.l2TransactionBaseCost(gasPriceForEstimation, transaction.l2GasLimit, transaction.gasPerPubdataByte).sendAsync().join();

        BigInteger balance = getBalanceL1().sendAsync().join();

        if (baseCost.compareTo(balance.add(transaction.amount)) >= 0){
            BigInteger recommendedETHBalance = (Objects.equals(transaction.tokenAddress, ZkSyncAddresses.ETH_ADDRESS) ?
                    L1_RECOMMENDED_MIN_ETH_DEPOSIT_GAS_LIMIT : L1_RECOMMENDED_MIN_ERC20_DEPOSIT_GAS_LIMIT).multiply(
                            gasPriceForEstimation).multiply(
                                    baseCost
            );
            throw new Error("Not enough balance for deposit. Under the provided gas price, the recommended balance to perform a deposit is " + recommendedETHBalance + " ETH");
        }

        if (!Objects.equals(transaction.tokenAddress, ZkSyncAddresses.ETH_ADDRESS) && getAllowanceL1(transaction.tokenAddress).join().compareTo(transaction.amount) < 0){
            throw new Error("Not enough allowance to cover the deposit");
        }

        FullDepositFee fullFee = new FullDepositFee();
        if (transaction.options.getGasPrice() != null){
            fullFee.gasPrice = transaction.options.getGasPrice();
        }else{
            fullFee.maxFeePerGas = transaction.options.getMaxFeePerGas();
            fullFee.maxPriorityFeePerGas = transaction.options.getMaxPriorityFeePerGas();
        }

        transaction.options.setGasPrice(null);
        transaction.options.setMaxPriorityFeePerGas(null);
        transaction.options.setMaxFeePerGas(null);

        fullFee.l1GasLimit = estimateGasDeposit(transaction);
        fullFee.baseCost = baseCost;
        fullFee.l2GasLimit = transaction.l2GasLimit;

        return fullFee;
    }
    public DepositTransaction getDepositTransaction(DepositTransaction transaction){
        L1BridgeContracts l1BridgeContracts = getL1BridgeContracts();

        l1BridgeContracts.erc20L1Bridge = transaction.bridgeAddress != null ? IL1Bridge.load(transaction.bridgeAddress, providerL1, credentials, gasProvider) : l1BridgeContracts.erc20L1Bridge;
        transaction.to = transaction.to == null ? signer.getAddress() : transaction.to;
        transaction.refoundRecepient = transaction.refoundRecepient == null ? signer.getAddress() : transaction.refoundRecepient;
        transaction.operatorTip = transaction.operatorTip == null ? BigInteger.ZERO : transaction.operatorTip;
        transaction.gasPerPubdataByte = transaction.gasPerPubdataByte == null ? L1_TO_L2_GAS_PER_PUBDATA : transaction.gasPerPubdataByte;
        transaction.customBridgeData = transaction.customBridgeData == null ? Numeric.hexStringToByteArray("0x") : transaction.customBridgeData;
        if (transaction.bridgeAddress != null){
            String customBridgeData = transaction.customBridgeData == null ?
                    (Objects.equals(transaction.bridgeAddress, l1BridgeContracts.wethL1Bridge.getContractAddress()) ? "0x" :
                            getERC20DefaultBridgeData(transaction.tokenAddress, providerL1, credentials, gasProvider))
                    : Numeric.toHexString(transaction.customBridgeData);
            IL1Bridge bridge = IL1Bridge.load(transaction.tokenAddress, providerL1, transactionManager, gasProvider);
            String l2Address = bridge.l2Bridge().sendAsync().join();
            if (transaction.l2GasLimit == null){
                transaction.l2GasLimit = estimateCustomBridgeDepositL2Gas(transaction.bridgeAddress, l2Address, transaction.tokenAddress, transaction.amount, transaction.to, customBridgeData, signer.getAddress(), transaction.gasPerPubdataByte, transaction.options.getValue(), providerL2);
            }
        } else {
            transaction.l2GasLimit = estimateDefaultBridgeDepositL2Gas(
                    transaction.tokenAddress, transaction.amount, transaction.to, providerL2, getL1BridgeContracts(), providerL1, credentials, gasProvider, signer.getAddress(), transaction.gasPerPubdataByte);
        }
        transaction.options = insertGasPriceInTransactionOptions(transaction.options, providerL1);
        transaction.options.setChainId(transaction.options.getChainId() == null ? providerL1.ethChainId().sendAsync().join().getChainId() : transaction.options.getChainId());
        transaction.options.setNonce(transaction.options.getNonce() == null ? providerL1.ethGetTransactionCount(signer.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().join().getTransactionCount() : transaction.options.getNonce());
        BigInteger gasPriceForEstimation = transaction.options.getMaxFeePerGas() == null ? transaction.options.getGasPrice() : transaction.options.getMaxFeePerGas();
        BigInteger baseCost = contract.l2TransactionBaseCost(gasPriceForEstimation, transaction.l2GasLimit, transaction.gasPerPubdataByte).sendAsync().join();

        if (Objects.equals(transaction.tokenAddress, ZkSyncAddresses.ETH_ADDRESS)){
            if (transaction.options.getValue() == null){
                transaction.options.setValue(baseCost.add(transaction.amount.add(transaction.operatorTip)));
            }
            return transaction;
        }
        if (transaction.options.getValue() == null){
            transaction.options.setValue(baseCost.add(transaction.operatorTip));
        }

        checkBaseCost(baseCost, transaction.options.getValue());

        return transaction;
    }

    public RemoteFunctionCall<TransactionReceipt> finalizeWithdraw(String txHash, int index) throws Exception {
        ZkTransactionReceipt receipt = providerL2.zksGetTransactionReceipt(txHash).sendAsync().join().getResult();

        int logIndex = getWithdrawalLogIndex(receipt.getLogs(), index);
        Log log = receipt.getLogs().get(logIndex);

        int l2ToL1LogIndex = getWithdrawalL2ToL1LogIndex(receipt.getl2ToL1Logs(), index);
        L2ToL1MessageProof l2ToL1MessageProof = providerL2.zksGetL2ToL1LogProof(txHash, l2ToL1LogIndex).sendAsync().join().getResult();

        EventValues eventValues = Contract.staticExtractEventParameters(L1MESSAGESENT_EVENT, log);
        byte[] bytes_data = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();

        BigInteger l1BatchNumber = receipt.getL1BatchNumber();

        List<byte[]> merkle_proof = new ArrayList<>();
        for (int i = 0 ; i < l2ToL1MessageProof.getProof().size() ; i++){
            merkle_proof.add(Numeric.hexStringToByteArray(l2ToL1MessageProof.getProof().get(i)));
        }
        byte[] senderBytes = Numeric.hexStringToByteArray(log.getTopics().get(1));
        String sender = Numeric.toHexString(Arrays.copyOfRange(senderBytes, 12, senderBytes.length));

        if (sender.equals(L2_ETH_TOKEN_ADDRESS)){
            return contract.finalizeEthWithdrawal(l1BatchNumber, BigInteger.valueOf(l2ToL1MessageProof.getId()), receipt.getL1BatchTxIndex(), bytes_data, merkle_proof);
        }
        IL2Bridge il2Bridge = IL2Bridge.load(sender, providerL2, credentials, gasProvider);
        String a = il2Bridge.l1Bridge().send();
        IL1Bridge il1Bridge = IL1Bridge.load(il2Bridge.l1Bridge().send(), providerL1, transactionManager, gasProvider);

        return il1Bridge.finalizeWithdrawal(l1BatchNumber, BigInteger.valueOf(l2ToL1MessageProof.getId()), receipt.getL1BatchTxIndex(), bytes_data, merkle_proof);
    }

    public int getWithdrawalLogIndex(List<Log> logs, int index){
        String topic = "L1MessageSent(address,bytes32,bytes)";
        List<Integer> logIndex = new ArrayList<>();

        for (int i = 0 ; i < logs.size() ; i++){
            if (logs.get(i).getAddress().equals(MESSENGER_ADDRESS) && Arrays.equals(logs.get(i).getTopics().get(0).getBytes(), Hash.sha3String(topic).getBytes())){
                logIndex.add(i);
            }
        }

        return logIndex.get(index);
    }

    public int getWithdrawalL2ToL1LogIndex(List<L2toL1Log> logs, int index){
        List<Integer> logIndex = new ArrayList<>();

        for (int i = 0 ; i < logs.size() ; i++){
            if (logs.get(i).getSender().getValue().equals(MESSENGER_ADDRESS)){
                logIndex.add(i);
            }
        }

        return logIndex.get(index);
    }

    public Request<?, EthSendTransaction> requestExecute(RequestExecuteTransaction transaction) throws IOException {
        transaction = getRequestExecuteTransaction(transaction);
        String data = contract.encodeRequestL2Transaction(transaction.getContractAddress(), transaction.getL2Value(), transaction.getCalldata(), transaction.getL2GasLimit(), transaction.getGasPerPubDataByte(), Collections.emptyList(), transaction.getRefoundRecepient(), transaction.getOptions().getValue());
        RawTransaction tx = RawTransaction.createTransaction(transaction.getOptions().getChainId().longValue(), transaction.getOptions().getNonce(), transaction.getOptions().getGasLimit(), transaction.getContractAddress(), transaction.getOptions().getValue(), data, transaction.getOptions().getMaxPriorityFeePerGas(), transaction.getOptions().getMaxFeePerGas());
        byte[] message = TransactionEncoder.signMessage(tx, credentials);
        return providerL1.ethSendRawTransaction(Numeric.toHexString(message));
    }

    public Request<?, EthEstimateGas> estimateGasRequestExecute(RequestExecuteTransaction transaction){
        transaction = getRequestExecuteTransaction(transaction);
        transaction.getOptions().setGasPrice(null);
        transaction.getOptions().setMaxFeePerGas(null);
        transaction.getOptions().setMaxPriorityFeePerGas(null);
        String data = contract.encodeRequestL2Transaction(transaction.getContractAddress(), transaction.getL2Value(), transaction.getCalldata(), transaction.getL2GasLimit(), transaction.getGasPerPubDataByte(), Collections.emptyList(), transaction.getRefoundRecepient(), transaction.getOptions().getValue());
        org.web3j.protocol.core.methods.request.Transaction tr = Transaction.createEthCallTransaction(signer.getAddress(), transaction.getContractAddress(), data, transaction.getOptions().getValue());
        return providerL1.ethEstimateGas(tr);
    }

}
