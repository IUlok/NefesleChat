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

public class RegistrationView {

    private Stage stage;
    private TextField tokenField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField passwordFieldTest;
    private Button registerButton;
    private Button backButton;
    private Label warningsLabel;

    public RegistrationView() {
        this.stage = new Stage();
        initialize();
    }

    private void initialize() {
        stage.setTitle("Регистрация");
        stage.setResizable(false);

        BorderPane regForm = new BorderPane();
        regForm.getStyleClass().add("authRegForm");

        Label regLogo = new Label();
        regLogo.setPrefSize(82,82);
        regLogo.getStyleClass().add("authRegLogo");
        Label regText = new Label("Регистрация");
        regText.getStyleClass().add("authRegTextLogo");
        HBox headerPane = new HBox(2);
        VBox footerPane = new VBox(10);
        headerPane.setPadding(new Insets(22));
        headerPane.setSpacing(10);
        headerPane.setAlignment(Pos.CENTER);
        headerPane.getChildren().addAll(regLogo, regText);

        GridPane regPoles = new GridPane(0, 6);
        regPoles.setVgap(15);
        regPoles.setAlignment(Pos.CENTER);

        tokenField = new TextField();
        tokenField.getStyleClass().add("authRegTextField");
        tokenField.setPrefSize(350,50);
        tokenField.setMaxSize(350,50);
        tokenField.setPromptText("Токен");
        lastNameField = new TextField();
        lastNameField.getStyleClass().add("authRegTextField");
        lastNameField.setPrefSize(350,50);
        lastNameField.setMaxSize(350,50);
        lastNameField.setPromptText("Фамилия");
        emailField = new TextField();
        emailField.getStyleClass().add("authRegTextField");
        emailField.setPrefSize(350,50);
        emailField.setMaxSize(350,50);
        emailField.setPromptText("Электронная почта");
        passwordField = new PasswordField();
        passwordField.getStyleClass().add("authRegTextField");
        passwordField.setPrefSize(350,50);
        passwordField.setMaxSize(350,50);
        passwordField.setPromptText("Пароль");
        passwordFieldTest = new PasswordField();
        passwordFieldTest.getStyleClass().add("authRegTextField");
        passwordFieldTest.setPrefSize(350,50);
        passwordFieldTest.setMaxSize(350,50);
        passwordFieldTest.setPromptText("Подтверждение пароля");

        registerButton = new Button("Регистрация");
        registerButton.getStyleClass().add("authRegTopButton");
        registerButton.setPrefSize(250, 50);
        backButton = new Button("Авторизация");
        backButton.getStyleClass().add("authRegBottomButton");

        footerPane.setAlignment(Pos.CENTER);
        footerPane.setPadding(new Insets(20));

        registerButton.setOnMouseEntered(event -> registerButton.setCursor(Cursor.HAND));
        registerButton.setOnMouseExited(event -> registerButton.setCursor(Cursor.DEFAULT));
        backButton.setOnMouseEntered(event -> backButton.setCursor(Cursor.HAND));
        backButton.setOnMouseExited(event -> backButton.setCursor(Cursor.DEFAULT));
        footerPane.getChildren().addAll(registerButton, backButton);

        warningsLabel = new Label();
        warningsLabel.setMaxWidth(350);
        warningsLabel.setPrefWidth(350);
        //warningsLabel.setTextFill(Color.RED);
        if (warningsLabel.getText().equals("Регистрация прошла успешно!")) { warningsLabel.setTextFill(new Color(3, 145,86,1.0)); }
        else { warningsLabel.setTextFill(Color.RED); }
        warningsLabel.setAlignment(Pos.CENTER);

        RegistrationController controller = new RegistrationController(this);

        registerButton.setOnAction(controller::handleRegistration);
        backButton.setOnAction(controller::handleAuth);

        regForm.setPrefSize(550, 600);
        regPoles.add(tokenField, 0, 0);
        regPoles.add(lastNameField, 0, 1);
        regPoles.add(emailField, 0, 2);
        regPoles.add(passwordField, 0, 3);
        regPoles.add(passwordFieldTest, 0, 4);
        regPoles.add(warningsLabel, 0, 5);
        regForm.setCenter(regPoles);
        regForm.setTop(headerPane);
        regForm.setBottom(footerPane);

        VBox image = new VBox();
        image.setPrefSize(650, 600);
        image.getStyleClass().add("regbg");

        GridPane workingBox = new GridPane(2, 0);
        workingBox.add(regForm, 0, 0);
        workingBox.add(image, 1, 0);

        Scene scene = new Scene(workingBox, 1200, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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
        warningsLabel.setText(message);
    }

    public Stage getStage() {
        return stage;
    }
}
