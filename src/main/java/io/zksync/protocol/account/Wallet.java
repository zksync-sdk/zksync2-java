package io.zksync.protocol.account;

import io.zksync.abi.TransactionEncoder;
import io.zksync.abi.ZkFunctionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.ZksAccountBalances;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.BridgeAddresses;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;
import io.zksync.transaction.type.*;
import io.zksync.utils.AccountAbstractionVersion;
import io.zksync.utils.ContractDeployer;
import io.zksync.utils.ZkSyncAddresses;
import io.zksync.wrappers.*;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;
import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_FREQUENCY;

@Getter
public class Wallet extends WalletL1{

    private final TransactionReceiptProcessor transactionReceiptProcessor;
    private final ZkTransactionFeeProvider feeProviderL2;
    protected final EthSigner signerL2;

    public Wallet(Web3j providerL1, ZkSync providerL2, TransactionManager transactionManager, ContractGasProvider feeProviderL1, ZkTransactionFeeProvider feeProviderL2, TransactionReceiptProcessor transactionReceiptProcessor, Credentials credentials) {
        super(providerL1, providerL2, transactionManager, feeProviderL1, credentials);
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.feeProviderL2 = feeProviderL2;
        this.signerL2 = new PrivateKeyEthSigner(credentials, providerL2.ethChainId().sendAsync().join().getChainId().longValue());
    }

