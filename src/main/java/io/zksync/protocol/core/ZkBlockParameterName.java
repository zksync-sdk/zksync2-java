package io.zksync.protocol.core;

import com.fasterxml.jackson.annotation.JsonValue;

import org.web3j.protocol.core.DefaultBlockParameter;

public enum ZkBlockParameterName implements DefaultBlockParameter {
    COMMITTED("committed"),
    FINALIZED("finalized");

    private String name;

    ZkBlockParameterName(String name) {
        this.name = name;
    }

    @JsonValue
    @Override
    public String getValue() {
        return name;
    }

    public static ZkBlockParameterName fromString(String name) {
        if (name != null) {
            for (ZkBlockParameterName defaultBlockParameterName :
                    ZkBlockParameterName.values()) {
                if (name.equalsIgnoreCase(defaultBlockParameterName.name)) {
                    return defaultBlockParameterName;
                }
            }
        }
        return valueOf(name);
    }
    
}
