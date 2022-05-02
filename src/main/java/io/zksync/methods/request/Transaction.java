package io.zksync.methods.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.zksync.transaction.type.Transaction712;
import io.zksync.transaction.DeployContract;
import io.zksync.transaction.Execute;
import io.zksync.transaction.Withdraw;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.core.methods.response.AccessListObject;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {

    private String from;
    private String to;
    private BigInteger gas;
    private BigInteger gasPrice;
    private BigInteger value;
    private String data;

    private Long transactionType;
    private AccessListObject accessList;
    private Eip712Meta eip712Meta;

    private Transaction(io.zksync.transaction.Transaction transaction) {
        this.eip712Meta = new Eip712Meta();
        this.from = transaction.getInitiatorAddressString();
        this.gas = transaction.getFee().getErgsLimitNumber();
        this.gasPrice = transaction.getFee().getErgsPriceLimitNumber();

        this.eip712Meta.setFeeToken(transaction.getFee().getFeeTokenString());
        this.eip712Meta.setErgsPerPubdata(transaction.getFee().getErgsPerPubdataLimitNumber());
        this.eip712Meta.setErgsPerStorage(transaction.getFee().getErgsPerStorageLimitNumber());

        this.transactionType = (long) Transaction712.EIP_712_TX_TYPE;
    }

    public Transaction(DeployContract deployContract) {
        this((io.zksync.transaction.Transaction) deployContract);

        this.to = Address.DEFAULT.getValue();
        this.data = Numeric.toHexString(deployContract.getInput());
        this.eip712Meta.setFactoryDeps(deployContract.getFactoryDeps());
    }

    public Transaction(Execute execute) {
        this((io.zksync.transaction.Transaction) execute);

        this.to = execute.getContractAddressString();
        this.data = Numeric.toHexString(execute.getCalldata());
    }

    public Transaction(Withdraw withdraw) {
        this((io.zksync.transaction.Transaction) withdraw);

        this.to = withdraw.getToString();
        this.value = withdraw.getAmountNumber();
        this.eip712Meta.setWithdrawToken(withdraw.getTokenString());
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getGas() {
        return convert(gas);
    }

    public String getGasPrice() {
        return convert(gasPrice);
    }

    public String getValue() {
        return convert(value);
    }

    public String getData() {
        return data;
    }

    public String getTransactionType() {
        return convert(BigInteger.valueOf(transactionType));
    }

    public AccessListObject getAccessList() {
        return accessList;
    }

    public Eip712Meta getEip712Meta() {
        return eip712Meta;
    }

    private static String convert(BigInteger value) {
        if (value != null) {
            return Numeric.encodeQuantity(value);
        } else {
            return null; // we don't want the field to be encoded if not present
        }
    }

}
