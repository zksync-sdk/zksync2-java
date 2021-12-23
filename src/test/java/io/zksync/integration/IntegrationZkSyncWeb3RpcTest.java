package io.zksync.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.utils.Convert.Unit;

import io.zksync.abi.TransactionEncoder;
import io.zksync.abi.ZkFunctionEncoder;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.helper.CounterContract;
import io.zksync.methods.request.ZksEstimateFeeRequest;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.TimeRange;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.MigrateToPorter;
import io.zksync.transaction.Transfer;
import io.zksync.transaction.Withdraw;
import io.zksync.transaction.fee.DefaultTransactionFeeProvider;
import io.zksync.wrappers.ERC20;

public class IntegrationZkSyncWeb3RpcTest {

    private static final Token ETH = Token.createETH();

    ZkSync zksync;
    Credentials credentials;
    EthSigner signer;

    PollingTransactionReceiptProcessor processor;

    String contractAddress;

    @Before
    public void setUp() {
        this.zksync = ZkSync.build(new HttpService("http://127.0.0.1:3050"));
        this.credentials = Credentials.create(ECKeyPair.create(BigInteger.ONE));

        BigInteger chainId = this.zksync.ethChainId().sendAsync().join().getChainId();

        this.signer = new PrivateKeyEthSigner(credentials, chainId.longValue());

        this.processor = new PollingTransactionReceiptProcessor(this.zksync, 200, 10);
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

        System.out.printf("%s: %d", this.credentials.getAddress(), Numeric.toBigInt(getBalance.getResult()));
    }

    @Test
    public void testGetTransactionReceipt() throws IOException {
        TransactionReceipt receipt = this.zksync
                .ethGetTransactionReceipt("0xf9e709fb5c7fc79d576e04eb29a0e85a20c7b234c670185cdb19263cd8a3bebf").send()
                .getResult();

        System.out.println(receipt);
    }

