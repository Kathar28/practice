package com.app.formapplication;

import com.app.formapplication.documents.Bid;
import com.app.formapplication.documents.Document;
import com.app.formapplication.documents.Payment;
import com.app.formapplication.documents.WayBill;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class MainForm {

    private Stage parentStage;
    private Scene mainScene;
    private BorderPane mainContainer;
    private VBox buttonsContainer;
    private ListView<Document> documentList;

    public MainForm(Stage parentStage) {
        this.parentStage = parentStage;
        this.buttonsContainer = addButtons();
        this.documentList = new ListView<>();
        addDocumentList();
        drawingAllElements();
        this.mainScene = new Scene(mainContainer);
    }

    private VBox addButtons() {
        VBox buttonContainer = new VBox();
        Button buttonWaybill = new Button("Накладная");
        buttonWaybill.setMaxWidth(Double.MAX_VALUE);
        buttonWaybill.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            stage.setTitle("Накладная");
            DocumentForm documentForm = new DocumentForm("Накладная", documentList, stage);
            stage.setMinHeight(documentForm.getContainer().getMinHeight());
            stage.setMinWidth(documentForm.getContainer().getMinWidth());
            stage.setScene(documentForm.getDocument());
            stage.show();
        });

        Button buttonPayment = new Button("Платёжка");
        buttonPayment.setMaxWidth(Double.MAX_VALUE);

        buttonPayment.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            stage.setTitle("Платёжка");
            DocumentForm documentForm = new DocumentForm("Платёжка", documentList, stage);
            stage.setMinHeight(documentForm.getContainer().getMinHeight());
            stage.setMinWidth(documentForm.getContainer().getMinWidth());
            stage.setScene(documentForm.getDocument());
            stage.show();
        });

        Button buttonBid = new Button("Заявка на оплату");
        buttonBid.setMaxWidth(Double.MAX_VALUE);

        buttonBid.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            stage.setTitle("Заявка на оплату");
            DocumentForm documentForm = new DocumentForm("Заявка на оплату", documentList, stage);
            stage.setMinHeight(documentForm.getContainer().getMinHeight());
            stage.setMinWidth(documentForm.getContainer().getMinWidth());
            stage.setScene(documentForm.getDocument());
            stage.show();
        });

        Button buttonSave = new Button("Сохранить");
        buttonSave.setMaxWidth(Double.MAX_VALUE);

        buttonSave.setOnAction(actionEvent -> {
            Document selectedItem = documentList.getSelectionModel().getSelectedItem();

            if (selectedItem != null) saveFile(selectedItem, selectedItem.toString());
        });

        Button buttonLoad = new Button("Загрузить");
        buttonLoad.setMaxWidth(Double.MAX_VALUE);

        buttonLoad.setOnAction(actionEvent -> {
            loadFile();
        });

        Button buttonView = new Button("Просмотр");
        buttonView.setMaxWidth(Double.MAX_VALUE);

        buttonView.setOnAction(actionEvent -> {
            Document selectedItem = documentList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) showDocumentProperties(selectedItem);
        });

        Button buttonDelete = new Button("Удалить");
        buttonDelete.setMaxWidth(Double.MAX_VALUE);
        buttonDelete.setOnAction(actionEvent -> {
            removeSelectedItemsFromDocumentList();
        });

        Button buttonExit = new Button("Выход");
        buttonExit.setOnAction(actionEvent -> {
            Platform.exit();
            System.exit(0);
        });
        buttonExit.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(buttonExit, new Insets(40, 0, 0, 0));

        buttonContainer.getChildren().addAll(buttonWaybill, buttonPayment, buttonBid,
                buttonSave, buttonLoad, buttonView, buttonDelete, buttonExit);
        return buttonContainer;
    }

    private void addDocumentList() {

        documentList.setCellFactory(param -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();
            private final Label nameLabel = new Label();

            private final Region region = new Region();
            private final HBox container = new HBox(nameLabel, region, checkBox);

            {
                setWrapText(true);
                setPrefWidth(0);
                container.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(region, Priority.ALWAYS);
                checkBox.setAlignment(Pos.CENTER_RIGHT);
            }

            @Override
            protected void updateItem(Document item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    nameLabel.setText(item.getDocumentType() + " от " +
                            item.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                            " номер " + item.getNumber());
                    nameLabel.setWrapText(true);
                    nameLabel.setTextAlignment(TextAlignment.JUSTIFY);
                    checkBox.setSelected(item.isSelected());
                    checkBox.setOnAction(e -> item.setSelected(checkBox.isSelected()));
                    setGraphic(container);
                }
            }
        });
    }

    private void drawingAllElements() {
        mainContainer = new BorderPane();
        BorderPane.setMargin(documentList, new Insets(20, 20, 20, 20));
        BorderPane.setMargin(buttonsContainer, new Insets(20, 20, 20, 20));
        mainContainer.setCenter(documentList);
        mainContainer.setRight(buttonsContainer);
        buttonsContainer.setSpacing(20);
        documentList.minHeight(300);
        documentList.minWidth(300);
        documentList.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private void saveFile(Document document, String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение файла");
        fileChooser.setInitialFileName(fileName);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        String content = "";
        if (document instanceof WayBill) {
            WayBill wayBill = (WayBill) document;
            content = "Номер: " + wayBill.getNumber() +
                    "\n" + "Дата: " + wayBill.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                    "\n" + "Пользователь: " + wayBill.getUserName() +
                    "\n" + "Сумма: " + wayBill.getSum() +
                    "\n" + "Валюта: " + wayBill.getCurrency() +
                    "\n" + "Курс валюты: " + wayBill.getCurrencyRate() +
                    "\n" + "Товар: " + wayBill.getProduct() +
                    "\n" + "Количество: " + wayBill.getAmount();
        }
        if (document instanceof Bid) {
            Bid bid = (Bid) document;
            content = "Номер: " + bid.getNumber() +
                    "\n" + "Дата: " + bid.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                    "\n" + "Пользователь: " + bid.getUserName() +
                    "\n" + "Контрагент: " + bid.getCounteragent() +
                    "\n" + "Сумма: " + bid.getSum() +
                    "\n" + "Валюта: " + bid.getCurrency() +
                    "\n" + "Курс валюты: " + bid.getCurrencyRate() +
                    "\n" + "Комиссия: " + bid.getCommission();
        }
        if (document instanceof Payment) {
            Payment payment = (Payment) document;
            content = "Номер: " + payment.getNumber() +
                    "\n" + "Дата: " + payment.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                    "\n" + "Пользователь: " + payment.getUserName() +
                    "\n" + "Сумма: " + payment.getSum() +
                    "\n" + "Сотрудник: " + payment.getEmployee();
        }

        File file = fileChooser.showSaveDialog(parentStage);

        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void showDocumentProperties(Document document) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(document.getDocumentType());
        alert.setHeaderText(null);
        alert.getDialogPane().setGraphic(null);
        alert.getDialogPane().setPrefWidth(200);
        alert.getDialogPane().setPrefHeight(200);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Номер:"), 0, 1);
        grid.add(new Label(document.getNumber()), 1, 1);
        grid.add(new Label("Дата:"), 0, 2);
        grid.add(new Label(document.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))), 1, 2);
        grid.add(new Label("Пользователь:"), 0, 3);
        grid.add(new Label(document.getUserName()), 1, 3);


        if (document instanceof WayBill) {
            grid.add(new Label("Сумма:"), 0, 4);
            grid.add(new Label(Integer.toString(document.getSum())), 1, 4);
            grid.add(new Label("Валюта:"), 0, 5);
            grid.add(new Label(((WayBill) document).getCurrency()), 1, 5);
            grid.add(new Label("Курс валюты:"), 0, 6);
            grid.add(new Label(Integer.toString(((WayBill) document).getCurrencyRate())), 1, 6);
            grid.add(new Label("Товар:"), 0, 7);
            grid.add(new Label(((WayBill) document).getProduct()), 1, 7);
            grid.add(new Label("Количество:"), 0, 8);
            grid.add(new Label(Integer.toString(((WayBill) document).getAmount())), 1, 8);

        } else if (document instanceof Payment) {
            grid.add(new Label("Сумма:"), 0, 4);
            grid.add(new Label(Integer.toString(document.getSum())), 1, 4);
            grid.add(new Label("Сотрудник:"), 0, 5);
            grid.add(new Label(((Payment) document).getEmployee()), 1, 5);
        } else if (document instanceof Bid) {
            grid.add(new Label("Контрагент:"), 0, 4);
            grid.add(new Label(((Bid) document).getCounteragent()), 1, 4);
            grid.add(new Label("Сумма:"), 0, 5);
            grid.add(new Label(Integer.toString(document.getSum())), 1, 5);
            grid.add(new Label("Валюта:"), 0, 6);
            grid.add(new Label(((Bid) document).getCurrency()), 1, 6);
            grid.add(new Label("Курс валюты:"), 0, 7);
            grid.add(new Label(Integer.toString(((Bid) document).getCurrencyRate())), 1, 7);
            grid.add(new Label("Комиссия:"), 0, 8);
            grid.add(new Label(Integer.toString(((Bid) document).getCommission())), 1, 8);
        }

        alert.getDialogPane().setContent(grid);
        alert.showAndWait();
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(parentStage);

        if (file != null) {
            Document loadedDocument;
            try {
                loadedDocument = loadDocumentFromFile(file);
            } catch (IOException | ParseException | NullPointerException e) {
                DocumentForm.alertMessage("Ошибка при загрузке: " + e.getMessage());
                return;
            }
            if (loadedDocument != null) documentList.getItems().add(loadedDocument);
        }
    }


    private Document loadDocumentFromFile(File file) throws IOException, ParseException {

        String[] nameParts = file.getName().split("от", 2);
        String documentType = nameParts[0].trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            Map<String, String> data = new HashMap<>();
            while (line != null) {
                String[] keyValue = line.split(":", 2);
                if (keyValue.length == 2) {
                    data.put(keyValue[0].trim(), keyValue[1].trim());
                }
                line = reader.readLine();
            }

            for (Map.Entry<String, String> entry : data.entrySet()) {
                if (entry.getValue().equals("")) {
                    DocumentForm.alertMessage("Неверный тип документа");
                    return null;
                }
            }

            try {
                switch (documentType) {
                    case "Накладная":
                        return new WayBill(documentType,
                                data.get("Номер"),
                                LocalDate.parse(data.get("Дата"), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                data.get("Пользователь"),
                                Integer.parseInt(data.get("Сумма")),
                                data.get("Валюта"),
                                Integer.parseInt(data.get("Курс валюты")),
                                data.get("Товар"),
                                Integer.parseInt(data.get("Количество")));
                    case "Заявка на оплату":
                        return new Bid(documentType,
                                data.get("Номер"),
                                LocalDate.parse(data.get("Дата"), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                data.get("Пользователь"),
                                Integer.parseInt(data.get("Сумма")),
                                data.get("Контрагент"),
                                data.get("Валюта"),
                                Integer.parseInt(data.get("Курс валюты")),
                                Integer.parseInt(data.get("Комиссия")));
                    case "Платёжка":
                        return new Payment(documentType,
                                data.get("Номер"),
                                LocalDate.parse(data.get("Дата"), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                data.get("Пользователь"),
                                Integer.parseInt(data.get("Сумма")),
                                data.get("Сотрудник"));
                    default:
                        throw new IllegalArgumentException("Неизвестный тип документа");
                }
            } catch (IllegalArgumentException e) {
                DocumentForm.alertMessage("Неизвестный тип документа");
                return null;
            }
        }
    }

    private void removeSelectedItemsFromDocumentList() {
        documentList.getItems().removeIf(Document::isSelected);
    }

    public Scene getMainScene() {
        return mainScene;
    }
}
