package io.zksync.transaction.fee;

import io.zksync.methods.request.Transaction;
import io.zksync.protocol.core.Token;
import java.math.BigInteger;
import org.web3j.tx.gas.ContractGasProvider;

public interface ZkTransactionFeeProvider extends ContractGasProvider {

  Fee getFee(Transaction transaction);

  BigInteger getGasLimit(Transaction transaction);

  Token getFeeToken();
}
