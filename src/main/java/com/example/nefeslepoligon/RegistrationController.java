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

        if (token != null && !token.isEmpty() && lastName != null && !lastName.isEmpty() && email != null && !email.isEmpty()) {
            // TODO: Add registration logic here (e.g., store in a database)

            // Placeholder: If registration is successful
            System.out.println("Registration successful for user with token: " + token);
            view.setMessage("Регистрация прошла успешно!");
            view.close();  // Закрываем окно регистрации
        } else {
            view.setMessage("Пожалуйста, заполните все поля.");
        }
    }
}
