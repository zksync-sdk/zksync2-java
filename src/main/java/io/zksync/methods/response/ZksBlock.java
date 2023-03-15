package io.zksync.methods.response;

import org.web3j.protocol.core.methods.response.EthBlock;


public class ZksBlock extends EthBlock {
    private String baseFeePerGas;
    private String l1BatchNumber;
    private String l1BatchTimestamp;

    public ZksBlock(String baseFeePerGas, String l1BatchNumber, String l1BatchTimestamp) {
        this.baseFeePerGas = baseFeePerGas;
        this.l1BatchNumber = l1BatchNumber;
        this.l1BatchTimestamp = l1BatchTimestamp;
    }

    public String getBaseFeePerGas() {
        return baseFeePerGas;
    }

    public void setBaseFeePerGas(String baseFeePerGas) {
        this.baseFeePerGas = baseFeePerGas;
    }

    public String getL1BatchNumber() {
        return l1BatchNumber;
    }

    public void setL1BatchNumber(String l1BatchNumber) {
        this.l1BatchNumber = l1BatchNumber;
    }

    public String getL1BatchTimestamp() {
        return l1BatchTimestamp;
    }

    public void setL1BatchTimestamp(String l1BatchTimestamp) {
        this.l1BatchTimestamp = l1BatchTimestamp;
    }
}
