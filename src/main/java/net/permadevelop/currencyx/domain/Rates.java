package net.permadevelop.currencyx.domain;

import java.util.Map;

public interface Rates {
    long getTimestamp();

    String getBaseCurrency();

    Map<String, String> getRates();
}