    public Wallet(ZkSync providerL2, Credentials credentials) {
        this(
                null,
                providerL2,
                null,
                null,
                new DefaultTransactionFeeProvider(providerL2, Token.ETH),
                new ZkSyncTransactionReceiptProcessor(providerL2, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                credentials);
    }

    public Wallet(Web3j providerL1, ZkSync providerL2, Credentials credentials) {
        this(
                providerL1,
                providerL2,
                new RawTransactionManager(providerL1, credentials, providerL1.ethChainId().sendAsync().join().getChainId().longValue()),
                new StaticGasProvider(providerL1.ethGasPrice().sendAsync().join().getGasPrice(), BigInteger.valueOf(300_000L)),
                new DefaultTransactionFeeProvider(providerL2, Token.ETH),
                new ZkSyncTransactionReceiptProcessor(providerL2, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                credentials);
    }


    /**
     * @return Returns the wallet address.
     */
    public String getAddress(){
        return signer.getAddress();
    }

    /**
     * @return Returns wrappers of L2 bridge contracts.
     */
    public L2BridgeContracts getL2BridgeContracts(){
        BridgeAddresses bridgeAddresses = providerL2.zksGetBridgeContracts().sendAsync().join().getResult();
        return new L2BridgeContracts(bridgeAddresses.getL2Erc20DefaultBridge(), bridgeAddresses.getL2wETHBridge(), providerL2, transactionManager, feeProviderL2);
    }

    public CompletableFuture<BigInteger> getDeploymentNonce(){
        return INonceHolder.load(ZkSyncAddresses.NONCE_HOLDER_ADDRESS, providerL2, credentials, feeProviderL2).getDeploymentNonce(getAddress()).sendAsync();
    }

    /**
     * Transfer coins or tokens
     *
     * @param tx TransferTransaction class
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> transfer(TransferTransaction tx) {
        return new RemoteCall<>(() -> {
            tx.options = tx.options == null ? new TransactionOptions() : tx.options;
            BigInteger maxPriorityFeePerGas = tx.options.getMaxPriorityFeePerGas() == null ? BigInteger.ZERO : tx.options.getMaxPriorityFeePerGas();
            BigInteger nonceToUse = getNonce().send();

            Transaction estimate = providerL2.getTransferTransaction(tx, transactionManager, gasProvider);

            EthSendTransaction sent = estimateAndSend(estimate, nonceToUse, maxPriorityFeePerGas).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Withdraw native coins to L1 chain
     *
     * @param tx WithdrawTransaction class
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> withdraw(WithdrawTransaction tx) {
        tx.tokenAddress = tx.tokenAddress == null ? ZkSyncAddresses.ETH_ADDRESS : tx.tokenAddress;
        tx.from = tx.from == null ? getAddress() : tx.from;
        tx.options = tx.options == null ? new TransactionOptions() : tx.options;
        BigInteger maxPriorityFeePerGas = tx.options.getMaxPriorityFeePerGas() == null ? BigInteger.ZERO : tx.options.getMaxPriorityFeePerGas();

        return new RemoteCall<>(() -> {
            Transaction transaction = providerL2.getWithdrawTransaction(tx, gasProvider, transactionManager);

            EthSendTransaction sent = estimateAndSend(transaction, getNonce().send(), maxPriorityFeePerGas).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Deploy new smart-contract into chain (this method uses create2, see <a href="https://eips.ethereum.org/EIPS/eip-1014">EIP-1014</a>)
     *
     * @param bytecode Compiled bytecode of the contract
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode) {
        return deploy(bytecode, null, null);
    }

    /**
     * Deploy new smart-contract into chain (this method uses create2, see <a href="https://eips.ethereum.org/EIPS/eip-1014">EIP-1014</a>)
     *
     * @param bytecode Compiled bytecode of the contract
     * @param calldata Encoded constructor parameter of contract
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, byte[] calldata) {
        return deploy(bytecode, calldata, null);
    }

    /**
     * Deploy new smart-contract into chain (this method uses create2, see <a href="https://eips.ethereum.org/EIPS/eip-1014">EIP-1014</a>)
     *
     * @param bytecode Compiled bytecode of the contract
     * @param calldata Encoded constructor parameter of contract
     * @param nonce Custom nonce value of the wallet
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, @Nullable byte[] calldata,
                                                 @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();

            Transaction estimate = Transaction.createContractTransaction(
                    getAddress(),
                    BigInteger.ZERO,
                    BigInteger.ZERO,
                    Numeric.toHexString(bytecode),
                    calldata != null ? Numeric.toHexString(calldata) : "0x"
            );

            EthSendTransaction sent = estimateAndSend(estimate, nonceToUse).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public RemoteCall<TransactionReceipt> deployAccount(byte[] bytecode,
                                                        byte[] calldata){
        return deployAccount(bytecode, calldata, null, null);
    }
    public RemoteCall<TransactionReceipt> deployAccount(byte[] bytecode,
                                                        byte[] calldata,
                                                        BigInteger nonce){
        return deployAccount(bytecode, calldata, null, nonce);
    }
    public RemoteCall<TransactionReceipt> deployAccount(byte[] bytecode,
                                                        byte[] calldata,
                                                        byte[] salt) {
        return deployAccount(bytecode, calldata, salt, null);
    }
    public RemoteCall<TransactionReceipt> deployAccount(byte[] bytecode,
                                                        byte[] calldata,
                                                        @Nullable byte[] salt,
                                                        @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();

            byte[] saltToUse = salt == null ? SecureRandom.getSeed(32) : salt;
            Function function = ContractDeployer.encodeCreate2Account(bytecode, calldata, saltToUse, AccountAbstractionVersion.Version1);
            ZkFunctionEncoder encoder = new ZkFunctionEncoder();
            String data = encoder.encodeFunction(function);
            Transaction estimate = Transaction.create2ContractTransaction(
                    getAddress(),
                    BigInteger.ZERO,
                    BigInteger.ZERO,
                    Numeric.toHexString(bytecode),
                    data,
                    Collections.singletonList(Numeric.toHexString(bytecode))
            );

            EthSendTransaction sent = estimateAndSend(estimate, nonceToUse).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }
    /**
     * Execute function of deployed contract
     *
     * @param contractAddress Address of deployed contract
     * @param function Prepared function call with or without parameters
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function) {
        return execute(contractAddress, function, null);
    }

    /**
     * Execute function of deployed contract
     *
     * @param contractAddress Address of deployed contract
     * @param function Prepared function call with or without parameters
     * @param nonce Custom nonce value of the wallet
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function, @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
            String calldata = FunctionEncoder.encode(function);

            Transaction estimate = Transaction.createFunctionCallTransaction(
                    getAddress(),
                    contractAddress,
                    BigInteger.ZERO,
                    BigInteger.ZERO,
                    calldata
            );

            EthSendTransaction sent = estimateAndSend(estimate, nonceToUse).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Get balance of wallet in native coin (wallet address gets from {@link EthSigner})
     *
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalance() {
        return getBalance(getAddress(), ZkSyncAddresses.ETH_ADDRESS, ZkBlockParameterName.COMMITTED);
    }

    /**
     * Get balance of wallet in {@link Token} (wallet address gets from {@link EthSigner})
     *
     * @param token Address of the token supported by ZkSync
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalance(String token) {
        return getBalance(getAddress(), token, ZkBlockParameterName.COMMITTED);
    }

    /**
     * Get balance of wallet in {@link Token}
     *
     * @param address Address of the wallet
     * @param token Address of the token supported by ZkSync
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalance(String address, String token) {
        return getBalance(address, token, ZkBlockParameterName.COMMITTED);
    }

    /**
     * Get balance of wallet by address in {@link Token} at block {@link DefaultBlockParameter}
     * also see {@link org.web3j.protocol.core.DefaultBlockParameterName}, {@link org.web3j.protocol.core.DefaultBlockParameterNumber}, {@link ZkBlockParameterName}
     *
     * @param address Wallet address
     * @param token Address of the token supported by ZkSync
     * @param at Block variant
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalance(String address, String token, DefaultBlockParameter at) {
        if (token == ZkSyncAddresses.ETH_ADDRESS) {
            return new RemoteCall<>(() ->
                    this.providerL2.ethGetBalance(address, at).sendAsync().join().getBalance());
        } else {
            ERC20 erc20 = ERC20.load(token, this.providerL2, new ReadonlyTransactionManager(this.providerL2, address), new DefaultGasProvider());

            return erc20.balanceOf(address);
        }
    }


    /**
     * @return Returns all balances for confirmed tokens given by an account address.
     */
    public CompletableFuture<ZksAccountBalances> getAllBalances(){
        return providerL2.zksGetAllAccountBalances(signer.getAddress()).sendAsync();
    }

    /**
     * Get nonce for wallet at block {@link DefaultBlockParameter} (wallet address gets from {@link EthSigner})
     * also see {@link org.web3j.protocol.core.DefaultBlockParameterName}, {@link org.web3j.protocol.core.DefaultBlockParameterNumber}, {@link ZkBlockParameterName}
     * @param at Block variant
     * @return Prepared get nonce call
     */
    public RemoteCall<BigInteger> getNonce(DefaultBlockParameter at) {
        return new RemoteCall<>(() -> this.providerL2
                .ethGetTransactionCount(getAddress(), at).sendAsync().join()
                .getTransactionCount());
    }

    /**
     * Get nonce for wallet at block `COMMITTED` {@link ZkBlockParameterName} (wallet address gets from {@link EthSigner})
     * @return Prepared get nonce call
     */
    public RemoteCall<BigInteger> getNonce() {
        return getNonce(ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<TransactionReceipt> sendMessageToL1(byte[] message) {
        return sendMessageToL1(message, null);
    }

    public RemoteCall<TransactionReceipt> sendMessageToL1(byte[] message, @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
            String calldata = IL1Messenger.encodeSendToL1(message);

            Transaction estimate = Transaction.createFunctionCallTransaction(
                    getAddress(),
                    ZkSyncAddresses.MESSENGER_ADDRESS,
                    BigInteger.ZERO,
                    BigInteger.ZERO,
                    calldata
            );

            EthSendTransaction sent = estimateAndSend(estimate, nonceToUse).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private CompletableFuture<EthSendTransaction> estimateAndSend(Transaction transaction, BigInteger nonce) {
        return estimateAndSend(transaction, nonce, BigInteger.ZERO);
    }

    private CompletableFuture<EthSendTransaction> estimateAndSend(Transaction transaction, BigInteger nonce, BigInteger maxPriorityFeePerGas) {
        return CompletableFuture
                .supplyAsync(() -> {
                    long chainId = providerL2.ethChainId().sendAsync().join().getChainId().longValue();
                    BigInteger gas = getFeeProviderL2().getGasLimit(transaction);
                    BigInteger gasPrice = getFeeProviderL2().getGasPrice();

                    Transaction712 prepared = new Transaction712(
                            chainId,
                            nonce,
                            gas,
                            transaction.getTo(),
                            transaction.getValueNumber(),
                            transaction.getData(),
                            maxPriorityFeePerGas,
                            gasPrice,
                            transaction.getFrom(),
                            transaction.getEip712Meta()
                    );
                    String signature = signerL2.getDomain().thenCompose(domain -> signerL2.signTypedData(domain, prepared)).join();
                    byte[] signed = TransactionEncoder.encode(prepared, TransactionEncoder.getSignatureData(signature));

                    return this.providerL2.ethSendRawTransaction(Numeric.toHexString(signed))
                            .sendAsync().join();
                })
                .thenApply(response -> {
                    if (response.hasError()) {
                        throw new JsonRpcResponseException(response);
                    } else {
                        return response;
                    }
                });
    }
}