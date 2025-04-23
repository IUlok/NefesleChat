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

public class LoginRegistrationView {

    private Stage stage;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label messageLabel;
    private DataModel dataModel;

    public LoginRegistrationView(DataModel dataModel) {
        this.stage = new Stage();
        this.dataModel = dataModel;
        initialize();
    }

    private void initialize() {
        stage.setTitle("Авторизация / Регистрация");

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        usernameField = new TextField();
        usernameField.setPromptText("Имя пользователя");
        passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");

        loginButton = new Button("Войти");
        registerButton = new Button("Зарегистрироваться");

        messageLabel = new Label();

        LoginRegistrationController controller = new LoginRegistrationController(this, dataModel);

        loginButton.setOnAction(controller::handleLogin);
        registerButton.setOnAction(controller::handleRegister);

        vbox.getChildren().addAll(usernameField, passwordField, loginButton, registerButton, messageLabel);

        Scene scene = new Scene(vbox, 300, 250);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public String getUsername() {
        return usernameField.getText();
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

    public DataModel getDataModel() {
        return dataModel;
    }
}
