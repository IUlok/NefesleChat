package com.example.NefesleChat;

import com.example.NefesleChat.entity.UserDetailsDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsDialog {
    public void showSettings(MainView view) {
        try {
            Stage settingsStage = new Stage();
            settingsStage.setResizable(false);
            settingsStage.setTitle("");
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.initOwner(view.getPrimaryStage());
            BorderPane settingsBox = new BorderPane();
            settingsBox.getStyleClass().add("authRegForm");

            HBox topPanel = new HBox(1);
            topPanel.setAlignment(Pos.CENTER);
            topPanel.setPrefHeight(50);
            topPanel.setPadding(new Insets(5));
            topPanel.getStyleClass().add("top-panel");
            Label settings = new Label("Настройки");
            settings.getStyleClass().add("settingsText");
            topPanel.getChildren().add(settings);
            settingsBox.setTop(topPanel);

            VBox accountBox = new VBox(10);
            accountBox.setMaxSize(340,276);
            accountBox.setMinSize(340,276);
            accountBox.getStyleClass().add("accountBox");

            VBox otherBox = new VBox(10);
            otherBox.setMinSize(340,300);

            UserDetailsDTO result = HttpUtil.getCurrentUser();
            String name = result.getLastName() + " " + result.getFirstName() + " " + result.getPatronymic();
            String role = result.getRole();
            String email = result.getEmail();
            Date enabledUntil = result.getEnabledUntil();

            String facultyDepartment;
            String groupDegree;
            String reimTitle;

            if (result.getRole().equals("Студент")) {
                facultyDepartment = result.getFaculty();
                groupDegree = result.getGroupName();
                reimTitle = result.getReimbursement();
                accountBox.setStyle("-fx-background-color: #00a7ff");
            } else {
                facultyDepartment = result.getDepartment();
                groupDegree = result.getAcademicDegree();
                reimTitle = result.getAcademicTitle();
                accountBox.setStyle("-fx-background-color: #ff8d00");
            }

            Label nameLabel = new Label(name);
            Label roleLabel = new Label(role);
            Label emailLabel = new Label(email);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = outputFormat.format(enabledUntil);
            Label untilLabel = new Label(formattedDate);

            Label facultyDepartmentLabel = new Label(facultyDepartment);
            Label groupDegreeLabel = new Label(groupDegree);
            Label reimTitleLabel = new Label(reimTitle);

            accountBox.getChildren().addAll(nameLabel, roleLabel, facultyDepartmentLabel, emailLabel, groupDegreeLabel, reimTitleLabel, untilLabel);

            VBox exitPane = new VBox(1);
            exitPane.setAlignment(Pos.CENTER);
            exitPane.setPadding(new Insets(0,0,20,0));
            Button exitButton = new Button("Выйти из аккаунта");
            exitButton.setOnMouseEntered(event -> exitButton.setCursor(Cursor.HAND));
            exitButton.setOnMouseExited(event -> exitButton.setCursor(Cursor.DEFAULT));
            exitButton.getStyleClass().add("settingsExitButton");
            MainController mainController = new MainController(view);
            exitButton.setOnMouseClicked(mainController::openLogout);
            exitPane.getChildren().add(exitButton);

            VBox mainBox = new VBox(10);
            mainBox.setAlignment(Pos.CENTER);
            mainBox.getChildren().addAll(accountBox, otherBox);

            settingsBox.setBottom(exitPane);
            settingsBox.setCenter(mainBox);
            Scene scene = new Scene(settingsBox, 409, 729);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            settingsStage.setScene(scene);
            settingsStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
