package com.example.nefeslepoligon;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationView {

    private Stage stage;
    private TextField tokenField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField passwordFieldTest;
    private Button registerButton;
    private Label messageLabel;

    public RegistrationView() {
        this.stage = new Stage();
        initialize();
    }

    private void initialize() {
        stage.setTitle("Регистрация");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        tokenField = new TextField();
        tokenField.setPromptText("Токен");
        lastNameField = new TextField();
        lastNameField.setPromptText("Фамилия");
        emailField = new TextField();
        emailField.setPromptText("Электронная почта");
        passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");
        passwordFieldTest = new PasswordField();
        passwordFieldTest.setPromptText("Подтверждение пароля");

        registerButton = new Button("Зарегистрироваться");
        messageLabel = new Label();

        RegistrationController controller = new RegistrationController(this);

        registerButton.setOnAction(controller::handleRegistration);

        vbox.getChildren().addAll(tokenField, lastNameField, emailField, passwordField, passwordFieldTest, registerButton, messageLabel);

        Scene scene = new Scene(vbox, 300, 300);
        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait(); // Используем showAndWait, чтобы дождаться закрытия окна
    }

    public void close() {
        stage.close();
    }

    // Геттеры для получения данных из полей
    public String getToken() {
        return tokenField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {return passwordField.getText();}

    public String getPasswordTest() {return passwordFieldTest.getText();}

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public Stage getStage() {
        return stage;
    }
}
