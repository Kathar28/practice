package com.app.formapplication.documents;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WayBill extends Document {

    private String currency;
    private int currencyRate;
    private String product;
    private int amount;

    public WayBill( String number, LocalDate date, String userName, int sum, String currency, int currencyRate, String product, int amount) throws ParseException {
        super(DocumentType.WAYBILL, number, date, userName, sum);
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.product = product;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }


    public int getCurrencyRate() {
        return currencyRate;
    }

    public String getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Номер: " + getNumber() +
                "\n" + "Дата: " + getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                "\n" + "Пользователь: " + getUserName() +
                "\n" + "Сумма: " + getSum() +
                "\n" + "Валюта: " + getCurrency() +
                "\n" + "Курс валюты: " + getCurrencyRate() +
                "\n" + "Товар: " + getProduct() +
                "\n" + "Количество: " + getAmount();
    }
}
