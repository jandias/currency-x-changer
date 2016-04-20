package net.permadevelop.currencyx.service.external;

import java.util.Map;

public class OerRates implements net.permadevelop.currencyx.domain.Rates {
    private long timestamp;

    private String baseCurrency;

    private OerCurrencies rates;

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Override
    public Map<String, String> getRates() {
        return rates.getCurrencies();
    }

    public void setRates(OerCurrencies rates) {
        this.rates = rates;
    }
}
