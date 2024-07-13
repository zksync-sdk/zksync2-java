package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseSystemContracts {
    private String bootloader;
    private String defaultAa;
}
