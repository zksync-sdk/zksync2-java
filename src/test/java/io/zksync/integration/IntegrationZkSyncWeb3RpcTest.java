package io.zksync.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import io.zksync.abi.TransactionEncoder;
import io.zksync.helper.CounterContract;
import io.zksync.methods.response.*;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.manager.ZkSyncTransactionManager;
import io.zksync.transaction.fee.ZkTransactionFeeProvider;
import io.zksync.transaction.type.Transaction712;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.utils.Convert.Unit;

import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.provider.EthereumProvider;
import io.zksync.transaction.Execute;
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

        this.contractAddress = "0x2946259e0334f33a064106302415ad3391bed384";
    }

    @Test
    public void printChainId() {
        System.out.println(chainId);
        System.out.println(credentials.getAddress());
    }

    @Test
    public void sendTestMoney() {
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

        String account = web3j.ethAccounts().sendAsync().join().getAccounts().get(0);

        EthSendTransaction sent = web3j.ethSendTransaction(
                Transaction.createEtherTransaction(account, null, Convert.toWei("1", Unit.GWEI).toBigInteger(), BigInteger.valueOf(21_000L),
                        this.credentials.getAddress(), Convert.toWei("1000000", Unit.ETHER).toBigInteger()))
                .sendAsync().join();

        assertResponse(sent);
    }

    @Test
    public void testDeposit() throws IOException {
        Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        BigInteger chainId = web3j.ethChainId().send().getChainId();
        TransactionManager manager = new RawTransactionManager(web3j, credentials, chainId.longValue());
        ContractGasProvider gasProvider = new StaticGasProvider(Convert.toWei("1", Unit.GWEI).toBigInteger(), BigInteger.valueOf(555_000L));
        TransactionReceipt receipt = EthereumProvider
                .load(zksync, web3j, manager, gasProvider).join()
                .deposit(ETH, Convert.toWei("100", Unit.ETHER).toBigInteger(), credentials.getAddress()).join();

        System.out.println(receipt);
    }

    @Test
    public void testGetBalanceOfToken() throws IOException {
        EthGetBalance getBalance = this.zksync
                .ethGetBalance(this.credentials.getAddress(), DefaultBlockParameterName.LATEST, ETH.getL2Address())
                .send();

        System.out.printf("%s: %d\n", this.credentials.getAddress(), Numeric.toBigInt(getBalance.getResult()));
    }

    @Test
    public void testGetNonce() throws IOException {
        EthGetTransactionCount getTransactionCount = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), DefaultBlockParameterName.LATEST)
                .send();

        System.out.printf("%s: %d\n", this.credentials.getAddress(), Numeric.toBigInt(getTransactionCount.getResult()));
    }

    @Test
    public void testGetTransactionReceipt() throws IOException {
        TransactionReceipt receipt = this.zksync
                .ethGetTransactionReceipt("0xb10c52ae1348bc3fc3a764c26bff9d928a544dabed3a8004e849bcab59a402f4").send()
                .getResult();

        System.out.println(receipt);
    }

//    @Test
//    public void testTransferToSelf() throws IOException, TransactionException {
//        BigInteger nonce = this.zksync
//                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
//                .getTransactionCount();
//        Function transfer = ERC20.encodeTransfer("0xe1fab3efd74a77c23b426c302d96372140ff7d0c", BigInteger.valueOf(1L));
//        String calldata = FunctionEncoder.encode(transfer);
//
//        Execute zkTransfer = new Execute(
//                ETH.getL2Address(),
//                calldata,
//                credentials.getAddress(),
//                new Fee(ETH.getL2Address()),
//                nonce);
//
//        ZksEstimateFee estimateFee = estimateFee(zkTransfer);
//        zkTransfer.setFee(estimateFee.getResult());
//
//        Transaction712 transaction = new Transaction712(chainId.longValue(), new TransactionRequest(zkTransfer));
//
//        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkTransfer))).join();
//        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));
//
//        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();
//
//        assertResponse(sent);
//
//        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());
//
//        assertTrue(receipt::isStatusOK);
//    }

    @Test
    public void testTransferToSelfWeb3jContract() throws Exception {
        ERC20 erc20 = ERC20.load(ETH.getL2Address(), this.zksync,
                new ZkSyncTransactionManager(this.zksync, this.signer, this.feeProvider),
                new StaticGasProvider(BigInteger.ZERO, BigInteger.ZERO));

        TransactionReceipt receipt = erc20.transfer("0xe1fab3efd74a77c23b426c302d96372140ff7d0c", BigInteger.valueOf(1L)).send();

        assertTrue(receipt::isStatusOK);
    }

