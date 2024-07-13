package io.zksync.utils.smart.account;

import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.transaction.type.Transaction712;
import org.web3j.crypto.Credentials;

import java.util.List;

public class SignPayloadWithMultipleECDSA implements ISignPayload {

    @Override
    public String sign(Transaction712 payload, List<String> secrets, long chainId) {
        String signature = "0x";
        for (String secret:
                secrets) {
            Credentials credentials = Credentials.create(secret);
            EthSigner signer = new PrivateKeyEthSigner(credentials, chainId);
            signature = signature + signer.getDomain().thenCompose(domain -> signer.signTypedData(domain, payload)).join().substring(2);
        }


        return signature;
    }
}
