package io.zksync.transaction.type;

import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.Transaction;
import io.zksync.transaction.Withdraw;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Sign;
import org.web3j.crypto.transaction.type.ITransaction;
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

public class Transaction712<T extends Transaction> implements ITransaction {

    public static final byte EIP_712_TX_TYPE = (byte) 0x70;

    private final T transaction;
    private final long chainId;

    public Transaction712(
            long chainId,
            T transaction
    ) {
        this.chainId = chainId;
        this.transaction = transaction;
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
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getV()))); //6
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR()))); //7
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS()))); //8
        }

        // ZkSync part

        result.add(RlpString.create(getChainId())); //9

        String feeToken = transaction.getFee().getFeeTokenString();
        result.add(RlpString.create(Numeric.hexStringToByteArray(feeToken)));//10

        if (transaction.getType().equals(Withdraw.WITHDRAW_TYPE)) {
            Withdraw withdraw = (Withdraw) transaction;
            String withdrawToken = withdraw.getTokenString();

            result.add(RlpString.create(Numeric.hexStringToByteArray(withdrawToken))); //11
        } else {
            result.add(RlpString.create("")); //11
        }

        BigInteger ergsPerStorage = transaction.getFee().getErgsPerStorageLimitNumber();
        result.add(RlpString.create(ergsPerStorage)); //12

        BigInteger ergsPerPubdata = transaction.getFee().getErgsPerPubdataLimitNumber();
        result.add(RlpString.create(ergsPerPubdata)); //13

        List<RlpType> factoryDeps;

        if (transaction.getType().equals(DeployContract.DEPLOY_CONTRACT_TYPE)) {
            DeployContract deployContract = (DeployContract) transaction;
            factoryDeps = Arrays.stream(deployContract.getFactoryDeps())
                    .map(RlpString::create)
                    .collect(Collectors.toList());
        } else {
            factoryDeps = Collections.emptyList();
        }

        result.add(new RlpList(factoryDeps));

        return result;
    }

    @Override
    public BigInteger getNonce() {
        return transaction.getNonceNumber();
    }

    @Override
    public BigInteger getGasPrice() {
        return transaction.getFee().getErgsPriceLimitNumber();
    }

    @Override
    public BigInteger getGasLimit() {
        return transaction.getFee().getErgsLimitNumber();
    }

    @Override
    public String getTo() {
        if (transaction.getType().equals(DeployContract.DEPLOY_CONTRACT_TYPE)) {
            return Address.DEFAULT.getValue();
        } else if (transaction.getType().equals(Execute.EXECUTE_TYPE)) {
            Execute execute = (Execute) transaction;
            return execute.getContractAddressString();
        } else if (transaction.getType().equals(Withdraw.WITHDRAW_TYPE)) {
            Withdraw withdraw = (Withdraw) transaction;
            return withdraw.getToString();
        } else {
            return null;
        }
    }

    @Override
    public BigInteger getValue() {
        if (transaction.getType().equals(Withdraw.WITHDRAW_TYPE)) {
            Withdraw withdraw = (Withdraw) transaction;
            return withdraw.getAmountNumber();
        } else {
            return BigInteger.ZERO;
        }
    }

    @Override
    public String getData() {
        if (transaction.getType().equals(DeployContract.DEPLOY_CONTRACT_TYPE)) {
            DeployContract deployContract = (DeployContract) transaction;
            return Numeric.toHexString(deployContract.getInput());
        } else if (transaction.getType().equals(Execute.EXECUTE_TYPE)) {
            Execute execute = (Execute) transaction;
            return Numeric.toHexString(execute.getCalldata());
        } else {
            return "";
        }
    }

    public long getChainId() {
        return chainId;
    }

    public T getTransaction() {
        return transaction;
    }

    @Override
    public TransactionType getType() {
        // NOTE: Transaction type encoded manually in `asRlpValues`
        return TransactionType.LEGACY;
    }
}
