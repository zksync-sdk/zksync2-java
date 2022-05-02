package io.zksync.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import io.zksync.helper.CounterContract;
import io.zksync.methods.response.ZksAccountBalances;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import io.zksync.transaction.type.Transaction712;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.utils.Convert.Unit;

import io.zksync.abi.TransactionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.Withdraw;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.wrappers.ERC20;

public class IntegrationZkSyncWeb3RpcTest {

    private static final Token ETH = Token.createETH();

    ZkSync zksync;
    Credentials credentials;
    EthSigner signer;

    PollingTransactionReceiptProcessor processor;

    ZkTransactionFeeProvider feeProvider;

    String contractAddress;

    BigInteger chainId;

    @Before
    public void setUp() {
        this.zksync = ZkSync.build(new HttpService("http://127.0.0.1:3050"));
        this.credentials = Credentials.create(ECKeyPair.create(BigInteger.ONE));

        chainId = this.zksync.ethChainId().sendAsync().join().getChainId();

        this.signer = new PrivateKeyEthSigner(credentials, chainId.longValue());

        this.processor = new PollingTransactionReceiptProcessor(this.zksync, 200, 10);

        this.feeProvider = new DefaultTransactionFeeProvider(zksync, ETH);
    }

    @Test
    public void printChainId() {
        System.out.println(chainId);
    }

    @Test
    public void sendTestMoney() throws IOException {
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        String account = web3j.ethAccounts().sendAsync().join().getAccounts().get(0);

        EthSendTransaction sent = web3j.ethSendTransaction(
                Transaction.createEtherTransaction(account, null, BigInteger.ZERO, BigInteger.valueOf(21_000L),
                        this.credentials.getAddress(), Convert.toWei("10", Unit.ETHER).toBigInteger()))
                .sendAsync().join();

        if (sent.hasError()) {
            System.out.println(sent.getError().getMessage());
            System.out.println(sent.getError().getData());
        } else {
            System.out.println(sent.getResult());
        }

        assertFalse(sent::hasError);
    }

    @Test
    public void testDeposit() throws IOException {
        TransactionReceipt receipt = EthereumProvider
                .load(zksync, Web3j.build(new HttpService("http://127.0.0.1:8545")), this.credentials).join()
                .deposit(ETH, Convert.toWei("9", Unit.ETHER).toBigInteger(), credentials.getAddress()).join();

        System.out.println(receipt);
    }

    @Test
    public void testGetBalanceOfToken() throws IOException {
        EthGetBalance getBalance = this.zksync
                .ethGetBalance(this.credentials.getAddress(), DefaultBlockParameterName.LATEST, ETH.getAddress())
                .send();

        System.out.printf("%s: %d\n", this.credentials.getAddress(), Numeric.toBigInt(getBalance.getResult()));
    }

    @Test
    public void testGetTransactionReceipt() throws IOException {
        TransactionReceipt receipt = this.zksync
                .ethGetTransactionReceipt("0xb10c52ae1348bc3fc3a764c26bff9d928a544dabed3a8004e849bcab59a402f4").send()
                .getResult();

        System.out.println(receipt);
    }

    @Test
    public void testTransferToSelf() throws IOException, TransactionException {
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        ERC20 erc20 = ERC20.load(ETH.getAddress(), this.zksync,
                new ReadonlyTransactionManager(this.zksync, Address.DEFAULT.getValue()),
                new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO));
        Function transfer = erc20.encodeTransfer("0xe1fab3efd74a77c23b426c302d96372140ff7d0c", BigInteger.valueOf(1L));
        String calldata = FunctionEncoder.encode(transfer);

        Execute zkTransfer = new Execute(
                ETH.getAddress(),
                calldata,
                credentials.getAddress(),
                new Fee(ETH.getAddress()),
                nonce);

        ZksEstimateFee estimateFee = estimateFee(zkTransfer);
        zkTransfer.setFee(estimateFee.getResult());

