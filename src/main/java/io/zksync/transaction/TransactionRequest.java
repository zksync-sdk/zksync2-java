package io.zksync.transaction;

import io.zksync.crypto.eip712.Structurable;
import io.zksync.methods.request.AAParams;
import io.zksync.methods.request.Eip712Meta;
import io.zksync.transaction.fee.Fee;
import io.zksync.transaction.type.Transaction712;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class TransactionRequest implements Structurable {
    // TODO: update according etc/system-contracts/contracts/TransactionHelper.sol
    static final String TRANSACTION_REQUEST_TYPE = "TransactionRequest";

    private String to;
    private BigInteger nonce;
    private BigInteger value;
    private byte[] data;
    private BigInteger ergsLimit;
    private String feeToken;
    private BigInteger ergsPerPubdata;
    private BigInteger ergsPrice;

    @Deprecated
    private TransactionRequest(Transaction transaction) {
        this.nonce = transaction.getNonceNumber();
        this.ergsLimit = transaction.getFee().getErgsLimitNumber();
        this.ergsPrice = transaction.getFee().getErgsPriceLimitNumber();
    }

    @Deprecated
    public TransactionRequest(Execute execute) {
        this((io.zksync.transaction.Transaction) execute);

        this.to = execute.getContractAddressString();
        this.data = execute.getCalldata();
    }

    public static TransactionRequest from(Transaction712 transaction) {
        return new TransactionRequest(
                transaction.getTo(),
                transaction.getNonce(),
                transaction.getValue(),
                Numeric.hexStringToByteArray(transaction.getData()),
                transaction.getGasLimit(),
                transaction.getFeeToken(),
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
        List<Pair<String, Type<?>>> result = new ArrayList<>(10);

        result.add(Pair.of("txType", new Uint8(Transaction712.EIP_712_TX_TYPE)));
        result.add(Pair.of("to", new Address(to)));
        result.add(Pair.of("value", value != null ? new Uint256(value) : Uint256.DEFAULT));
        result.add(Pair.of("data", data != null ? new DynamicBytes(data) : DynamicBytes.DEFAULT));
        result.add(Pair.of("feeToken", new Address(feeToken)));
        result.add(Pair.of("ergsLimit", new Uint256(ergsLimit)));
        result.add(Pair.of("ergsPerPubdataByteLimit", new Uint256(ergsPerPubdata)));
        result.add(Pair.of("ergsPrice", new Uint256(ergsPrice)));
        result.add(Pair.of("nonce", new Uint256(nonce)));

        return result;
    }
}
