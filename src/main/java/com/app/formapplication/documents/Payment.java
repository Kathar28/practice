package com.app.formapplication.documents;

import java.text.ParseException;
import java.time.LocalDate;

public class Payment extends Document {

    private String employee;

    public Payment(String documentType, String number, LocalDate date, String userName, int sum, String employee) throws ParseException {
        super(documentType, number, date, userName, sum);
        this.employee = employee;
    }

    public String getEmployee(){
        return employee;
    }
}
