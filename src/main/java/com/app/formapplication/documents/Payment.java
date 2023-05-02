package com.app.formapplication.documents;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Payment extends Document {

    private String employee;

    public Payment(String number, LocalDate date, String userName, int sum, String employee) throws ParseException {
        super(DocumentType.PAYMENT, number, date, userName, sum);
        this.employee = employee;
    }

    public String getEmployee(){
        return employee;
    }

    @Override
    public String toString() {
        return "Номер: " + getNumber() +
                "\n" + "Дата: " + getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                "\n" + "Пользователь: " + getUserName() +
                "\n" + "Сумма: " + getSum() +
                "\n" + "Сотрудник: " + getEmployee();
    }
}
