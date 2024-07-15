package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StorageLog {
    private String address;
    private String key;
    private String writtenValue;
}
