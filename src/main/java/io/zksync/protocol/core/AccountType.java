package io.zksync.protocol.core;

import org.web3j.abi.datatypes.generated.Uint8;

import lombok.Getter;

@Getter
public enum AccountType {
    ZkRollup(0),
    ZkPorter(1);

    Uint8 type;

    AccountType(long type) {
        this.type = new Uint8(type);
    }
}