    @Test
    public void testTransferToSelf() throws IOException, TransactionException {
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        Transfer zkTransfer = new Transfer(
                ETH.getAddress(),
                this.credentials.getAddress(),
                Convert.toWei("1", Unit.ETHER).toBigInteger(),
                this.credentials.getAddress(),
                ETH.getAddress(),
                BigInteger.ZERO,
                nonce.intValue(),
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkTransfer);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();
        zkTransfer.setFee(new BigInteger(estimateFee.getResult().getTotalFee()));

        RawTransaction transactionForSubmit = TransactionEncoder.encodeToRawTransaction(zkTransfer, signer);

        byte[] message = TransactionEncoder.signMessage(transactionForSubmit, credentials);

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
    public void testEstimateFee_Transfer() throws IOException {
        Transfer zkTransfer = new Transfer(
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                BigInteger.ZERO,
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                BigInteger.ZERO,
                0,
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkTransfer);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();

        if (estimateFee.hasError()) {
            System.out.println(estimateFee.getError().getMessage());
        } else {
            System.out.println(estimateFee.getResult());
        }

        assertFalse(estimateFee::hasError);
    }

    @Test
    public void testEstimateFee_Withdraw() throws IOException {
        Withdraw zkWithdraw = new Withdraw(
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                BigInteger.ZERO,
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                BigInteger.ZERO,
                0,
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkWithdraw);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();

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
        byte[] calldata = ZkFunctionEncoder.encodeCalldata(transfer);

        Execute zkExecute = new Execute(
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                Numeric.toHexString(calldata),
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                BigInteger.ZERO,
                0,
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkExecute);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();

        if (estimateFee.hasError()) {
            System.out.println(estimateFee.getError().getMessage());
        } else {
            System.out.println(estimateFee.getResult());
        }

        assertFalse(estimateFee::hasError);
    }

    @Test
    public void testEstimateFee_DeployContract_Rollup() throws IOException {
        DeployContract zkDeployContract = new DeployContract(
                AccountType.ZkRollup,
                Numeric.toHexString(CounterContract.getCode()),
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                BigInteger.ZERO,
                0,
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkDeployContract);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();

        if (estimateFee.hasError()) {
            System.out.println(estimateFee.getError().getMessage());
        } else {
            System.out.println(estimateFee.getResult());
        }

        assertFalse(estimateFee::hasError);
    }

    @Test
    public void testEstimateFee_DeployContract_Porter() throws IOException {
        DeployContract zkDeployContract = new DeployContract(
                AccountType.ZkPorter,
                Numeric.toHexString(CounterContract.getCode()),
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                BigInteger.ZERO,
                0,
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkDeployContract);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();

        if (estimateFee.hasError()) {
            System.out.println(estimateFee.getError().getMessage());
        } else {
            System.out.println(estimateFee.getResult());
        }

        assertFalse(estimateFee::hasError);
    }

    @Test
    public void testEstimateFee_MigrateToPorter() throws IOException {
        MigrateToPorter zkMigrateToPorter = new MigrateToPorter(
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                BigInteger.ZERO,
                0,
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkMigrateToPorter);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();

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
                .deploy(zksync, credentials, new DefaultTransactionFeeProvider(zksync, ETH)).send();

        assertNotNull(contract.getContractAddress());
        System.out.println(contract.getContractAddress());

        this.contractAddress = contract.getContractAddress();
    }

    @Test
    public void testDeployWeb3jContractWithNewMethod() throws Exception {
        CounterContract contract = CounterContract.deploy(zksync, new RawTransactionManager(zksync, credentials),
                new DefaultTransactionFeeProvider(zksync, ETH), signer).send();

        assertNotNull(contract.getContractAddress());
        System.out.println(contract.getContractAddress());
    }

    @Test
    public void testReadWeb3jContract() throws Exception {
        CounterContract zkCounterContract = CounterContract.load(contractAddress, zksync,
                new RawTransactionManager(zksync, credentials), new DefaultTransactionFeeProvider(zksync, ETH), signer);

        BigInteger result = zkCounterContract.get().send();

        System.out.println(result);

        assertEquals(BigInteger.ZERO, result);
    }

    @Test
    public void testWriteWeb3jContract() throws Exception {
        CounterContract zkCounterContract = CounterContract.load(contractAddress, zksync,
                new RawTransactionManager(zksync, credentials), new DefaultTransactionFeeProvider(zksync, ETH), signer);

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
                AccountType.ZkPorter,
                Numeric.toHexString(CounterContract.getCode()),
                this.credentials.getAddress(),
                ETH.getAddress(),
                BigInteger.ZERO,
                nonce.intValue(),
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkDeploy);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();
        zkDeploy.setFee(new BigInteger(estimateFee.getResult().getTotalFee()));

        RawTransaction transactionForSubmit = TransactionEncoder.encodeToRawTransaction(zkDeploy, signer);

        byte[] message = TransactionEncoder.signMessage(transactionForSubmit, credentials);

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
        assertNotNull(receipt.getContractAddress());
        System.out.println(receipt);

        this.contractAddress = receipt.getContractAddress();
    }

    @Test
    public void testExecuteContract() throws IOException, TransactionException {
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
                .getTransactionCount();
        byte[] calldata = ZkFunctionEncoder.encodeCalldata(CounterContract.encodeIncrement(BigInteger.valueOf(10)));

        Execute zkExecute = new Execute(
                contractAddress,
                Numeric.toHexString(calldata),
                this.credentials.getAddress(),
                ETH.getAddress(),
                BigInteger.ZERO,
                nonce.intValue(),
                new TimeRange());

        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(zkExecute);
        ZksEstimateFee estimateFee = this.zksync
                .zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();
        zkExecute.setFee(new BigInteger(estimateFee.getResult().getTotalFee()));

        RawTransaction transactionForSubmit = TransactionEncoder.encodeToRawTransaction(zkExecute, signer);

        byte[] message = TransactionEncoder.signMessage(transactionForSubmit, credentials);

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

}
