package io.zksync.protocol.core;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FeeParams {
    /** Fee parameter configuration for the current version of the ZKsync protocol. */
    private V2Config V2;
}

