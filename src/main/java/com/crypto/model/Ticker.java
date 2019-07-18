package com.crypto.model;

import java.sql.Timestamp;

public class Ticker {
    private Long id;
    private CryptoCurrency baseCurrency;
    private CryptoCurrency quoteCurrency;
    private double price;
    private Timestamp at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CryptoCurrency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CryptoCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CryptoCurrency getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(CryptoCurrency quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getAt() {
        return at;
    }

    public void setAt(Timestamp at) {
        this.at = at;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", quoteCurrency='" + quoteCurrency + '\'' +
                ", price=" + price +
                ", at=" + at +
                '}';
    }
}
