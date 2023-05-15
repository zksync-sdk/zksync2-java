package io.zksync.methods.response;

import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class ZkTransaction extends Transaction {

    private String l1BatchNumber;
    private String l1BatchTxIndex;

    public ZkTransaction(String hash,
                         String nonce,
                         String blockHash,
                         String blockNumber,
                         String chainId,
                         String transactionIndex,
                         String from,
                         String to,
                         String value,
                         String gas,
                         String gasPrice,
                         String input,
                         String creates,
                         String publicKey,
                         String raw,
                         String r,
                         String s,
                         long v,
                         String type,
                         String maxFeePerGas,
                         String maxPriorityFeePerGas,
                         List accessList,
                         String l1BatchNumber,
                         String l1BatchTxIndex) {
        super(hash, nonce, blockHash, blockNumber, chainId, transactionIndex, from, to, value, gas, gasPrice, input,
                creates, publicKey, raw, r, s, v, type, maxFeePerGas, maxPriorityFeePerGas, accessList);
        this.l1BatchNumber = l1BatchNumber;
        this.l1BatchTxIndex = l1BatchTxIndex;
    }

    public String getL1BatchNumberRaw() {
        return l1BatchNumber;
    }

    public BigInteger getL1BatchNumber() {
        return Numeric.decodeQuantity(l1BatchNumber);
    }


    public void setL1BatchNumber(String l1BatchNumber) {
        this.l1BatchNumber = l1BatchNumber;
    }

    public String getL1BatchTxIndexRaw() {
        return l1BatchTxIndex;
    }

    public BigInteger getL1BatchTxIndex() {
        return Numeric.decodeQuantity(l1BatchTxIndex);
    }

    public void setL1BatchTxIndex(String l1BatchTxIndex) {
        this.l1BatchTxIndex = l1BatchTxIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZkTransaction)) return false;
        if (!super.equals(o)) return false;
        ZkTransaction that = (ZkTransaction) o;
        return l1BatchNumber.equals(that.l1BatchNumber) && l1BatchTxIndex.equals(that.l1BatchTxIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), l1BatchNumber, l1BatchTxIndex);
    }


}
