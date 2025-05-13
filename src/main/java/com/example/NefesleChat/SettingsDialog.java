package com.example.NefesleChat;

import com.example.NefesleChat.entity.UserDetailsDTO;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsDialog {
    public void showSettings(MainView view) {
        try {
            Stage settingsStage = new Stage();
            settingsStage.getIcons().add(new Image("file:logo.png"));
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

            UserDetailsDTO result = Main.getHttpUtil().getCurrentUser();
            String name = result.getLastName() + " " + result.getFirstName() + " " + result.getPatronymic();
            String role = result.getRole();
            String email = result.getEmail();
            Date enabledUntil = result.getEnabledUntil();

            String facultyDepartment;
            String groupDegree;
            String reimTitle;
            Label firstLabel;
            Label secondLabel;
            Label thirdLabel;
            Label untilText = new Label("Действителен до");
            Label accountText = new Label("Аккаунт");
            accountText.getStyleClass().add("settingsBold16");

            HBox accountTextBox = new HBox(1);
            accountTextBox.setAlignment(Pos.CENTER);
            accountTextBox.getChildren().add(accountText);

            Label userLogo = new Label();
            userLogo.setMinSize(60,60);

            if (result.getRole().equals("Студент")) {
                facultyDepartment = result.getFaculty();
                groupDegree = result.getGroupName();
                reimTitle = result.getReimbursement();
                firstLabel = new Label("Факультет");
                secondLabel = new Label("Учебная группа");
                thirdLabel = new Label("Форма возмещения");
                accountBox.setStyle("-fx-background-color: #00a7ff");
                userLogo.getStyleClass().add("userLogoSettings");
            } else {
                facultyDepartment = result.getDepartment();
                groupDegree = result.getAcademicDegree();
                reimTitle = result.getAcademicTitle();
                firstLabel = new Label("Кафедра");
                secondLabel = new Label("Ученая степень");
                thirdLabel = new Label("Ученое звание");
                accountBox.setStyle("-fx-background-color: #ff8d00");
                userLogo.getStyleClass().add("teacherLogoSettings");
            }

            Label nameLabel = new Label(name);
            nameLabel.getStyleClass().add("settingsBold16");
            Label roleLabel = new Label(role);
            roleLabel.getStyleClass().add("settingsRoleText");
            Label emailLabel = new Label(email);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = outputFormat.format(enabledUntil);
            Label untilLabel = new Label(formattedDate);

            Label facultyDepartmentLabel = new Label(facultyDepartment);
            Label groupDegreeLabel = new Label(groupDegree);
            Label reimTitleLabel = new Label(reimTitle);

            HBox mainInfo = new HBox(2);

            VBox mainDetails = new VBox(2);
            mainDetails.getChildren().addAll(nameLabel, roleLabel);
            mainInfo.getChildren().addAll(userLogo, mainDetails);

            VBox facultyBox = new VBox(2);
            facultyBox.setSpacing(0);
            facultyBox.getChildren().addAll(firstLabel, facultyDepartmentLabel);

            VBox emailBox = new VBox(2);
            emailBox.setSpacing(0);
            Label emailText = new Label("Почта");
            emailBox.getChildren().addAll(emailText, emailLabel);

            VBox groupBox = new VBox(2);
            groupBox.setSpacing(0);
            groupBox.getChildren().addAll(secondLabel, groupDegreeLabel);

            VBox titleBox = new VBox(2);
            titleBox.setSpacing(0);
            titleBox.getChildren().addAll(thirdLabel, reimTitleLabel);

            VBox untilBox = new VBox(2);
            untilBox.setSpacing(0);
            untilBox.getChildren().addAll(untilText, untilLabel);

            HBox footerUntil = new HBox(1);
            footerUntil.setAlignment(Pos.BOTTOM_RIGHT);
            footerUntil.getChildren().add(untilBox);

            firstLabel.getStyleClass().add("settingsTitles");
            secondLabel.getStyleClass().add("settingsTitles");
            thirdLabel.getStyleClass().add("settingsTitles");
            emailText.getStyleClass().add("settingsTitles");
            untilText.getStyleClass().add("settingsTitles");

            facultyDepartmentLabel.getStyleClass().add("settingsResults");
            emailLabel.getStyleClass().add("settingsResults");
            groupDegreeLabel.getStyleClass().add("settingsResults");
            reimTitleLabel.getStyleClass().add("settingsResults");

            untilLabel.getStyleClass().add("settingsResultsUntil");

            accountBox.setSpacing(5);
            accountBox.setPadding(new Insets(10,10,10,10));

            HBox footerBox = new HBox(2);
            footerBox.getChildren().addAll(titleBox, footerUntil);
            if (result.getRole().equals("Студент")) { footerUntil.setPadding(new Insets(10,0,0,90)); }
            else { footerUntil.setPadding(new Insets(10,0,0,120)); }

            accountBox.getChildren().addAll(accountTextBox, mainInfo, facultyBox, emailBox, groupBox, footerBox);

            VBox exitPane = new VBox(1);
            exitPane.setAlignment(Pos.CENTER);
            exitPane.setPadding(new Insets(0,0,20,0));
            Button exitButton = new Button("Выйти из аккаунта");
            exitButton.setOnMouseEntered(event -> exitButton.setCursor(Cursor.HAND));
            exitButton.setOnMouseExited(event -> exitButton.setCursor(Cursor.DEFAULT));
            exitButton.getStyleClass().add("settingsExitButton");
            MainController mainController = new MainController(view);
            exitButton.setOnMouseClicked(event -> mainController.openLogout(1));
            exitPane.getChildren().add(exitButton);

            VBox mainBox = new VBox(10);
            mainBox.setAlignment(Pos.CENTER);
            mainBox.getChildren().addAll(accountBox, otherBox);

            settingsBox.setBottom(exitPane);
            settingsBox.setCenter(mainBox);
            Scene scene = new Scene(settingsBox, 409, 729);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            settingsStage.setScene(scene);

            settingsStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    view.getPrimaryStage().getScene().getRoot().setEffect(null);
                }
            });

            settingsStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
