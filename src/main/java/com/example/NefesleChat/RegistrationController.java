package com.example.NefesleChat;

import com.example.NefesleChat.entity.RegistrationForm;
import javafx.event.ActionEvent;

import java.net.http.HttpResponse;

public class RegistrationController {

    private RegistrationView view;
    private HttpUtil httpUtil = new HttpUtil();

    public RegistrationController(RegistrationView view) {
        this.view = view;
    }

    public void handleRegistration(ActionEvent event) {
        String token = view.getToken();
        String lastName = view.getLastName();
        String email = view.getEmail();
        String password = view.getPassword();
        String passwordTest = view.getPasswordTest();

        if (token != null && !token.isEmpty() && lastName != null && !lastName.isEmpty() && email != null && !email.isEmpty() && !password.isEmpty()
        && !passwordTest.isEmpty()) {
            if (password.equals(passwordTest)) {
                RegistrationForm registrationForm = new RegistrationForm(token, lastName, password, email);
                HttpResponse<String> objectServerResponse = httpUtil.regUser(registrationForm);
                if(objectServerResponse.statusCode() == 200) {
                    view.setMessage("Регистрация прошла успешно!");
                    view.getStage().close();
                    DataModel dataModel = new DataModel();
                    LoginRegistrationView loginRegistrationView = new LoginRegistrationView(dataModel);
                    loginRegistrationView.show();
                }
                else {
                    view.setMessage("Ошибка! " + objectServerResponse.body());
                }
            } else {
                view.setMessage("Пароли не совпадают!");
            }
        } else {
            view.setMessage("Пожалуйста, заполните все поля.");
        }
    }

    public void handleAuth(ActionEvent event) {
        view.getStage().close();
        DataModel dataModel = new DataModel();
        LoginRegistrationView loginRegistrationView = new LoginRegistrationView(dataModel);
        loginRegistrationView.show();
    }
}
