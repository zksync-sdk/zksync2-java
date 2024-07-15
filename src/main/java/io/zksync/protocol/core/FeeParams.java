package io.zksync.protocol.core;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
@AllArgsConstructor
@Getter
public class FeeParams {
    /** Fee parameter configuration for the current version of the ZKsync protocol. */
    private V2Config V2;
}

