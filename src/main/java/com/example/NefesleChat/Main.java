package com.example.NefesleChat;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginRegistrationView loginRegistrationView = new LoginRegistrationView();
        loginRegistrationView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}