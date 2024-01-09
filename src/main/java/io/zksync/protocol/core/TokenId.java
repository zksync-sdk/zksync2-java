package io.zksync.protocol.core;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface TokenId {

  String getSymbol();

  BigDecimal intoDecimal(BigInteger amount);
}
