package com.example.NefesleChat;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ChatView {

    private MainView mainView;
    private VBox chatArea;
    private javafx.scene.control.TextField messageInput;

    public ChatView(MainView mainView) {
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

    public void addMessage(String sender, String message, int userID, Date cratedDate) {
        VBox messageContainer = createMessageContainer(sender, message, userID, cratedDate);
        chatArea.getChildren().add(messageContainer);
    }

    private VBox createMessageContainer(String sender, String message, int userID, Date createdDate) {
        VBox messageContainer = new VBox();
        VBox messageBox = new VBox(3);
        messageBox.setPadding(new Insets(15));
        messageBox.setSpacing(0);
        messageBox.setMaxWidth(450);
        messageBox.getStyleClass().add("message-container");

        if (userID == mainView.getMyID()) {
            messageContainer.setAlignment(Pos.TOP_RIGHT);
        } else {
            messageContainer.setAlignment(Pos.TOP_LEFT);
        }

        Label senderLabel = new Label(sender);
        senderLabel.setAlignment(Pos.TOP_LEFT);
        senderLabel.getStyleClass().add("message-sender");
        senderLabel.setOnMouseEntered(event -> senderLabel.setCursor(Cursor.HAND));
        senderLabel.setOnMouseExited(event -> senderLabel.setCursor(Cursor.DEFAULT));

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER_LEFT);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add("message-text");

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = timeFormat.format(createdDate);

        Label timeLabel = new Label(formattedTime);
        timeLabel.setAlignment(Pos.BOTTOM_RIGHT);
        timeLabel.getStyleClass().add("message-time");

        HBox timeBox = new HBox();
        timeBox.setAlignment(Pos.BOTTOM_RIGHT);
        timeBox.getChildren().add(timeLabel);

        messageBox.getChildren().addAll(senderLabel, messageLabel, timeBox);
        messageContainer.getChildren().add(messageBox);

        return messageContainer;
    }

}
