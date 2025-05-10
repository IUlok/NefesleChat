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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserBox {
    public void showUserBox(MainView view, int id) {
        try {
            Stage userBoxStage = new Stage();
            userBoxStage.initStyle(StageStyle.TRANSPARENT);
            userBoxStage.setResizable(false);
            userBoxStage.setTitle("");
            userBoxStage.initModality(Modality.APPLICATION_MODAL);
            userBoxStage.initOwner(view.getPrimaryStage());

            BorderPane userMain = new BorderPane();
            VBox userBox = new VBox(10);
            userMain.getStyleClass().add("userPaneBox");

            UserDetailsDTO result = HttpUtil.getOtherUser(id);

            String name = result.getLastName() + " " + result.getFirstName() + " " + result.getPatronymic();
            String role = result.getRole();

            String facultyDepartment;
            String groupDegree;
            String reimTitle;
            Label firstLabel;
            Label secondLabel;
            Label thirdLabel;
            Label onlineLabel;

            if (result.getStatus().equals("В сети")) {
                onlineLabel = new Label("Онлайн");
                onlineLabel.getStyleClass().add("onlineLabel");
            } else {
                onlineLabel = new Label("Офлайн");
                onlineLabel.getStyleClass().add("offlineLabel");
            }

            onlineLabel.setPrefSize(55, 20);
            onlineLabel.setAlignment(Pos.CENTER);

            Button closeButton = new Button();
            closeButton.setOnMouseEntered(event -> closeButton.setCursor(Cursor.HAND));
            closeButton.setOnMouseExited(event -> closeButton.setCursor(Cursor.DEFAULT));
            closeButton.getStyleClass().add("userCloseButton");
            closeButton.setPrefSize(15,15);
            closeButton.setOnMouseClicked(event -> {
                userBoxStage.close();
                view.getPrimaryStage().getScene().getRoot().setEffect(null);
            });

            BorderPane userHeaderBox = new BorderPane();
            userHeaderBox.setCenter(onlineLabel);
            userHeaderBox.setRight(closeButton);

            Label userLogo = new Label();
            userLogo.setMinSize(60,60);

            if (result.getRole().equals("Студент")) {
                facultyDepartment = result.getFaculty();
                groupDegree = result.getGroupName();
                reimTitle = result.getReimbursement();
                firstLabel = new Label("Факультет");
                secondLabel = new Label("Учебная группа");
                thirdLabel = new Label("Форма возмещения");
                userMain.setStyle("-fx-background-color: #00a7ff");
                userLogo.getStyleClass().add("userLogoSettings");
            } else {
                facultyDepartment = result.getDepartment();
                groupDegree = result.getAcademicDegree();
                reimTitle = result.getAcademicTitle();
                firstLabel = new Label("Кафедра");
                secondLabel = new Label("Ученая степень");
                thirdLabel = new Label("Ученое звание");
                userMain.setStyle("-fx-background-color: #ff8d00");
                userLogo.getStyleClass().add("teacherLogoSettings");
            }

            Label nameLabel = new Label(name);
            nameLabel.getStyleClass().add("settingsBold16");
            Label roleLabel = new Label(role);
            roleLabel.getStyleClass().add("settingsRoleText");

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

            VBox groupBox = new VBox(2);
            groupBox.setSpacing(0);
            groupBox.getChildren().addAll(secondLabel, groupDegreeLabel);

            VBox titleBox = new VBox(2);
            titleBox.setSpacing(0);
            titleBox.getChildren().addAll(thirdLabel, reimTitleLabel);

            firstLabel.getStyleClass().add("settingsTitles");
            secondLabel.getStyleClass().add("settingsTitles");
            thirdLabel.getStyleClass().add("settingsTitles");

            facultyDepartmentLabel.getStyleClass().add("settingsResults");
            groupDegreeLabel.getStyleClass().add("settingsResults");
            reimTitleLabel.getStyleClass().add("settingsResults");

            userBox.setSpacing(5);
            userBox.setPadding(new Insets(10,10,10,10));

            VBox writePane = new VBox(1);
            writePane.setAlignment(Pos.CENTER);
            Button writeButton = new Button("Написать");
            writeButton.setPrefSize(130, 40);
            writeButton.setOnMouseEntered(event -> writeButton.setCursor(Cursor.HAND));
            writeButton.setOnMouseExited(event -> writeButton.setCursor(Cursor.DEFAULT));
            writeButton.getStyleClass().add("writeUserButton");
            if(id == view.getMyID()) {writeButton.setVisible(false);}
            writeButton.setOnMouseClicked(event -> {
                userBoxStage.close();
                view.getPrimaryStage().getScene().getRoot().setEffect(null);
            });

            writePane.getChildren().add(writeButton);

            userBox.getChildren().addAll(mainInfo, facultyBox, groupBox, titleBox);

            userHeaderBox.setPadding(new Insets(10, 10, 0, 0));
            writePane.setPadding(new Insets(0,0,10,0));

            userMain.setTop(userHeaderBox);
            userMain.setCenter(userBox);
            userMain.setBottom(writePane);

            Scene scene = new Scene(userMain, 400, 350);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            userBoxStage.setScene(scene);
            userBoxStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
