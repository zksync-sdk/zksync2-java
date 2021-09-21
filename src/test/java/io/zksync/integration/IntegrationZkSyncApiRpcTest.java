package io.zksync.integration;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import io.zksync.protocol.core.TimeRange;
import io.zksync.protocol.core.domain.fee.TransactionFeeDetails;
import io.zksync.protocol.core.domain.fee.TransactionFeeRequest;
import io.zksync.protocol.provider.Provider;
import io.zksync.protocol.transport.HttpTransport;
import io.zksync.transaction.Transfer;

public class IntegrationZkSyncApiRpcTest {

    Provider zksync;

    @Before
    public void setUp() {
        this.zksync = Provider.build(new HttpTransport("http://127.0.0.1:3030"));
    }

    @Test
    public void testEstimateFee() {
        Transfer zkTransfer = new Transfer(
            "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
            "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
            BigInteger.ZERO,
            "0xe1fab3efd74a77c23b426c302d96372140ff7d0c",
            "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
            BigInteger.ZERO,
            0,
            new TimeRange()
        );

        TransactionFeeDetails fee = this.zksync.getTransactionFee(TransactionFeeRequest.build(zkTransfer.getInitiatorAddress().getValue(), zkTransfer), zkTransfer.getFeeToken().getValue());

        System.out.println(fee.getTotalFee());
    }
}
