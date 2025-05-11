package com.example.NefesleChat;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class Main extends Application {

    @Getter
    private static Properties properties = new Properties();

    @Getter
    private static WebSocketUtil webSocketUtil;

    @Override
    public void start(Stage primaryStage) {
        loadProperties();
        webSocketUtil = new WebSocketUtil();
        URL resourceURL = getClass().getResource("/jwtToken");
        if (resourceURL != null) {
            try {
                File jwtTokenFile = new File(resourceURL.getFile());
                FileReader fileReader = new FileReader(jwtTokenFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String token = bufferedReader.readLine();
                bufferedReader.close();
                if (token != null && !token.trim().isEmpty()) {
                    HttpUtil.restartSession(token);
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

    private void loadProperties() {
        try {
            properties.load(new FileInputStream(getClass().getResource("/application.properties").getPath()));
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке файла с настройками");
        }
    }
}