package io.zksync.transaction.type;

import io.zksync.methods.request.Eip712Meta;
import org.web3j.crypto.Sign;
import org.web3j.crypto.transaction.type.LegacyTransaction;
import org.web3j.crypto.transaction.type.TransactionType;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Bytes;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Transaction712 extends LegacyTransaction {

    public static final byte EIP_712_TX_TYPE = (byte) 0x71;

    private final long chainId;
    private final Eip712Meta meta;

    public Transaction712(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, long chainId, Eip712Meta meta) {
        super(nonce, gasPrice, gasLimit, to, value, data);
        this.chainId = chainId;
        this.meta = meta;
    }

    @Override
    public List<RlpType> asRlpValues(Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(getNonce())); //0

        result.add(RlpString.create(getGasPrice())); //1
        result.add(RlpString.create(getGasLimit())); //2

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to))); //3
        } else {
            result.add(RlpString.create("")); //3
        }

        result.add(RlpString.create(getValue())); //4

        // value field will already be hex encoded, so we need to convert into binary first
        byte[] data = Numeric.hexStringToByteArray(getData());
        result.add(RlpString.create(data)); //5

        if (signatureData != null) {
            byte[] v = signatureData.getV()[0] == (byte) 0 ? new byte[] {} : signatureData.getV();
            result.add(RlpString.create(v)); //6
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR()))); //7
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS()))); //8
        }

        // ZkSync part

        result.add(RlpString.create(getChainId())); //9

        BigInteger ergsPerPubdata = getErgsPerPubdata();
        result.add(RlpString.create(ergsPerPubdata)); //10

        List<RlpType> factoryDeps;

        if (getFactoryDeps() != null) {
            factoryDeps = Arrays.stream(getFactoryDeps())
                    .map(RlpString::create)
                    .collect(Collectors.toList());
        } else {
            factoryDeps = Collections.emptyList();
        }

        result.add(new RlpList(factoryDeps)); // 11

        List<RlpType> aaParams;

        if (getMeta().getAaParams() != null) {
            aaParams = Arrays.asList(
                    RlpString.create(Numeric.hexStringToByteArray(getMeta().getAaParams().getFrom())),
                    RlpString.create(getMeta().getAaParams().getSignature())
            );
        } else {
            aaParams = Collections.emptyList();
        }

        result.add(new RlpList(aaParams)); // 12

        List<RlpType> paymasterParams;

        if (getMeta().getPaymasterParams() != null) {
            paymasterParams = Arrays.asList(
                    RlpString.create(Numeric.hexStringToByteArray(getMeta().getPaymasterParams().getPaymaster())),
                    RlpString.create(getMeta().getPaymasterParams().getPaymasterInput())
            );
        } else {
            paymasterParams = Collections.emptyList();
        }

        result.add(new RlpList(paymasterParams)); // 13

        return result;
    }

    public long getChainId() {
        return chainId;
    }

    public BigInteger getErgsPerPubdata() {
        return meta.getErgsPerPubdataNumber();
    }

    public byte[][] getFactoryDeps() {
        return meta.getFactoryDeps();
    }

    public Eip712Meta getMeta() {
        return meta;
    }

    @Override
    public TransactionType getType() {
        // NOTE: Transaction type encoded manually in `asRlpValues`
        return TransactionType.LEGACY;
    }
}
