package io.zksync.transaction;

import io.zksync.crypto.eip712.Structurable;
import io.zksync.transaction.type.Transaction712;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class TransactionRequest implements Structurable {
    static final String TRANSACTION_REQUEST_TYPE = "Transaction";

    private String to;
    private BigInteger nonce;
    private BigInteger value;
    private byte[] data;
    private BigInteger ergsLimit;
    private BigInteger ergsPerPubdata;
    private BigInteger ergsPrice;

    public static TransactionRequest from(Transaction712 transaction) {
        return new TransactionRequest(
                transaction.getTo(),
                transaction.getNonce(),
                transaction.getValue(),
                Numeric.hexStringToByteArray(transaction.getData()),
                transaction.getGasLimit(),
                transaction.getErgsPerPubdata(),
                transaction.getGasPrice()
        );
    }

    @Override
    public String getType() {
        return TRANSACTION_REQUEST_TYPE;
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> result = new ArrayList<>(8);

        result.add(Pair.of("txType", new Uint8(Transaction712.EIP_712_TX_TYPE)));
        result.add(Pair.of("to", new Uint256(Numeric.toBigInt(to)))); // TODO: Keep in mind this value can be changed back
        result.add(Pair.of("value", value != null ? new Uint256(value) : Uint256.DEFAULT));
        result.add(Pair.of("data", data != null ? new DynamicBytes(data) : DynamicBytes.DEFAULT));
        result.add(Pair.of("ergsLimit", new Uint256(ergsLimit)));
        result.add(Pair.of("ergsPerPubdataByteLimit", new Uint256(ergsPerPubdata)));
        result.add(Pair.of("ergsPrice", new Uint256(ergsPrice)));
        result.add(Pair.of("nonce", new Uint256(nonce)));

        return result;
    }
}
