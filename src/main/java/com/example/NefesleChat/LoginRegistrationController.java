package com.example.NefesleChat;

import com.example.NefesleChat.entity.AuthForm;
import javafx.event.ActionEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;

public class LoginRegistrationController {

    private LoginRegistrationView view;
    private HttpUtil httpUtil = Main.getHttpUtil();

    public LoginRegistrationController(LoginRegistrationView view) {
        this.view = view;
    }

    public void handleLogin(ActionEvent event) {
        String email = view.getEmail();
        String password = view.getPassword();

        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
            AuthForm authForm = new AuthForm(email, password);
            HttpResponse<String> objectServerResponse  = httpUtil.authUser(authForm);
            if (objectServerResponse.statusCode() == 200) {
                try {
                    URL resourceURL = getClass().getResource("/jwtToken");
                    if (resourceURL == null) {
                        return;
                    }
                    File jwtTokenFile = new File(resourceURL.getFile());
                    FileWriter fileWriter = new FileWriter(jwtTokenFile);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(httpUtil.getJwtToken());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e){
                    e.printStackTrace();
                }

                view.getStage().close();
                MainView mainView = new MainView();
                mainView.show();
            } else {
                view.setMessage("Ошибка! " + objectServerResponse.body());
            }

        } else {
            view.setMessage("Пожалуйста, заполните все поля.");
        }
    }

    public void handleRegister(ActionEvent event) {
        view.getStage().close();
        view.openRegistrationWindow();
        //TODO: Внесите запись о пользователе после регистрации
        // dataModel.addAllUsers(username); //Нужно добавлять после реги
    }
}
