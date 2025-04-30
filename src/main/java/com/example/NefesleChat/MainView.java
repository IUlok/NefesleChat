package com.example.NefesleChat;

// MainView.java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.transform.Translate;
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
        primaryStage.setTitle("NefesleChat");
        root = new BorderPane();

        BorderPane menuPanel = createMenu();
        HBox topPanel = createHeader();

        GridPane messageBox = createMessageBox();

        root.setLeft(menuPanel);
        root.setTop(topPanel);
        root.setCenter(messageBox);

        Scene scene = new Scene(root, 1280, 768);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane createMenu() {
        BorderPane menuPanel = new BorderPane();
        menuPanel.setPadding(new Insets(7));
        menuPanel.getStyleClass().add("menu-panel");

        VBox topMenuPanel = new VBox(10);
        topMenuPanel.setAlignment(Pos.CENTER);
        VBox bottomMenuPanel = new VBox(10);
        bottomMenuPanel.setAlignment(Pos.CENTER);

        Label chatButton = new Label();
        chatButton.setMinSize(38, 38);
        chatButton.setOnMouseEntered(event -> chatButton.setCursor(Cursor.HAND));
        chatButton.setOnMouseExited(event -> chatButton.setCursor(Cursor.DEFAULT));
        chatButton.getStyleClass().add("chat-button");
        Label timelineButton = new Label();
        timelineButton.setMinSize(28, 28);
        timelineButton.setOnMouseEntered(event -> timelineButton.setCursor(Cursor.HAND));
        timelineButton.setOnMouseExited(event -> timelineButton.setCursor(Cursor.DEFAULT));
        timelineButton.getStyleClass().add("timeline-button");
        Label notesButton = new Label();
        notesButton.setMinSize(28, 28);
        notesButton.setOnMouseEntered(event -> notesButton.setCursor(Cursor.HAND));
        notesButton.setOnMouseExited(event -> notesButton.setCursor(Cursor.DEFAULT));
        notesButton.getStyleClass().add("notes-button");
        Label tasksButton = new Label();
        tasksButton.setMinSize(28, 28);
        tasksButton.setOnMouseEntered(event -> tasksButton.setCursor(Cursor.HAND));
        tasksButton.setOnMouseExited(event -> tasksButton.setCursor(Cursor.DEFAULT));
        tasksButton.getStyleClass().add("tasks-button");
        Label contactsButton = new Label();
        contactsButton.setMinSize(28, 28);
        contactsButton.setOnMouseEntered(event -> contactsButton.setCursor(Cursor.HAND));
        contactsButton.setOnMouseExited(event -> contactsButton.setCursor(Cursor.DEFAULT));
        contactsButton.getStyleClass().add("contacts-button");
        Label settingsButton = new Label();
        settingsButton.setMinSize(28, 28);
        settingsButton.setOnMouseEntered(event -> settingsButton.setCursor(Cursor.HAND));
        settingsButton.setOnMouseExited(event -> settingsButton.setCursor(Cursor.DEFAULT));
        settingsButton.getStyleClass().add("settings-button");
        Label logoutButton = new Label();
        logoutButton.setMinSize(28, 28);
        logoutButton.setOnMouseEntered(event -> logoutButton.setCursor(Cursor.HAND));
        logoutButton.setOnMouseExited(event -> logoutButton.setCursor(Cursor.DEFAULT));
        logoutButton.getTransforms().add(new Translate(4, 0));
        logoutButton.getStyleClass().add("exit-button");
        MainController mainController = new MainController(this);
        chatButton.setOnMouseClicked(mainController::openChat);
        timelineButton.setOnMouseClicked(mainController::openTimeline);
        notesButton.setOnMouseClicked(mainController::openNotes);
        tasksButton.setOnMouseClicked(mainController::openTasks);
        contactsButton.setOnMouseClicked(mainController::openContacts);

        settingsButton.setOnMouseClicked(mainController::openSettings);
        logoutButton.setOnMouseClicked(mainController::logout);

        topMenuPanel.getChildren().addAll(chatButton, timelineButton, notesButton, tasksButton, contactsButton);
        bottomMenuPanel.getChildren().addAll(settingsButton, logoutButton);

        //menuPanel.getChildren().addAll(topMenuPanel, bottomMenuPanel); // Добавляем ComboBox в меню один раз
        menuPanel.setTop(topMenuPanel);
        menuPanel.setBottom(bottomMenuPanel);
        return menuPanel;
    }

    private HBox createHeader() {
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(5));
        topPanel.getStyleClass().add("top-panel");
        Label logo = new Label();
        logo.setMinSize(34, 34);
        logo.getStyleClass().add("logo");
        MainController mainController = new MainController(this);

        topPanel.getChildren().addAll(logo, userComboBox);
        return topPanel;
    }

    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox(10);
        bottomPanel.setPadding(new Insets(10));

        messageInput = new TextField();
        messageInput.setPromptText("Введите сообщение");
        HBox.setHgrow(messageInput, Priority.ALWAYS);

        Button sendButton = new Button("Отправить");
        sendButton.setOnMouseEntered(event -> sendButton.setCursor(Cursor.HAND));
        sendButton.setOnMouseExited(event -> sendButton.setCursor(Cursor.DEFAULT));

        chatView = new ChatView( this);
        ChatController chatController = new ChatController(chatView);

        messageInput.setOnKeyPressed(chatController::handleEnterKey);
        sendButton.setOnAction(chatController::sendMessage);
        bottomPanel.getChildren().addAll(messageInput, sendButton);
        return bottomPanel;
    }

    private GridPane createMessageBox() {
        GridPane messageBox = new GridPane(2, 0);

        messageBox.setVgap(10);
        messageBox.setHgap(10);

        BorderPane chatPanel = new BorderPane();
        GridPane dialogPanel = new GridPane();

        GridPane.setHgrow(chatPanel, Priority.ALWAYS);
        GridPane.setVgrow(chatPanel, Priority.ALWAYS);
        GridPane.setHgrow(dialogPanel, Priority.ALWAYS);
        GridPane.setVgrow(dialogPanel, Priority.ALWAYS);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        messageBox.getColumnConstraints().addAll(col1, col2);

        messageBox.add(dialogPanel, 0, 0);
        messageBox.add(chatPanel, 1, 0);

        chatArea = new VBox();
        chatArea.setPadding(new Insets(10));
        chatArea.setSpacing(5);

        scrollPane = new ScrollPane(chatArea);
        scrollPane.getStyleClass().add("scrollpane");
        scrollPane.setFitToWidth(true);
        chatPanel.setCenter(scrollPane);
        scrollPane.vvalueProperty().bind(chatArea.heightProperty());

        HBox bottomPanel = createBottomPanel();
        chatPanel.setBottom(bottomPanel);

        return messageBox;
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
