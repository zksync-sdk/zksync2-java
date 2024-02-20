package io.zksync.integration.account;

import io.zksync.integration.BaseIntegrationEnv;
import io.zksync.methods.request.PaymasterParams;
import io.zksync.methods.response.FullDepositFee;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.type.*;
import io.zksync.utils.Paymaster;
import io.zksync.utils.ZkSyncAddresses;
import org.junit.jupiter.api.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest extends BaseIntegrationEnv {

    @Test
    public void testGetMainContract() {
        assertNotNull(testWallet.getMainContract());
    }

    @Test
    public void testGetL1BridgeContracts(){
        assertNotNull(testWallet.getL1BridgeContracts());
    }

    @Test
    public void testGetBalanceL1_ShouldReturnL1Balance(){
        assertTrue(testWallet.getBalanceL1().sendAsync().join().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testGetAllowanceL1(){
        assertTrue(testWallet.getAllowanceL1(L1_DAI).join().compareTo(BigInteger.ZERO) >= 0);
    }

    @Test
    public void testL2TokenAddressETH(){
        assertEquals(ZkSyncAddresses.ETH_ADDRESS.toLowerCase(), testWallet.l2TokenAddress(ZkSyncAddresses.ETH_ADDRESS).toLowerCase());
    }

    @Test
    public void testL2TokenAddressERC20(){
        assertNotNull(testWallet.l2TokenAddress(L1_DAI));
    }

    @Test
    public void testApproveERC20(){
        TransactionReceipt tx = testWallet.approveERC20(L1_DAI, BigInteger.valueOf(5)).join();
        assertNotNull(tx);
    }

    @Test
    public void testGetBaseCost(){
        assertNotNull(testWallet.getBaseCost(BigInteger.valueOf(100_000)));
    }

    @Test
    public void testGetBalance(){
        assertTrue(wallet.getBalance().sendAsync().join().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testGetAllBalances(){
        Map<String, BigInteger> result = testWallet.getAllBalances().join().getBalances();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetL2BridgeContracts(){
        assertNotNull(testWallet.getL2BridgeContracts());
    }

    @Test
    public void getAddress_ShouldReturnWalletAddress(){
        assertEquals(ADDRESS.toLowerCase(), testWallet.getAddress().toLowerCase());
    }

    @Test
    public void testGetRequestExecuteTransaction() throws IOException {
        RequestExecuteTransaction transaction = new RequestExecuteTransaction(null, zksync.zksMainContract().send().getResult(), Numeric.hexStringToByteArray("0x"), BigInteger.valueOf(7000000), null, null, null, null, null);
        RequestExecuteTransaction x = testWallet.getRequestExecuteTransaction(transaction);
        System.out.println(x);
    }

    @Test
    public void testGetDeploymentNonce(){
        assertNotNull(testWallet.getDeploymentNonce().join());
    }

    @Test
    public void testGetDepositTransaction(){
        TransactionOptions options = new TransactionOptions(null, new BigInteger("286654500007000"), null, BigInteger.valueOf(1500000007), BigInteger.valueOf(1500000000), null, null);
        DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.ETH_ADDRESS, BigInteger.valueOf(7_000), ADDRESS, BigInteger.valueOf(573_309), null, Numeric.hexStringToByteArray("0x"), BigInteger.valueOf(800), BigInteger.ZERO, ADDRESS, null, options);
        DepositTransaction result = testWallet.getDepositTransaction(new DepositTransaction(ZkSyncAddresses.ETH_ADDRESS, BigInteger.valueOf(7_000)));
        System.out.println(result.hashCode());
        System.out.println(transaction.hashCode());

        assertEquals(transaction, result);
    }

    @Test
    public void testEstimateGasDepositETH() throws IOException {
        DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.ETH_ADDRESS, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
        BigInteger result = testWallet.estimateGasDeposit(transaction);
        assertEquals(0, result.compareTo(BigInteger.valueOf(132_711)));
    }

    @Test
    public void testEstimateGasDepositERC20() throws IOException {
        DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
        BigInteger result = testWallet.estimateGasDeposit(transaction);
        assertEquals(0, result.compareTo(BigInteger.valueOf(253_418)));
    }
    @Test
    public void testDepositETH() throws IOException, InterruptedException, TransactionException {
        BigInteger amount = new BigInteger("7000000000");
        BigInteger l2BalanceBeforeDeposit = testWallet.getBalance().sendAsync().join();
        BigInteger l1BalanceBeforeDeposit = testWallet.getBalanceL1().sendAsync().join();

        DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.ETH_ADDRESS, amount);
        String hash = testWallet.deposit(transaction).sendAsync().join().getResult();
        TransactionReceipt l1Receipt = testWallet.getTransactionReceiptProcessorL1().waitForTransactionReceipt(hash);
        String l2Hash = zksync.getL2HashFromPriorityOp(l1Receipt, zksync.zksMainContract().sendAsync().join().getResult());
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(l2Hash);
        BigInteger l2BalanceAfterDeposit = testWallet.getBalance().sendAsync().join();
        BigInteger l1BalanceAfterDeposit = testWallet.getBalanceL1().sendAsync().join();

        assertNotNull(hash);
        assertTrue(l2BalanceAfterDeposit.subtract(l2BalanceBeforeDeposit).compareTo(amount) >= 0);
        assertTrue(l1BalanceBeforeDeposit.subtract(l1BalanceAfterDeposit).compareTo(amount) >= 0);
    }
    @Test
    public void testDepositERC20() throws IOException, TransactionException {
        String l2DAI = testWallet.l2TokenAddress(L1_DAI);
        BigInteger balanceBefore = testWallet.getBalance(l2DAI).sendAsync().join();

        DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(5), null,null, null, null, null, null, null, true, null);
        String hash = testWallet.deposit(transaction).sendAsync().join().getResult();
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessorL1().waitForTransactionReceipt(hash);
        String l2Hash = zksync.getL2HashFromPriorityOp(receipt, zksync.zksMainContract().sendAsync().join().getResult());

        BigInteger balanceAfter = testWallet.getBalance(l2DAI).sendAsync().join();

        assertNotNull(receipt);
        assertTrue(balanceAfter.subtract(balanceBefore).compareTo(BigInteger.valueOf(5)) == 0);
    }

    @Test
    public void testTransferEth() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);

        BigInteger balanceBeforeTransfer = testWallet.getBalance(RECEIVER, ZkSyncAddresses.ETH_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, signer.getAddress());
        TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger balanceAfterTransfer = testWallet.getBalance(RECEIVER, ZkSyncAddresses.ETH_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

        assertNotNull(receipt);
        assertEquals(balanceAfterTransfer.subtract(balanceBeforeTransfer), amount);
    }
    @Test
    public void testTransferErc20() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(5);
        String l2DAI = testWallet.l2TokenAddress(L1_DAI);

        BigInteger balanceBeforeTransfer = testWallet.getBalance(RECEIVER, l2DAI, ZkBlockParameterName.COMMITTED).sendAsync().join();
        BigInteger senderBefore = testWallet.getBalance(l2DAI).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, signer.getAddress(), l2DAI);
        TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger balanceAfterTransfer = testWallet.getBalance(RECEIVER, l2DAI, ZkBlockParameterName.COMMITTED).sendAsync().join();
        BigInteger senderAfter = testWallet.getBalance(l2DAI).sendAsync().join();

        assertNotNull(receipt);
        assertEquals(senderBefore.subtract(senderAfter), amount);
        assertEquals(balanceAfterTransfer.subtract(balanceBeforeTransfer), amount);
    }
    @Test
    public void testTransferEthWithPaymaster() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);
        PaymasterParams paymasterParams = new PaymasterParams(PAYMASTER, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(TOKEN, BigInteger.ONE, new byte[] {}))));

        BigInteger paymasterBeforeTransfer = testWallet.getBalance(PAYMASTER, ZkSyncAddresses.ETH_ADDRESS).sendAsync().join();
        BigInteger paymasterTokenBeforeTransfer = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBalanceBeforeTransfer = testWallet.getBalance().sendAsync().join();
        BigInteger senderApprovalBeforeTransfer = testWallet.getBalance(TOKEN).sendAsync().join();
        BigInteger receiverBefore = testWallet.getBalance(RECEIVER, ZkSyncAddresses.ETH_ADDRESS).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, signer.getAddress(), paymasterParams);
        TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger paymasterAfterTransfer = testWallet.getBalance(PAYMASTER, ZkSyncAddresses.ETH_ADDRESS).sendAsync().join();
        BigInteger paymasterTokenAfterTransfer = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBalanceAfterTransfer = testWallet.getBalance().sendAsync().join();
        BigInteger senderApprovalAfterTransfer = testWallet.getBalance(TOKEN).sendAsync().join();
        BigInteger receiverAfter = testWallet.getBalance(RECEIVER, ZkSyncAddresses.ETH_ADDRESS).sendAsync().join();

        assertNotNull(receipt);
        assertTrue(paymasterBeforeTransfer.subtract(paymasterAfterTransfer).compareTo(BigInteger.ZERO) >= 0);
        assertTrue(paymasterTokenAfterTransfer.subtract(paymasterTokenBeforeTransfer).compareTo(BigInteger.ONE) == 0);
        assertTrue(senderBalanceBeforeTransfer.subtract(senderBalanceAfterTransfer).compareTo(amount) == 0);
        assertTrue(receiverAfter.subtract(receiverBefore).compareTo(amount) == 0);
        assertTrue(senderApprovalBeforeTransfer.subtract(senderApprovalAfterTransfer).compareTo(BigInteger.ONE) == 0);
    }
    @Test
    public void testTransferErc20WithPaymaster() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(5);
        String l2DAI = testWallet.l2TokenAddress(L1_DAI);
        PaymasterParams paymasterParams = new PaymasterParams(PAYMASTER, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(TOKEN, BigInteger.ONE, new byte[] {}))));

        BigInteger paymasterTokenBeforeTransfer = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger balanceBeforeTransfer = testWallet.getBalance(RECEIVER, l2DAI, ZkBlockParameterName.COMMITTED).sendAsync().join();
        BigInteger senderBefore = testWallet.getBalance(l2DAI).sendAsync().join();
        BigInteger senderApprovalBeforeTransfer = testWallet.getBalance(TOKEN).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, signer.getAddress(), l2DAI, paymasterParams);
        TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger paymasterTokenAfterTransfer = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger balanceAfterTransfer = testWallet.getBalance(RECEIVER, l2DAI, ZkBlockParameterName.COMMITTED).sendAsync().join();
        BigInteger senderAfter = testWallet.getBalance(l2DAI).sendAsync().join();
        BigInteger senderApprovalAfterTransfer = testWallet.getBalance(TOKEN).sendAsync().join();

        assertNotNull(receipt);
        assertTrue(paymasterTokenAfterTransfer.subtract(paymasterTokenBeforeTransfer).compareTo(BigInteger.ONE) == 0);
        assertTrue(senderApprovalBeforeTransfer.subtract(senderApprovalAfterTransfer).compareTo(BigInteger.ONE) == 0);
        assertEquals(senderBefore.subtract(senderAfter), amount);
        assertEquals(balanceAfterTransfer.subtract(balanceBeforeTransfer), amount);
    }
    @Test
    public void testWithdrawEth() throws Exception {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);

        BigInteger senderBefore = testWallet.getBalance().sendAsync().join();

        WithdrawTransaction transaction = new WithdrawTransaction(ZkSyncAddresses.ETH_ADDRESS, amount, testWallet.getAddress());
        TransactionReceipt result = testWallet.withdraw(transaction).sendAsync().join();
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(result.getTransactionHash());

        BigInteger senderAfter = testWallet.getBalance().sendAsync().join();

        assertNotNull(receipt);
        assertTrue(senderBefore.subtract(senderAfter).compareTo(amount) >= 0);
    }
    @Test
    public void testWithdrawEthWithPaymaster() throws Exception {
        BigInteger amount = BigInteger.valueOf(5);
        PaymasterParams paymasterParams = new PaymasterParams(PAYMASTER, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(TOKEN, BigInteger.ONE, new byte[] {}))));

        BigInteger paymasterTokenBefore = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBefore = testWallet.getBalance().sendAsync().join();
        BigInteger senderApprovalBefore = testWallet.getBalance(TOKEN).sendAsync().join();

        WithdrawTransaction transaction = new WithdrawTransaction(ZkSyncAddresses.ETH_ADDRESS, amount, paymasterParams);
        TransactionReceipt result = testWallet.withdraw(transaction).sendAsync().join();
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(result.getTransactionHash());

        BigInteger paymasterTokenAfter = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderAfter = testWallet.getBalance().sendAsync().join();
        BigInteger senderApprovalAfter = testWallet.getBalance(TOKEN).sendAsync().join();

        assertNotNull(receipt);
        assertTrue(paymasterTokenAfter.subtract(paymasterTokenBefore).compareTo(BigInteger.ONE) == 0);
        assertTrue(senderApprovalBefore.subtract(senderApprovalAfter).compareTo(BigInteger.ONE) == 0);
        assertEquals(senderBefore.subtract(senderAfter), amount);
    }
    @Test
    public void testWithdrawErc20() throws Exception {
        BigInteger amount = BigInteger.valueOf(5);
        String l2DAI = testWallet.l2TokenAddress(L1_DAI);

        BigInteger senderBefore = testWallet.getBalance(l2DAI).sendAsync().join();

        WithdrawTransaction transaction = new WithdrawTransaction(l2DAI, amount, testWallet.getAddress());
        TransactionReceipt result = testWallet.withdraw(transaction).sendAsync().join();
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(result.getTransactionHash());

        BigInteger senderAfter = testWallet.getBalance(l2DAI).sendAsync().join();

        assertNotNull(receipt);
        assertEquals(senderBefore.subtract(senderAfter), amount);
    }
    @Test
    public void testWithdrawErc20WithPaymaster() throws Exception {
        BigInteger amount = BigInteger.valueOf(5);
        String l2DAI = testWallet.l2TokenAddress(L1_DAI);
        PaymasterParams paymasterParams = new PaymasterParams(PAYMASTER, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(TOKEN, BigInteger.ONE, new byte[] {}))));

        BigInteger paymasterTokenBefore = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBefore = testWallet.getBalance(l2DAI).sendAsync().join();
        BigInteger senderApprovalBefore = testWallet.getBalance(TOKEN).sendAsync().join();

        WithdrawTransaction transaction = new WithdrawTransaction(l2DAI, amount, paymasterParams);
        TransactionReceipt result = testWallet.withdraw(transaction).sendAsync().join();
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(result.getTransactionHash());

        BigInteger paymasterTokenAfter = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderAfter = testWallet.getBalance(l2DAI).sendAsync().join();
        BigInteger senderApprovalAfter = testWallet.getBalance(TOKEN).sendAsync().join();

        assertNotNull(receipt);
        assertTrue(paymasterTokenAfter.subtract(paymasterTokenBefore).compareTo(BigInteger.ONE) == 0);
        assertTrue(senderApprovalBefore.subtract(senderApprovalAfter).compareTo(BigInteger.ONE) == 0);
        assertEquals(senderBefore.subtract(senderAfter), amount);
    }
    @Test
    public void testGetFullRequiredDepositFee() throws IOException {
        DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.ETH_ADDRESS, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
        FullDepositFee fee =  testWallet.getFullRequiredDepositFee(transaction);
        assertTrue(fee.baseCost.compareTo(BigInteger.valueOf(285096500000000L)) == 0);
        assertTrue(fee.l1GasLimit.compareTo(BigInteger.valueOf(132711)) == 0);
        assertTrue(fee.l2GasLimit.compareTo(BigInteger.valueOf(570193)) == 0);
        assertTrue(fee.maxPriorityFeePerGas.compareTo(BigInteger.valueOf(1500000000)) == 0);
        assertTrue(fee.maxFeePerGas.compareTo(BigInteger.valueOf(1500000007)) == 0);
    }
}
