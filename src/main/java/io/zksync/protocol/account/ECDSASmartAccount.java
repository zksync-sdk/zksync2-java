package io.zksync.protocol.account;

import io.zksync.protocol.ZkSync;
import io.zksync.utils.smart.account.PopulateTransactionECDSA;
import io.zksync.utils.smart.account.SignPayloadWithECDSA;

import java.util.Arrays;

public class ECDSASmartAccount extends SmartAccount{
    public ECDSASmartAccount(ZkSync provider, String address, String secret) {
        super(provider, Arrays.asList(secret), address, new SignPayloadWithECDSA(), new PopulateTransactionECDSA());
    }
}
