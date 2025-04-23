package com.example.nefeslepoligon;

import javafx.event.ActionEvent;

public class ChatController {

    private ChatView view;
    private DataModel dataModel;

    public ChatController(ChatView view, DataModel dataModel) {
        this.view = view;
        this.dataModel = dataModel;
    }

    public void sendMessage(ActionEvent event) {
        String message = view.getMessageInput().getText();
        if (message != null && !message.isEmpty()) {
            MainView mainView = view.getMainView();
            String selectedUser = mainView.getSelectedUser(); // Get selected user from ComboBox
            view.addMessage(selectedUser, message, selectedUser.equals(dataModel.getUsername()));
            view.getMessageInput().clear();
        }
    }
}
