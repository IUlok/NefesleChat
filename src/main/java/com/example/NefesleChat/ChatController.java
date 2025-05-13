package com.example.NefesleChat;

import com.example.NefesleChat.entity.MessageDTO;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.net.URISyntaxException;
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

    public void sendMessageUse() {
        String message = view.getMessageInput().getText();
        if (message != null && !message.isEmpty()) {
            view.sendMessage(message);
            view.getMessageInput().clear();
        }
    }

    public void loadChat(int ChatID, int listMessages) {
        List<MessageDTO> result;
        try {
            result = Main.getHttpUtil().getMessagesInChat(ChatID, listMessages);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            result = Collections.emptyList();
            e.printStackTrace();
        }

        for (MessageDTO message:result) {
            view.addMessage(message.getSenderName(), message.getMessage(), message.getSenderId(), message.getCreatedAt(), message.getType().toString(), message.isSeen(), message.getChatId(), message.getId(), message.isSeenByMe());
        }
    }
}
