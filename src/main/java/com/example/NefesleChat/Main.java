package com.example.NefesleChat;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        DataModel dataModel = new DataModel();
        LoginRegistrationView loginRegistrationView = new LoginRegistrationView(dataModel);
        loginRegistrationView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
