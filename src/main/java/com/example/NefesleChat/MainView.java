package com.example.NefesleChat;

// MainView.java
import com.example.NefesleChat.entity.ChatDTO;
import com.example.NefesleChat.entity.ChatTypeEnum;
import com.example.NefesleChat.entity.RoleEnum;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
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
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private ScrollPane scrollMessages;
    private ScrollPane scrollChats;
    private ScrollPane scrollUsers;
    private ComboBox<String> userComboBox; // Объявляем ComboBox как поле класса
    private GridPane workingBox = new GridPane();
    private Label chatButton;
    private Label usersButton;
    private Label timelineButton;
    private Label notesButton;
    private Label tasksButton;
    private Label currentListLabel;
    private HBox currentChatBox;
    private List<VBox> chatList = new ArrayList<>();
    private Label chatNameForHeader;
    private Label chatIconForHeader;
    private int numberList;
    private int myID;

    public MainView() {
        this.primaryStage = new Stage();
        userComboBox = new ComboBox<>();  // Создаем ComboBox в конструкторе
        updateUserComboBox();           // Заполняем ComboBox данными в конструкторе
        try {
            myID = HttpUtil.getMyID();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            myID = 0;
            e.printStackTrace();
        }
    }

    public void show() {
        primaryStage.setTitle("NefesleChat");
        primaryStage.getIcons().add(new Image("file:logo.png"));
        root = new BorderPane();
        root.getStyleClass().add("authRegForm");

        BorderPane menuPanel = createMenu();
        BorderPane topPanel = createHeader();

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
        logoutButton.setOnMouseClicked(event -> mainController.openLogout(0));

        topMenuPanel.getChildren().addAll(chatButton, usersButton, timelineButton, notesButton, tasksButton);
        bottomMenuPanel.getChildren().addAll(settingsButton, logoutButton);

        menuPanel.setTop(topMenuPanel);
        menuPanel.setBottom(bottomMenuPanel);
        return menuPanel;
    }

    private BorderPane createHeader() {
        BorderPane topBox = new BorderPane();
        HBox topPanel = new HBox();
        topPanel.setPadding(new Insets(5));
        topBox.getStyleClass().add("top-panel");
        Label logo = new Label();
        logo.setMinSize(40, 40);
        logo.getStyleClass().add("logo");
        currentListLabel = new Label("Сообщения");
        currentListLabel.setAlignment(Pos.CENTER);
        currentListLabel.setPrefHeight(40);
        currentListLabel.setPadding(new Insets(10));
        currentListLabel.getStyleClass().add("currentListLabel");
        topPanel.getChildren().addAll(logo, currentListLabel, userComboBox);

        currentChatBox = new HBox(2);
        currentChatBox.setPrefHeight(40);
        currentChatBox.setAlignment(Pos.CENTER_RIGHT);

        HBox topSecondPanel = new HBox();
        topSecondPanel.setAlignment(Pos.CENTER_RIGHT);
        topSecondPanel.setPadding(new Insets(0,20,0,0));
        topSecondPanel.getChildren().addAll(currentChatBox);
        topPanel.setSpacing(0);
        topSecondPanel.setSpacing(0);

        topBox.setLeft(topPanel);
        topBox.setRight(topSecondPanel);
        topBox.setMaxHeight(40);

        return topBox;
    }

    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox();
        bottomPanel.setSpacing(15);
        bottomPanel.setPadding(new Insets(0,10,0,10));
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.getStyleClass().add("chatSendBox");

        messageInput = new TextField();
        messageInput.getStyleClass().add("chatSendInput");
        messageInput.setMaxSize(350,50);
        messageInput.setMinSize(500,50);
        messageInput.setPromptText("Введите сообщение");

        Button sendButton = new Button("");
        sendButton.getStyleClass().add("chatSendButton");
        sendButton.setMinSize(38,38);
        sendButton.setOnMouseEntered(event -> sendButton.setCursor(Cursor.HAND));
        sendButton.setOnMouseExited(event -> sendButton.setCursor(Cursor.DEFAULT));

        Button fileButton = new Button("");
        fileButton.getStyleClass().add("fileButton");
        fileButton.setMinSize(38,38);
        fileButton.setOnMouseEntered(event -> fileButton.setCursor(Cursor.HAND));
        fileButton.setOnMouseExited(event -> fileButton.setCursor(Cursor.DEFAULT));

        chatView = new ChatView( this);
        ChatController chatController = new ChatController(chatView);

        messageInput.setOnKeyPressed(chatController::handleEnterKey);
        sendButton.setOnAction(chatController::sendMessage);
        bottomPanel.getChildren().addAll(messageInput, fileButton, sendButton);

        HBox footerPane = new HBox();
        footerPane.setPadding(new Insets(20,0,20,0));
        footerPane.setAlignment(Pos.CENTER);
        footerPane.getChildren().add(bottomPanel);

        return footerPane;
    }

    public void createMessageBox() {
        chatList.clear();
        workingBox.getChildren().clear();
        workingBox = new GridPane(2, 0);

        BorderPane chatPanel = new BorderPane();
        chatPanel.setVisible(false);

        BorderPane chatPanelNull = new BorderPane();
        Label chatPanelNullLabel = new Label("Выберите чат");
        chatPanelNullLabel.getStyleClass().add("chatPanelNullLabel");

        Label chatImageStart = new Label();
        chatImageStart.getStyleClass().add("chatImageStart");
        chatImageStart.setPrefSize(216,180);

        VBox startText = new VBox(2);
        startText.setAlignment(Pos.CENTER);
        startText.getChildren().addAll(chatImageStart, chatPanelNullLabel);

        chatPanelNull.setCenter(startText);

        VBox dialogPanel = new VBox();
        dialogPanel.setStyle("-fx-background-color: #c4c4c4");
        dialogPanel.setPadding(new Insets(3));
        dialogPanel.setSpacing(10);

        scrollChats = new ScrollPane(dialogPanel);
        scrollChats.setFitToWidth(true);
        scrollChats.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollChats.getStyleClass().add("scrollchat");

        GridPane.setHgrow(chatPanel, Priority.ALWAYS);
        GridPane.setVgrow(chatPanel, Priority.ALWAYS);
        GridPane.setHgrow(chatPanelNull, Priority.ALWAYS);
        GridPane.setVgrow(chatPanelNull, Priority.ALWAYS);
        GridPane.setHgrow(scrollChats, Priority.ALWAYS);
        GridPane.setVgrow(scrollChats, Priority.ALWAYS);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(75);
        workingBox.getColumnConstraints().addAll(col1, col2);
        List<ChatDTO> result;
        try {
            result = HttpUtil.getListChats();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            result = Collections.emptyList();
            e.printStackTrace();
        }

        for (ChatDTO chat:result) {
            VBox chatBox = new VBox(2);
            chatBox.setPadding(new Insets(10));
            chatBox.setSpacing(5);
            chatBox.getStyleClass().add("chat");
            chatBox.setPrefHeight(80);
            chatList.add(chatBox);
            HBox titleChat = new HBox(2);
            titleChat.setPadding(new Insets(0, 0, 0, 2));
            Label chatIcon = new Label();
            chatIcon.setMinSize(30,30);
            chatIcon.setAlignment(Pos.CENTER);
            int isTypeChat;

            if (chat.getUserType() == RoleEnum.PROFESSOR) {
                chatIcon.getStyleClass().add("chatIconTeacher");
                isTypeChat = 0;
            } else if (chat.getUserType() == RoleEnum.STUDENT) {
                chatIcon.getStyleClass().add("chatIconUser");
                isTypeChat = 1;
            } else {
                chatIcon.getStyleClass().add("chatIconGroup");
                isTypeChat = 2;
            }

            String name = chat.getName();
            Label chatName = new Label(name);
            chatName.getStyleClass().add("chatName");
            chatName.setPadding(new Insets(5,0,0,0));

            titleChat.getChildren().addAll(chatIcon, chatName);
            String lastMessage;

            if (chat.getLastMessage() == null) {
                lastMessage = "";
            } else {
                lastMessage = chat.getMessageFrom() + " " + chat.getLastMessage().getText();
            }
            Label chatLastText = new Label(lastMessage);
            chatLastText.setAlignment(Pos.CENTER);
            chatLastText.getStyleClass().add("chatLastText");

            chatBox.getChildren().addAll(titleChat, chatLastText);
            chatBox.setOnMouseEntered(event -> chatBox.setCursor(Cursor.HAND));
            chatBox.setOnMouseExited(event -> chatBox.setCursor(Cursor.DEFAULT));

            chatBox.setOnMouseClicked(mouseEvent -> {
                for (VBox c : chatList) {
                    c.getStyleClass().removeAll("chatSelected");
                }
                chatBox.getStyleClass().add("chatSelected");
                chatPanel.setVisible(true);
                chatPanelNull.setVisible(false);
                numberList = 0;
                chatArea.getChildren().clear();
                loadChat(chat.getId(), numberList);
                selectedChatHeader(name, isTypeChat);
            });

            dialogPanel.getChildren().add(chatBox);
        }

        workingBox.add(scrollChats, 0, 0);
        workingBox.add(chatPanel, 1, 0);
        workingBox.add(chatPanelNull, 1, 0);

        chatArea = new VBox();
        chatArea.setPadding(new Insets(5));
        chatArea.setSpacing(5);

        scrollMessages = new ScrollPane(chatArea);
        scrollMessages.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMessages.getStyleClass().add("scrollpane");
        scrollMessages.setFitToWidth(true);
        chatPanel.setCenter(scrollMessages);

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
        searchBox.setMaxSize(600,50);
        searchBox.setPadding(new Insets(0,10,0,10));
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getStyleClass().add("usersSearchBox");

        searchInput = new TextField();
        searchInput.getStyleClass().add("usersSearchInput");
        searchInput.setMaxSize(350,50);
        searchInput.setMinSize(500,50);
        searchInput.setPromptText("Введите фамилию пользователя");

        Button searchButton = new Button();
        searchButton.getStyleClass().add("usersSearchButton");
        searchButton.setMinSize(30,30);
        searchButton.setOnMouseEntered(event -> searchButton.setCursor(Cursor.HAND));
        searchButton.setOnMouseExited(event -> searchButton.setCursor(Cursor.DEFAULT));

        usersArea = new VBox();
        usersArea.setPadding(new Insets(10));
        usersArea.getStyleClass().add("authRegForm");
        usersArea.setSpacing(5);

        scrollUsers = new ScrollPane(usersArea);
        scrollUsers.getStyleClass().add("scrollpane");
        scrollUsers.setFitToWidth(true);
        scrollUsers.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollUsers.setMinWidth(1070);

        usersView = new UsersView( this);
        UsersController usersController = new UsersController(usersView);

        searchInput.setOnKeyPressed(usersController::handleEnterKey);
        searchButton.setOnAction(usersController::searchUsersAction);
        searchBox.getChildren().addAll(searchInput, searchButton);

        HBox headerPane = new HBox(1);
        headerPane.setPadding(new Insets(20,0,0,0));
        headerPane.setAlignment(Pos.CENTER);
        headerPane.setMinWidth(1070);
        headerPane.getChildren().add(searchBox);

        workingBox.add(headerPane, 0, 0);
        workingBox.add(scrollUsers, 0, 1);

        root.setCenter(workingBox);
    }

    public void createTimelineBox() {

    }

    // Method to update the ComboBox items
    private void updateUserComboBox() {
        List<String> users = new ArrayList<String>();
        users.add("Федоров Даниил Юрьевич");
        users.add("Проурзин Олег Владимирович");
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

    public void selectedUserBox(int id) {
        setEffects();

        UserBox userBoxView = new UserBox();
        userBoxView.showUserBox(this, id);
    }

    public int getMyID() {
        return myID;
    }

    private void selectedChatHeader(String name, int isTypeChat) {
        currentChatBox.getChildren().clear();
        chatIconForHeader = new Label();
        chatIconForHeader.setMinSize(30,30);
        chatIconForHeader.setAlignment(Pos.CENTER);

        if (isTypeChat == 0) {
            chatIconForHeader.getStyleClass().add("chatIconTeacher");
        } else if (isTypeChat == 1) {
            chatIconForHeader.getStyleClass().add("chatIconUser");
        } else {
            chatIconForHeader.getStyleClass().add("chatIconGroup");
        }

        chatNameForHeader = new Label(name);
        chatNameForHeader.getStyleClass().add("chatName");

        currentChatBox.getChildren().addAll(chatIconForHeader, chatNameForHeader);
        currentChatBox.setOnMouseEntered(event -> currentChatBox.setCursor(Cursor.HAND));
        currentChatBox.setOnMouseExited(event -> currentChatBox.setCursor(Cursor.DEFAULT));

    }

    public void setVisibleFalseChat() {
        currentChatBox.getChildren().clear();
        currentChatBox.setVisible(false);
        userComboBox.setVisible(false);
    }

    public void setVisibleTrueChat() {
        currentChatBox.getChildren().clear();
        currentChatBox.setVisible(true);
        userComboBox.setVisible(true);
    }

    public void setEffects() {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.1);
        colorAdjust.setContrast(-0.9);
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);
        blend.setTopInput(colorAdjust);
        blend.setBottomInput(blur);
        primaryStage.getScene().getRoot().setEffect(blend);
    }

    public void loadChat(int chatID, int listChat) {
        ChatController chatController = new ChatController(chatView);
        chatController.loadChat(chatID, listChat);
    }

    public void setCurrentListLabel(String text) {
        currentListLabel.setText(text);
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
