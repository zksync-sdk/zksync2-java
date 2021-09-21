package io.zksync.abi;

import java.math.BigInteger;

import org.jetbrains.annotations.Nullable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.RawTransaction;
import org.web3j.utils.Numeric;

import io.zksync.crypto.signer.EthSigner;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.MigrateToPorter;
import io.zksync.transaction.Transaction;
import io.zksync.transaction.Transfer;
import io.zksync.transaction.Withdraw;
import io.zksync.wrappers.ZkSyncL2Proto;
import io.zksync.wrappers.ZkSyncL2Proto.CommonData;
import io.zksync.wrappers.ZkSyncL2Proto.DeployContractStruct;
import io.zksync.wrappers.ZkSyncL2Proto.ExecuteStruct;
import io.zksync.wrappers.ZkSyncL2Proto.MigrateToPorterStruct;
import io.zksync.wrappers.ZkSyncL2Proto.TransferStruct;
import io.zksync.wrappers.ZkSyncL2Proto.WithdrawStruct;

public class TransactionEncoder extends org.web3j.crypto.TransactionEncoder {

    public static <T extends Transaction> Function encodeToFunction(T transaction) {
        return encodeToFunction(transaction, null);
    }

    public static <T extends Transaction> Function encodeToFunction(T transaction, @Nullable EthSigner signer) {
        final String signature = signer != null ? signer.signTransaction(transaction).join() : "";
        switch (transaction.getType()) {
            case "DeployContract":
            {
                DeployContract deployContract = (DeployContract) transaction;
                DeployContractStruct deployContractStruct = new DeployContractStruct(deployContract);
                CommonData commonData = new CommonData(transaction, Numeric.hexStringToByteArray(signature));
                Function encodedFunction = ZkSyncL2Proto.encodeFunction(deployContractStruct, commonData);
                return encodedFunction;
            }
            case "Execute":
            {
                Execute execute = (Execute) transaction;
                ExecuteStruct executeStruct = new ExecuteStruct(execute);
                CommonData commonData = new CommonData(transaction, Numeric.hexStringToByteArray(signature));
                Function encodedFunction = ZkSyncL2Proto.encodeFunction(executeStruct, commonData);
                return encodedFunction;
            }
            case "MigrateToPorter":
            {
                MigrateToPorter migrateToPorter = (MigrateToPorter) transaction;
                MigrateToPorterStruct migrateToPorterStruct = new MigrateToPorterStruct(migrateToPorter);
                CommonData commonData = new CommonData(transaction, Numeric.hexStringToByteArray(signature));
                Function encodedFunction = ZkSyncL2Proto.encodeFunction(migrateToPorterStruct, commonData);
                return encodedFunction;
            }
            case "Transfer":
            {
                Transfer transfer = (Transfer) transaction;
                TransferStruct transferStruct = new TransferStruct(transfer);
                CommonData commonData = new CommonData(transaction, Numeric.hexStringToByteArray(signature));
                Function encodedFunction = ZkSyncL2Proto.encodeFunction(transferStruct, commonData);
                return encodedFunction;
            }
            case "Withdraw":
            {
                Withdraw withdraw = (Withdraw) transaction;
                WithdrawStruct withdrawStruct = new WithdrawStruct(withdraw);
                CommonData commonData = new CommonData(transaction, Numeric.hexStringToByteArray(signature));
                Function encodedFunction = ZkSyncL2Proto.encodeFunction(withdrawStruct, commonData);
                return encodedFunction;
            }

            default: throw new IllegalArgumentException(String.format("Unsupported transaction type: %s", transaction.getType()));
        }
    }

    public static <T extends Transaction> RawTransaction encodeToRawTransaction(T transaction) {
        return encodeToRawTransaction(transaction, null);
    }

    public static <T extends Transaction> RawTransaction encodeToRawTransaction(T transaction, @Nullable EthSigner signer) {
        Function encodedFunction = encodeToFunction(transaction, signer);
        return RawTransaction.createTransaction(
                transaction.getNonceNumber(),
                BigInteger.ZERO,
                BigInteger.ZERO,
                ZkSyncL2Proto.ZK_SYNC_CORE_ADDRESS,
                BigInteger.ZERO,
                FunctionEncoder.encode(encodedFunction)
            );
    }
    
}
