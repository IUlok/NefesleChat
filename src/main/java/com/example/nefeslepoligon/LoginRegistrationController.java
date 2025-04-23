package com.example.nefeslepoligon;

import javafx.event.ActionEvent;

public class LoginRegistrationController {

    private LoginRegistrationView view;
    private DataModel dataModel;

    public LoginRegistrationController(LoginRegistrationView view, DataModel dataModel) {
        this.view = view;
        this.dataModel = dataModel;
    }

    public void handleLogin(ActionEvent event) {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            dataModel.setUsername(username); // Store username in DataModel
            if(!dataModel.getAllUsers().contains(username)) {      //Если такого пользователя нет в списке
                dataModel.addAllUsers(username); //Добавили добавление пользователя в dataModel
                dataModel.addAllUsers("gavno");
            }

            // Placeholder: If authentication is successful
            System.out.println("Login successful for user: " + username);
            view.getStage().close();
            MainView mainView = new MainView(dataModel);
            mainView.show();

        } else {
            view.setMessage("Пожалуйста, заполните все поля.");
        }
    }

    public void handleRegister(ActionEvent event) {
        view.openRegistrationWindow();
        //TODO: Внесите запись о пользователе после регистрации
        // dataModel.addAllUsers(username); //Нужно добавлять после реги
    }
}
