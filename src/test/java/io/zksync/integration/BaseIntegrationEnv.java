package io.zksync.integration;

import io.zksync.ZkSyncWallet;
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.ContractDeployer;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class BaseIntegrationEnv {
    public static final Token ETH = Token.ETH;

    private final String L1_NODE;
    private final String L2_NODE;

    protected final Web3j l1Web3;
    protected final ZkSync zksync;
    protected final Credentials credentials;
    protected final EthSigner signer;

    protected final ZkSyncTransactionReceiptProcessor processor;

    protected final ZkTransactionFeeProvider feeProvider;

    protected final BigInteger chainId;

    protected final ZkSyncWallet wallet;

    protected BaseIntegrationEnv() {
        L1_NODE = System.getenv("ZKSYNC2_JAVA_CI_L1_NODE_URL");
        L2_NODE = System.getenv("ZKSYNC2_JAVA_CI_L2_NODE_URL");

        final String privateKey = System.getenv("ZKSYNC2_JAVA_CI_PRIVATE_KEY");

        this.l1Web3 = Web3j.build(new HttpService(L1_NODE));
        this.zksync = ZkSync.build(new HttpService(L2_NODE));
        this.credentials = privateKey != null ? Credentials.create(privateKey) : Credentials.create(ECKeyPair.create(BigInteger.ONE));

        chainId = this.zksync.ethChainId().sendAsync().join().getChainId();

        this.signer = new PrivateKeyEthSigner(credentials, chainId.longValue());

        this.processor = new ZkSyncTransactionReceiptProcessor(this.zksync, 200, 100);

        this.feeProvider = new DefaultTransactionFeeProvider(zksync, ETH);

        this.wallet = new ZkSyncWallet(zksync, signer, ETH);
    }

    protected void sendTestMoney() {
        String account = l1Web3.ethAccounts().sendAsync().join().getAccounts().get(0);

        EthSendTransaction sent = l1Web3.ethSendTransaction(
                        Transaction.createEtherTransaction(account, null, Convert.toWei("1", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(21_000L),
                                this.credentials.getAddress(), Convert.toWei("100", Convert.Unit.ETHER).toBigInteger()))
                .sendAsync().join();

        assertResponse(sent);
    }

    protected void deposit() throws IOException {
        BigInteger chainId = l1Web3.ethChainId().send().getChainId();
        TransactionManager manager = new RawTransactionManager(l1Web3, credentials, chainId.longValue());
        ContractGasProvider gasProvider = new StaticGasProvider(Convert.toWei("1", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(555_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(zksync, l1Web3, manager, gasProvider).join()
                .deposit(ETH, Convert.toWei("100", Convert.Unit.ETHER).toBigInteger(), credentials.getAddress()).join();

        System.out.println(receipt);
    }

    protected void assertResponse(Response<?> response) {
        if (response.hasError()) {
            System.out.println(response.getError().getMessage());
            System.out.println(response.getError().getData());
        } else {
            System.out.println(response.getResult());
        }

        assertFalse(response::hasError);
    }

    protected void assertContractDeployResponse(TransactionReceipt response) {
        assertNotNull(response.getContractAddress());
    }

    protected void assertContractDeployResponse(TransactionReceipt response, String expected) {
        assertContractDeployResponse(response);
        String correctContractAddress = ContractDeployer.extractContractAddress(response).getValue();
        assertEquals(correctContractAddress, expected);
    }

    protected TransactionReceipt submitTransaction(io.zksync.methods.request.Transaction estimate) throws IOException, TransactionException {
        BigInteger nonce = zksync
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send()
                .getTransactionCount();
        ZksEstimateFee estimateFee = zksync.zksEstimateFee(estimate).send();

        EthGasPrice gasPrice = zksync.ethGasPrice().send();

        assertResponse(estimateFee);
        assertResponse(gasPrice);

        Fee fee = estimateFee.getResult();

        Eip712Meta meta = estimate.getEip712Meta();
        meta.setErgsPerPubdata(fee.getErgsPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getErgsLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getErgsPriceLimitNumber(),
                credentials.getAddress(),
                meta
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, transaction)).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        return receipt;
    }
}
