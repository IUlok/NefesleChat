package com.example.nefeslepoligon;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox; // Импортируйте HBox
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatView {

    private DataModel dataModel;
    private MainView mainView;
    private VBox chatArea;
    private javafx.scene.control.TextField messageInput;

    public ChatView(DataModel dataModel, MainView mainView) {
        this.dataModel = dataModel;
        this.mainView = mainView;
        initialize();
    }

    public void initialize() {
        chatArea = mainView.getChatArea();
        messageInput = mainView.getMessageInput();
    }

    public VBox getChatArea() {
        return chatArea;
    }

    public javafx.scene.control.TextField getMessageInput() {
        return messageInput;
    }

    public MainView getMainView() {
        return mainView;
    }

    // В ChatView.java
    public void addMessage(String sender, String message, boolean isMyMessage) {
        VBox messageContainer = createMessageContainer(sender, message, isMyMessage);
        chatArea.getChildren().add(messageContainer);
    }

    private VBox createMessageContainer(String sender, String message, boolean isMyMessage) {
        VBox messageContainer = new VBox();
        messageContainer.getStyleClass().add("message-container");

        // Alignment based on sender
        if (isMyMessage) {
            messageContainer.setAlignment(Pos.TOP_RIGHT); // Align my messages to the right
        } else {
            messageContainer.setAlignment(Pos.TOP_LEFT); // Align other messages to the left
        }

        // Sender Label
        Label senderLabel = new Label(sender);
        senderLabel.getStyleClass().add("message-sender");

        // Message Label
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add("message-text");

        // Time Label
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(formatter);

        Label timeLabel = new Label(formattedTime);
        timeLabel.getStyleClass().add("message-time");

        messageContainer.getChildren().addAll(senderLabel, messageLabel, timeLabel);

        return messageContainer;
    }

}
