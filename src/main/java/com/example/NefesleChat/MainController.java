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
        view.setCurrentListLabel("Сообщения");
        view.showChatBox();
    }

    public void openTimeline(MouseEvent event) {
        view.setCurrentListLabel("Расписание");
        view.selectedTimelineButton();
    }

    public void openNotes(MouseEvent event) {
        view.setCurrentListLabel("Заметки");
        view.selectedNotesButton();
    }

    public void openTasks(MouseEvent event) {
        view.setCurrentListLabel("Задачи");
        view.selectedTasksButton();
    }

    public void openUsers(MouseEvent event) {
        view.setCurrentListLabel("Пользователи");
        view.showUsersBox();
    }

    public void openSettings(MouseEvent event) {
        view.setEffects();
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.showSettings(view);
    }

    public void openLogout(int b) {
        view.setEffects();
        LogoutView logoutView = new LogoutView();
        logoutView.showLogout(view, b);
    }
}
