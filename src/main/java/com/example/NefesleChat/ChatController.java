package com.example.NefesleChat;

import com.example.NefesleChat.entity.ChatDTO;
import com.example.NefesleChat.entity.MessageDTO;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
            //view.addMessage(selectedUser, message, 1, );
            view.getMessageInput().clear();
        }
    }

    public void loadChat(int ChatID, int listMessages) {
        List<MessageDTO> result;
        try {
            result = HttpUtil.getMessagesInChat(ChatID, listMessages);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            result = Collections.emptyList();
            e.printStackTrace();
        }

        Collections.reverse(result);
        for (MessageDTO message:result) {
            view.addMessage(message.getSenderName(), message.getMessage(), message.getSenderId(), message.getCreatedAt());
        }
    }
}
