package com.example.NefesleChat;

import javafx.event.ActionEvent;

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
        //ChatView chatView = new ChatView();
        //chatView.show();
        //TODO Открытие окна с чатом
    }

    public void openContacts(ActionEvent event) {
        System.out.println("Opening Contacts");
        //TODO Открытие окна с контактами
    }

    public void logout(ActionEvent event) {
        System.out.println("Logging out");
        view.getPrimaryStage().close();
        LoginRegistrationView loginRegistrationView = new LoginRegistrationView();
        loginRegistrationView.show();
    }
}
