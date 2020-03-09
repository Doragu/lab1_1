package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

public class Money {

    private BigDecimal denomination;
    private String currency;

    public Money(BigDecimal denomination, String currency) {
        this.denomination = denomination;
        this.currency = currency;
    }

    public BigDecimal getDenomination() {
        return denomination;
    }

    public String getCurrency() {
        return currency;
    }
}
