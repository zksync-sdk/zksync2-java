package io.zksync.transaction.type;

import io.zksync.crypto.eip712.Structurable;
import io.zksync.methods.request.Eip712Meta;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.crypto.transaction.type.Transaction1559;
import org.web3j.crypto.transaction.type.TransactionType;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Transaction712 extends Transaction1559 implements Structurable {
    static final String TRANSACTION_TYPE = "Transaction";
    public static final byte EIP_712_TX_TYPE = (byte) 0x71;
    private final String from;
    private final Eip712Meta meta;

    public Transaction712(long chainId, BigInteger nonce, BigInteger gasLimit, String to, BigInteger value, String data, BigInteger maxPriorityFeePerGas, BigInteger maxFeePerGas, String from, Eip712Meta meta) {
        super(chainId, nonce, gasLimit, to, value, data, maxPriorityFeePerGas, maxFeePerGas);
        this.from = from;
        this.meta = meta;
    }


    @Override
    public List<RlpType> asRlpValues(Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(getNonce())); // 0

        // add maxPriorityFeePerGas and maxFeePerGas if this is an EIP-1559 transaction
        result.add(RlpString.create(getMaxPriorityFeePerGas())); // 1
        result.add(RlpString.create(getMaxFeePerGas()));// GasPrice // 2

        result.add(RlpString.create(getGasLimit())); // 3

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to))); // 4
        } else {
            result.add(RlpString.create("")); // 4
        }

        result.add(RlpString.create(getValue())); // 5

        // value field will already be hex encoded, so we need to convert into binary first
        byte[] data = Numeric.hexStringToByteArray(getData());
        result.add(RlpString.create(data)); // 6

        // ZkSync part

        result.add(RlpString.create(getChainId())); //7
        result.add(RlpString.create("")); // 8
        result.add(RlpString.create("")); // 9

        result.add(RlpString.create(getChainId())); // 10

        result.add(RlpString.create(Numeric.hexStringToByteArray(getFrom()))); // 11

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
            final ByteArrayOutputStream output = new ByteArrayOutputStream();

            try {
                output.write(signatureData.getR());
                output.write(signatureData.getS());
                output.write(signatureData.getV());
            } catch (IOException e) {
                throw new IllegalStateException("Error when creating ETH signature", e);
            }
            result.add(RlpString.create(output.toByteArray())); // 14
        }

        List<RlpType> paymasterParams;

        if (getMeta().getPaymasterParams() != null && getPaymaster() != null && getPaymasterInput() != null) {
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

    public String getFrom() {
        return from;
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

    public String getPaymaster() {
        return getMeta() != null && getMeta().getPaymasterParams() != null ? getMeta().getPaymasterParams().getPaymaster() : null;
    }

    public byte[] getPaymasterInput() {
        return getMeta() != null && getMeta().getPaymasterParams() != null ? getMeta().getPaymasterParams().getPaymasterInput() : null;
    }

    @Override
    public TransactionType getType() {
        // NOTE: Transaction type encoded manually in `asRlpValues`
        return TransactionType.LEGACY;
    }

    @Override
    public String getTypeName() {
        return TRANSACTION_TYPE;
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> result = new ArrayList<>(13);

        result.add(Pair.of("txType", new Uint256(Transaction712.EIP_712_TX_TYPE)));
        result.add(Pair.of("from", new Uint256(Numeric.toBigInt(getFrom()))));
        result.add(Pair.of("to", getTo() != null ? new Uint256(Numeric.toBigInt(getTo())) : Uint256.DEFAULT));
        result.add(Pair.of("ergsLimit", new Uint256(getGasLimit())));
        result.add(Pair.of("ergsPerPubdataByteLimit", new Uint256(getErgsPerPubdata())));
        result.add(Pair.of("maxFeePerErg", new Uint256(getMaxFeePerGas())));
        result.add(Pair.of("maxPriorityFeePerErg", new Uint256(getMaxPriorityFeePerGas())));
        result.add(Pair.of("paymaster", getPaymaster() != null ? new Uint256(Numeric.toBigInt(getPaymaster())) : Uint256.DEFAULT));
        result.add(Pair.of("nonce", new Uint256(getNonce())));
        result.add(Pair.of("value", getValue() != null ? new Uint256(getValue()) : Uint256.DEFAULT));
        result.add(Pair.of("data", getData() != null ? new DynamicBytes(Numeric.hexStringToByteArray(getData())) : DynamicBytes.DEFAULT));
        result.add(Pair.of("factoryDeps", getFactoryDepsHashes()));
        result.add(Pair.of("paymasterInput", getPaymasterInput() != null ? new DynamicBytes(getPaymasterInput()) : DynamicBytes.DEFAULT));

        return result;
    }

    private DynamicArray<Bytes32> getFactoryDepsHashes() {
        if (getFactoryDeps() != null) {
            return new DynamicArray<>(Bytes32.class, Arrays.stream(getFactoryDeps())
                    .map(Hash::sha3)
                    .map(Bytes32::new)
                    .collect(Collectors.toList()));
        } else {
            return new DynamicArray<>(Bytes32.class, Collections.emptyList());
        }
    }
}