//    @Test
//    public void testWithdraw() throws IOException, TransactionException {
//        BigInteger nonce = this.zksync
//                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
//                .getTransactionCount();
//        Withdraw zkWithdraw = new Withdraw(
//                ETH.getL2Address(),
//                credentials.getAddress(),
//                Convert.toWei("1", Unit.ETHER).toBigInteger(),
//                credentials.getAddress(),
//                new Fee(ETH.getL2Address()),
//                nonce);
//
//        ZksEstimateFee estimateFee = estimateFee(zkWithdraw);
//
//        zkWithdraw.setFee(estimateFee.getResult());
//
//        Transaction712<Withdraw> transaction = new Transaction712<>(chainId.longValue(), zkWithdraw);
//
//        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkWithdraw))).join();
//        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));
//
//        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();
//
//        assertResponse(sent);
//
//        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());
//
//        assertTrue(receipt::isStatusOK);
//    }

//    @Test
//    public void testEstimateFee_Withdraw() throws IOException {
//        Withdraw zkWithdraw = new Withdraw(
//                "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
//                "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
//                BigInteger.ONE,
//                credentials.getAddress(),
//                new Fee(ETH.getL2Address()),
//                BigInteger.valueOf(0));
//
//        ZksEstimateFee estimateFee = estimateFee(zkWithdraw);
//
//        assertResponse(estimateFee);
//    }

    @Test
    public void testEstimateGas_Execute() throws IOException {
        Function transfer = ERC20.encodeTransfer("0xe1fab3efd74a77c23b426c302d96372140ff7d0c", BigInteger.valueOf(1L));
        String calldata = FunctionEncoder.encode(transfer);

        EthEstimateGas estimateGas = zksync.ethEstimateGas(io.zksync.methods.request.Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                "0x79f73588fa338e685e9bbd7181b410f60895d2a3",
                BigInteger.ZERO,
                BigInteger.ZERO,
                ETH.getL2Address(),
                calldata
        )).send();

        assertResponse(estimateGas);
    }

    @Test
    public void testEstimateFee_DeployContract() throws IOException {
        EthEstimateGas estimateGas = zksync.ethEstimateGas(io.zksync.methods.request.Transaction.createContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                ETH.getL2Address(),
                CounterContract.BINARY
        )).send();

        assertResponse(estimateGas);
    }

    @Test
    public void testDeployWeb3jContract() throws Exception {
        TransactionManager transactionManager = new ZkSyncTransactionManager(zksync, signer, feeProvider);
        CounterContract contract = CounterContract
                .deploy(zksync, transactionManager, new DefaultGasProvider()).send();

        assertNotNull(contract.getContractAddress());
        System.out.println(contract.getContractAddress());

        this.contractAddress = contract.getContractAddress();
    }

    @Test
    public void testReadWeb3jContract() throws Exception {
        TransactionManager transactionManager = new ZkSyncTransactionManager(zksync, signer, feeProvider);
        CounterContract zkCounterContract = CounterContract.load(contractAddress, zksync, transactionManager, new DefaultGasProvider());

        BigInteger result = zkCounterContract.get().send();

        System.out.println(result);

        assertEquals(BigInteger.ZERO, result);
    }

    @Test
    public void testWriteWeb3jContract() throws Exception {
        TransactionManager transactionManager = new ZkSyncTransactionManager(zksync, signer, feeProvider);
        CounterContract zkCounterContract = CounterContract.load(contractAddress, zksync, transactionManager, new DefaultGasProvider());

        TransactionReceipt receipt = zkCounterContract.increment(BigInteger.TEN).send();

        assertTrue(receipt::isStatusOK);

        BigInteger result = zkCounterContract.get().send();

        assertEquals(BigInteger.TEN, result);
    }

    @Test
    public void testDeployContract() throws IOException, TransactionException {
        BigInteger nonce = this.zksync
                .ethGetTransactionCount(this.credentials.getAddress(), DefaultBlockParameterName.LATEST).send()
                .getTransactionCount();

        io.zksync.methods.request.Transaction estimate = io.zksync.methods.request.Transaction.createContractTransaction(
                credentials.getAddress(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                ETH.getL2Address(),
                CounterContract.BINARY
        );

        EthEstimateGas estimateGas = zksync.ethEstimateGas(estimate).send();
        EthGasPrice gasPrice = zksync.ethGasPrice(ETH.getL2Address()).send();

        assertResponse(estimateGas);
        assertResponse(gasPrice);

        System.out.printf("Fee for transaction is: %d\n", estimateGas.getAmountUsed().multiply(gasPrice.getGasPrice()));

        Transaction712 transaction = new Transaction712(
                nonce,
//                gasPrice.getGasPrice(),
                BigInteger.ZERO,
                estimateGas.getAmountUsed(),
                estimate.getTo(),
                estimate.getValueNumber(),
                estimate.getData(),
                chainId.longValue(),
                estimate.getEip712Meta()
        );

        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, TransactionRequest.from(transaction))).join();
        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));

        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();

        assertResponse(sent);

        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());

        assertTrue(receipt::isStatusOK);

        this.contractAddress = receipt.getContractAddress();

        Transaction call = Transaction.createEthCallTransaction(
                credentials.getAddress(),
                contractAddress,
                FunctionEncoder.encode(CounterContract.encodeGet())
        );

        zksync.ethCall(call, ZkBlockParameterName.COMMITTED).send();
    }


