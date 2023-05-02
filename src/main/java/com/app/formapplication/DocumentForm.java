package com.app.formapplication;

import com.app.formapplication.documents.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DocumentForm {

    private Stage parentStage;
    private ListView<Document> documentList;
    private Scene document;
    private VBox container;

    public DocumentForm(String formType, ListView<Document> documentList, Stage parentStage) {
        this.parentStage = parentStage;
        this.documentList = documentList;
        switch (formType) {
            case "Накладная" -> this.container = createWayBillForm();
            case "Платёжка" -> this.container = createPaymentForm();
            case "Заявка на оплату" -> this.container = createBidForm();
        }
        setContainerProperties();
        this.document = new Scene(container);
    }

    private VBox createWayBillForm() {
        VBox container = new VBox();

        Label number = new Label("Номер");
        number.setAlignment(Pos.CENTER_LEFT);

        TextField numberInput = new TextField();
        numberInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        numberInput.setPrefHeight(10);

        Label date = new Label("Дата");
        date.setAlignment(Pos.CENTER_LEFT);

        DatePicker dateInput = createDatePickerWithConverter(new DatePicker());
        dateInput.setPrefWidth(Double.MAX_VALUE);
        dateInput.setPrefHeight(10);

        Label userName = new Label("Пользователь");
        userName.setAlignment(Pos.CENTER_LEFT);

        TextField userNameInput = new TextField();
        userNameInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        userNameInput.setPrefHeight(10);

        Label sum = new Label("Сумма");
        sum.setAlignment(Pos.CENTER_LEFT);

        TextField sumInput = new TextField();
        sumInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        sumInput.setPrefHeight(10);
        sumInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    sumInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label currency = new Label("Валюта");
        currency.setAlignment(Pos.CENTER_LEFT);

        TextField currencyInput = new TextField();
        currencyInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        currencyInput.setPrefHeight(10);

        Label currencyRate = new Label("Курс валюты");
        currencyRate.setAlignment(Pos.CENTER_LEFT);

        TextField currencyRateInput = new TextField();
        currencyRateInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        currencyRateInput.setPrefHeight(10);
        currencyRateInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    currencyRateInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label product = new Label("Товар");
        product.setAlignment(Pos.CENTER_LEFT);

        TextField productInput = new TextField();
        productInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        productInput.setPrefHeight(10);

        Label amount = new Label("Количество");
        amount.setAlignment(Pos.CENTER_LEFT);

        TextField amountInput = new TextField();
        amountInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        amountInput.setPrefHeight(10);
        amountInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    amountInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


        Button buttonSubmit = new Button("Ок");
        buttonSubmit.setAlignment(Pos.CENTER);

        buttonSubmit.setOnAction(actionEvent -> {
            try {
                if (validateFormFields(container)) {
                    buttonSubmit.setDisable(false);
                    documentList.getItems().add(new WayBill(numberInput.getText(), dateInput.getValue(),
                            userNameInput.getText(), Integer.parseInt(sumInput.getText()), currencyInput.getText(),
                            Integer.parseInt(currencyRateInput.getText()), productInput.getText(), Integer.parseInt(amountInput.getText())));
                    parentStage.close();
                } else {
                    alertMessage("Не все поля заполнены");
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        HBox buttonBox = new HBox(buttonSubmit);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 20, 0, 20));

        container.getChildren().addAll(number, numberInput, date, dateInput, userName, userNameInput,
                sum, sumInput, currency, currencyInput, currencyRate, currencyRateInput, product,
                productInput, amount, amountInput, buttonBox);

        container.setPadding(new Insets(20));
        return container;
    }

    private VBox createPaymentForm() {
        VBox container = new VBox();

        Label number = new Label("Номер");
        number.setAlignment(Pos.CENTER_LEFT);

        TextField numberInput = new TextField();
        numberInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        numberInput.setPrefHeight(10);

        Label date = new Label("Дата");
        date.setAlignment(Pos.CENTER_LEFT);

        DatePicker dateInput = createDatePickerWithConverter(new DatePicker());
        dateInput.setPrefWidth(Double.MAX_VALUE);
        dateInput.setPrefHeight(10);
        dateInput.addEventFilter(KeyEvent.KEY_PRESSED, Event::consume);

        Label userName = new Label("Пользователь");
        userName.setAlignment(Pos.CENTER_LEFT);

        TextField userNameInput = new TextField();
        userNameInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        userNameInput.setPrefHeight(10);

        Label sum = new Label("Сумма");
        sum.setAlignment(Pos.CENTER_LEFT);

        TextField sumInput = new TextField();
        sumInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        sumInput.setPrefHeight(10);
        sumInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    sumInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label employee = new Label("Сотрудник");
        employee.setAlignment(Pos.CENTER_LEFT);

        TextField employeeInput = new TextField();
        employeeInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        employeeInput.setPrefHeight(10);

        Button buttonSubmit = new Button("Ок");
        buttonSubmit.setAlignment(Pos.CENTER);

        buttonSubmit.setOnAction(actionEvent -> {
            try {
                if (validateFormFields(container)) {
                    buttonSubmit.setDisable(false);
                        documentList.getItems().add(new Payment(numberInput.getText(),
                                dateInput.getValue(), userNameInput.getText(), Integer.parseInt(sumInput.getText()), employeeInput.getText()));
                        parentStage.close();
                } else {
                    alertMessage("Не все поля заполнены");
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        });
        HBox buttonBox = new HBox(buttonSubmit);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 20, 0, 20));

        container.getChildren().addAll(number, numberInput, date, dateInput, userName, userNameInput,
                sum, sumInput, employee, employeeInput, buttonBox);

        container.setPadding(new Insets(20));
        return container;
    }

    private VBox createBidForm() {
        VBox container = new VBox();

        Label number = new Label("Номер");
        number.setAlignment(Pos.CENTER_LEFT);

        TextField numberInput = new TextField();
        numberInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        numberInput.setPrefHeight(10);

        Label date = new Label("Дата");
        date.setAlignment(Pos.CENTER_LEFT);

        DatePicker dateInput = createDatePickerWithConverter(new DatePicker());
        dateInput.setPrefWidth(Double.MAX_VALUE);
        dateInput.setPrefHeight(10);

        Label userName = new Label("Пользователь");
        userName.setAlignment(Pos.CENTER_LEFT);

        TextField userNameInput = new TextField();
        userNameInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        userNameInput.setPrefHeight(10);

        Label counteragent = new Label("Контрагент");
        counteragent.setAlignment(Pos.CENTER_LEFT);

        TextField counteragentInput = new TextField();
        counteragentInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        counteragentInput.setPrefHeight(10);

        Label sum = new Label("Сумма");
        sum.setAlignment(Pos.CENTER_LEFT);

        TextField sumInput = new TextField();
        sumInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        sumInput.setPrefHeight(10);
        sumInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    sumInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label currency = new Label("Валюта");
        currency.setAlignment(Pos.CENTER_LEFT);

        TextField currencyInput = new TextField();
        currencyInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        currencyInput.setPrefHeight(10);

        Label currencyRate = new Label("Курс валюты");
        currencyRate.setAlignment(Pos.CENTER_LEFT);

        TextField currencyRateInput = new TextField();
        currencyRateInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        currencyRateInput.setPrefHeight(10);
        currencyRateInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    currencyRateInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


        Label commission = new Label("Коммиссия");
        commission.setAlignment(Pos.CENTER_LEFT);

        TextField commissionInput = new TextField();
        commissionInput.setPrefWidth(Region.USE_COMPUTED_SIZE);
        commissionInput.setPrefHeight(10);
        commissionInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    commissionInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


        Button buttonSubmit = new Button("Ок");
        buttonSubmit.setAlignment(Pos.CENTER);

        buttonSubmit.setOnAction(actionEvent -> {
                if (validateFormFields(container)) {
                    buttonSubmit.setDisable(false);
                        documentList.getItems().add(new Bid(numberInput.getText(), dateInput.getValue(),
                                userNameInput.getText(), Integer.parseInt(sumInput.getText()), counteragentInput.getText(), currencyInput.getText(),
                                Integer.parseInt(currencyRateInput.getText()), Integer.parseInt(commissionInput.getText())));
                        parentStage.close();
                } else {
                    alertMessage("Не все поля заполнены");
                }

        });
        HBox buttonBox = new HBox(buttonSubmit);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 20, 0, 20));

        container.getChildren().addAll(number, numberInput, date, dateInput, userName, userNameInput, counteragent,
                counteragentInput, sum, sumInput, currency, currencyInput, currencyRate, currencyRateInput, commission, commissionInput, buttonBox);

        container.setPadding(new Insets(20));
        return container;
    }

    public DatePicker createDatePickerWithConverter(DatePicker datePicker){
        datePicker.setConverter(new StringConverter<>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return dateFormatter.parse(string, LocalDate::from);
                } catch (DateTimeParseException e) {
                    datePicker.getEditor().setText("");
                    return null;
                }
            }
        });
        return datePicker;
    }

    public static void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Предупреждение");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateFormFields(VBox form) {
        boolean isValid = true;
        for (Node node : form.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText().trim().isEmpty()) {
                    isValid = false;
                    break;
                }
            }
            if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                if (datePicker.getEditor().getText().equals("")) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    private void setContainerProperties() {
        container.setMinSize(470, 250);
        container.setMaxSize(470, 450);
        parentStage.setResizable(false);
    }

    public Stage getParentStage() {
        return parentStage;
    }

    public ListView<Document> getDocumentList() {
        return documentList;
    }

    public Scene getDocument() {
        return document;
    }

    public VBox getContainer() {
        return container;
    }
}
