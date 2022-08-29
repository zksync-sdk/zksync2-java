package io.zksync;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import io.zksync.methods.request.Transaction;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;
import io.zksync.transaction.type.Transaction712;
import io.zksync.wrappers.ERC20;
import io.zksync.wrappers.IL2Bridge;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Numeric;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import lombok.Getter;

import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH;
import static io.zksync.transaction.manager.ZkSyncTransactionManager.DEFAULT_POLLING_FREQUENCY;

@Getter
public class ZkSyncWallet {

    private final ZkSync zksync;
    private final EthSigner signer;
    private final TransactionReceiptProcessor transactionReceiptProcessor;
    private final ZkTransactionFeeProvider feeProvider;

    public ZkSyncWallet(ZkSync zksync, EthSigner signer, TransactionReceiptProcessor transactionReceiptProcessor,
                        ZkTransactionFeeProvider feeProvider) {
        this.zksync = zksync;
        this.signer = signer;
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.feeProvider = feeProvider;
    }

    public ZkSyncWallet(ZkSync zksync, EthSigner signer) {
        this(zksync, signer, new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                new DefaultTransactionFeeProvider(zksync, Token.ETH));
    }

    public ZkSyncWallet(ZkSync zksync, EthSigner signer, Token feeToken) {
        this(zksync, signer, new ZkSyncTransactionReceiptProcessor(zksync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                new DefaultTransactionFeeProvider(zksync, feeToken));
    }

    public ZkSyncWallet(ZkSync zksync, Credentials credentials, Long chainId) {
        this(zksync, new PrivateKeyEthSigner(credentials, chainId));
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount) {
        return transfer(to, amount, null, null);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, Token token) {
        return transfer(to, amount, token, null);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger amount, @Nullable Token token, @Nullable BigInteger nonce) {
        Token tokenToUse = token == null ? Token.ETH : token;
        String calldata;
        String txTo;
        BigInteger txAmount;

        if (tokenToUse.isETH()) {
            calldata = "0x";
            txTo = to;
            txAmount = amount;
        } else {
            Function function = ERC20.encodeTransfer(to, amount);
            calldata = FunctionEncoder.encode(function);
            txTo = tokenToUse.getL2Address();
            txAmount = null;
        }
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();

            Transaction estimate = Transaction.createFunctionCallTransaction(
                    signer.getAddress(),
                    txTo,
                    BigInteger.ZERO,
                    BigInteger.ZERO,
                    txAmount,
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

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount) {
        return withdraw(to, amount, null, null);
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, Token token) {
        return withdraw(to, amount, token, null);
    }

    public RemoteCall<TransactionReceipt> withdraw(String to, BigInteger amount, @Nullable Token token, @Nullable BigInteger nonce) {
        Token tokenToUse = token == null ? Token.ETH : token;

        Function function = new Function(
                IL2Bridge.FUNC_WITHDRAW,
                Arrays.asList(new Address(to),
                        new Address(tokenToUse.getL2Address()),
                        new Uint256(amount)),
                Collections.emptyList());
        String calldata = FunctionEncoder.encode(function);
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
            String l2Bridge;
            if (tokenToUse.isETH()) {
                l2Bridge = zksync.zksGetBridgeContracts().send().getResult().getL2EthDefaultBridge();
            } else {
                l2Bridge = zksync.zksGetBridgeContracts().send().getResult().getL2Erc20DefaultBridge();
            }

            Transaction estimate = Transaction.createFunctionCallTransaction(
                    signer.getAddress(),
                    l2Bridge,
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

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode) {
        return deploy(bytecode, null, null);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, byte[] calldata) {
        return deploy(bytecode, calldata, null);
    }

    public RemoteCall<TransactionReceipt> deploy(byte[] bytecode, @Nullable byte[] calldata,
                                                 @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();

            // TODO: Add using calldata as initializer
            Transaction estimate = Transaction.create2ContractTransaction(
                    signer.getAddress(),
                    BigInteger.ZERO,
                    BigInteger.ZERO,
                    Numeric.toHexString(bytecode)
            );

            EthSendTransaction sent = estimateAndSend(estimate, nonceToUse).join();

            try {
                return this.transactionReceiptProcessor.waitForTransactionReceipt(sent.getTransactionHash());
            } catch (IOException | TransactionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function) {
        return execute(contractAddress, function, null);
    }

    public RemoteCall<TransactionReceipt> execute(String contractAddress, Function function, @Nullable BigInteger nonce) {
        return new RemoteCall<>(() -> {
            BigInteger nonceToUse = nonce != null ? nonce : getNonce().send();
            String calldata = FunctionEncoder.encode(function);

            Transaction estimate = Transaction.createFunctionCallTransaction(
                    signer.getAddress(),
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

    public RemoteCall<BigInteger> getBalance() {
        return getBalance(signer.getAddress(), Token.ETH, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(Token token) {
        return getBalance(signer.getAddress(), token, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(String address) {
        return getBalance(address, Token.ETH, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(String address, Token token) {
        return getBalance(address, token, ZkBlockParameterName.COMMITTED);
    }

    public RemoteCall<BigInteger> getBalance(String address, Token token, DefaultBlockParameter at) {
        if (token.isETH()) {
            return new RemoteCall<>(() ->
                    this.zksync.ethGetBalance(address, at).sendAsync().join().getBalance());
        } else {
            ERC20 erc20 = ERC20.load(token.getL2Address(), this.zksync, new ReadonlyTransactionManager(this.zksync, getSigner().getAddress()), new DefaultGasProvider());
            return erc20.balanceOf(address);
        }
    }

    public RemoteCall<BigInteger> getNonce(DefaultBlockParameter at) {
        return new RemoteCall<>(() -> this.zksync
                .ethGetTransactionCount(signer.getAddress(), at).sendAsync().join()
                .getTransactionCount());
    }

    public RemoteCall<BigInteger> getNonce() {
        return getNonce(ZkBlockParameterName.COMMITTED);
    }

    private CompletableFuture<EthSendTransaction> estimateAndSend(Transaction transaction, BigInteger nonce) {
        return CompletableFuture
                .supplyAsync(() -> {
                    long chainId = signer.getDomain().join().getChainId().getValue().longValue();
                    BigInteger gas = getFeeProvider().getGasLimit(transaction);
                    BigInteger gasPrice = getFeeProvider().getGasPrice();

                    Transaction712 prepared = new Transaction712(
                            chainId,
                            nonce,
                            gas,
                            transaction.getTo(),
                            transaction.getValueNumber(),
                            transaction.getData(),
                            BigInteger.valueOf(100000000L), // TODO: Estimate correct one
                            gasPrice,
                            transaction.getFrom(),
                            transaction.getEip712Meta()
                    );

                    String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, prepared)).join();
                    byte[] signed = TransactionEncoder.encode(prepared, TransactionEncoder.getSignatureData(signature));

                    return this.zksync.ethSendRawTransaction(Numeric.toHexString(signed))
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
