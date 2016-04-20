package net.permadevelop.currencyx.service;

import com.google.common.collect.Sets;
import net.permadevelop.currencyx.service.external.CurrencyProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Currency;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyServiceTest {

    private static final String INVALID_CODE = "invalid";

    private final Set<String> providerCodes = Sets.newHashSet("AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD",
            "AWG", "AZN");

    @InjectMocks
    private CurrencyService service;

    @Mock
    private CurrencyProvider provider;

    @Before
    public void setup() {
        when(provider.acceptedCurrencies()).thenReturn(providerCodes);
    }

    @Test
    public void conversionFromProvider() {
        Collection<Currency> allCurrencies = service.allCurrencies();

        assertThat(allCurrencies.size(), is(providerCodes.size()));
    }

    @Test
    public void conversionFromProviderWithWrongCode() {
        providerCodes.add(INVALID_CODE);

        Collection<Currency> allCurrencies = service.allCurrencies();

        assertThat(allCurrencies.size(), is(providerCodes.size() - 1));
    }

}