//    @Test
//    public void testExecuteContract() throws IOException, TransactionException {
//        BigInteger nonce = this.zksync
//                .ethGetTransactionCount(this.credentials.getAddress(), ZkBlockParameterName.COMMITTED).send()
//                .getTransactionCount();
//        String calldata = FunctionEncoder.encode(CounterContract.encodeIncrement(BigInteger.valueOf(10)));
//
//        Execute zkExecute = new Execute(
//                contractAddress,
//                calldata,
//                this.credentials.getAddress(),
//                new Fee(ETH.getL2Address()),
//                nonce);
//
//        ZksEstimateFee estimateFee = estimateFee(zkExecute);
//        zkExecute.setFee(estimateFee.getResult());
//
//        Transaction712 transaction = new Transaction712(chainId.longValue(), new TransactionRequest(zkExecute));
//
//        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, new TransactionRequest(zkExecute))).join();
//        byte[] message = TransactionEncoder.encode(transaction, TransactionEncoder.getSignatureData(signature));
//
//        EthSendTransaction sent = this.zksync.ethSendRawTransaction(Numeric.toHexString(message)).send();
//
//        assertResponse(estimateFee);
//
//        TransactionReceipt receipt = this.processor.waitForTransactionReceipt(sent.getResult());
//
//        assertTrue(receipt::isStatusOK);
//    }

    @Test
    public void testGetAllAccountBalances() throws IOException {
        ZksAccountBalances response = this.zksync.zksGetAllAccountBalances(credentials.getAddress()).send();

        assertResponse(response);

        Map<String, BigInteger> balances = response.getBalances();

        System.out.println(balances);
    }

    @Test
    public void testGetConfirmedTokens() throws IOException {
        int offset = 0;
        short limit = 10; // Get first 10 confirmed tokens

        ZksTokens response = this.zksync.zksGetConfirmedTokens(offset, limit).send();

        assertResponse(response);
    }

    @Test
    public void testIsTokenLiquid() throws IOException {
        ZksIsTokenLiquid response = this.zksync.zksIsTokenLiquid(ETH.getL2Address()).send();

        assertResponse(response);
        assertTrue(response.getResult());
    }

    @Test
    public void testGetTokenPrice() throws IOException {
        ZksTokenPrice response = this.zksync.zksGetTokenPrice(ETH.getL2Address()).send();

        assertResponse(response);
    }

    @Test
    public void testGetL1ChainId() throws IOException {
        ZksL1ChainId response = this.zksync.zksL1ChainId().send();

        assertResponse(response);
    }

    @Test
    public void testGetBridgeAddresses() throws IOException {
        ZksBridgeAddresses response = this.zksync.zksGetBridgeContracts().send();

        assertResponse(response);
    }

    private <T extends io.zksync.transaction.Transaction> ZksEstimateFee estimateFee(T transaction) throws IOException {
        ZksEstimateFee estimateFee = null;
        if (transaction instanceof Execute) {
            estimateFee = this.zksync.zksEstimateFee(new io.zksync.methods.request.Transaction((Execute) transaction)).send();
        }

        return estimateFee;
    }

    private void assertResponse(Response<?> response) {
        if (response.hasError()) {
            System.out.println(response.getError().getMessage());
            System.out.println(response.getError().getData());
        } else {
            System.out.println(response.getResult());
        }

        assertFalse(response::hasError);
    }

}
