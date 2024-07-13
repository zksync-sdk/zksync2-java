package io.zksync.integration.account;

import io.zksync.integration.BaseIntegrationEnv;
import io.zksync.methods.request.PaymasterParams;
import io.zksync.methods.response.FullDepositFee;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.type.*;
import io.zksync.utils.Paymaster;
import io.zksync.utils.ZkSyncAddresses;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
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
        assertTrue(testWallet.getAllowanceL1(L1_DAI).sendAsync().join().compareTo(BigInteger.ZERO) >= 0);
    }

    @Test
    public void testBaseL2TokenAddress(){
        String baseTokenAddress = testWallet.getBaseToken().sendAsync().join();
        assertEquals(ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS, testWallet.l2TokenAddress(baseTokenAddress).toLowerCase());
    }

    @Test
    public void testL2TokenAddress(){
        if (!testWallet.isETHBasedChain()){
            assertNotNull(testWallet.l2TokenAddress(ZkSyncAddresses.LEGACY_ETH_ADDRESS).toLowerCase());
        }
    }

    @Test
    public void testL2DaiTokenAddress(){
        assertNotNull(testWallet.l2TokenAddress(L1_DAI).toLowerCase());
    }

    @Test
    public void testL2TokenAddressERC20(){
        assertNotNull(testWallet.l2TokenAddress(L1_DAI));
    }

    @Test
    public void testApproveERC20(){
        TransactionReceipt tx = testWallet.approveERC20(L1_DAI, BigInteger.valueOf(5)).sendAsync().join();
        assertNotNull(tx);
    }

    @Test
    public void testGetBaseCost(){
        assertNotNull(testWallet.getBaseCost(BigInteger.valueOf(100_000)));
    }

    @Test
    public void testGetBalance() throws Exception {
        System.out.println(testWallet.getBalanceL1Async(credentials.getAddress() , L1_DAI, DefaultBlockParameterName.LATEST).join());
        assertTrue(testWallet.getBalanceL1Async(credentials.getAddress() , L1_DAI, DefaultBlockParameterName.LATEST).join().compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    public void testGetAllBalances(){
        int expected = testWallet.isETHBasedChain() ? 2 : 3;
        Map<String, BigInteger> result = testWallet.getAllBalances().join().getBalances();
        assertEquals(expected, result.size());
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
        RawTransaction result = testWallet.getRequestExecuteTransaction(transaction);
        assertNotNull(result);
    }

    @Test
    public void testGetDeploymentNonce(){
        assertNotNull(testWallet.getDeploymentNonce().join());
    }

    @Test
    public void testEstimateGasDepositETH() throws Exception {
        if (testWallet.isETHBasedChain()){
            DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
            BigInteger result = testWallet.estimateGasDeposit(transaction);
            assertTrue(result.compareTo(BigInteger.valueOf(0)) > 0);
        }else{
            List<AllowanceParams> allowanceParams = testWallet.getDepositAllowanceParams(ZkSyncAddresses.LEGACY_ETH_ADDRESS, BigInteger.valueOf(5));
            testWallet.approveERC20(allowanceParams.get(0).token, allowanceParams.get(0).amount).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
            BigInteger result = testWallet.estimateGasDeposit(transaction);
            assertTrue(result.compareTo(BigInteger.valueOf(0)) > 0);
        }
    }

    @Test
    public void testEstimateGasDepositBaseToken() throws Exception {
        if (!testWallet.isETHBasedChain()){
            String token = testWallet.getBaseToken().sendAsync().join();
            List<AllowanceParams> allowanceParams = testWallet.getDepositAllowanceParams(token, BigInteger.valueOf(5));
            testWallet.approveERC20(allowanceParams.get(0).token, allowanceParams.get(0).amount).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(token, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
            BigInteger result = testWallet.estimateGasDeposit(transaction);
            assertTrue(result.compareTo(BigInteger.valueOf(0)) > 0);
        }
    }

    @Test
    public void testEstimateGasDepositERC20() throws Exception {
        if (testWallet.isETHBasedChain()){
            DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
            BigInteger result = testWallet.estimateGasDeposit(transaction);
            assertTrue(result.compareTo(BigInteger.valueOf(0)) > 0);
        }else{
            List<AllowanceParams> allowanceParams = testWallet.getDepositAllowanceParams(L1_DAI, BigInteger.valueOf(5));
            testWallet.approveERC20(allowanceParams.get(0).token, allowanceParams.get(0).amount).sendAsync().join();
            testWallet.approveERC20(allowanceParams.get(1).token, allowanceParams.get(1).amount).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
            BigInteger result = testWallet.estimateGasDeposit(transaction);
            assertTrue(result.compareTo(BigInteger.valueOf(0)) > 0);
        }
    }

    @Test
    public void testGetFullRequiredDepositFeeEth() throws Exception {
        if (testWallet.isETHBasedChain()){
            DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
            FullDepositFee fee =  testWallet.getFullRequiredDepositFee(transaction).sendAsync().join();
            assertTrue(fee.baseCost.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l1GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l2GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxPriorityFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
        }else{
            List<AllowanceParams> allowanceParams = testWallet.getDepositAllowanceParams(ZkSyncAddresses.LEGACY_ETH_ADDRESS, BigInteger.valueOf(1));
            testWallet.approveERC20(allowanceParams.get(0).token, allowanceParams.get(0).amount).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, BigInteger.valueOf(1), null,null, null, null, null, null, null, null, null);
            FullDepositFee fee =  testWallet.getFullRequiredDepositFee(transaction).sendAsync().join();
            assertTrue(fee.baseCost.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l1GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l2GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxPriorityFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
        }
    }

    @Test
    public void testGetFullRequiredDepositFeeErc20Token() throws Exception {
        if (testWallet.isETHBasedChain()){

            DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(5), null,null, null, null, null, null, null, null, null);
            FullDepositFee fee =  testWallet.getFullRequiredDepositFee(transaction).sendAsync().join();
            assertTrue(fee.baseCost.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l1GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l2GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxPriorityFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
        }else{
            List<AllowanceParams> allowanceParams = testWallet.getDepositAllowanceParams(L1_DAI, BigInteger.valueOf(1));
            testWallet.approveERC20(allowanceParams.get(0).token, allowanceParams.get(0).amount).sendAsync().join();
            testWallet.approveERC20(allowanceParams.get(1).token, allowanceParams.get(1).amount).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(1), null,null, null, null, null, null, null, null, null);
            FullDepositFee fee =  testWallet.getFullRequiredDepositFee(transaction).sendAsync().join();
            assertTrue(fee.baseCost.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l1GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l2GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxPriorityFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
        }
    }

    @Test
    public void testGetFullRequiredDepositFeeBaseToken() throws Exception {
        if (!testWallet.isETHBasedChain()){
            String token = testWallet.getBaseToken().sendAsync().join();
            List<AllowanceParams> allowanceParams = testWallet.getDepositAllowanceParams(token, BigInteger.valueOf(1));
            testWallet.approveERC20(allowanceParams.get(0).token, allowanceParams.get(0).amount).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(token, BigInteger.valueOf(1), null,null, null, null, null, null, null, null, null);
            FullDepositFee fee =  testWallet.getFullRequiredDepositFee(transaction).sendAsync().join();
            assertTrue(fee.baseCost.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l1GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.l2GasLimit.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxPriorityFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
            assertTrue(fee.maxFeePerGas.compareTo(BigInteger.valueOf(0)) > 0);
        }
    }

    @Test
    public void testDepositETH() throws Exception {
        if (testWallet.isETHBasedChain()){
            BigInteger amount = new BigInteger("7000000000");
            BigInteger l2BalanceBeforeDeposit = testWallet.getBalance().sendAsync().join();
            BigInteger l1BalanceBeforeDeposit = testWallet.getBalanceL1().sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, amount);
            EthSendTransaction hash = testWallet.deposit(transaction).send();

            TransactionReceipt l1Receipt = processorL1.waitForTransactionReceipt(hash.getTransactionHash());
            String l2Hash = zksync.getL2HashFromPriorityOp(l1Receipt, zksync.zksMainContract().sendAsync().join().getResult());
            testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(l2Hash);
            BigInteger l2BalanceAfterDeposit = testWallet.getBalance().sendAsync().join();
            BigInteger l1BalanceAfterDeposit = testWallet.getBalanceL1().sendAsync().join();

            assertNotNull(hash);
            assertTrue(l2BalanceAfterDeposit.subtract(l2BalanceBeforeDeposit).compareTo(amount) >= 0);
            assertTrue(l1BalanceBeforeDeposit.subtract(l1BalanceAfterDeposit).compareTo(amount) >= 0);
        }else{
            BigInteger amount = new BigInteger("7000000000");
            String l2Eth = testWallet.l2TokenAddress(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS);
            BigInteger l2BalanceBeforeDeposit = testWallet.getBalance(l2Eth).sendAsync().join();
            BigInteger a = testWallet.getBalance(ZkSyncAddresses.LEGACY_ETH_ADDRESS).sendAsync().join();
            BigInteger l1BalanceBeforeDeposit = testWallet.getBalanceL1().sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, amount, true);
            EthSendTransaction hash = testWallet.deposit(transaction).send();

            TransactionReceipt l1Receipt = processorL1.waitForTransactionReceipt(hash.getTransactionHash());
            String l2Hash = zksync.getL2HashFromPriorityOp(l1Receipt, zksync.zksMainContract().sendAsync().join().getResult());
            testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(l2Hash);
            BigInteger l2BalanceAfterDeposit = testWallet.getBalance(l2Eth).sendAsync().join();
            BigInteger l1BalanceAfterDeposit = testWallet.getBalanceL1().sendAsync().join();

            assertNotNull(hash);
            assertTrue(l2BalanceAfterDeposit.subtract(l2BalanceBeforeDeposit).compareTo(amount) == 0);
            assertTrue(l1BalanceBeforeDeposit.subtract(l1BalanceAfterDeposit).compareTo(amount) > 0);
        }
    }
    @Test
    public void testDepositBaseToken() throws Exception {
        BigInteger amount = new BigInteger("5");
        String baseToken = testWallet.getBaseToken().sendAsync().join();
        BigInteger l2BalanceBeforeDeposit = testWallet.getBalance().sendAsync().join();
        BigInteger l1BalanceBeforeDeposit = testWallet.getBalanceL1(baseToken).sendAsync().join();

        DepositTransaction transaction = new DepositTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, amount, true);
        EthSendTransaction hash = testWallet.deposit(transaction).send();

        TransactionReceipt l1Receipt = processorL1.waitForTransactionReceipt(hash.getTransactionHash());
        String l2Hash = zksync.getL2HashFromPriorityOp(l1Receipt, zksync.zksMainContract().sendAsync().join().getResult());
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(l2Hash);
        BigInteger l2BalanceAfterDeposit = testWallet.getBalance().sendAsync().join();
        BigInteger l1BalanceAfterDeposit = testWallet.getBalanceL1(baseToken).sendAsync().join();

        assertNotNull(hash);
        assertTrue(l2BalanceAfterDeposit.subtract(l2BalanceBeforeDeposit).compareTo(amount) >= 0);
        assertTrue(l1BalanceBeforeDeposit.subtract(l1BalanceAfterDeposit).compareTo(amount) >= 0);
    }
    @Test
    public void testDepositERC20() throws Exception {
        if (testWallet.isETHBasedChain()){
            String l2DAI = testWallet.l2TokenAddress(L1_DAI);

            BigInteger balanceBefore = testWallet.getBalance(l2DAI).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(5), null,null, null, null, null, null, null, true, null);
            EthSendTransaction hash = testWallet.deposit(transaction).sendAsync().join();
            TransactionReceipt receipt = processorL1.waitForTransactionReceipt(hash.getTransactionHash());
            String l2Hash = zksync.getL2HashFromPriorityOp(receipt, zksync.zksMainContract().sendAsync().join().getResult());
            testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(l2Hash);
            BigInteger balanceAfter = testWallet.getBalance(l2DAI).sendAsync().join();

            assertNotNull(receipt);
            assertEquals(0, balanceAfter.subtract(balanceBefore).compareTo(BigInteger.valueOf(5)));
        }else{
            String l2DAI = testWallet.l2TokenAddress(L1_DAI);

            BigInteger balanceBefore = testWallet.getBalance(l2DAI).sendAsync().join();

            DepositTransaction transaction = new DepositTransaction(L1_DAI, BigInteger.valueOf(5), true, true);
            EthSendTransaction hash = testWallet.deposit(transaction).sendAsync().join();
            TransactionReceipt receipt = processorL1.waitForTransactionReceipt(hash.getTransactionHash());
            String l2Hash = zksync.getL2HashFromPriorityOp(receipt, zksync.zksMainContract().sendAsync().join().getResult());
            testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(l2Hash);
            BigInteger balanceAfter = testWallet.getBalance(l2DAI).sendAsync().join();

            assertNotNull(receipt);
            assertEquals(0, balanceAfter.subtract(balanceBefore).compareTo(BigInteger.valueOf(5)));
        }
    }

    @Test
    public void testTransferEth() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);

        BigInteger balanceBeforeTransfer = testWallet.getBalance(RECEIVER, ZkSyncAddresses.LEGACY_ETH_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, signer.getAddress());
        TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger balanceAfterTransfer = testWallet.getBalance(RECEIVER, ZkSyncAddresses.LEGACY_ETH_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

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
    public void testTransferBaseToken() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);

        BigInteger balanceBeforeTransfer = testWallet.getBalance(RECEIVER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, signer.getAddress());
        TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger balanceAfterTransfer = testWallet.getBalance(RECEIVER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

        assertNotNull(receipt);
        assertEquals(balanceAfterTransfer.subtract(balanceBeforeTransfer), amount);
    }
    @Test
    @Disabled
    public void testTransferEthWithPaymaster() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);
        PaymasterParams paymasterParams = new PaymasterParams(PAYMASTER, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(TOKEN, BigInteger.ONE, new byte[] {}))));

        BigInteger paymasterBeforeTransfer = testWallet.getBalance(PAYMASTER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS).sendAsync().join();
        BigInteger paymasterTokenBeforeTransfer = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBalanceBeforeTransfer = testWallet.getBalance(ZkSyncAddresses.LEGACY_ETH_ADDRESS).sendAsync().join();
        BigInteger senderApprovalBeforeTransfer = testWallet.getBalance(TOKEN).sendAsync().join();
        BigInteger receiverBefore = testWallet.getBalance(RECEIVER, ZkSyncAddresses.LEGACY_ETH_ADDRESS).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, signer.getAddress(), paymasterParams);
        transaction.tokenAddress = ZkSyncAddresses.LEGACY_ETH_ADDRESS;
        TransactionReceipt receipt = testWallet.transfer(transaction).sendAsync().join();
        testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger paymasterAfterTransfer = testWallet.getBalance(PAYMASTER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS).sendAsync().join();
        BigInteger paymasterTokenAfterTransfer = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBalanceAfterTransfer = testWallet.getBalance(ZkSyncAddresses.LEGACY_ETH_ADDRESS).sendAsync().join();
        BigInteger senderApprovalAfterTransfer = testWallet.getBalance(TOKEN).sendAsync().join();
        BigInteger receiverAfter = testWallet.getBalance(RECEIVER, ZkSyncAddresses.LEGACY_ETH_ADDRESS).sendAsync().join();

        assertNotNull(receipt);
        assertTrue(paymasterBeforeTransfer.subtract(paymasterAfterTransfer).compareTo(BigInteger.ZERO) >= 0);
        assertTrue(paymasterTokenAfterTransfer.subtract(paymasterTokenBeforeTransfer).compareTo(BigInteger.ONE) == 0);
        assertTrue(senderBalanceBeforeTransfer.subtract(senderBalanceAfterTransfer).compareTo(amount) == 0);
        assertTrue(receiverAfter.subtract(receiverBefore).compareTo(amount) == 0);
        assertTrue(senderApprovalBeforeTransfer.subtract(senderApprovalAfterTransfer).compareTo(BigInteger.ONE) == 0);
    }
    @Test
    @Disabled
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

        WithdrawTransaction transaction = new WithdrawTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, amount, testWallet.getAddress());
        TransactionReceipt result = testWallet.withdraw(transaction).sendAsync().join();
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessor().waitFinalized(result.getTransactionHash());

        assertFalse(testWallet.isWithdrawalFinalized(receipt.getTransactionHash(), 0).join());
        EthSendTransaction finalizeWithdraw = testWallet.finalizeWithdraw(receipt.getTransactionHash(), 0).join();
        assertNotNull(finalizeWithdraw.getResult());
        BigInteger senderAfter = testWallet.getBalance().sendAsync().join();

        assertNotNull(receipt);
        assertNotNull(finalizeWithdraw);
        assertTrue(senderBefore.subtract(senderAfter).compareTo(amount) >= 0);
    }
    @Test
    @Disabled
    public void testWithdrawEthWithPaymaster() throws Exception {
        BigInteger amount = BigInteger.valueOf(5);
        PaymasterParams paymasterParams = new PaymasterParams(PAYMASTER, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(TOKEN, BigInteger.ONE, new byte[] {}))));

        BigInteger paymasterTokenBefore = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBefore = testWallet.getBalance(ZkSyncAddresses.LEGACY_ETH_ADDRESS).sendAsync().join();
        BigInteger senderApprovalBefore = testWallet.getBalance(TOKEN).sendAsync().join();

        WithdrawTransaction transaction = new WithdrawTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, amount, paymasterParams);
        TransactionReceipt result = testWallet.withdraw(transaction).sendAsync().join();
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessor().waitForTransactionReceipt(result.getTransactionHash());

        BigInteger paymasterTokenAfter = testWallet.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderAfter = testWallet.getBalance(ZkSyncAddresses.LEGACY_ETH_ADDRESS).sendAsync().join();
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
        TransactionReceipt receipt = testWallet.getTransactionReceiptProcessor().waitFinalized(result.getTransactionHash());

        EthSendTransaction finalizeWithdraw = testWallet.finalizeWithdraw(receipt.getTransactionHash(), 0).join();
        assertNotNull(finalizeWithdraw.getResult());
        BigInteger senderAfter = testWallet.getBalance(l2DAI).sendAsync().join();

        assertNotNull(receipt);
        assertNotNull(finalizeWithdraw);
        assertEquals(senderBefore.subtract(senderAfter), amount);
    }
    @Test
    @Disabled
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
}
