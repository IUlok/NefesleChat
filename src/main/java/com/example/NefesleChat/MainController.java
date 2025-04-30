package com.example.NefesleChat;

import javafx.event.ActionEvent;

import java.io.*;
import java.net.URL;

public class MainController {
    private MainView view;

    public MainController(MainView view) {
        this.view = view;
    }

    public void openTimeline(ActionEvent event) {
        System.out.println("Opening Timeline");
        //TODO Открытие окна с расписанием
    }

    public void openChat(ActionEvent event) {
        System.out.println("Opening Chat");
        //ChatView chatView = new ChatView(view);
        //chatView.show();
        //TODO Открытие окна с чатом
    }

    public void openContacts(ActionEvent event) {
        System.out.println("Opening Contacts");
        //TODO Открытие окна с контактами
    }

    public void logout(ActionEvent event) {
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
