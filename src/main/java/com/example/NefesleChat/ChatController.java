package com.example.NefesleChat;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;

public class ChatController {

    private ChatView view;

    public ChatController(ChatView view) {
        this.view = view;
    }

    public void sendMessage(ActionEvent event) {
        sendMessageUse();
    }

    public void handleEnterKey(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessageUse();
        }
    }

    private void sendMessageUse() {
        String message = view.getMessageInput().getText();
        if (message != null && !message.isEmpty()) {
            MainView mainView = view.getMainView();
            String selectedUser = mainView.getSelectedUser(); // Get selected user from ComboBox
            view.addMessage(selectedUser, message, selectedUser.equals("user1"));
            view.getMessageInput().clear();
        }
    }
}
