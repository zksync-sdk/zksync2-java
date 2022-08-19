package io.zksync.transaction.type;

import io.zksync.methods.request.Eip712Meta;
import org.web3j.crypto.Sign;
import org.web3j.crypto.transaction.type.Transaction1559;
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

public class Transaction712 extends Transaction1559 {

    public static final byte EIP_712_TX_TYPE = (byte) 0x71;
    private final Eip712Meta meta;

    public Transaction712(long chainId, BigInteger nonce, BigInteger gasLimit, String to, BigInteger value, String data, BigInteger maxPriorityFeePerGas, BigInteger maxFeePerGas, Eip712Meta meta) {
        super(chainId, nonce, gasLimit, to, value, data, maxPriorityFeePerGas, maxFeePerGas);

        this.meta = meta;
    }


    @Override
    public List<RlpType> asRlpValues(Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(getNonce()));

        // add maxPriorityFeePerGas and maxFeePerGas if this is an EIP-1559 transaction
        result.add(RlpString.create(getMaxPriorityFeePerGas()));
        result.add(RlpString.create(getMaxFeePerGas()));// GasPrice

        result.add(RlpString.create(getGasLimit()));

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(getValue()));

        // value field will already be hex encoded, so we need to convert into binary first
        byte[] data = Numeric.hexStringToByteArray(getData());
        result.add(RlpString.create(data));

        // access list
        result.add(new RlpList());

        if (signatureData != null) {
            result.add(RlpString.create(Sign.getRecId(signatureData, getChainId()))); // 7
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR()))); // 8
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS()))); // 9
        }

        // ZkSync part

        result.add(RlpString.create(getChainId())); // 10

        result.add(RlpString.create("")); // From (currently empty) // 11

        BigInteger ergsPerPubdata = getErgsPerPubdata();
        result.add(RlpString.create(ergsPerPubdata)); //12

        List<RlpType> factoryDeps;

        if (getFactoryDeps() != null) {
            factoryDeps = Arrays.stream(getFactoryDeps())
                    .map(RlpString::create)
                    .collect(Collectors.toList());
        } else {
            factoryDeps = Collections.emptyList();
        }

        result.add(new RlpList(factoryDeps)); // 13

        if (getMeta().getCustomSignature() != null) {
            result.add(RlpString.create(getMeta().getCustomSignature())); // 14
        } else {
            result.add(RlpString.create("")); // 14
        }

        List<RlpType> paymasterParams;

        if (getMeta().getPaymasterParams() != null) {
            paymasterParams = Arrays.asList(
                    RlpString.create(Numeric.hexStringToByteArray(getMeta().getPaymasterParams().getPaymaster())),
                    RlpString.create(getMeta().getPaymasterParams().getPaymasterInput())
            );
        } else {
            paymasterParams = Collections.emptyList();
        }

        result.add(new RlpList(paymasterParams)); // 15

        return result;
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
