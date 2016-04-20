package net.permadevelop.currencyx.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;

@SuppressWarnings("serial")
@Entity
public class ConversionQuery implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable=false, updatable=false)
    @JsonIgnore
    private Account user;

    private double amount;

    private String fromCurrency;

    private String toCurrency;

    private double result;

    private long creationTimestamp;

    private long ratesTimestamp;


    public ConversionQuery() {
        this.creationTimestamp = Instant.now().toEpochMilli();
    }

    public Long getId() {
        return id;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getRatesTimestamp() {
        return ratesTimestamp;
    }

    public void setRatesTimestamp(long ratesTimestamp) {
        this.ratesTimestamp = ratesTimestamp;
    }
}
