package com.example.NefesleChat;

import com.example.NefesleChat.entity.AuthForm;
import javafx.event.ActionEvent;

import java.net.http.HttpResponse;

public class LoginRegistrationController {

    private LoginRegistrationView view;
    private HttpUtil httpUtil = new HttpUtil();

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
                view.setMessage("Успешный вход!");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
