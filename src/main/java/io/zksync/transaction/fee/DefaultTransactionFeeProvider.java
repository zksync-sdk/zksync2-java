package io.zksync.transaction.fee;

import org.web3j.crypto.RawTransaction;

import io.zksync.abi.TransactionEncoder;
import io.zksync.methods.request.ZksEstimateFeeRequest;
import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.methods.response.ZksFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class DefaultTransactionFeeProvider implements ZkTransactionFeeProvider {

    private ZkSync zksync;
    private Token feeToken;

    @Override
    @SneakyThrows
    public <T extends Transaction> ZksFee getFee(T transaction) {
        RawTransaction transactionForEstimate = TransactionEncoder.encodeToRawTransaction(transaction);
        ZksEstimateFee estimateFee = this.zksync.zksEstimateFee(ZksEstimateFeeRequest.fromRawTransaction(transactionForEstimate)).send();

        return estimateFee.getResult();
    }

    @Override
    public Token getFeeToken() {
        return this.feeToken;
    }
    
}
