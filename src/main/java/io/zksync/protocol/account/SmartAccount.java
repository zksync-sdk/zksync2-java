package io.zksync.protocol.account;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.methods.response.ZksAccountBalances;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;
import io.zksync.transaction.type.Transaction712;
import io.zksync.transaction.type.TransactionOptions;
import io.zksync.transaction.type.TransferTransaction;
import io.zksync.transaction.type.WithdrawTransaction;
import io.zksync.utils.ZkSyncAddresses;
import io.zksync.utils.smart.account.IPopulateTransaction;
import io.zksync.utils.smart.account.ISignPayload;
import io.zksync.utils.smart.account.PopulateTransactionECDS;
import io.zksync.utils.smart.account.SignPayloadWithECDSA;
import io.zksync.wrappers.ERC20;
import io.zksync.wrappers.INonceHolder;
import org.jetbrains.annotations.Nullable;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;
import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_FREQUENCY;

public class SmartAccount {
    public final ZkSyncTransactionReceiptProcessor transactionReceiptProcessor;
    protected final ZkTransactionFeeProvider feeProviderL2;
    protected final List<String> secrets;

    protected final Credentials credentials;

    protected final String address;
    protected final ZkSync providerL2;
    protected final ISignPayload payloadSigner;
    protected final IPopulateTransaction transactionBuilder;


