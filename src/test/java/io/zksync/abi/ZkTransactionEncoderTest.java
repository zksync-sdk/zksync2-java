package io.zksync.abi;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.utils.Convert.Unit;

import io.zksync.helper.CounterContract;
import io.zksync.protocol.core.AccountType;
import io.zksync.protocol.core.TimeRange;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.Withdraw;

public class ZkTransactionEncoderTest {

//    private static final Token ETH = Token.createETH();
//
//    Credentials credentials;
//
//    @Before
//    public void setUp() {
//        this.credentials = Credentials.create(ECKeyPair.create(BigInteger.ONE));
//    }
//
//    @Test
//    public void testEncodeTransfer() {
//        Transfer zkTransfer = new Transfer(
//            ETH.getAddress(),
//            this.credentials.getAddress(),
//            Convert.toWei("1", Unit.ETHER).toBigInteger(),
//            this.credentials.getAddress(),
//            ETH.getAddress(),
//            BigInteger.ZERO,
//            0,
//            new TimeRange()
//        );
//
//        Function function = TransactionEncoder.encodeToFunction(zkTransfer);
//
//        assertEquals("transfer", function.getName());
//
//        assertEquals(2, function.getInputParameters().size());
//
//        assertTrue(function.getInputParameters().get(0) instanceof ZkSyncL2Proto.TransferStruct);
//        assertTrue(function.getInputParameters().get(1) instanceof ZkSyncL2Proto.CommonData);
//
//        {
//            ZkSyncL2Proto.TransferStruct struct = (ZkSyncL2Proto.TransferStruct) function.getInputParameters().get(0);
//
//            assertEquals(zkTransfer.getAmountNumber(), struct.amount);
//            assertEquals(zkTransfer.getToString(), struct.to);
//            assertEquals(zkTransfer.getTokenString(), struct.token);
//        }
//
//        {
//            ZkSyncL2Proto.CommonData struct = (ZkSyncL2Proto.CommonData) function.getInputParameters().get(1);
//
//            assertEquals(zkTransfer.getInitiatorAddressString(), struct.initiator);
//            assertEquals(zkTransfer.getFeeNumber(), struct.fee);
//            assertEquals(zkTransfer.getFeeTokenString(), struct.feeToken);
//            assertEquals(zkTransfer.getNonceNumber(), struct.nonce);
//            assertEquals(zkTransfer.getValidIn().getFromNumber(), struct.validFrom);
//            assertEquals(zkTransfer.getValidIn().getUntilNumber(), struct.validTo);
//        }
//    }
//
//    @Test
//    public void testEncodeWithdraw() {
//        Withdraw zkWithdraw = new Withdraw(
//            ETH.getAddress(),
//            this.credentials.getAddress(),
//            Convert.toWei("1", Unit.ETHER).toBigInteger(),
//            this.credentials.getAddress(),
//            ETH.getAddress(),
//            BigInteger.ZERO,
//            0,
//            new TimeRange()
//        );
//
//        Function function = TransactionEncoder.encodeToFunction(zkWithdraw);
//
//        assertEquals("withdraw", function.getName());
//
//        assertEquals(2, function.getInputParameters().size());
//
//        assertTrue(function.getInputParameters().get(0) instanceof ZkSyncL2Proto.WithdrawStruct);
//        assertTrue(function.getInputParameters().get(1) instanceof ZkSyncL2Proto.CommonData);
//
//        {
//            ZkSyncL2Proto.WithdrawStruct struct = (ZkSyncL2Proto.WithdrawStruct) function.getInputParameters().get(0);
//
//            assertEquals(zkWithdraw.getAmountNumber(), struct.amount);
//            assertEquals(zkWithdraw.getToString(), struct.to);
//            assertEquals(zkWithdraw.getTokenString(), struct.token);
//        }
//
//        {
//            ZkSyncL2Proto.CommonData struct = (ZkSyncL2Proto.CommonData) function.getInputParameters().get(1);
//
//            assertEquals(zkWithdraw.getInitiatorAddressString(), struct.initiator);
//            assertEquals(zkWithdraw.getFeeNumber(), struct.fee);
//            assertEquals(zkWithdraw.getFeeTokenString(), struct.feeToken);
//            assertEquals(zkWithdraw.getNonceNumber(), struct.nonce);
//            assertEquals(zkWithdraw.getValidIn().getFromNumber(), struct.validFrom);
//            assertEquals(zkWithdraw.getValidIn().getUntilNumber(), struct.validTo);
//        }
//    }
//
//    @Test
//    public void testEncodeMigrateToPorter() {
//        MigrateToPorter zkMigrateToPorter = new MigrateToPorter(
//            this.credentials.getAddress(),
//            this.credentials.getAddress(),
//            ETH.getAddress(),
//            BigInteger.ZERO,
//            0,
//            new TimeRange()
//        );
//
//        Function function = TransactionEncoder.encodeToFunction(zkMigrateToPorter);
//
//        assertEquals("migrateToPorter", function.getName());
//
//        assertEquals(2, function.getInputParameters().size());
//
//        assertTrue(function.getInputParameters().get(0) instanceof ZkSyncL2Proto.MigrateToPorterStruct);
//        assertTrue(function.getInputParameters().get(1) instanceof ZkSyncL2Proto.CommonData);
//
//        {
//            ZkSyncL2Proto.MigrateToPorterStruct struct = (ZkSyncL2Proto.MigrateToPorterStruct) function.getInputParameters().get(0);
//
//            assertEquals(zkMigrateToPorter.getAccountAddressString(), struct.accountAddress);
//        }
//
//        {
//            ZkSyncL2Proto.CommonData struct = (ZkSyncL2Proto.CommonData) function.getInputParameters().get(1);
//
//            assertEquals(zkMigrateToPorter.getInitiatorAddressString(), struct.initiator);
//            assertEquals(zkMigrateToPorter.getFeeNumber(), struct.fee);
//            assertEquals(zkMigrateToPorter.getFeeTokenString(), struct.feeToken);
//            assertEquals(zkMigrateToPorter.getNonceNumber(), struct.nonce);
//            assertEquals(zkMigrateToPorter.getValidIn().getFromNumber(), struct.validFrom);
//            assertEquals(zkMigrateToPorter.getValidIn().getUntilNumber(), struct.validTo);
//        }
//    }
//
//    @Test
//    public void testEncodeDeploy() {
//        DeployContract zkDeploy = new DeployContract(
//            AccountType.ZkRollup,
//            Numeric.toHexString(CounterContract.getCode()),
//            this.credentials.getAddress(),
//            ETH.getAddress(),
//            BigInteger.ZERO,
//            0,
//            new TimeRange()
//        );
//
//        Function function = TransactionEncoder.encodeToFunction(zkDeploy);
//
//        assertEquals("deployContract", function.getName());
//
//        assertEquals(2, function.getInputParameters().size());
//
//        assertTrue(function.getInputParameters().get(0) instanceof ZkSyncL2Proto.DeployContractStruct);
//        assertTrue(function.getInputParameters().get(1) instanceof ZkSyncL2Proto.CommonData);
//
//        {
//            ZkSyncL2Proto.DeployContractStruct struct = (ZkSyncL2Proto.DeployContractStruct) function.getInputParameters().get(0);
//
//            assertEquals(zkDeploy.getAccountType().getType().getValue(), struct.accountType);
//            assertArrayEquals(zkDeploy.getBytecode(), struct.bytecode);
//            assertArrayEquals(zkDeploy.getCalldata(), struct.calldata);
//        }
//
//        {
//            ZkSyncL2Proto.CommonData struct = (ZkSyncL2Proto.CommonData) function.getInputParameters().get(1);
//
//            assertEquals(zkDeploy.getInitiatorAddressString(), struct.initiator);
//            assertEquals(zkDeploy.getFeeNumber(), struct.fee);
//            assertEquals(zkDeploy.getFeeTokenString(), struct.feeToken);
//            assertEquals(zkDeploy.getNonceNumber(), struct.nonce);
//            assertEquals(zkDeploy.getValidIn().getFromNumber(), struct.validFrom);
//            assertEquals(zkDeploy.getValidIn().getUntilNumber(), struct.validTo);
//        }
//    }
//
//    @Test
//    public void testEncodeExecute() {
//        Execute zkExecute = new Execute(
//            "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
//            Numeric.toHexString(RandomUtils.nextBytes(32)),
//            "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
//            "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
//            BigInteger.ZERO,
//            0,
//            new TimeRange()
//        );
//
//        Function function = TransactionEncoder.encodeToFunction(zkExecute);
//
//        assertEquals("execute", function.getName());
//
//        assertEquals(2, function.getInputParameters().size());
//
//        assertTrue(function.getInputParameters().get(0) instanceof ZkSyncL2Proto.ExecuteStruct);
//        assertTrue(function.getInputParameters().get(1) instanceof ZkSyncL2Proto.CommonData);
//
//        {
//            ZkSyncL2Proto.ExecuteStruct struct = (ZkSyncL2Proto.ExecuteStruct) function.getInputParameters().get(0);
//
//            assertEquals(zkExecute.getContractAddressString(), struct.contractAddress);
//            assertArrayEquals(zkExecute.getCalldata(), struct.callData);
//        }
//
//        {
//            ZkSyncL2Proto.CommonData struct = (ZkSyncL2Proto.CommonData) function.getInputParameters().get(1);
//
//            assertEquals(zkExecute.getInitiatorAddressString(), struct.initiator);
//            assertEquals(zkExecute.getFeeNumber(), struct.fee);
//            assertEquals(zkExecute.getFeeTokenString(), struct.feeToken);
//            assertEquals(zkExecute.getNonceNumber(), struct.nonce);
//            assertEquals(zkExecute.getValidIn().getFromNumber(), struct.validFrom);
//            assertEquals(zkExecute.getValidIn().getUntilNumber(), struct.validTo);
//        }
//    }
}
