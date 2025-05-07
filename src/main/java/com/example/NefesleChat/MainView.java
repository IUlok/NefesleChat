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
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class MainView {
    private Stage primaryStage;
    private VBox chatArea;
    private VBox usersArea;
    private TextField messageInput;
    private TextField searchInput;
    private BorderPane root;
    private ChatView chatView;
    private UsersView usersView;
    private ScrollPane scrollPane;
    private ComboBox<String> userComboBox; // Объявляем ComboBox как поле класса
    private GridPane workingBox = new GridPane();
    private Label chatButton;
    private Label usersButton;
    private Label timelineButton;
    private Label notesButton;
    private Label tasksButton;

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

        root.setLeft(menuPanel);
        root.setTop(topPanel);

        showChatBox();

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

        chatButton = new Label();
        chatButton.setMinSize(38, 38);
        chatButton.setOnMouseEntered(event -> chatButton.setCursor(Cursor.HAND));
        chatButton.setOnMouseExited(event -> chatButton.setCursor(Cursor.DEFAULT));
        chatButton.getStyleClass().add("chat-button");


        usersButton = new Label();
        usersButton.setMinSize(38, 38);
        usersButton.setOnMouseEntered(event -> usersButton.setCursor(Cursor.HAND));
        usersButton.setOnMouseExited(event -> usersButton.setCursor(Cursor.DEFAULT));
        usersButton.getStyleClass().add("users-button");


        timelineButton = new Label();
        timelineButton.setMinSize(38, 38);
        timelineButton.setOnMouseEntered(event -> timelineButton.setCursor(Cursor.HAND));
        timelineButton.setOnMouseExited(event -> timelineButton.setCursor(Cursor.DEFAULT));
        timelineButton.getStyleClass().add("timeline-button");


        notesButton = new Label();
        notesButton.setMinSize(38, 38);
        notesButton.setOnMouseEntered(event -> notesButton.setCursor(Cursor.HAND));
        notesButton.setOnMouseExited(event -> notesButton.setCursor(Cursor.DEFAULT));
        notesButton.getStyleClass().add("notes-button");


        tasksButton = new Label();
        tasksButton.setMinSize(38, 38);
        tasksButton.setOnMouseEntered(event -> tasksButton.setCursor(Cursor.HAND));
        tasksButton.setOnMouseExited(event -> tasksButton.setCursor(Cursor.DEFAULT));
        tasksButton.getStyleClass().add("tasks-button");


        Label settingsButton = new Label();
        settingsButton.setMinSize(38, 38);
        settingsButton.setOnMouseEntered(event -> settingsButton.setCursor(Cursor.HAND));
        settingsButton.setOnMouseExited(event -> settingsButton.setCursor(Cursor.DEFAULT));
        settingsButton.getStyleClass().add("settings-button");


        Label logoutButton = new Label();
        logoutButton.setMinSize(38, 38);
        logoutButton.setOnMouseEntered(event -> logoutButton.setCursor(Cursor.HAND));
        logoutButton.setOnMouseExited(event -> logoutButton.setCursor(Cursor.DEFAULT));
        logoutButton.getStyleClass().add("exit-button");


        MainController mainController = new MainController(this);
        chatButton.setOnMouseClicked(mainController::openChat);
        timelineButton.setOnMouseClicked(mainController::openTimeline);
        notesButton.setOnMouseClicked(mainController::openNotes);
        tasksButton.setOnMouseClicked(mainController::openTasks);
        usersButton.setOnMouseClicked(mainController::openUsers);

        settingsButton.setOnMouseClicked(mainController::openSettings);
        logoutButton.setOnMouseClicked(mainController::openLogout);

        topMenuPanel.getChildren().addAll(chatButton, usersButton, timelineButton, notesButton, tasksButton);
        bottomMenuPanel.getChildren().addAll(settingsButton, logoutButton);

        menuPanel.setTop(topMenuPanel);
        menuPanel.setBottom(bottomMenuPanel);
        return menuPanel;
    }

    private HBox createHeader() {
        HBox topPanel = new HBox(10);
        topPanel.setPadding(new Insets(5));
        topPanel.getStyleClass().add("top-panel");
        Label logo = new Label();
        logo.setMinSize(40, 40);
        logo.getStyleClass().add("logo");
        //MainController mainController = new MainController(this);

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

    public void createMessageBox() {
        workingBox.getChildren().clear();
        workingBox = new GridPane(2, 0);

        workingBox.setVgap(10);
        workingBox.setHgap(10);

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
        workingBox.getColumnConstraints().addAll(col1, col2);

        workingBox.add(dialogPanel, 0, 0);
        workingBox.add(chatPanel, 1, 0);

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
        root.setCenter(workingBox);
    }

    public void showChatBox() {
        createMessageBox();
        selectedChatButton();
    }

    public void showUsersBox() {
        createUsersBox();
        selectedUsersButton();
    }

    public void createUsersBox() {
        workingBox.getChildren().clear();
        workingBox = new GridPane(0, 2);

        workingBox.setVgap(10);
        workingBox.setHgap(10);
        workingBox.setAlignment(Pos.CENTER);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(90);
        workingBox.getRowConstraints().addAll(row1, row2);

        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));

        searchInput = new TextField();
        searchInput.setPrefWidth(300);
        searchInput.setPromptText("Введите фамилию пользователя");
        HBox.setHgrow(searchInput, Priority.ALWAYS);

        Button searchButton = new Button("Поиск");
        searchButton.setOnMouseEntered(event -> searchButton.setCursor(Cursor.HAND));
        searchButton.setOnMouseExited(event -> searchButton.setCursor(Cursor.DEFAULT));

        usersArea = new VBox();
        usersArea.setPadding(new Insets(10));
        usersArea.setSpacing(5);

        scrollPane = new ScrollPane(usersArea);
        scrollPane.getStyleClass().add("scrollpane");
        scrollPane.setFitToWidth(true);
        scrollPane.vvalueProperty().bind(usersArea.heightProperty());

        usersView = new UsersView( this);
        UsersController usersController = new UsersController(usersView);

        searchInput.setOnKeyPressed(usersController::handleEnterKey);
        searchButton.setOnAction(usersController::searchUsersAction);
        searchBox.getChildren().addAll(searchInput, searchButton);

        workingBox.add(searchBox, 0, 0);
        workingBox.add(scrollPane, 0, 1);

        root.setCenter(workingBox);
    }

    public void createTimelineBox() {

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

    public void selectedChatButton() {
        chatButton.getStyleClass().removeAll("chat-button", "chat-button1");
        usersButton.getStyleClass().removeAll("users-button", "users-button1");
        timelineButton.getStyleClass().removeAll("timeline-button", "timeline-button1");
        notesButton.getStyleClass().removeAll("notes-button", "notes-button1");
        tasksButton.getStyleClass().removeAll("tasks-button", "tasks-button1");

        chatButton.getStyleClass().add("chat-button1");
        usersButton.getStyleClass().add("users-button");
        timelineButton.getStyleClass().add("timeline-button");
        notesButton.getStyleClass().add("notes-button");
        tasksButton.getStyleClass().add("tasks-button");
    }

    public void selectedUsersButton() {
        chatButton.getStyleClass().removeAll("chat-button", "chat-button1");
        usersButton.getStyleClass().removeAll("users-button", "users-button1");
        timelineButton.getStyleClass().removeAll("timeline-button", "timeline-button1");
        notesButton.getStyleClass().removeAll("notes-button", "notes-button1");
        tasksButton.getStyleClass().removeAll("tasks-button", "tasks-button1");

        chatButton.getStyleClass().add("chat-button");
        usersButton.getStyleClass().add("users-button1");
        timelineButton.getStyleClass().add("timeline-button");
        notesButton.getStyleClass().add("notes-button");
        tasksButton.getStyleClass().add("tasks-button");
    }

    public void selectedTimelineButton() {
        chatButton.getStyleClass().removeAll("chat-button", "chat-button1");
        usersButton.getStyleClass().removeAll("users-button", "users-button1");
        timelineButton.getStyleClass().removeAll("timeline-button", "timeline-button1");
        notesButton.getStyleClass().removeAll("notes-button", "notes-button1");
        tasksButton.getStyleClass().removeAll("tasks-button", "tasks-button1");

        chatButton.getStyleClass().add("chat-button");
        usersButton.getStyleClass().add("users-button");
        timelineButton.getStyleClass().add("timeline-button1");
        notesButton.getStyleClass().add("notes-button");
        tasksButton.getStyleClass().add("tasks-button");
    }

    public void selectedNotesButton() {
        chatButton.getStyleClass().removeAll("chat-button", "chat-button1");
        usersButton.getStyleClass().removeAll("users-button", "users-button1");
        timelineButton.getStyleClass().removeAll("timeline-button", "timeline-button1");
        notesButton.getStyleClass().removeAll("notes-button", "notes-button1");
        tasksButton.getStyleClass().removeAll("tasks-button", "tasks-button1");

        chatButton.getStyleClass().add("chat-button");
        usersButton.getStyleClass().add("users-button");
        timelineButton.getStyleClass().add("timeline-button");
        notesButton.getStyleClass().add("notes-button1");
        tasksButton.getStyleClass().add("tasks-button");
    }

    public void selectedTasksButton() {
        chatButton.getStyleClass().removeAll("chat-button", "chat-button1");
        usersButton.getStyleClass().removeAll("users-button", "users-button1");
        timelineButton.getStyleClass().removeAll("timeline-button", "timeline-button1");
        notesButton.getStyleClass().removeAll("notes-button", "notes-button1");
        tasksButton.getStyleClass().removeAll("tasks-button", "tasks-button1");

        chatButton.getStyleClass().add("chat-button");
        usersButton.getStyleClass().add("users-button");
        timelineButton.getStyleClass().add("timeline-button");
        notesButton.getStyleClass().add("notes-button");
        tasksButton.getStyleClass().add("tasks-button1");
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public VBox getChatArea() {
        return chatArea;
    }

    public VBox getUsersArea() {
        return usersArea;
    }

    public TextField getMessageInput() {
        return messageInput;
    }

    public TextField getSearchUsersInput() {
        return searchInput;
    }

    public BorderPane getRoot() {
        return root;
    }

    public String getSelectedUser() {
        return userComboBox.getValue();
    }
}
