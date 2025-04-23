package com.example.nefeslepoligon;

import javafx.event.ActionEvent;

public class RegistrationController {

    private RegistrationView view;

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
                view.setMessage("Регистрация прошла успешно!");
                view.close();  // Закрываем окно регистрации
            } else {
                view.setMessage("Пароли не совпадают!");
            }
        } else {
            view.setMessage("Пожалуйста, заполните все поля.");
        }
    }
}
