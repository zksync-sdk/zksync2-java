package io.zksync.utils.smart.account;

import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.transaction.type.Transaction712;
import org.web3j.crypto.Credentials;

import java.util.List;

public class SignPayloadWithECDSA implements ISignPayload {
    @Override
    public String sign(Transaction712 payload, List<String> secrets, long chainId) {
        Credentials credentials = Credentials.create(secrets.get(0));
        EthSigner signer = new PrivateKeyEthSigner(credentials, chainId);
        String signature = signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, payload)).join();

        return signature;
    }
}
