package net.permadevelop.currencyx.service.external;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class OerCurrencies {
    private Map<String, String> currencies = new HashMap<>();

    @JsonAnyGetter
    public Map<String, String> getCurrencies() {
        return currencies;
    }

    @JsonAnySetter
    public void add(String code, String value) {
        currencies.put(code, value);
    }
}