    public SmartAccount(ZkSync providerL2, ZkTransactionFeeProvider feeProviderL2, ZkSyncTransactionReceiptProcessor transactionReceiptProcessor, List<String> secrets, String address, @Nullable ISignPayload payloadSigner, @Nullable IPopulateTransaction transactionBuilder) {
        this.providerL2 = providerL2;
        this.secrets = secrets;
        this.credentials = Credentials.create(secrets.get(0));
        this.address = address;
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.feeProviderL2 = feeProviderL2;
        this.payloadSigner = payloadSigner == null ? new SignPayloadWithECDSA() : payloadSigner;
        this.transactionBuilder = transactionBuilder == null ? new PopulateTransactionECDS() : transactionBuilder;
    }
    public SmartAccount(ZkSync providerL2, List<String> secrets, String address, @Nullable ISignPayload payloadSigner, @Nullable IPopulateTransaction transactionBuilder ) {
        this(
                providerL2,
                new DefaultTransactionFeeProvider(providerL2, Token.ETH),
                new ZkSyncTransactionReceiptProcessor(providerL2, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                secrets,
                address,
                payloadSigner,
                transactionBuilder);
    }

    /**
     * @return Returns the wallet address.
     */
    public String getAddress(){
        return this.address;
    }

    /**
     * Get balance of wallet in native coin (wallet address gets from {@link EthSigner})
     *
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalance() {
        return getBalance(this.address, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS, ZkBlockParameterName.COMMITTED);
    }

    /**
     * Get balance of wallet in {@link Token} (wallet address gets from {@link EthSigner})
     *
     * @param token Address of the token supported by ZkSync
     * @return Prepared get balance call
     */
    public RemoteCall<BigInteger> getBalance(String token) {
        return getBalance(this.address, token, ZkBlockParameterName.COMMITTED);
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
        if (token.equalsIgnoreCase(ZkSyncAddresses.LEGACY_ETH_ADDRESS) || token.equalsIgnoreCase(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS)){
            token = providerL2.l2TokenAddress(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS);
        }
        if (token.equalsIgnoreCase(ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS)) {
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
        return providerL2.zksGetAllAccountBalances(this.address).sendAsync();
    }

    /**
     * Returns the deployment nonce of the account.
     */
    public CompletableFuture<BigInteger> getDeploymentNonce(){
        return INonceHolder.load(ZkSyncAddresses.NONCE_HOLDER_ADDRESS, providerL2, credentials, feeProviderL2).getDeploymentNonce(getAddress()).sendAsync();
    }

    /**
     * Populates the transaction `tx` using the provided {@link IPopulateTransaction} function.
     * If `tx.from` is not set, it sets the value from the {@link getAddress} method which can
     * be utilized in the {@link IPopulateTransaction} function.
     *
     * @param tx The transaction that needs to be populated.
     */
    public CompletableFuture<Transaction712> populateTransaction(Transaction tx){
        return this.transactionBuilder.populateTransaction(tx, this.secrets, this.providerL2, null);
    }

    /**
     * Signs the transaction `tx` using the provided {@link ISignPayload} function,
     * returning the fully signed transaction. The {@link IPopulateTransaction} method
     * is called first to ensure that all necessary properties for the transaction to be valid
     * have been populated.
     *
     * @param tx The transaction that needs to be signed.
     */
    public String signTransaction(Transaction tx){
        Transaction712 populated = this.populateTransaction(tx).join();
        String signature = this.payloadSigner.sign(populated, this.secrets, providerL2.ethChainId().sendAsync().join().getChainId().longValue());
        Eip712Meta meta = populated.getMeta();
        meta.setCustomSignature(Numeric.hexStringToByteArray(signature));

        Transaction712 signed = new Transaction712(
                populated.getChainId(),
                providerL2.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).sendAsync().join().getTransactionCount(),
                populated.getGasLimit(),
                populated.getTo(),
                populated.getValue(),
                populated.getData(),
                populated.getMaxPriorityFeePerGas(),
                populated.getMaxFeePerGas(),
                address,
                meta);
        return Numeric.toHexString(TransactionEncoder.encode(signed, null));
    }

    /**
     * Sends `tx` to the Network. The {@link ISignPayload}
     * is called first to ensure transaction is properly signed.
     *
     * @param tx The transaction that needs to be sent.
     */
    public CompletableFuture<EthSendTransaction> sendTransaction(Transaction tx){
        return CompletableFuture
                .supplyAsync(() -> {
                    String signed = this.signTransaction(tx);
                    return this.providerL2.ethSendRawTransaction(signed)
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

    /**
     * Transfer coins or tokens
     *
     * @param tx TransferTransaction class
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> transfer(TransferTransaction tx){
        return new RemoteCall<>(() -> {
            tx.from = tx.from == null ? this.address : tx.from;
            tx.options = tx.options == null ? new TransactionOptions() : tx.options;
            Transaction transaction = providerL2.getTransferTransaction(tx,
                    new RawTransactionManager(providerL2, credentials, providerL2.ethChainId().sendAsync().join().getChainId().longValue()),
                    new StaticGasProvider(providerL2.ethGasPrice().sendAsync().join().getGasPrice(), BigInteger.valueOf(300_000L))
            );

            EthSendTransaction sent = sendTransaction(transaction).join();
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
     * @param tx {@link WithdrawTransaction} class
     * @return Prepared remote call of transaction
     */
    public RemoteCall<TransactionReceipt> withdraw(WithdrawTransaction tx) {
        tx.tokenAddress = tx.tokenAddress == null ? ZkSyncAddresses.LEGACY_ETH_ADDRESS : tx.tokenAddress;
        tx.from = tx.from == null ? getAddress() : tx.from;
        tx.options = tx.options == null ? new TransactionOptions() : tx.options;
        BigInteger maxPriorityFeePerGas = tx.options.getMaxPriorityFeePerGas() == null ? BigInteger.ZERO : tx.options.getMaxPriorityFeePerGas();

        return new RemoteCall<>(() -> {
            Transaction transaction = providerL2.getWithdrawTransaction(tx,
                    new StaticGasProvider(providerL2.ethGasPrice().sendAsync().join().getGasPrice(), BigInteger.valueOf(300_000L)),
                    new RawTransactionManager(providerL2, credentials, providerL2.ethChainId().sendAsync().join().getChainId().longValue()));

            EthSendTransaction sent = sendTransaction(transaction).join();
            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
