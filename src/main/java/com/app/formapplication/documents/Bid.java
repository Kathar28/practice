package com.app.formapplication.documents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bid extends Document {

    private String counteragent;

    private String currency;

    private int currencyRate;

    private int commission;

    public Bid(String number, LocalDate date, String userName, int sum, String counteragent,
               String currency, int currencyRate, int commission) {
        super(DocumentType.BID, number, date, userName, sum);
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

    @Override
    public String toString() {
        return "Номер: " + getNumber() +
                "\n" + "Дата: " + getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                "\n" + "Пользователь: " + getUserName() +
                "\n" + "Контрагент: " + getCounteragent() +
                "\n" + "Сумма: " + getSum() +
                "\n" + "Валюта: " + getCurrency() +
                "\n" + "Курс валюты: " + getCurrencyRate() +
                "\n" + "Комиссия: " + getCommission();
    }
}
