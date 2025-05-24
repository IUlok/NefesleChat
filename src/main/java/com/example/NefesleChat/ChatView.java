package com.example.NefesleChat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import net.synedra.validatorfx.Check;

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
        //if (mainView.getFocusedChat()) Main.getWebSocketUtil().sendMessageToChat(mainView.getFocusChat(), message);
        //else Main.getWebSocketUtil().sendMessageToUser(mainView.getFocusUser(), message);
    }

    public void addMessage(String sender, String message, int userID, Date cratedDate, String typeMessage, boolean seen, int chatID, int messageID, boolean isSeenByMe) {
        VBox messageContainer = createMessageContainer(sender, message, userID, cratedDate, typeMessage, seen, chatID, messageID, isSeenByMe);
        chatArea.getChildren().add(0, messageContainer);
    }

    public void addMessageFromSocket(String sender, String message, int userID, Date cratedDate, String typeMessage, boolean seen, int chatID, int messageID, boolean isSeenByMe) {
        VBox messageContainer = createMessageContainer(sender, message, userID, cratedDate, typeMessage, seen, chatID, messageID, isSeenByMe);
        chatArea.getChildren().add(messageContainer);
        chatArea.applyCss();
        chatArea.layout();
        mainView.setScrollMessages(1.0);
    }

    private VBox createMessageContainer(String sender, String message, int userID, Date createdDate, String typeMessage, boolean seen, int chatID, int messageID, boolean isSeenByMe) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Редактировать");
        MenuItem deleteItem = new MenuItem("Удалить");
        contextMenu.getStyleClass().add("contextMenu");
        editItem.getStyleClass().add("contextItems");
        deleteItem.getStyleClass().add("contextItems");
        contextMenu.getItems().addAll(editItem, deleteItem);

        VBox messageContainer = new VBox();
        VBox messageContainer1 = new VBox();

        VBox messageBox = new VBox(3);
        VBox messageBox1 = new VBox(3);

        messageBox.setPadding(new Insets(15));
        messageBox.setSpacing(0);
        messageBox.setMaxWidth(450);
        messageBox.getStyleClass().add("message-container");

        messageBox1.setPadding(new Insets(15));
        messageBox1.setSpacing(0);
        messageBox1.setMaxWidth(450);
        messageBox1.getStyleClass().add("message-container");

        if (userID == mainView.getMyID()) {
            messageContainer.setAlignment(Pos.TOP_RIGHT);
            messageContainer1.setAlignment(Pos.TOP_RIGHT);
        } else {
            messageContainer.setAlignment(Pos.TOP_LEFT);
            messageContainer1.setAlignment(Pos.TOP_LEFT);
        }

        Label senderLabel = new Label(sender);
        Label senderLabel1 = new Label(sender);

        senderLabel.setAlignment(Pos.TOP_LEFT);
        senderLabel.getStyleClass().add("message-sender");
        senderLabel.setOnMouseEntered(event -> senderLabel.setCursor(Cursor.HAND));
        senderLabel.setOnMouseExited(event -> senderLabel.setCursor(Cursor.DEFAULT));
        senderLabel.setOnMouseClicked(event -> {
            mainView.selectedUserBox(userID);
        });

        senderLabel1.setAlignment(Pos.TOP_LEFT);
        senderLabel1.getStyleClass().add("message-sender1");

        Label messageLabel = new Label(message);
        Label messageLabel1 = new Label(message);

        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER_LEFT);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add("message-text");

        messageLabel1.setWrapText(true);
        messageLabel1.setAlignment(Pos.CENTER_LEFT);
        messageLabel1.setWrapText(true);
        messageLabel1.getStyleClass().add("message-text");

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
        String formattedTime = timeFormat.format(createdDate);

        Label timeLabel = new Label(formattedTime);
        Label timeLabel1 = new Label(formattedTime);

        timeLabel.setAlignment(Pos.BOTTOM_RIGHT);
        timeLabel.getStyleClass().add("message-time");

        timeLabel1.setAlignment(Pos.BOTTOM_RIGHT);
        timeLabel1.getStyleClass().add("message-time");

        HBox timeBox = new HBox();
        timeBox.setAlignment(Pos.CENTER_RIGHT);
        timeBox.setSpacing(5);

        HBox timeBox1 = new HBox();
        timeBox1.setAlignment(Pos.CENTER_RIGHT);
        timeBox1.setSpacing(5);

        Label isViewed = new Label();
        isViewed.setMinHeight(20);
        Label isViewed1 = new Label();
        isViewed1.setMinHeight(20);

        isViewed.getStyleClass().add("message-isViewed");
        isViewed1.getStyleClass().add("message-isViewed");

        if (seen) {
            isViewed.setText("◉");
            isViewed1.setText("◉");
        } else {
            isViewed.setText("○");
            isViewed.setStyle("-fx-font-size: 10px;");
            isViewed1.setText("○");
            isViewed1.setStyle("-fx-font-size: 10px;");
        }

        timeBox.getChildren().addAll(isViewed, timeLabel);
        timeBox1.getChildren().addAll(isViewed1, timeLabel1);

        messageBox.getChildren().addAll(senderLabel, messageLabel, timeBox);
        messageBox1.getChildren().addAll(senderLabel1, messageLabel1, timeBox1);
        messageContainer1.getChildren().add(messageBox1);

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

        messageContainer.setOnMouseEntered(event -> {
            if (userID != mainView.getMyID() && typeMessage != "INFO" && !seen && !isSeenByMe) {
                //Main.getWebSocketUtil().readMessageChat(chatID, messageID);
                //Main.getWebSocketUtil().readMessageUser(userID, messageID);
                isViewed.setText("◉");
                isViewed.setStyle("-fx-font-size: 15px;");
                isViewed1.setText("◉");
                isViewed1.setStyle("-fx-font-size: 15px;");
            }
        });

        messageContainer.setOnContextMenuRequested(event -> {
            if (userID == mainView.getMyID()) contextMenu.show(messageBox, event.getScreenX(), event.getScreenY());
        });

        editItem.setOnAction(event -> {

        });

        deleteItem.setOnAction(event -> {
            mainView.setEffects();
            Stage deleteStage = new Stage();
            deleteStage.initStyle(StageStyle.TRANSPARENT);
            deleteStage.setResizable(false);
            deleteStage.setTitle("");
            deleteStage.initModality(Modality.APPLICATION_MODAL);
            deleteStage.initOwner(mainView.getPrimaryStage());
            GridPane delete = new GridPane(0,2);
            delete.setAlignment(Pos.CENTER);
            delete.getStyleClass().add("delete-panel");

            Label text = new Label("Вы действительно хотите удалить сообщение?");
            text.setPadding(new Insets(10));
            text.getStyleClass().add("exitText");
            Button yesButton = new Button("Да");
            yesButton.setOnMouseEntered(e -> yesButton.setCursor(Cursor.HAND));
            yesButton.setOnMouseExited(e -> yesButton.setCursor(Cursor.DEFAULT));
            yesButton.getStyleClass().add("exitButtonYes");
            Button noButton = new Button("Нет");
            noButton.setOnMouseEntered(e -> noButton.setCursor(Cursor.HAND));
            noButton.setOnMouseExited(e -> noButton.setCursor(Cursor.DEFAULT));
            noButton.getStyleClass().add("exitButtonNo");
            HBox buttons = new HBox(2);
            buttons.setAlignment(Pos.CENTER);
            buttons.setPadding(new Insets(20,0,0,0));
            buttons.getChildren().addAll(yesButton, noButton);

            delete.add(text, 0, 0);
            delete.add(messageContainer1, 0, 1);
            delete.add(buttons, 0, 2);

            yesButton.setOnMouseClicked(e -> {
                // лоигка удаления
                messageContainer.getChildren().clear();

                mainView.getPrimaryStage().getScene().getRoot().setEffect(null);
                deleteStage.close();
            });

            noButton.setOnMouseClicked(e -> {
                mainView.getPrimaryStage().getScene().getRoot().setEffect(null);
                deleteStage.close();
            });

            Scene scene = new Scene(delete, 700, 300);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            deleteStage.setScene(scene);
            deleteStage.showAndWait();
        });

        messageContainer.getChildren().add(messageBox);

        return messageContainer;
    }

}
