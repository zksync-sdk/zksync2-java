package io.zksync.integration.account;

import io.zksync.integration.BaseIntegrationEnv;
import io.zksync.methods.request.PaymasterParams;
import io.zksync.protocol.core.ZkBlockParameterName;
import io.zksync.transaction.type.TransferTransaction;
import io.zksync.transaction.type.WithdrawTransaction;
import io.zksync.utils.Paymaster;
import io.zksync.utils.ZkSyncAddresses;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class SmartAccountTest extends BaseIntegrationEnv {
    @Test
    public void testTransferBaseToken() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);

        BigInteger balanceBeforeTransfer = smartAccount.getBalance(RECEIVER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, smartAccount.getAddress());
        TransactionReceipt receipt = smartAccount.transfer(transaction).sendAsync().join();
        smartAccount.transactionReceiptProcessor.waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger balanceAfterTransfer = smartAccount.getBalance(RECEIVER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

        assertNotNull(receipt);
        assertEquals(balanceAfterTransfer.subtract(balanceBeforeTransfer), amount);
    }
    @Test
    public void testTransferEthOnNonEthChain() throws TransactionException, IOException {
        if (!testWallet.isETHBasedChain()){
            BigInteger amount = BigInteger.valueOf(7_000_000_000L);

            BigInteger balanceBeforeTransfer = smartAccount.getBalance(RECEIVER, ZkSyncAddresses.LEGACY_ETH_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

            TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, smartAccount.getAddress(), ZkSyncAddresses.LEGACY_ETH_ADDRESS);
            TransactionReceipt receipt = smartAccount.transfer(transaction).sendAsync().join();
            smartAccount.transactionReceiptProcessor.waitForTransactionReceipt(receipt.getTransactionHash());

            BigInteger balanceAfterTransfer = smartAccount.getBalance(RECEIVER, ZkSyncAddresses.LEGACY_ETH_ADDRESS, ZkBlockParameterName.COMMITTED).sendAsync().join();

            assertNotNull(receipt);
            assertEquals(balanceAfterTransfer.subtract(balanceBeforeTransfer), amount);
        }
    }
    @Test
    public void testTransferErc20() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(5);
        String l2DAI = testWallet.l2TokenAddress(L1_DAI);

        BigInteger balanceBeforeTransfer = smartAccount.getBalance(RECEIVER, l2DAI, ZkBlockParameterName.COMMITTED).sendAsync().join();
        BigInteger senderBefore = smartAccount.getBalance(l2DAI).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, smartAccount.getAddress(), l2DAI);
        TransactionReceipt receipt = smartAccount.transfer(transaction).sendAsync().join();
        smartAccount.transactionReceiptProcessor.waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger balanceAfterTransfer = smartAccount.getBalance(RECEIVER, l2DAI, ZkBlockParameterName.COMMITTED).sendAsync().join();
        BigInteger senderAfter = smartAccount.getBalance(l2DAI).sendAsync().join();

        assertNotNull(receipt);
        assertEquals(senderBefore.subtract(senderAfter), amount);
        assertEquals(balanceAfterTransfer.subtract(balanceBeforeTransfer), amount);
    }

    @Test
    @Disabled
    public void testTransferEthWithPaymaster() throws TransactionException, IOException {
        BigInteger amount = BigInteger.valueOf(7_000_000_000L);
        PaymasterParams paymasterParams = new PaymasterParams(PAYMASTER, Numeric.hexStringToByteArray(FunctionEncoder.encode(Paymaster.encodeApprovalBased(TOKEN, BigInteger.ONE, new byte[] {}))));

        BigInteger paymasterBeforeTransfer = smartAccount.getBalance(PAYMASTER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS).sendAsync().join();
        BigInteger paymasterTokenBeforeTransfer = smartAccount.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBalanceBeforeTransfer = smartAccount.getBalance().sendAsync().join();
        BigInteger senderApprovalBeforeTransfer = smartAccount.getBalance(TOKEN).sendAsync().join();
        BigInteger receiverBefore = smartAccount.getBalance(RECEIVER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS).sendAsync().join();

        TransferTransaction transaction = new TransferTransaction(RECEIVER, amount, smartAccount.getAddress(), paymasterParams);
        TransactionReceipt receipt = smartAccount.transfer(transaction).sendAsync().join();
        smartAccount.transactionReceiptProcessor.waitForTransactionReceipt(receipt.getTransactionHash());

        BigInteger paymasterAfterTransfer = smartAccount.getBalance(PAYMASTER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS).sendAsync().join();
        BigInteger paymasterTokenAfterTransfer = smartAccount.getBalance(PAYMASTER, TOKEN).sendAsync().join();
        BigInteger senderBalanceAfterTransfer = smartAccount.getBalance().sendAsync().join();
        BigInteger senderApprovalAfterTransfer = smartAccount.getBalance(TOKEN).sendAsync().join();
        BigInteger receiverAfter = smartAccount.getBalance(RECEIVER, ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS).sendAsync().join();

        assertNotNull(receipt);
        assertTrue(paymasterBeforeTransfer.subtract(paymasterAfterTransfer).compareTo(BigInteger.ZERO) >= 0);
        assertTrue(paymasterTokenAfterTransfer.subtract(paymasterTokenBeforeTransfer).compareTo(BigInteger.ONE) == 0);
        assertTrue(senderBalanceBeforeTransfer.subtract(senderBalanceAfterTransfer).compareTo(amount) == 0);
        assertTrue(receiverAfter.subtract(receiverBefore).compareTo(amount) == 0);
        assertTrue(senderApprovalBeforeTransfer.subtract(senderApprovalAfterTransfer).compareTo(BigInteger.ONE) == 0);
    }

    @Test
    public void testWithdrawEth() throws Exception {
        if (testWallet.isETHBasedChain()){
            BigInteger amount = BigInteger.valueOf(7_000_000_000L);

            BigInteger senderBefore = smartAccount.getBalance().sendAsync().join();

            WithdrawTransaction transaction = new WithdrawTransaction(ZkSyncAddresses.LEGACY_ETH_ADDRESS, amount, smartAccount.getAddress());
            TransactionReceipt result = smartAccount.withdraw(transaction).sendAsync().join();
            TransactionReceipt receipt = smartAccount.transactionReceiptProcessor.waitFinalized(result.getTransactionHash());

            assertFalse(testWallet.isWithdrawalFinalized(receipt.getTransactionHash(), 0).join());
            EthSendTransaction finalizeWithdraw = testWallet.finalizeWithdraw(receipt.getTransactionHash(), 0).join();
            assertNotNull(finalizeWithdraw.getResult());
            BigInteger senderAfter = smartAccount.getBalance().sendAsync().join();

            assertNotNull(receipt);
            assertNotNull(finalizeWithdraw);
            assertTrue(senderBefore.subtract(senderAfter).compareTo(amount) >= 0);
        }else{
            BigInteger amount = BigInteger.valueOf(7_000_000_000L);
            String l2TokenAddress = testWallet.l2TokenAddress(ZkSyncAddresses.ETH_ADDRESS_IN_CONTRACTS);

            BigInteger senderBefore = smartAccount.getBalance(l2TokenAddress).sendAsync().join();

            WithdrawTransaction transaction = new WithdrawTransaction(l2TokenAddress, amount, smartAccount.getAddress());
            TransactionReceipt result = smartAccount.withdraw(transaction).sendAsync().join();
            TransactionReceipt receipt = smartAccount.transactionReceiptProcessor.waitFinalized(result.getTransactionHash());

            assertFalse(testWallet.isWithdrawalFinalized(receipt.getTransactionHash(), 0).join());
            EthSendTransaction finalizeWithdraw = testWallet.finalizeWithdraw(receipt.getTransactionHash(), 0).join();
            assertNotNull(finalizeWithdraw.getResult());
            BigInteger senderAfter = smartAccount.getBalance(l2TokenAddress).sendAsync().join();

            assertNotNull(receipt);
            assertNotNull(finalizeWithdraw);
            assertTrue(senderBefore.subtract(senderAfter).compareTo(amount) >= 0);
        }
    }

    @Test
    public void testWithdrawBaseToken() throws Exception {
        if (!testWallet.isETHBasedChain()){
            BigInteger amount = BigInteger.valueOf(7_000_000_000L);

            BigInteger senderBefore = smartAccount.getBalance().sendAsync().join();

            WithdrawTransaction transaction = new WithdrawTransaction(ZkSyncAddresses.L2_BASE_TOKEN_ADDRESS, amount, smartAccount.getAddress());
            TransactionReceipt result = smartAccount.withdraw(transaction).sendAsync().join();
            TransactionReceipt receipt = smartAccount.transactionReceiptProcessor.waitFinalized(result.getTransactionHash());

            assertFalse(testWallet.isWithdrawalFinalized(receipt.getTransactionHash(), 0).join());
            EthSendTransaction finalizeWithdraw = testWallet.finalizeWithdraw(receipt.getTransactionHash(), 0).join();
            assertNotNull(finalizeWithdraw.getResult());
            BigInteger senderAfter = smartAccount.getBalance().sendAsync().join();

            assertNotNull(receipt);
            assertNotNull(finalizeWithdraw);
            assertTrue(senderBefore.subtract(senderAfter).compareTo(amount) >= 0);
        }
    }

    @Test
    public void testWithdrawErc20() throws Exception {
        BigInteger amount = BigInteger.valueOf(5);
        String l2DAI = testWallet.l2TokenAddress(L1_DAI);

        BigInteger senderBefore = smartAccount.getBalance(l2DAI).sendAsync().join();

        WithdrawTransaction transaction = new WithdrawTransaction(l2DAI, amount, smartAccount.getAddress());
        TransactionReceipt result = smartAccount.withdraw(transaction).sendAsync().join();
        TransactionReceipt receipt = smartAccount.transactionReceiptProcessor.waitFinalized(result.getTransactionHash());

        EthSendTransaction finalizeWithdraw = testWallet.finalizeWithdraw(receipt.getTransactionHash(), 0).join();
        assertNotNull(finalizeWithdraw.getResult());
        BigInteger senderAfter = smartAccount.getBalance(l2DAI).sendAsync().join();

        assertNotNull(receipt);
        assertNotNull(finalizeWithdraw);
        assertEquals(senderBefore.subtract(senderAfter), amount);
    }
}
