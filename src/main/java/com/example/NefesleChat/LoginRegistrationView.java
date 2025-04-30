package com.example.NefesleChat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

public class LoginRegistrationView {

    @Getter
    private Stage stage;
    private TextField email;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label messageLabel;

    //@Getter
    public LoginRegistrationView() {
        this.stage = new Stage();
        initialize();
    }

    private void initialize() {
        stage.setTitle("Авторизация");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        email = new TextField();
        email.setPromptText("Эл. почта");
        passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        loginButton = new Button("Войти");
        registerButton = new Button("Зарегистрироваться");

        messageLabel = new Label();

        LoginRegistrationController controller = new LoginRegistrationController(this);

        loginButton.setOnAction(controller::handleLogin);
        registerButton.setOnAction(controller::handleRegister);

        vbox.getChildren().addAll(email, passwordField, loginButton, registerButton, messageLabel);

        Scene scene = new Scene(vbox, 300, 250);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void openRegistrationWindow() {
        RegistrationView registrationView = new RegistrationView();
        registrationView.show();
    }

}
