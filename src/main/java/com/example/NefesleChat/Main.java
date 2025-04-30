package com.example.NefesleChat;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Getter
    private static HttpUtil httpUtil = new HttpUtil();

    @Override
    public void start(Stage primaryStage) {
        URL resourceURL = getClass().getResource("/jwtToken");
        if (resourceURL != null) {
            try {
                File jwtTokenFile = new File(resourceURL.getFile());
                FileReader fileReader = new FileReader(jwtTokenFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String token = bufferedReader.readLine();
                bufferedReader.close();
                if (token != null && !token.trim().isEmpty()) {
                    httpUtil.restartSession(token);
                    MainView mainView = new MainView();
                    mainView.show();
                }
                else {
                    LoginRegistrationView loginRegistrationView = new LoginRegistrationView();
                    loginRegistrationView.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            LoginRegistrationView loginRegistrationView = new LoginRegistrationView();
            loginRegistrationView.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}