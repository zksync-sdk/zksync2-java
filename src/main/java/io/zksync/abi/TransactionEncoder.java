package io.zksync.abi;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import io.zksync.crypto.eip712.Eip712Domain;
import io.zksync.crypto.signer.EthSigner;
import io.zksync.crypto.signer.PrivateKeyEthSigner;
import io.zksync.transaction.TransactionRequest;
import io.zksync.transaction.type.Transaction712;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.crypto.transaction.type.ITransaction;
import org.web3j.crypto.transaction.type.TransactionType;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

public class TransactionEncoder extends org.web3j.crypto.TransactionEncoder {

    public static Sign.SignatureData getSignatureData(String signature) {
        byte[] signatureRaw = Numeric.hexStringToByteArray(signature);

        return new Sign.SignatureData(
                Arrays.copyOfRange(signatureRaw, 64, signatureRaw.length),
                Arrays.copyOfRange(signatureRaw, 0, 32),
                Arrays.copyOfRange(signatureRaw, 32, 64)
        );
    }

    public static Sign.SignatureData signMessage(TransactionRequest transactionRequest, Eip712Domain domain, Credentials credentials) {
        EthSigner signer = new PrivateKeyEthSigner(credentials, domain);
        String signature = signer.signTypedData(domain, transactionRequest).join();
        return getSignatureData(signature);
    }

    public static byte[] encode(ITransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> values = rawTransaction.asRlpValues(signatureData);
        RlpList rlpList = new RlpList(values);
        byte[] encoded = RlpEncoder.encode(rlpList);
        if (!rawTransaction.getType().equals(TransactionType.LEGACY)) {
            return ByteBuffer.allocate(encoded.length + 1)
                    .put(rawTransaction.getType().getRlpType())
                    .put(encoded)
                    .array();
        } else if (rawTransaction instanceof Transaction712) {
            return ByteBuffer.allocate(encoded.length + 1)
                    .put(Transaction712.EIP_712_TX_TYPE)
                    .put(encoded)
                    .array();
        }
        return encoded;
    }
    
}
