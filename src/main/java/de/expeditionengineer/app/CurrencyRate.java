package de.expeditionengineer.app;

public class CurrencyRate{
    private double rate;
    private String currency;
    public CurrencyRate(String currency, double rate) {
        this.rate = rate;
        this.currency = currency;   
    }

    public double getRate() {
        return rate;
    }
    public String getCurrency() {
        return currency;
    }
}