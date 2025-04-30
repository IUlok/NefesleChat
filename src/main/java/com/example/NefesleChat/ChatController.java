package com.example.NefesleChat;

import javafx.event.ActionEvent;

public class ChatController {

    private ChatView view;

    public ChatController(ChatView view) {
        this.view = view;
    }

    public void sendMessage(ActionEvent event) {
        String message = view.getMessageInput().getText();
        if (message != null && !message.isEmpty()) {
            MainView mainView = view.getMainView();
            String selectedUser = mainView.getSelectedUser(); // Get selected user from ComboBox
            view.addMessage(selectedUser, message, selectedUser.equals("user1"));
            view.getMessageInput().clear();
        }
    }
}
