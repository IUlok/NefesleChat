package com.example.NefesleChat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogoutView {
    public void showLogout(MainView view) {
        LogoutController logoutController = new LogoutController(view);

        Stage logoutStage = new Stage();
        logoutStage.initStyle(StageStyle.TRANSPARENT);
        logoutStage.setResizable(false);
        logoutStage.setTitle("");
        logoutStage.initModality(Modality.APPLICATION_MODAL);
        logoutStage.initOwner(view.getPrimaryStage());
        GridPane logout = new GridPane(0,1);
        logout.setAlignment(Pos.CENTER);
        logout.getStyleClass().add("exit-panel");

        Label text = new Label("Вы действительно хотите выйти?");
        text.getStyleClass().add("exitText");
        text.setMaxWidth(300);
        Button yesButton = new Button("Да");
        yesButton.setOnMouseEntered(event -> yesButton.setCursor(Cursor.HAND));
        yesButton.setOnMouseExited(event -> yesButton.setCursor(Cursor.DEFAULT));
        yesButton.getStyleClass().add("exitButtonYes");
        Button noButton = new Button("Нет");
        noButton.setOnMouseEntered(event -> noButton.setCursor(Cursor.HAND));
        noButton.setOnMouseExited(event -> noButton.setCursor(Cursor.DEFAULT));
        noButton.getStyleClass().add("exitButtonNo");
        HBox buttons = new HBox(2);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20,0,0,0));
        buttons.getChildren().addAll(yesButton, noButton);

        logout.add(text, 0, 0);
        logout.add(buttons, 0, 1);

        yesButton.setOnMouseClicked(logoutController::logout);
        noButton.setOnMouseClicked(event -> logoutStage.close());

        Scene scene = new Scene(logout, 300, 100);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        logoutStage.setScene(scene);
        logoutStage.showAndWait();
    }
}
