package io.zksync.protocol.core.domain.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    private String address;

    private String symbol;

    private Integer decimals;

    public String formatToken(BigInteger amount) {
        return new BigDecimal(amount).divide(BigDecimal.TEN.pow(decimals)).toString();
    }

    public boolean isETH() {
        return address.equals("0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee") && symbol.equals("ETH");
    }

    public BigDecimal intoDecimal(BigInteger amount) {
        return new BigDecimal(amount)
            .setScale(decimals)
            .divide(BigDecimal.TEN.pow(decimals), RoundingMode.DOWN);
    }

    public static Token createETH() {
        return new Token(
            "0xeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
            "ETH",
            18
        );
    }
}
