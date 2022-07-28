package io.zksync.transaction.manager;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.request.Transaction;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;
import io.zksync.transaction.type.Transaction712;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.utils.Create2;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Assertions;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

import static io.zksync.protocol.JsonRpc2_0ZkSync.DEFAULT_BLOCK_COMMIT_TIME;

public class ZkSyncTransactionManager extends TransactionManager {

    public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = 40;
    public static final long DEFAULT_POLLING_FREQUENCY = DEFAULT_BLOCK_COMMIT_TIME;

    private final ZkSync zkSync;
    private final EthSigner signer;

    private final ZkTransactionFeeProvider feeProvider;

    public ZkSyncTransactionManager(ZkSync zkSync, EthSigner signer, ZkTransactionFeeProvider feeProvider) {
        super(new ZkSyncTransactionReceiptProcessor(zkSync, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH), signer.getAddress());

        this.zkSync = zkSync;
        this.signer = signer;
        this.feeProvider = feeProvider;
    }

    @Override
    public EthSendTransaction sendTransaction(BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value, boolean constructor) throws IOException {
        final Transaction712 transaction;
        long chainId = getSigner().getDomain().join().getChainId().getValue().longValue();
        if (gasPrice == null) {
            gasPrice = getFeeProvider().getGasPrice();
        }
        gasPrice = BigInteger.ZERO; // TODO: Currently using zero cause fee calculation of the node doesn't work
        Eip712Meta meta = new Eip712Meta(
                getFeeProvider().getFeeToken().getL2Address(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                null,
                null
        );
        if (constructor) {
            Assertions.verifyPrecondition(!isVanillaEVMByteCode(data), "ZkSync zkEVM does not support EVM bytecode");
            if (gasLimit == null) {
                Transaction estimate = Transaction.createContractTransaction(
                        getFromAddress(),
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        meta.getFeeToken(),
                        data
                );
                gasLimit = getFeeProvider().getGasLimit(estimate);
                data = estimate.getData();
                meta = estimate.getEip712Meta();
            }
            transaction = new Transaction712(
                    getNonce(),
                    gasPrice,
                    gasLimit,
                    Create2.DEPLOYER_SYSTEM_CONTRACT_ADDRESS,
                    value,
                    data,
                    chainId,
                    meta
            );
        } else {
            if (gasLimit == null) {
                Transaction estimate = Transaction.createFunctionCallTransaction(
                        getFromAddress(),
                        to,
                        BigInteger.ZERO,
                        BigInteger.ZERO,
                        getFeeProvider().getFeeToken().getL2Address(),
                        data
                );
                gasLimit = getFeeProvider().getGasLimit(estimate);
            }
            transaction = new Transaction712(
                    getNonce(),
                    gasPrice,
                    gasLimit,
                    to,
                    value,
                    data,
                    chainId,
                    meta
            );
        }

        String signature = getSigner().getDomain().thenCompose(domain -> getSigner().signTypedData(domain, TransactionRequest.from(transaction))).join();
        byte[] signed = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction response = zkSync.ethSendRawTransaction(Numeric.toHexString(signed)).send();

        if (response.hasError()) {
            throw new JsonRpcResponseException(response);
        } else {
            return response;
        }
    }

    @Override
    public EthSendTransaction sendEIP1559Transaction(long chainId, BigInteger maxPriorityFeePerGas, BigInteger maxFeePerGas, BigInteger gasLimit, String to, String data, BigInteger value, boolean constructor) throws IOException {
        return sendTransaction(maxPriorityFeePerGas, gasLimit, to, data, value, constructor);
    }

    @Override
    public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter) throws IOException {
        EthCall ethCall =
                zkSync.ethCall(
                                Transaction.createEthCallTransaction(getFromAddress(), to, data),
                                defaultBlockParameter)
                        .send();

        if (ethCall.hasError()) {
            throw new JsonRpcResponseException(ethCall);
        }

        return ethCall.getValue();
    }

    @Override
    public EthGetCode getCode(String contractAddress, DefaultBlockParameter defaultBlockParameter) throws IOException {
        return zkSync.ethGetCode(contractAddress, defaultBlockParameter).send();
    }

    public ZkTransactionFeeProvider getFeeProvider() {
        return feeProvider;
    }

    public EthSigner getSigner() {
        return signer;
    }

    protected BigInteger getNonce() throws IOException {
        EthGetTransactionCount ethGetTransactionCount =
                zkSync.ethGetTransactionCount(
                                getSigner().getAddress(), ZkBlockParameterName.COMMITTED)
                        .send();

        return ethGetTransactionCount.getTransactionCount();
    }

    private boolean isVanillaEVMByteCode(String bytecodeHex) {
        return bytecodeHex.startsWith("0x60");
    }
}
