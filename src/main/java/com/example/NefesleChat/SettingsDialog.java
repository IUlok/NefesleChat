package com.example.NefesleChat;

import com.example.NefesleChat.entity.UserDetailsDTO;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SettingsDialog {
    public void showSettings(Stage view) {
        try {
            Stage settingsStage = new Stage();
            settingsStage.setResizable(false);
            settingsStage.setTitle("Настройки");
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.initOwner(view);
            BorderPane settingsBox = new BorderPane();

            UserDetailsDTO result = HttpUtil.getCurrentUser();
            String role = result.getRole();
            String name = result.getLastName() + " " + result.getFirstName() + " " + result.getPatronymic();

            Label nameLabel = new Label(name);
            Label roleLabel = new Label(role);

            settingsBox.setTop(nameLabel);
            settingsBox.setCenter(roleLabel);
            Scene scene = new Scene(settingsBox, 400, 600);
            settingsStage.setScene(scene);
            settingsStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
