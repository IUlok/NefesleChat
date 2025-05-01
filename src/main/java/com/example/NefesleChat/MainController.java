package com.example.NefesleChat;

import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;

public class MainController {
    private MainView view;

    public MainController(MainView view) {
        this.view = view;
    }

    public void openChat(MouseEvent event) {
        //view.createMessageBox(); // Не работает нормально. нужно делать отдельные stage и классы

        view.selectedChatButton();
    }

    public void openTimeline(MouseEvent event) {
        //view.createTimelineBox(); // Не работает нормально. нужно делать отдельные stage и классы

        view.selectedTimelineButton();
    }

    public void openNotes(MouseEvent event) {


        view.selectedNotesButton();
    }

    public void openTasks(MouseEvent event) {


        view.selectedTasksButton();
    }

    public void openUsers(MouseEvent event) {


        view.selectedUsersButton();
    }

    public void openSettings(MouseEvent event) {
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.showSettings(view.getPrimaryStage());
    }

    public void logout(MouseEvent event) {
        URL resourceURL = getClass().getResource("/jwtToken");
        File jwtTokenFile = new File(resourceURL.getFile());
        try {
            FileWriter fileWriter = new FileWriter(jwtTokenFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException e){
                e.printStackTrace();
        }

        view.getPrimaryStage().close();
        LoginRegistrationView loginRegistrationView = new LoginRegistrationView();
        loginRegistrationView.show();
    }
}
