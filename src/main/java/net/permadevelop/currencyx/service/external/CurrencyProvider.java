package net.permadevelop.currencyx.service.external;

import net.permadevelop.currencyx.domain.Rates;

import java.util.Set;

public interface CurrencyProvider {
    /**
     * Obtain all the currency codes supported by this provider.
     * Codes should be a {@link java.util.Currency} valid code.
     *
     * @return a Set with all supported codes
     */
    Set<String> acceptedCurrencies();

    Rates currentRates();

    Rates rates(String date);
}
