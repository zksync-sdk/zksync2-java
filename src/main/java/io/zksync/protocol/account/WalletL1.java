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
import io.zksync.utils.WalletUtils;
import io.zksync.utils.ZkSyncAddresses;
import io.zksync.wrappers.*;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.*;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;
import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_FREQUENCY;
import static io.zksync.utils.WalletUtils.*;
import static io.zksync.utils.ZkSyncAddresses.MESSENGER_ADDRESS;
import static io.zksync.wrappers.IBridgehub.FUNC_REQUESTL2TRANSACTIONDIRECT;
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
    protected final IZkSyncHyperchain contract;
    protected final EthSigner signer;
    protected final Credentials credentials;
    public WalletL1(Web3j providerL1, ZkSync providerL2, TransactionManager transactionManager, ContractGasProvider gasProvider, Credentials credentials) {
        this.providerL1 = providerL1;
        this.providerL2 = providerL2;
        this.transactionManager = transactionManager;
        this.gasProvider = gasProvider;
        this.mainContractAddress = providerL2.zksMainContract().sendAsync().join().getResult();
        this.contract = IZkSyncHyperchain.load(providerL2.zksMainContract().sendAsync().join().getResult(), providerL1, transactionManager, gasProvider);
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
    public IZkSyncHyperchain getMainContract(){
        if (contract == null){
            return IZkSyncHyperchain.load(providerL2.zksMainContract().sendAsync().join().getResult(), providerL1, transactionManager, gasProvider);
        }
        return contract;
    }

    public IBridgehub getBridgehubContract(){
        String address = providerL2.zksGetBridgehubContract().sendAsync().join().getResult();
        return IBridgehub.load(address, providerL1, transactionManager, gasProvider);
    }

    /**
     * Get balance of wallet in native coin (wallet address gets from {@link EthSigner})
     *
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalanceL1() {
        return getBalanceL1(signer.getAddress(), ZkSyncAddresses.LEGACY_ETH_ADDRESS, DefaultBlockParameterName.LATEST);
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
        if (ZkSyncAddresses.isEth(token)) {
            return new RemoteCall<>(() ->
                    this.providerL1.ethGetBalance(address, at).sendAsync().join().getBalance());
        } else {
            ERC20 erc20 = ERC20.load(token, this.providerL2, transactionManager, gasProvider);
            return erc20.balanceOf(address);
        }
    }
    public RemoteFunctionCall<TransactionReceipt> approveERC20(String token, BigInteger amount) {
        return approveERC20(token, amount, null);
    }

    public CompletableFuture<BigInteger> getBalanceL1Async(String address, String token, DefaultBlockParameter at) {
        if (ZkSyncAddresses.isEth(token)) {
            return this.providerL1.ethGetBalance(address, at).sendAsync()
                    .thenApply(ethGetBalance -> ethGetBalance.getBalance())
                    .exceptionally(ex -> {
                        System.err.println("Failed to get ETH balance: " + ex.getMessage());
                        return BigInteger.ZERO;
                    });
        } else {
            ERC20 erc20 = ERC20.load(token, this.providerL2, transactionManager, gasProvider);
            return erc20.balanceOf(address).sendAsync()
                    .exceptionally(ex -> {
                        System.err.println("Failed to get ERC20 balance: " + ex.getMessage());
                        return BigInteger.ZERO;
                    });
        }
    }

    /**
     * Bridging ERC20 tokens from L1 requires approving the tokens to the zkSync Era smart contract.
     *
     * @param token         The L1 address of the token.
     * @param amount        The amount of the token to be approved.
     * @param bridgeAddress Bridge address to be used.
     * @throws {Error} If attempting to approve an ETH token.
     * @returns A promise that resolves to the response of the approval transaction.
     */
    public RemoteFunctionCall<TransactionReceipt> approveERC20(String token, @Nullable BigInteger amount, @Nullable String bridgeAddress) {
        if (ZkSyncAddresses.isEth(token)){
            throw new Error("ETH token can't be approved! The address of the token does not exist on L1.");
        }
        ERC20 tokenContract = ERC20.load(token, providerL1, transactionManager,
                gasProvider);

        if (bridgeAddress == null){
            bridgeAddress = providerL2.zksGetBridgeContracts().sendAsync().join().getResult().getL1SharedDefaultBridge();
        }

        return tokenContract.approve(bridgeAddress, amount);
    }

    public RemoteFunctionCall<BigInteger> getAllowanceL1(String token){
        return getAllowanceL1(token, null);
    }

    /**
     * Returns the amount of approved tokens for a specific L1 bridge.
     *
     * @param token         The Ethereum address of the token.
     * @param bridgeAddress The address of the bridge contract to be used.
     *                      Defaults to the default zkSync Era bridge, either `L1EthBridge` or `L1Erc20Bridge`.
     */
    public RemoteFunctionCall<BigInteger> getAllowanceL1(String token, @Nullable String bridgeAddress){
        if (bridgeAddress == null){
            bridgeAddress = providerL2.zksGetBridgeContracts().sendAsync().join().getResult().getL1SharedDefaultBridge();
        }
        ERC20 erc20 = ERC20.load(token, providerL1, transactionManager, gasProvider);
        return erc20.allowance(signer.getAddress(), bridgeAddress);
    }

    /**
     * Returns the L2 token address equivalent for a L1 token address as they are not necessarily equal.
     * The ETH address is set to the zero address.
     *
     * @remarks Only works for tokens bridged on default zkSync Era bridges.
     *
     * @param l1Address The address of the token on L1.
     */
    public String l2TokenAddress(String l1Address){
        return providerL2.l2TokenAddress(l1Address);
    }

    private EthSigner getSigner() {
        return signer;
    }

    /**
     * Returns L1 bridge contracts.
     *
     * @remarks There is no separate Ether bridge contract, {@link } is used instead.
     */
    public L1BridgeContracts getL1BridgeContracts(){
        BridgeAddresses bridgeAddresses = providerL2.zksGetBridgeContracts().sendAsync().join().getResult();
        return new L1BridgeContracts(bridgeAddresses.getL1Erc20DefaultBridge(),bridgeAddresses.getL1SharedDefaultBridge(), bridgeAddresses.getL1SharedDefaultBridge(), providerL1, transactionManager, gasProvider);
    }

    /**
     * Returns the address of the base token on L1.
     */
    public RemoteCall<String> getBaseToken(){
        IBridgehub bridgehub = getBridgehubContract();
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();
        return bridgehub.baseToken(chainId);
    }

    /**
     * Returns whether the chain is ETH-based.
     */
    public boolean isETHBasedChain(){
        return providerL2.isEthBasedChain();
    }

    public RemoteFunctionCall<BigInteger> getBaseCost(BigInteger gasLimit) {
        return getBaseCost(gasLimit, null, null);
    }

    /**
     * Returns the base cost for an L2 transaction.
     *
     * @param gasLimit The gasLimit for the L2 contract call.
     * @param gasPerPubdataByte The L2 gas price for each published L1 calldata byte.
     * @param gasPrice The L1 gas price of the L1 transaction that will send the request for an execute call.
     */
    public RemoteFunctionCall<BigInteger> getBaseCost(BigInteger gasLimit, @Nullable BigInteger gasPerPubdataByte, @Nullable BigInteger gasPrice) {
        IBridgehub bridgehub = getBridgehubContract();
        if (gasPrice == null){
            gasPrice = providerL1.ethGasPrice().sendAsync().join().getGasPrice();
        }
        if (gasPerPubdataByte == null){
            gasPerPubdataByte = L1_TO_L2_GAS_PER_PUBDATA;
        }

        return bridgehub.l2TransactionBaseCost(providerL2.ethChainId().sendAsync().join().getChainId() ,gasPrice, gasLimit, gasPerPubdataByte);
    }

    public List<AllowanceParams> getDepositAllowanceParams(String tokenAddress, BigInteger amount) throws Exception {
        if (tokenAddress.equalsIgnoreCase(ZkSyncAddresses.LEGACY_ETH_ADDRESS)){
            tokenAddress = ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS;
        }

        String baseTokenAddress = getBaseToken().sendAsync().join();
        boolean isEthBasedChain = isETHBasedChain();

        List<AllowanceParams> result = new ArrayList<>();
        if (isEthBasedChain && tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            throw new Error("ETH token can't be approved! The address of the token does not exist on L1.");
        }else if (baseTokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            result.add(new AllowanceParams(tokenAddress, amount));
            return result;
        } else if (tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)) {
            result.add(new AllowanceParams(baseTokenAddress,
                    _getDepositETHOnNonETHBasedChainTx(
                            new DepositTransaction(tokenAddress, amount)).mintValue));
            return result;
        } else if (tokenAddress.equalsIgnoreCase(baseTokenAddress)) {
            result.add(new AllowanceParams(baseTokenAddress,
                    _getDepositBaseTokenOnNonETHBasedChainTx(
                            new DepositTransaction(tokenAddress, amount)).mintValue));
            return result;
        }
        // A deposit of a non-base token to a non-ETH-based chain requires two approvals.
        result.add(new AllowanceParams(baseTokenAddress,
                _getDepositNonBaseTokenToNonETHBasedChain(
                        new DepositTransaction(tokenAddress, amount)).mintValue));
        result.add(new AllowanceParams(tokenAddress, amount));
        return result;
    }

    public RawTransaction getRequestExecuteTransaction(RequestExecuteTransaction transaction){
        boolean isEthBasedChain = isETHBasedChain();
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

        BigInteger baseCost = getBaseCost(transaction.getL2GasLimit(), transaction.getGasPerPubDataByte(), gasPriceForestimation).sendAsync().join();
        BigInteger l2Cost = baseCost.add(transaction.operatorTip.add(transaction.l2Value));
        BigInteger providedValue = isEthBasedChain  ? transaction.options.value : transaction.mintValue;

        if (transaction.options.value == null || transaction.options.value.equals(BigInteger.ZERO)){
            providedValue = l2Cost;
            if (isEthBasedChain){
                transaction.options.value = providedValue;
            }
        }

        checkBaseCost(baseCost, providedValue);

        IBridgehub.L2TransactionRequestDirect request = new IBridgehub.L2TransactionRequestDirect(
                providerL2.ethChainId().sendAsync().join().getChainId(),
                providedValue,
                transaction.getContractAddress(),
                transaction.getL2Value(),
                transaction.getCalldata(),
                transaction.getL2GasLimit(),
                transaction.getGasPerPubDataByte(),
                Collections.emptyList(),
                transaction.getRefoundRecepient());
        final Function function = new Function(
                FUNC_REQUESTL2TRANSACTIONDIRECT,
                Arrays.<Type>asList(request),
                Collections.<TypeReference<?>>emptyList());
        String data = FunctionEncoder.encode(function);

        return RawTransaction.createTransaction(transaction.getOptions().getChainId().longValue(), transaction.getOptions().getNonce(), transaction.getOptions().getGasLimit(), getBridgehubContract().getContractAddress(), transaction.getOptions().getValue(), data, transaction.getOptions().getMaxPriorityFeePerGas(), transaction.getOptions().getMaxFeePerGas());
    }

    public Request<?, EthSendTransaction> deposit(DepositTransaction transaction) throws Exception {
        if (transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.LEGACY_ETH_ADDRESS)){
            transaction.tokenAddress = ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS;
        }

        String baseTokenAddress = getBaseToken().sendAsync().join();
        boolean isEthBasedChain = baseTokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS);

        if (isEthBasedChain && transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            return _depositETHToETHBasedChain(transaction);
        }else if (baseTokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            return _depositTokenToETHBasedChain(transaction);
        }else if (transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            return _depositETHToNonETHBasedChain(transaction);
        } else if (transaction.tokenAddress.equalsIgnoreCase(baseTokenAddress)) {
            return _depositBaseTokenToNonETHBasedChain(transaction);
        }
        return _depositNonBaseTokenToNonETHBasedChain(transaction);
    }

    private Request<?, EthSendTransaction> _depositNonBaseTokenToNonETHBasedChain(DepositTransaction transaction) {
        String baseTokenAddress = getBaseToken().sendAsync().join();
        L1BridgeContracts bridgeContracts = getL1BridgeContracts();

        GetDepositTransaction depositTransaction = _getDepositNonBaseTokenToNonETHBasedChain(transaction);
        Transaction tx = depositTransaction.tx;
        BigInteger mintValue = depositTransaction.mintValue;

        if (transaction.approveBaseERC20){
            BigInteger allowance = getAllowanceL1(baseTokenAddress, bridgeContracts.sharedL1Bridge.getContractAddress()).sendAsync().join();

            if (allowance.compareTo(mintValue) < 0){
                approveERC20(
                        baseTokenAddress, mintValue, bridgeContracts.sharedL1Bridge.getContractAddress()).sendAsync().join();
            }
        }
        if (transaction.approveERC20){
            String bridgeAddress = transaction.bridgeAddress == null || transaction.bridgeAddress.isEmpty()
                    ? bridgeContracts.sharedL1Bridge.getContractAddress()
                    : transaction.bridgeAddress;
            BigInteger allowance = getAllowanceL1(transaction.tokenAddress, bridgeAddress).sendAsync().join();

            if (allowance.compareTo(mintValue) < 0){
                approveERC20(
                        transaction.tokenAddress, transaction.amount, bridgeAddress).sendAsync().join();
            }
        }

        BigInteger gasLimit = tx.getGas() != null ? Numeric.toBigInt(tx.getGas()) : BigInteger.ZERO;
        if (gasLimit.equals(BigInteger.ZERO)){
            BigInteger baseGasLimit = providerL1.ethEstimateGas(tx).sendAsync().join().getAmountUsed();
            gasLimit = scaleGasLimit(baseGasLimit);
        }

        BigInteger nonceToUse = tx.getNonce() == null
                ? providerL1.ethGetTransactionCount(credentials.getAddress(),
                DefaultBlockParameterName.LATEST).sendAsync().join().getTransactionCount()
                : Numeric.toBigInt(tx.getNonce());

        RawTransaction prepared = RawTransaction.createTransaction(
                Numeric.toBigInt(tx.getChainId()).longValue(),
                nonceToUse,
                gasLimit,
                tx.getTo(),
                Numeric.toBigInt(tx.getValue()),
                tx.getData(),
                Numeric.toBigInt(tx.getMaxPriorityFeePerGas()),
                Numeric.toBigInt(tx.getMaxFeePerGas()));
        byte[] message = TransactionEncoder.signMessage(prepared, credentials);
        return providerL1.ethSendRawTransaction(Numeric.toHexString(message));
    }

    private Request<?, EthSendTransaction> _depositBaseTokenToNonETHBasedChain(DepositTransaction transaction) {
        IBridgehub bridgehub = getBridgehubContract();
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();
        String baseTokenAddress = getBaseToken().sendAsync().join();
        IL1Bridge sharedL1Bridge = getL1BridgeContracts().sharedL1Bridge;

        GetDepositTransaction depositTransaction = _getDepositBaseTokenOnNonETHBasedChainTx(transaction);
        RequestExecuteTransaction tx = depositTransaction.requestExecuteTransaction;
        BigInteger mintValue = depositTransaction.mintValue;

        if (transaction.approveERC20 || transaction.approveBaseERC20){
            BigInteger allowance = getAllowanceL1(baseTokenAddress, sharedL1Bridge.getContractAddress()).sendAsync().join();

            if (allowance.compareTo(mintValue) < 0){
                approveERC20(
                        baseTokenAddress, mintValue, sharedL1Bridge.getContractAddress()).sendAsync().join();
            }
        }

        if (tx.options.gasLimit == null){
            BigInteger baseGasLimit = estimateGasRequestExecute(tx).sendAsync().join().getAmountUsed();
            tx.options.gasLimit = scaleGasLimit(baseGasLimit);
        }

        return requestExecute(tx);
    }

    private Request<?, EthSendTransaction> _depositETHToNonETHBasedChain(DepositTransaction transaction) throws Exception {
        String baseTokenAddress = getBaseToken().sendAsync().join();
        IL1Bridge sharedL1Bridge = getL1BridgeContracts().sharedL1Bridge;

        GetDepositTransaction depositTransaction = _getDepositETHOnNonETHBasedChainTx(transaction);
        Transaction tx = depositTransaction.tx;
        BigInteger mintValue = depositTransaction.mintValue;

        if (transaction.approveBaseERC20){
            String proposedBridge = sharedL1Bridge.getContractAddress();
            String bridgeAddress = transaction.bridgeAddress == null || transaction.bridgeAddress.isEmpty()
                    ? proposedBridge
                    : transaction.bridgeAddress;

            BigInteger allowance = getAllowanceL1(baseTokenAddress, bridgeAddress).sendAsync().join();

            if (allowance.compareTo(mintValue) < 0){
                approveERC20(
                        baseTokenAddress, mintValue, bridgeAddress).sendAsync().join();
            }
        }

        BigInteger gasLimit = tx.getGas() != null ? Numeric.toBigInt(tx.getGas()) : BigInteger.ZERO;
        if (gasLimit.equals(BigInteger.ZERO)){
            BigInteger baseGasLimit = providerL1.ethEstimateGas(tx).sendAsync().join().getAmountUsed();
            gasLimit = scaleGasLimit(baseGasLimit);
        }

        BigInteger nonceToUse = tx.getNonce() == null
                ? providerL1.ethGetTransactionCount(credentials.getAddress(),
                DefaultBlockParameterName.LATEST).sendAsync().join().getTransactionCount()
                : Numeric.toBigInt(tx.getNonce());

        RawTransaction prepared = RawTransaction.createTransaction(
                Numeric.toBigInt(tx.getChainId()).longValue(),
                nonceToUse,
                gasLimit,
                tx.getTo(),
                Numeric.toBigInt(tx.getValue()),
                tx.getData(),
                Numeric.toBigInt(tx.getMaxPriorityFeePerGas()),
                Numeric.toBigInt(tx.getMaxFeePerGas()));
        byte[] message = TransactionEncoder.signMessage(prepared, credentials);
        return providerL1.ethSendRawTransaction(Numeric.toHexString(message));
    }

    private Request<?, EthSendTransaction> _depositTokenToETHBasedChain(DepositTransaction transaction) {
        L1BridgeContracts bridgeContracts = getL1BridgeContracts();
        Transaction tx = _getDepositTokenOnETHBasedChainTx(transaction);

        if (transaction.approveERC20){
            String proposedBridge = bridgeContracts.sharedL1Bridge.getContractAddress();
            String bridgeAddress = transaction.bridgeAddress == null || transaction.bridgeAddress.isEmpty()
                    ? proposedBridge
                    : transaction.bridgeAddress;

            BigInteger allowance = getAllowanceL1(transaction.tokenAddress, bridgeAddress).sendAsync().join();

            if (allowance.compareTo(transaction.amount) < 0){
                approveERC20(
                        transaction.tokenAddress, transaction.amount, bridgeAddress).sendAsync().join();
            }
        }

        BigInteger gasLimit = tx.getGas() != null ? Numeric.toBigInt(tx.getGas()) : BigInteger.ZERO;
        if (gasLimit.equals(BigInteger.ZERO)){
            BigInteger baseGasLimit = providerL1.ethEstimateGas(tx).sendAsync().join().getAmountUsed();
            gasLimit = scaleGasLimit(baseGasLimit);
        }

        BigInteger nonceToUse = tx.getNonce() == null
                ? providerL1.ethGetTransactionCount(credentials.getAddress(),
                                                    DefaultBlockParameterName.LATEST).sendAsync().join().getTransactionCount()
                : Numeric.toBigInt(tx.getNonce());

        RawTransaction prepared = RawTransaction.createTransaction(
                Numeric.toBigInt(tx.getChainId()).longValue(),
                nonceToUse,
                gasLimit,
                tx.getTo(),
                Numeric.toBigInt(tx.getValue()),
                tx.getData(),
                Numeric.toBigInt(tx.getMaxPriorityFeePerGas()),
                Numeric.toBigInt(tx.getMaxFeePerGas()));
        byte[] message = TransactionEncoder.signMessage(prepared, credentials);
        return providerL1.ethSendRawTransaction(Numeric.toHexString(message));
    }

    private Request<?, EthSendTransaction> _depositETHToETHBasedChain(DepositTransaction transaction) {
        RequestExecuteTransaction tx = _getDepositEthOnEthBasedChain(transaction);

        if (tx.options.gasLimit == null){
            BigInteger baseGasLimit = estimateGasRequestExecute(tx).sendAsync().join().getAmountUsed();
            tx.options.gasLimit = scaleGasLimit(baseGasLimit);
        }

        return requestExecute(tx);
    }

    private GetDepositTransaction _getDepositNonBaseTokenToNonETHBasedChain(DepositTransaction transaction) {
        IBridgehub bridgehub = getBridgehubContract();
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();
        L1BridgeContracts bridgeContracts = getL1BridgeContracts();

        _getDepositTxWithDefaults(transaction);

        BigInteger gasPriceForestimation = transaction.options.maxFeePerGas != null ?
                transaction.options.maxFeePerGas : transaction.options.getGasPrice();
        BigInteger baseCost = getBaseCost(
                transaction.l2GasLimit,
                transaction.gasPerPubdataByte,
                gasPriceForestimation).sendAsync().join();

        BigInteger mintValue = baseCost.add(transaction.operatorTip);
        checkBaseCost(baseCost, mintValue);
        transaction.options.value = BigInteger.ZERO;

        Function f = new Function(null,
                Arrays.asList(new Address(transaction.tokenAddress), new Uint256(transaction.amount), new Address(transaction.to)),
                Collections.emptyList());
        String secondBridgeCalldata = Numeric.prependHexPrefix(FunctionEncoder.encode(f));

        String calldata = bridgehub.encodeRequestL2TransactionTwoBridges(
                new IBridgehub.L2TransactionRequestTwoBridgesOuter(
                        chainId,
                        mintValue,
                        BigInteger.ZERO,
                        transaction.l2GasLimit,
                        transaction.gasPerPubdataByte,
                        transaction.refoundRecepient,
                        bridgeContracts.sharedL1Bridge.getContractAddress(),
                        BigInteger.ZERO,
                        Numeric.hexStringToByteArray(secondBridgeCalldata)));

        Transaction tx =  new Transaction(
                credentials.getAddress(),
                transaction.options.nonce,
                null,
                transaction.options.gasLimit,
                bridgehub.getContractAddress(),
                BigInteger.ZERO,
                calldata,
                providerL1.ethChainId().sendAsync().join().getChainId().longValue(),
                transaction.options.maxPriorityFeePerGas,
                transaction.options.maxFeePerGas);

        return new GetDepositTransaction(tx, mintValue, null);
    }

    private GetDepositTransaction _getDepositBaseTokenOnNonETHBasedChainTx(DepositTransaction transaction) {
        IBridgehub bridgehub = getBridgehubContract();
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();

        _getDepositTxWithDefaults(transaction);

        BigInteger gasPriceForestimation = transaction.options.maxFeePerGas != null ?
                transaction.options.maxFeePerGas : transaction.options.getGasPrice();
        BigInteger baseCost = getBaseCost(
                transaction.l2GasLimit,
                transaction.gasPerPubdataByte,
                gasPriceForestimation).sendAsync().join();

        transaction.options.value = BigInteger.ZERO;
        BigInteger mintValue = baseCost.add(transaction.operatorTip.add(transaction.amount));
        return new GetDepositTransaction(null, mintValue, transaction.toRequestExecute(transaction.to));
    }

    private GetDepositTransaction _getDepositETHOnNonETHBasedChainTx(DepositTransaction transaction) throws Exception {
        IBridgehub bridgehub = getBridgehubContract();
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();
        IL1Bridge sharedBridge = getL1BridgeContracts().sharedL1Bridge;

        _getDepositTxWithDefaults(transaction);

        BigInteger gasPriceForestimation = transaction.options.maxFeePerGas != null ?
                transaction.options.maxFeePerGas : transaction.options.getGasPrice();
        BigInteger baseCost = getBaseCost(
                transaction.l2GasLimit,
                transaction.gasPerPubdataByte,
                gasPriceForestimation).sendAsync().join();

        transaction.options.value = transaction.options.value != null ? transaction.options.value : transaction.amount;
        BigInteger mintValue = baseCost.add(transaction.operatorTip);
        checkBaseCost(baseCost, mintValue);

        Function f = new Function(null,
                Arrays.asList(new Address(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS), new Uint256(BigInteger.ZERO), new Address(transaction.to)),
                Collections.emptyList());
        String secondBridgeCalldata = Numeric.prependHexPrefix(FunctionEncoder.encode(f));

        String calldata = bridgehub.encodeRequestL2TransactionTwoBridges(
                new IBridgehub.L2TransactionRequestTwoBridgesOuter(
                        chainId,
                        mintValue,
                        BigInteger.ZERO,
                        transaction.l2GasLimit,
                        transaction.gasPerPubdataByte,
                        transaction.refoundRecepient,
                        sharedBridge.getContractAddress(),
                        transaction.amount,
                        Numeric.hexStringToByteArray(secondBridgeCalldata)));
        BigInteger a = getBalanceL1().send();
        String aaa = bridgehub.requestL2TransactionTwoBridges(
                new IBridgehub.L2TransactionRequestTwoBridgesOuter(
                        chainId,
                        mintValue,
                        BigInteger.ZERO,
                        transaction.l2GasLimit,
                        transaction.gasPerPubdataByte,
                        transaction.refoundRecepient,
                        sharedBridge.getContractAddress(),
                        transaction.amount,
                        Numeric.hexStringToByteArray(secondBridgeCalldata)), BigInteger.ZERO).encodeFunctionCall();
        BigInteger b = getBalanceL1().send();

        Transaction tx =  new Transaction(
                credentials.getAddress(),
                transaction.options.nonce,
                null,
                transaction.options.gasLimit,
                bridgehub.getContractAddress(),
                transaction.amount,
                calldata,
                providerL1.ethChainId().sendAsync().join().getChainId().longValue(),
                transaction.options.maxPriorityFeePerGas,
                transaction.options.maxFeePerGas);

        return new GetDepositTransaction(tx, mintValue, null);
    }

    private Transaction _getDepositTokenOnETHBasedChainTx(DepositTransaction transaction){
        IBridgehub bridgehub = getBridgehubContract();
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();

        _getDepositTxWithDefaults(transaction);

        BigInteger gasPriceForestimation = transaction.options.maxFeePerGas != null ?
                transaction.options.maxFeePerGas : transaction.options.getGasPrice();
        BigInteger baseCost = getBaseCost(
                transaction.l2GasLimit,
                transaction.gasPerPubdataByte,
                gasPriceForestimation).sendAsync().join();

        BigInteger mintValue = baseCost.add(transaction.operatorTip);
        transaction.options.value = transaction.options.value != null ? transaction.options.value : mintValue;
        checkBaseCost(baseCost, mintValue);

        String secondBridgeAddress;
        String secondBridgeCalldata;
        if (transaction.bridgeAddress != null){
            secondBridgeAddress = transaction.bridgeAddress;
            secondBridgeCalldata = getERC20DefaultBridgeData(transaction.tokenAddress, providerL1, credentials, gasProvider);
        }else{
            secondBridgeAddress = getL1BridgeContracts().sharedL1Bridge.getContractAddress();
            Function f = new Function(null,
                    Arrays.asList(new Address(transaction.tokenAddress), new Uint256(transaction.amount), new Address(transaction.to)),
                    Collections.emptyList());
            secondBridgeCalldata = Numeric.prependHexPrefix(FunctionEncoder.encode(f));
        }

        String calldata = bridgehub.encodeRequestL2TransactionTwoBridges(
                new IBridgehub.L2TransactionRequestTwoBridgesOuter(
                        chainId,
                        mintValue,
                        BigInteger.ZERO,
                        transaction.l2GasLimit,
                        transaction.gasPerPubdataByte,
                        transaction.refoundRecepient,
                        secondBridgeAddress,
                        BigInteger.ZERO,
                        Numeric.hexStringToByteArray(secondBridgeCalldata)));
        return new Transaction(
                credentials.getAddress(),
                transaction.options.nonce,
                null,
                transaction.options.gasLimit,
                bridgehub.getContractAddress(),
                mintValue,
                calldata,
                providerL1.ethChainId().sendAsync().join().getChainId().longValue(),
                transaction.options.maxPriorityFeePerGas,
                transaction.options.maxFeePerGas);
    }

    private RequestExecuteTransaction _getDepositEthOnEthBasedChain(DepositTransaction transaction){
        IBridgehub bridgehub = getBridgehubContract();
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();

        _getDepositTxWithDefaults(transaction);

        BigInteger gasPriceForestimation = transaction.options.getMaxFeePerGas() != null ?
                transaction.options.getMaxFeePerGas() : transaction.options.getGasPrice();
        BigInteger baseCost = getBaseCost(transaction.l2GasLimit, transaction.gasPerPubdataByte, gasPriceForestimation).sendAsync().join();

        transaction.options.setValue(baseCost.add(transaction.operatorTip.add(transaction.amount)));

        return transaction.toRequestExecute(transaction.to);
    }

    private DepositTransaction _getDepositTxWithDefaults(DepositTransaction transaction){
        transaction.to = transaction.to == null ? signer.getAddress() : transaction.to;
        transaction.refoundRecepient = transaction.refoundRecepient == null ? signer.getAddress() : transaction.refoundRecepient;
        transaction.operatorTip = transaction.operatorTip == null ? BigInteger.ZERO : transaction.operatorTip;
        transaction.gasPerPubdataByte = transaction.gasPerPubdataByte == null ? L1_TO_L2_GAS_PER_PUBDATA : transaction.gasPerPubdataByte;
        transaction.options = transaction.options == null ? new TransactionOptions() : transaction.options;
        transaction.l2GasLimit = transaction.l2GasLimit == null ? _getL2GasLimit(transaction) : transaction.l2GasLimit;

        insertGasPriceInTransactionOptions(transaction.options, providerL1);

        return transaction;
    }

    private BigInteger _getL2GasLimit(DepositTransaction transaction){
        if (transaction.bridgeAddress != null){
            return _getL2GasLimitFromCustomBridge(transaction);
        }
        return estimateDefaultBridgeDepositL2Gas(
                transaction.tokenAddress, transaction.amount, transaction.to, providerL2, providerL1, credentials,gasProvider, signer.getAddress(), transaction.gasPerPubdataByte);
    }

    private BigInteger _getL2GasLimitFromCustomBridge(DepositTransaction transaction){
        String bridgeData = transaction.customBridgeData != null ?
                Numeric.toHexString(transaction.customBridgeData) :
                getERC20DefaultBridgeData(transaction.tokenAddress, providerL1, credentials, gasProvider);

        IL1Bridge bridge = IL1Bridge.load(transaction.tokenAddress, providerL1, credentials, gasProvider);
        BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();
        String l2Address = bridge.l2BridgeAddress(chainId).sendAsync().join();

        return WalletUtils.estimateCustomBridgeDepositL2Gas(
                transaction.bridgeAddress,
                l2Address,
                transaction.tokenAddress,
                transaction.amount,
                transaction.to,
                bridgeData,
                credentials.getAddress(),
                transaction.gasPerPubdataByte,
                BigInteger.ZERO,
                providerL2
        );
    }

    public BigInteger estimateGasDeposit(DepositTransaction transaction) throws Exception {
        if (transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.LEGACY_ETH_ADDRESS)){
            transaction.tokenAddress = ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS;
        }

        String baseTokenAddress = getBaseToken().sendAsync().join();
        boolean isEthBasedChain = baseTokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS);

        BigInteger baseGasLimit = BigInteger.ZERO;
        if (isEthBasedChain && transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            RequestExecuteTransaction tx = _getDepositEthOnEthBasedChain(transaction);
            baseGasLimit = estimateGasRequestExecute(tx).sendAsync().join().getAmountUsed();
        }else if (baseTokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            Transaction tx = _getDepositTokenOnETHBasedChainTx(transaction);
            baseGasLimit = providerL1.ethEstimateGas(tx).sendAsync().join().getAmountUsed();
        }else if (transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            Transaction tx = _getDepositETHOnNonETHBasedChainTx(transaction).tx;
            baseGasLimit = providerL1.ethEstimateGas(tx).sendAsync().join().getAmountUsed();
        } else if (transaction.tokenAddress.equalsIgnoreCase(baseTokenAddress)) {
            RequestExecuteTransaction tx = _getDepositBaseTokenOnNonETHBasedChainTx(transaction).requestExecuteTransaction;
            baseGasLimit = estimateGasRequestExecute(tx).sendAsync().join().getAmountUsed();
        }else{
            Transaction tx = _getDepositNonBaseTokenToNonETHBasedChain(transaction).tx;
            baseGasLimit = providerL1.ethEstimateGas(tx).sendAsync().join().getAmountUsed();
        }
        return scaleGasLimit(baseGasLimit);
    }

    public RemoteCall<FullDepositFee> getFullRequiredDepositFee(DepositTransaction transaction){
        return new RemoteCall<>(() -> {
            if (transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.LEGACY_ETH_ADDRESS)){
                transaction.tokenAddress = ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS;
            }
            transaction.amount = BigInteger.valueOf(1);
            IBridgehub bridgehub = getBridgehubContract();
            String baseTokenAddress = getBaseToken().sendAsync().join();
            BigInteger chainId = providerL2.ethChainId().sendAsync().join().getChainId();
            boolean isEthBasedChain = isETHBasedChain();

            _getDepositTxWithDefaults(transaction);

            BigInteger gasPriceForestimation = transaction.options.maxFeePerGas != null ?
                    transaction.options.maxFeePerGas : transaction.options.getGasPrice();
            BigInteger baseCost = getBaseCost(
                    transaction.l2GasLimit,
                    transaction.gasPerPubdataByte,
                    gasPriceForestimation).sendAsync().join();

            if (isEthBasedChain){
                BigInteger selfBalanceEth = getBalanceL1().sendAsync().join();
                if (baseCost.compareTo(selfBalanceEth.add(transaction.amount)) >= 0){
                    BigInteger recommendedL1GasLimit = transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.LEGACY_ETH_ADDRESS)
                            ? L1_RECOMMENDED_MIN_ETH_DEPOSIT_GAS_LIMIT
                            : L1_RECOMMENDED_MIN_ERC20_DEPOSIT_GAS_LIMIT;
                    BigInteger recommendedETHBalance = recommendedL1GasLimit.multiply(gasPriceForestimation).add(baseCost);
                    throw new Error("Not enough balance for deposit. Under the provided gas price, the recommended balance to perform a deposit is " + recommendedETHBalance + " ETH");
                }
                if (!transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS) &&
                        getAllowanceL1(transaction.tokenAddress, transaction.bridgeAddress).sendAsync().join().compareTo(transaction.amount) < 0){
                    throw new Error("Not enough allowance to cover the deposit!");
                }
            }else{
                BigInteger mintValue = baseCost.add(transaction.operatorTip);

                if (getAllowanceL1(baseTokenAddress).sendAsync().join().compareTo(mintValue) < 0){
                    throw new Error("Not enough base token allowance to cover the deposit!");
                }
                if (transaction.tokenAddress.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS) ||
                        transaction.tokenAddress.equalsIgnoreCase(baseTokenAddress)){
                    transaction.options.value = transaction.amount;
                }else{
                    transaction.options.value = BigInteger.ZERO;
                    if (getAllowanceL1(transaction.tokenAddress).sendAsync().join().compareTo(transaction.amount) < 0){
                        throw new Error("Not enough token allowance to cover the deposit!");
                    }
                }
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
        });
    }

    public void getPriorityOpConfirmation(String txHash, int index){

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

        IL1Bridge il1Bridge = IL1Bridge.load(getL1BridgeContracts().sharedL1Bridge.getContractAddress(), providerL1, transactionManager, gasProvider);

        return il1Bridge.finalizeWithdrawal(providerL2.ethChainId().sendAsync().join().getChainId(), l1BatchNumber, BigInteger.valueOf(l2ToL1MessageProof.getId()), receipt.getL1BatchTxIndex(), bytes_data, merkle_proof);
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

    public Request<?, EthSendTransaction> requestExecute(RequestExecuteTransaction transaction) {
        RawTransaction tx = getRequestExecuteTransaction(transaction);
        byte[] message = TransactionEncoder.signMessage(tx, credentials);
        return providerL1.ethSendRawTransaction(Numeric.toHexString(message));
    }

    public Request<?, EthEstimateGas> estimateGasRequestExecute(RequestExecuteTransaction transaction){
        RawTransaction tx = getRequestExecuteTransaction(transaction);

        org.web3j.protocol.core.methods.request.Transaction tr = Transaction.createEthCallTransaction(signer.getAddress(), tx.getTo(), tx.getData(), tx.getValue());
        return providerL1.ethEstimateGas(tr);
    }

    public AllowanceParams getRequestExecuteAllowanceParams(RequestExecuteTransaction transaction){
        boolean isEthBasedChain = isETHBasedChain();

        if (isEthBasedChain){
            throw new Error("ETH token can't be approved! The address of the token does not exist on L1.");
        }

        if (transaction.getOperatorTip() == null){
            transaction.setOperatorTip(BigInteger.valueOf(0));
        }
        if (transaction.getGasPerPubDataByte() == null){
            transaction.setGasPerPubDataByte(BigInteger.valueOf(800));
        }
        if (transaction.getRefoundRecepient() == null){
            transaction.setRefoundRecepient(signer.getAddress());
        }
        if (transaction.l2Value == null){
            transaction.l2Value = BigInteger.ZERO;
        }
        if (transaction.getL2GasLimit() == null){
            transaction.setL2GasLimit(providerL2.estimateL1ToL2Execute(transaction.getContractAddress(), transaction.getCalldata(), getSigner().getAddress(), null, transaction.l2Value, transaction.getFactoryDeps(), transaction.getOperatorTip(), transaction.getGasPerPubDataByte(), transaction.getRefoundRecepient()).sendAsync().join().getAmountUsed());
        }
        if (transaction.getFactoryDeps() == null)
            transaction.setFactoryDeps(new byte[1][]);

        insertGasPriceInTransactionOptions(transaction.options, providerL1);

        BigInteger gasPriceForestimation = transaction.getOptions().getMaxFeePerGas() != null ? transaction.getOptions().getMaxFeePerGas() : transaction.getOptions().getGasPrice();

        BigInteger baseCost = getBaseCost(transaction.getL2GasLimit(), transaction.getGasPerPubDataByte(), gasPriceForestimation).sendAsync().join();

        return new AllowanceParams(getBaseToken().sendAsync().join(), baseCost.add(transaction.operatorTip.add(transaction.l2Value)));
    }
}
