package com.app.formapplication.documents;

import com.app.formapplication.DocumentForm;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Document {

    private final String documentType;

    private String number;

    private LocalDate date;

    private String userName;

    private int sum;

    private boolean selected = false;


    public Document(String documentType, String number, LocalDate date, String userName, int sum) {
        this.documentType = documentType;
        this.number = number;
        this.date = date;
        this.userName = userName;
        this.sum = sum;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public int getSum() {
        return sum;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return getDocumentType() + " от " +
                getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                " номер " + getNumber();
    }


}
