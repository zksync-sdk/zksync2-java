package io.zksync.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlockRange {
    private BigInteger beggining;
    private BigInteger end;
}
