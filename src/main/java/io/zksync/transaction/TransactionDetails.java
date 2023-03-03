package io.zksync.transaction;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Date;

public class TransactionDetails {
    private boolean isL1Originated;
    private TransactionStatus status;
    private String fee;
    private String initiatorAddress;
    private Date receivedAt;
    private String ethCommitTxHash;
    private String ethProveTxHash;
    private String ethExecuteTxHash;

    public boolean isL1Originated() {
        return isL1Originated;
    }

    public void setL1Originated(boolean l1Originated) {
        isL1Originated = l1Originated;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigInteger getFee() {
        return Numeric.decodeQuantity(fee);
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getInitiatorAddress() {
        return initiatorAddress;
    }

    public void setInitiatorAddress(String initiatorAddress) {
        this.initiatorAddress = initiatorAddress;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getEthCommitTxHash() {
        return ethCommitTxHash;
    }

    public void setEthCommitTxHash(String ethCommitTxHash) {
        this.ethCommitTxHash = ethCommitTxHash;
    }

    public String getEthProveTxHash() {
        return ethProveTxHash;
    }

    public void setEthProveTxHash(String ethProveTxHash) {
        this.ethProveTxHash = ethProveTxHash;
    }

    public String getEthExecuteTxHash() {
        return ethExecuteTxHash;
    }

    public void setEthExecuteTxHash(String ethExecuteTxHash) {
        this.ethExecuteTxHash = ethExecuteTxHash;
    }

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "isL1Originated=" + isL1Originated +
                ", status=" + status +
                ", fee='" + fee + '\'' +
                ", initiatorAddress='" + initiatorAddress + '\'' +
                ", receivedAt=" + receivedAt +
                ", ethCommitTxHash='" + ethCommitTxHash + '\'' +
                ", ethProveTxHash='" + ethProveTxHash + '\'' +
                ", ethExecuteTxHash='" + ethExecuteTxHash + '\'' +
                '}';
    }
}