        Transaction712<Execute> transaction = new Transaction712<>(chainId.longValue(), zkTransfer);

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkTransfer))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        if (sent.hasError()) {
            System.out.println(sent.getError().getMessage());
            System.out.println(sent.getError().getData());
        } else {
            System.out.println(sent.getResult());
        }

        assertFalse(sent::hasError);

        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void testTransferToSelfWeb3jContract() throws Exception {
        ERC20 erc20 = ERC20.load(ETH.getAddress(), this.zksync,
                new ZkSyncTransactionManager(this.zksync, this.signer, this.feeProvider),
                new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO));

        TransactionReceipt receipt = erc20.transfer("0xe1fab3efd74a77c23b426c302d96372140ff7d0c", BigInteger.valueOf(1L)).send();

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void testEstimateFee_Withdraw() throws IOException {
        Withdraw zkWithdraw = new Withdraw(
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                BigInteger.ONE,
                credentials.getAddress(),
                new Fee(ETH.getAddress()),
                BigInteger.valueOf(0));

        ZksEstimateFee estimateFee = estimateFee(zkWithdraw);

        if (estimateFee.hasError()) {
            System.out.println(estimateFee.getError().getMessage());
        } else {
            System.out.println(estimateFee.getResult());
        }

        assertFalse(estimateFee::hasError);
    }

    @Test
    public void testEstimateFee_Execute() throws IOException {
        ERC20 erc20 = ERC20.load("0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", this.zksync,
                new ReadonlyTransactionManager(this.zksync, Address.DEFAULT.getValue()),
                new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO));
        Function transfer = erc20.encodeTransfer("0xe1fab3efd74a77c23b426c302d96372140ff7d0c", BigInteger.valueOf(1L));
        String calldata = FunctionEncoder.encode(transfer);

        Execute zkExecute = new Execute(
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                calldata,
                credentials.getAddress(),
                new Fee(ETH.getAddress()),
                BigInteger.valueOf(0));

        ZksEstimateFee estimateFee = estimateFee(zkExecute);

        if (estimateFee.hasError()) {
            System.out.println(estimateFee.getError().getMessage());
        } else {
            System.out.println(estimateFee.getResult());
        }

        assertFalse(estimateFee::hasError);
    }

    @Test
    public void testEstimateFee_DeployContract() throws IOException {
        DeployContract zkDeployContract = new DeployContract(
                Numeric.toHexString(CounterContract.getCode()),
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                new Fee(ETH.getAddress()),
                BigInteger.valueOf(0));

        ZksEstimateFee estimateFee = estimateFee(zkDeployContract);

        if (estimateFee.hasError()) {
            System.out.println(estimateFee.getError().getMessage());
        } else {
            System.out.println(estimateFee.getResult());
        }

        assertFalse(estimateFee::hasError);
    }

    @Test
    public void testDeployWeb3jContract() throws Exception {
        CounterContract contract = CounterContract
                .deploy(zksync, new DefaultTransactionFeeProvider(zksync, ETH), signer).send();

        assertNotNull(contract.getContractAddress());
        System.out.println(contract.getContractAddress());

        this.contractAddress = contract.getContractAddress();
    }

    @Test
    public void testReadWeb3jContract() throws Exception {
        CounterContract zkCounterContract = CounterContract.load(contractAddress, zksync, feeProvider, signer);

        BigInteger result = zkCounterContract.get().send();

        System.out.println(result);

        assertEquals(BigInteger.ZERO, result);
    }

    @Test
    public void testWriteWeb3jContract() throws Exception {
        CounterContract zkCounterContract = CounterContract.load(contractAddress, zksync, feeProvider, signer);

        TransactionReceipt receipt = zkCounterContract.increment(BigInteger.TEN).send();

        assertTrue(receipt::isStatusOK);

        BigInteger result = zkCounterContract.get().send();

        assertEquals(BigInteger.TEN, result);
    }

    @Test
    public void testDeployContract() throws IOException, TransactionException {
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        DeployContract zkDeploy = new DeployContract(
                Numeric.toHexString(CounterContract.getCode()),
                this.credentials.getAddress(),
                new Fee(ETH.getAddress()),
                nonce);

        ZksEstimateFee estimateFee = estimateFee(zkDeploy);
        zkDeploy.setFee(estimateFee.getResult());

        Transaction712<DeployContract> transaction = new Transaction712<>(chainId.longValue(), zkDeploy);

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkDeploy))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        if (sent.hasError()) {
            System.out.println(sent.getError().getMessage());
            System.out.println(sent.getError().getData());
        } else {
            System.out.println(sent.getResult());
        }

        assertFalse(sent::hasError);

        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        this.contractAddress = receipt.getContractAddress();
    }

    @Test
    public void testExecuteContract() throws IOException, TransactionException {
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        String calldata = FunctionEncoder.encode(CounterContract.encodeIncrement(BigInteger.valueOf(10)));

        Execute zkExecute = new Execute(
                contractAddress,
                calldata,
                this.credentials.getAddress(),
                new Fee(ETH.getAddress()),
                nonce);

        ZksEstimateFee estimateFee = estimateFee(zkExecute);
        zkExecute.setFee(estimateFee.getResult());

        Transaction712<Execute> transaction = new Transaction712<>(chainId.longValue(), zkExecute);

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkExecute))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        if (sent.hasError()) {
            System.out.println(sent.getError().getMessage());
            System.out.println(sent.getError().getData());
        } else {
            System.out.println(sent.getResult());
        }

        assertFalse(sent::hasError);

        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);
    }

    @Test
    public void testGetAllAccountBalances() throws IOException {
        ZksAccountBalances response = this.zksync.zksGetAllAccountBalances(credentials.getAddress()).send();

        if (response.hasError()) {
            System.out.println(response.getError().getMessage());
            System.out.println(response.getError().getData());
        } else {
            System.out.println(response.getResult());
        }

        assertFalse(response::hasError);

        Map<String, BigInteger> balances = response.getBalances();

        System.out.println(balances);
    }

    private <T extends io.zksync.transaction.Transaction> ZksEstimateFee estimateFee(T transaction) throws IOException {
        ZksEstimateFee estimateFee;
        if (transaction instanceof DeployContract) {
            estimateFee = this.zksync.zksEstimateFee(new io.zksync.methods.request.Transaction((DeployContract) transaction)).send();
        } else if (transaction instanceof Execute) {
            estimateFee = this.zksync.zksEstimateFee(new io.zksync.methods.request.Transaction((Execute) transaction)).send();
        } else {
            estimateFee = this.zksync.zksEstimateFee(new io.zksync.methods.request.Transaction((Withdraw) transaction)).send();
        }

        return estimateFee;
    }

}
