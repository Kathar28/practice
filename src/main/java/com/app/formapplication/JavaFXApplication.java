package com.app.formapplication;

import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFXApplication extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Тест");
        MainForm form = new MainForm(primaryStage);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(form.getMainScene());

        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }

}

