package com.example.NefesleChat;

// MainView.java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class MainView {
    private Stage primaryStage;
    private VBox chatArea;
    private TextField messageInput;
    private BorderPane root;
    private ChatView chatView;
    private ScrollPane scrollPane;
    private ComboBox<String> userComboBox; // Объявляем ComboBox как поле класса

    public MainView() {
        this.primaryStage = new Stage();
        userComboBox = new ComboBox<>();  // Создаем ComboBox в конструкторе
        updateUserComboBox();           // Заполняем ComboBox данными в конструкторе
    }

    public void show() {
        primaryStage.setTitle("Мессенджер");

        root = new BorderPane();
        VBox topPanel = createMenu();
        root.setLeft(topPanel);

        //VBox messengerPanel = new VBox();

        chatArea = new VBox();
        chatArea.setPadding(new Insets(10));
        chatArea.setSpacing(5);

        scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
        scrollPane.vvalueProperty().bind(chatArea.heightProperty());

        HBox bottomPanel = createBottomPanel();
        root.setBottom(bottomPanel);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMenu() {
        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.TOP_CENTER);

        Button timelineButton = new Button("Расписание");
        Button chatButton = new Button("Чат");
        Button contactsButton = new Button("Контакты");
        Button logoutButton = new Button("Выйти");
        MainController mainController = new MainController(this);
        timelineButton.setOnAction(mainController::openTimeline);
        chatButton.setOnAction(mainController::openChat);
        contactsButton.setOnAction(mainController::openContacts);
        logoutButton.setOnAction(mainController::logout);

        topPanel.getChildren().addAll(timelineButton, chatButton, contactsButton, logoutButton, userComboBox); // Добавляем ComboBox в меню один раз
        return topPanel;
    }

    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox(10);
        bottomPanel.setPadding(new Insets(10));

        messageInput = new TextField();
        messageInput.setPromptText("Введите сообщение");
        HBox.setHgrow(messageInput, Priority.ALWAYS);

        Button sendButton = new Button("Отправить");

        chatView = new ChatView( this);
        ChatController chatController = new ChatController(chatView);

        sendButton.setOnAction(chatController::sendMessage);
        bottomPanel.getChildren().addAll(messageInput, sendButton);
        return bottomPanel;
    }

    // Method to update the ComboBox items
    private void updateUserComboBox() {
        List<String> users = new ArrayList<String>();
        users.add("user1");
        users.add("user2");
        ObservableList<String> observableUsers = FXCollections.observableArrayList(users);
        userComboBox.setItems(observableUsers);

        if (!users.isEmpty()) {
            userComboBox.setValue(observableUsers.get(0)); // Select the first user by default
        } else {
            userComboBox.setValue(null); // Clear selection if no users
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public VBox getChatArea() {
        return chatArea;
    }

    public TextField getMessageInput() {
        return messageInput;
    }

    public BorderPane getRoot() {
        return root;
    }

    public String getSelectedUser() {
        return userComboBox.getValue();
    }
}
