package io.zksync.integration;

import io.zksync.ZkSyncWallet;
import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.account.Wallet;
import io.zksync.protocol.account.WalletL1;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.transaction.response.ZkSyncTransactionReceiptProcessor;
import io.zksync.transaction.type.Transaction712;
import io.zksync.utils.ContractDeployer;
import io.zksync.wrappers.IZkSync;
import io.zksync.wrappers.ZkSyncContract;
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
    protected final String ADDRESS;
    protected final String RECEIVER;
    protected final String PAYMASTER;
    protected final String TOKEN;
    protected final String L1_DAI;
    protected final Web3j l1Web3;
    protected final ZkSync zksync;
    protected final Credentials credentials;
    protected final EthSigner signer;

    protected final ZkSyncTransactionReceiptProcessor processor;

    protected final ZkTransactionFeeProvider feeProvider;

    protected final BigInteger chainId;

    protected final ZkSyncWallet wallet;
    protected final Wallet testWallet;

    protected BaseIntegrationEnv() {

        L1_NODE = "http://127.0.0.1:8545";
        L2_NODE = "http://127.0.0.1:3050";
        ADDRESS = "0x36615Cf349d7F6344891B1e7CA7C72883F5dc049";
        RECEIVER = "0xa61464658AfeAf65CccaaFD3a512b69A83B77618";
        PAYMASTER = "0x594E77D36eB367b3AbAb98775c99eB383079F966";
        TOKEN = "0x0183Fe07a98bc036d6eb23C3943d823bcD66a90F";
        L1_DAI = "0x70a0F165d6f8054d0d0CF8dFd4DD2005f0AF6B55";
        final String privateKey = "0x7726827caac94a7f9e1b160f7ea819f172f7b6f9d2a97f992c38edeab82d4110";

        this.l1Web3 = Web3j.build(new HttpService(L1_NODE));
        this.zksync = ZkSync.build(new HttpService(L2_NODE));
        this.credentials = privateKey != null ? Credentials.create(privateKey) : Credentials.create(ECKeyPair.create(BigInteger.ONE));

        chainId = this.zksync.ethChainId().sendAsync().join().getChainId();

        this.signer = new PrivateKeyEthSigner(credentials, chainId.longValue());

        this.processor = new ZkSyncTransactionReceiptProcessor(this.zksync, 200, 100);

        this.feeProvider = new DefaultTransactionFeeProvider(zksync, ETH);

        this.wallet = new ZkSyncWallet(zksync, signer, ETH);
        TransactionManager manager = new RawTransactionManager(l1Web3, credentials, l1Web3.ethChainId().sendAsync().join().getChainId().longValue());
        try {
            ContractGasProvider gasProvider = new StaticGasProvider(l1Web3.ethGasPrice().send().getGasPrice(), BigInteger.valueOf(300_000L));
            IZkSync zkSyncContract = IZkSync.load(zksync.zksMainContract().sendAsync().join().getResult(), l1Web3, manager, gasProvider);
            this.testWallet = new Wallet(l1Web3, zksync, credentials);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
                .deposit(ETH, Convert.toWei("100", Convert.Unit.ETHER).toBigInteger(), BigInteger.ZERO, credentials.getAddress()).join();

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
        meta.setGasPerPubdata(fee.getGasPerPubdataLimitNumber());

        Transaction712 transaction = new Transaction712(
                chainId.longValue(),
                nonce,
                fee.getGasLimitNumber(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                fee.getMaxPriorityFeePerErgNumber(),
                fee.getGasPriceLimitNumber(),
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
