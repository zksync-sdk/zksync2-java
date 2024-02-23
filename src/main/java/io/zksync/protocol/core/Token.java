package io.zksync.protocol.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.zksync.utils.ZkSyncAddresses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token implements TokenId {

    public static final Token ETH = createETH();

    private String l1Address;

    private String l2Address;

    private String symbol;

    private Integer decimals;

    public String formatToken(BigInteger amount) {
        return new BigDecimal(amount).divide(BigDecimal.TEN.pow(decimals)).toString();
    }

    public boolean isETH() {
        return ZkSyncAddresses.ETH_ADDRESS.equals(l2Address) && "ETH".equals(symbol);
    }

    public BigDecimal intoDecimal(BigInteger amount) {
        return new BigDecimal(amount)
                .setScale(decimals)
                .divide(BigDecimal.TEN.pow(decimals), RoundingMode.DOWN);
    }

    public BigInteger toBigInteger(BigDecimal amount) {
        return amount.multiply(BigDecimal.TEN.pow(decimals)).toBigInteger();
    }

    public BigInteger toBigInteger(double amount) {
        return BigDecimal.valueOf(amount).multiply(BigDecimal.TEN.pow(decimals)).toBigInteger();
    }

    public static Token createETH() {
        return new Token(
                ZkSyncAddresses.ETH_ADDRESS,
                ZkSyncAddresses.ETH_ADDRESS,
                "ETH",
                18);
    }
}
