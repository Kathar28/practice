package com.app.formapplication;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Тест");
        MainForm form = new MainForm(primaryStage);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(600);
        primaryStage.setScene(form.getMainScene());

        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}

