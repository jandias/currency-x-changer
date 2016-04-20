package net.permadevelop.currencyx.domain;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;

public class PopularCurrencyComparator implements Comparator<Currency> {
    public static final ArrayList<String> DEFAULT_ORDER = Lists.newArrayList(
            "EUR",
            "USD",
            "GBP",
            "NZD",
            "AUD",
            "JPY",
            "HUF"
    );

    private final ArrayList order;

    /**
     * Use {@link #DEFAULT_ORDER}
     */
    public PopularCurrencyComparator() {
        this(DEFAULT_ORDER);
    }

    public PopularCurrencyComparator(ArrayList order) {
        this.order = order;
    }

    @Override
    public int compare(Currency currency1, Currency currency2) {
        String currency1Code = currency1.getCurrencyCode();
        String currency2Code = currency2.getCurrencyCode();

        if (currency1Code.equals(currency2Code)) {
            return 0;
        }

        int index1 = order.indexOf(currency1Code);
        int index2 = order.indexOf(currency2Code);

        if (nonePopular(index1, index2)) {
            return currency1Code.compareTo(currency2Code);
        }

        if (index1 == -1) {
            return 1;
        }

        if (index2 == -1) {
            return -1;
        }

        return index1 > index2 ? 1 : -1;
    }

    private boolean nonePopular(int index1, int index2) {
        return index1 == -1 && index1 == index2;
    }
}
