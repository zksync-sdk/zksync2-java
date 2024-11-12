package io.zksync.protocol.account;

import io.zksync.protocol.ZkSync;
import io.zksync.utils.smart.account.PopulateTransactionECDSA;
import io.zksync.utils.smart.account.SignPayloadWithMultipleECDSA;

import java.util.List;

public class MultisigECDSASmartAccount extends SmartAccount{
    public MultisigECDSASmartAccount(ZkSync provider, String address, List<String> secrets) {
        super(provider, secrets, address, new SignPayloadWithMultipleECDSA(), new PopulateTransactionECDSA());
    }
}
