package com.example.NefesleChat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;

public class LoginRegistrationView {

    @Getter
    private Stage stage;
    private TextField email;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label warningsLabel;

    //@Getter
    public LoginRegistrationView() {
        this.stage = new Stage();
        initialize();
    }

    private void initialize() {
        stage.setTitle("Авторизация");
        stage.setResizable(false);

        BorderPane authForm = new BorderPane();
        authForm.getStyleClass().add("authRegForm");

        Label authLogo = new Label();
        authLogo.setPrefSize(82,82);
        authLogo.getStyleClass().add("authRegLogo");
        Label authText = new Label("Авторизация");
        authText.getStyleClass().add("authRegTextLogo");
        HBox headerPane = new HBox(2);
        VBox footerPane = new VBox(10);
        headerPane.setPadding(new Insets(22));
        headerPane.setSpacing(10);
        headerPane.setAlignment(Pos.CENTER);
        headerPane.getChildren().addAll(authLogo, authText);

        GridPane authPoles = new GridPane(0, 3);
        authPoles.setVgap(25);
        authPoles.setAlignment(Pos.CENTER);

        email = new TextField();
        email.getStyleClass().add("authRegTextField");
        email.setPrefSize(350,50);
        email.setMaxSize(350,50);
        email.setPromptText("   Эл. почта");
        passwordField = new PasswordField();
        passwordField.getStyleClass().add("authRegTextField");
        passwordField.setPrefSize(350,50);
        passwordField.setMaxSize(350,50);
        passwordField.setPromptText("   Пароль");

        loginButton = new Button("Вход");
        loginButton.getStyleClass().add("authRegTopButton");
        loginButton.setPrefSize(200, 50);
        registerButton = new Button("Регистрация");
        registerButton.getStyleClass().add("authRegBottomButton");
        footerPane.setAlignment(Pos.CENTER);
        footerPane.setPadding(new Insets(20));
        footerPane.setPadding(new Insets(0,0,50,0));
        loginButton.setOnMouseEntered(event -> loginButton.setCursor(Cursor.HAND));
        loginButton.setOnMouseExited(event -> loginButton.setCursor(Cursor.DEFAULT));
        registerButton.setOnMouseEntered(event -> registerButton.setCursor(Cursor.HAND));
        registerButton.setOnMouseExited(event -> registerButton.setCursor(Cursor.DEFAULT));
        footerPane.getChildren().addAll(loginButton, registerButton);

        warningsLabel = new Label();
        warningsLabel.setMaxWidth(350);
        warningsLabel.setPrefWidth(350);
        warningsLabel.setTextFill(Color.RED);
        warningsLabel.setAlignment(Pos.CENTER);

        LoginRegistrationController controller = new LoginRegistrationController(this);

        loginButton.setOnAction(controller::handleLogin);
        registerButton.setOnAction(controller::handleRegister);

        authForm.setPrefSize(550, 600);
        authPoles.add(email, 0, 0);
        authPoles.add(passwordField, 0, 1);
        authPoles.add(warningsLabel, 0, 2);
        authForm.setCenter(authPoles);
        authForm.setTop(headerPane);
        authForm.setBottom(footerPane);

        VBox image = new VBox();
        image.setPrefSize(650, 600);
        image.getStyleClass().add("authbg");

        GridPane workingBox = new GridPane(2, 0);
        workingBox.add(authForm, 0, 0);
        workingBox.add(image, 1, 0);

        Scene scene = new Scene(workingBox, 1200, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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
        warningsLabel.setText(message);
    }

    public void openRegistrationWindow() {
        RegistrationView registrationView = new RegistrationView();
        registrationView.show();
    }

}
