package com.app.formapplication.documents;

import java.text.ParseException;
import java.time.LocalDate;

public class Bid extends Document {

    private String counteragent;

    private String currency;

    private int currencyRate;

    private int commission;

    public Bid(String documentType, String number, LocalDate date, String userName, int sum, String counteragent,
               String currency, int currencyRate, int commission) throws ParseException {
        super(documentType, number, date, userName, sum);
        this.counteragent = counteragent;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.commission = commission;
    }

    public String getCounteragent() {
        return counteragent;
    }

    public String getCurrency() {
        return currency;
    }

    public int getCurrencyRate() {
        return currencyRate;
    }

    public int getCommission() {
        return commission;
    }
}
