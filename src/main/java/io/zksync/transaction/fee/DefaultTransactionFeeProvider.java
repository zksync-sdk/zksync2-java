package io.zksync.transaction.fee;

import io.zksync.methods.response.ZksEstimateFee;
import io.zksync.protocol.ZkSync;
import io.zksync.protocol.core.Token;
import io.zksync.protocol.exceptions.JsonRpcResponseException;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.Transaction;
import io.zksync.transaction.Withdraw;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class DefaultTransactionFeeProvider implements ZkTransactionFeeProvider {

    private ZkSync zksync;
    private Token feeToken;

    @Override
    @SneakyThrows
    public <T extends Transaction> Fee getFee(T transaction) {
        ZksEstimateFee estimateFee;
        if (transaction instanceof DeployContract) {
            estimateFee = this.zksync.zksEstimateFee(new io.zksync.methods.request.Transaction((DeployContract) transaction)).send();
        } else if (transaction instanceof Execute) {
            estimateFee = this.zksync.zksEstimateFee(new io.zksync.methods.request.Transaction((Execute) transaction)).send();
        } else {
            estimateFee = this.zksync.zksEstimateFee(new io.zksync.methods.request.Transaction((Withdraw) transaction)).send();
        }

        if (estimateFee.hasError()) {
            throw new JsonRpcResponseException(estimateFee);
        }

        return estimateFee.getResult();
    }

    @Override
    public Token getFeeToken() {
        return this.feeToken;
    }
    
}
