package pl.com.bottega.ecommerce.sales.domain.offer;

public class Money {

    private String denomination;
    private String currency;

    public Money(String denomination, String currency) {
        this.denomination = denomination;
        this.currency = currency;
    }

    public String getDenomination() {
        return denomination;
    }

    public String getCurrency() {
        return currency;
    }
}
