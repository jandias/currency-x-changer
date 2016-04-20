package net.permadevelop.currencyx.service;

import net.permadevelop.currencyx.domain.PopularCurrencyComparator;
import net.permadevelop.currencyx.domain.Rates;
import net.permadevelop.currencyx.service.external.CurrencyProvider;
import net.permadevelop.currencyx.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyService.class);

    private final CurrencyProvider provider;
    private final PopularCurrencyComparator comparator = new PopularCurrencyComparator();

    @Inject
    public CurrencyService(CurrencyProvider provider) {
        this.provider = provider;
    }

    public Collection<Currency> allCurrencies() {
        Set<String> acceptedCurrencies = provider.acceptedCurrencies();

        List<Currency> currencies = acceptedCurrencies.stream()
                .map(code -> currency(code))
                .flatMap(StreamUtil::extract)
                .collect(Collectors.toList());
        Collections.sort(currencies, comparator);
        return currencies;
    }

    private Optional<Currency> currency(String code) {
        Currency result = null;
        try {
            result = Currency.getInstance(code);
        } catch (Exception e) {
            LOG.warn("Could not translate provider currency code \"{}\". Reason: {}", code, e);
        }

        return Optional.ofNullable(result);
    }

    public Rates rates() {
        return provider.currentRates();
    }

    public Rates rates(String date) {
        return provider.rates(date);
    }
}
