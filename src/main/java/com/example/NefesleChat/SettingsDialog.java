package com.example.NefesleChat;

import javafx.scene.Scene;
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

            Scene scene = new Scene(settingsBox, 400, 600);
            settingsStage.setScene(scene);
            settingsStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
