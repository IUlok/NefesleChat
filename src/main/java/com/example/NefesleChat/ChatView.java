package com.example.NefesleChat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ChatView {

    private MainView mainView;
    private VBox chatArea;
    @Getter
    private javafx.scene.control.TextField messageInput;

    public ChatView(MainView mainView) {
        this.mainView = mainView;
        initialize();
    }

    public void initialize() {
        chatArea = mainView.getChatArea();
        messageInput = mainView.getMessageInput();
    }

    public void sendMessage(String message) {
        if (mainView.getFocusedChat()) Main.getWebSocketUtil().sendMessageToChat(mainView.getFocusChat(), message);
        else Main.getWebSocketUtil().sendMessageToUser(mainView.getFocusUser(), message);
    }

    public void addMessage(String sender, String message, int userID, Date cratedDate, String typeMessage, boolean seen) {
        VBox messageContainer = createMessageContainer(sender, message, userID, cratedDate, typeMessage, seen);
        chatArea.getChildren().add(0, messageContainer);
    }

    public void addMessageFromSocket(String sender, String message, int userID, Date cratedDate, String typeMessage, boolean seen) {
        VBox messageContainer = createMessageContainer(sender, message, userID, cratedDate, typeMessage, seen);
        chatArea.getChildren().add(messageContainer);
        chatArea.applyCss();
        chatArea.layout();
        mainView.setScrollMessages(1.0);
    }

    private VBox createMessageContainer(String sender, String message, int userID, Date createdDate, String typeMessage, boolean seen) {
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
        senderLabel.setOnMouseClicked(event -> {
            mainView.selectedUserBox(userID);
        });

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER_LEFT);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add("message-text");

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        String formattedTime = timeFormat.format(createdDate);

        Label timeLabel = new Label(formattedTime);
        timeLabel.setAlignment(Pos.BOTTOM_RIGHT);
        timeLabel.getStyleClass().add("message-time");

        HBox timeBox = new HBox();
        timeBox.setAlignment(Pos.CENTER_RIGHT);
        timeBox.setSpacing(5);

        Label isViewed = new Label();
        isViewed.getStyleClass().add("message-isViewed");
        if (seen) {
            isViewed.setText("◉");
        } else {
            isViewed.setText("○");
            isViewed.setStyle("-fx-font-size: 10px;");
        }

        timeBox.getChildren().addAll(isViewed, timeLabel);

        messageBox.getChildren().addAll(senderLabel, messageLabel, timeBox);

        if (typeMessage == "INFO") {
            messageBox.getChildren().removeAll(senderLabel, messageLabel, timeBox);
            messageBox.setMinWidth(700);
            messageBox.getStyleClass().remove("message-container");
            messageLabel.getStyleClass().remove("message-text");
            messageLabel.getStyleClass().add("message-informtext");
            messageBox.getStyleClass().add("message-informcontainer");
            messageLabel.setAlignment(Pos.CENTER);
            messageBox.setAlignment(Pos.CENTER);
            messageBox.getChildren().add(messageLabel);
            messageContainer.setAlignment(Pos.CENTER);
        }

        messageContainer.getChildren().add(messageBox);

        return messageContainer;
    }

}
