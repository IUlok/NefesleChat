package com.example.NefesleChat;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;

public class MainController {
    private MainView view;

    public MainController(MainView view) {
        this.view = view;
    }

    public void openChat(MouseEvent event) {
        System.out.println("Переход в чат");
        //ChatView chatView = new ChatView(view);
        //chatView.show();
        //TODO Открытие окна с чатом
    }

    public void openTimeline(MouseEvent event) {
        System.out.println("Переход в расписание");
        //TODO Открытие окна с расписанием
    }

    public void openNotes(MouseEvent event) {
        System.out.println("Переход в заметки");
        //TODO Открытие окна с расписанием
    }

    public void openTasks(MouseEvent event) {
        System.out.println("Переход в задачи");
        //TODO Открытие окна с расписанием
    }

    public void openContacts(MouseEvent event) {
        System.out.println("Переход в контакты");
        //TODO Открытие окна с контактами
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
