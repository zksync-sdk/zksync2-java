package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseSystemContractsHashes {
    private String bootloader;
    private String defaultAa;
}
