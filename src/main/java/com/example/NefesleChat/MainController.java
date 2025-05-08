package com.example.NefesleChat;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;

public class MainController {
    private MainView view;

    public MainController(MainView view) {
        this.view = view;
    }

    public void openChat(MouseEvent event) {
        view.showChatBox();
    }

    public void openTimeline(MouseEvent event) {

        view.selectedTimelineButton();
    }

    public void openNotes(MouseEvent event) {


        view.selectedNotesButton();
    }

    public void openTasks(MouseEvent event) {


        view.selectedTasksButton();
    }

    public void openUsers(MouseEvent event) {
        view.showUsersBox();
    }

    public void openSettings(MouseEvent event) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.1);
        colorAdjust.setContrast(-0.9);
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);
        blend.setTopInput(colorAdjust);
        blend.setBottomInput(blur);
        view.getPrimaryStage().getScene().getRoot().setEffect(blend);
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.showSettings(view);
    }

    public void openLogout(MouseEvent event) {
        LogoutView logoutView = new LogoutView();
        logoutView.showLogout(view);
    }
}
