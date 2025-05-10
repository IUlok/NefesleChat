package com.example.NefesleChat;

import javafx.scene.input.MouseEvent;

public class MainController {
    private MainView view;

    public MainController(MainView view) {
        this.view = view;
    }

    public void openChat(MouseEvent event) {
        view.setVisibleTrueChat();
        view.setCurrentListLabel("Сообщения");
        view.showChatBox();
    }

    public void openTimeline(MouseEvent event) {
        view.setVisibleFalseChat();
        view.setCurrentListLabel("Расписание");
        view.selectedTimelineButton();
        view.showTimelineBox();
    }

    public void openNotes(MouseEvent event) {
        view.setVisibleFalseChat();
        view.setCurrentListLabel("Заметки");
        view.selectedNotesButton();
        view.showNotesBox();
    }

    public void openTasks(MouseEvent event) {
        view.setVisibleFalseChat();
        view.setCurrentListLabel("Задачи");
        view.selectedTasksButton();
        view.showTasksBox();
    }

    public void openUsers(MouseEvent event) {
        view.setVisibleFalseChat();
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
