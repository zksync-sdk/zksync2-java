package io.zksync.transaction;

import io.zksync.crypto.eip712.Structurable;
import io.zksync.transaction.type.Transaction712;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TransactionRequest implements Structurable {
    static final String TRANSACTION_REQUEST_TYPE = "TransactionRequest";

    private String to;
    private BigInteger nonce;
    private BigInteger value;
    private byte[] data;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private BigInteger ergsPerStorage;
    private BigInteger ergsPerPubdata;
    private String feeToken;
    private String withdrawToken;

    private TransactionRequest(Transaction transaction) {
        this.nonce = transaction.getNonceNumber();
        this.gasLimit = transaction.getFee().getErgsLimitNumber();
        this.gasPrice = transaction.getFee().getErgsPriceLimitNumber();

        this.feeToken = transaction.getFee().getFeeTokenString();
        this.ergsPerPubdata = transaction.getFee().getErgsPerPubdataLimitNumber();
        this.ergsPerStorage = transaction.getFee().getErgsPerStorageLimitNumber();
    }

    public TransactionRequest(DeployContract deployContract) {
        this((io.zksync.transaction.Transaction) deployContract);

        this.to = Address.DEFAULT.getValue();
        this.data = deployContract.getInput();
    }

    public TransactionRequest(Execute execute) {
        this((io.zksync.transaction.Transaction) execute);

        this.to = execute.getContractAddressString();
        this.data = execute.getCalldata();
    }

    public TransactionRequest(Withdraw withdraw) {
        this((io.zksync.transaction.Transaction) withdraw);

        this.to = withdraw.getToString();
        this.value = withdraw.getAmountNumber();
        this.withdrawToken = withdraw.getTokenString();
    }

    public static TransactionRequest from(Transaction712<?> transaction712) {
        Transaction transaction = transaction712.getTransaction();
        if (transaction instanceof DeployContract) {
            return new TransactionRequest((DeployContract) transaction);
        } else if (transaction instanceof Execute) {
            return new TransactionRequest((Execute) transaction);
        } else {
            return new TransactionRequest((Withdraw) transaction);
        }
    }

    @Override
    public String getType() {
        return TRANSACTION_REQUEST_TYPE;
    }

    @Override
    public List<Pair<String, Type<?>>> eip712types() {
        List<Pair<String, Type<?>>> result = new ArrayList<>(10);

        result.add(Pair.of("to", new Address(to)));
        result.add(Pair.of("nonce", new Uint256(nonce)));
        result.add(Pair.of("value", value != null ? new Uint256(value) : Uint256.DEFAULT));
        result.add(Pair.of("data", data != null ? new DynamicBytes(data) : DynamicBytes.DEFAULT));
        result.add(Pair.of("gasPrice", new Uint256(gasPrice)));
        result.add(Pair.of("gasLimit", new Uint256(gasLimit)));
        result.add(Pair.of("ergsPerStorage", new Uint256(ergsPerStorage)));
        result.add(Pair.of("ergsPerPubdata", new Uint256(ergsPerPubdata)));
        result.add(Pair.of("feeToken", new Address(feeToken)));
        result.add(Pair.of("withdrawToken", withdrawToken != null ? new Address(withdrawToken) : Address.DEFAULT));

        return result;
    }
}
