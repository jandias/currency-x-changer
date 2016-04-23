package net.permadevelop.currencyx.service.external;

import net.permadevelop.currencyx.domain.Rates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class OpenExchangeRatesProvider implements CurrencyProvider {
    private static final Logger LOG = LoggerFactory.getLogger(OpenExchangeRatesProvider.class);
    private static final String CURRENCIES_URI = "https://openexchangerates.org/api/currencies.json?app_id={appId}";
    private static final String LATEST_RATES_URI = "https://openexchangerates.org/api/latest.json?app_id={appId}";
    private static final String HISTORICAL_RATES_URI = "https://openexchangerates.org/api/historical/{date}.json?app_id={appId}";
    private static final String DEFAULT_BASE_CURRENCY = "EUR";

    @Value("${externalservice.openexchangerates.appid}")
    private String appId;

    @Override
    @Cacheable("currencies")
    public Set<String> acceptedCurrencies() {
        ResponseEntity<OerCurrencies> response
                = makeRequest(CURRENCIES_URI, new ParameterizedTypeReference<OerCurrencies>() {
        });
        LOG.info("Request success");
        return response.getBody().getCurrencies().keySet();
    }

    @Override
    public Rates currentRates() {
        ResponseEntity<OerRates> response
                = makeRequest(LATEST_RATES_URI, new ParameterizedTypeReference<OerRates>() {
        });
        LOG.info("Request success");
        OerRates rates = response.getBody();
        rates.setBaseCurrency(DEFAULT_BASE_CURRENCY);
        return rates;
    }

    @Override
    public Rates rates(String date) {
        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        ResponseEntity<OerRates> response
                = makeRequest(HISTORICAL_RATES_URI, new ParameterizedTypeReference<OerRates>() {
        }, params);
        LOG.info("Request success");
        OerRates rates = response.getBody();
        rates.setBaseCurrency(DEFAULT_BASE_CURRENCY);
        return rates;
    }

    private <T> ResponseEntity<T> makeRequest(String uri, ParameterizedTypeReference<T> resource, Map<String, String> toCopyParams) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>(toCopyParams);
        params.put("appId", appId);

        LOG.info("Requesting {}", uri);

        ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, null, resource, params);
        return response;
    }

    private <T> ResponseEntity<T> makeRequest(String uri, ParameterizedTypeReference<T> resource) {
        return this.makeRequest(uri, resource, Collections.emptyMap());
    }
}
