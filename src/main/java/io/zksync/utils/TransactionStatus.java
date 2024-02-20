package io.zksync.utils;

import com.fasterxml.jackson.annotation.JsonValue;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

public enum TransactionStatus implements DefaultBlockParameter {
    PROCESSING("processing"),
    COMMITTED("committed"),
    FINALIZED("finalized"),
    NOT_FOUND("not-found");

    private String name;

    private TransactionStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getValue() {
        return this.name;
    }

    public static TransactionStatus fromString(String name) {
        if (name != null) {
            TransactionStatus[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                TransactionStatus transactionStatus = var1[var3];
                if (name.equalsIgnoreCase(transactionStatus.name)) {
                    return transactionStatus;
                }
            }
        }

        return valueOf(name);
    }
}
