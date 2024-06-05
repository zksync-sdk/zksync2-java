package io.zksync.transaction.type;

import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
public class AllowanceParams {
    public String token;
    public BigInteger amount;
}
