package com.example.NefesleChat;

// MainView.java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        BorderPane topPanel = createMenu();
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

        Scene scene = new Scene(root, 1280, 768);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane createMenu() {
        BorderPane menuPanel = new BorderPane();
        menuPanel.setPadding(new Insets(10));
        //menuPanel.setAlignment(Pos.TOP_CENTER);

        VBox topMenuPanel = new VBox(10);
        VBox bottomMenuPanel = new VBox(10);

        Label logo = new Label("Logo");
        Label chatButton = new Label("Чат");
        Label timelineButton = new Label("Расписание");
        Label notesButton = new Label("Заметки");
        Label tasksButton = new Label("Задачи");
        Label contactsButton = new Label("Контакты");
        Label settingsButton = new Label("Настройки");
        Label logoutButton = new Label("Выйти");
        MainController mainController = new MainController(this);
        chatButton.setOnMouseClicked(mainController::openChat);
        timelineButton.setOnMouseClicked(mainController::openTimeline);
        notesButton.setOnMouseClicked(mainController::openNotes);
        tasksButton.setOnMouseClicked(mainController::openTasks);
        contactsButton.setOnMouseClicked(mainController::openContacts);

        settingsButton.setOnMouseClicked(mainController::openSettings);
        logoutButton.setOnMouseClicked(mainController::logout);

        topMenuPanel.getChildren().addAll(logo, chatButton, timelineButton, notesButton, tasksButton, contactsButton, userComboBox);
        bottomMenuPanel.getChildren().addAll(settingsButton, logoutButton);

        //menuPanel.getChildren().addAll(topMenuPanel, bottomMenuPanel); // Добавляем ComboBox в меню один раз
        menuPanel.setTop(topMenuPanel);
        menuPanel.setBottom(bottomMenuPanel);
        return menuPanel;
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
