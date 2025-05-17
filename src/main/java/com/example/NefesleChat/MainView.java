package com.example.NefesleChat;
 
import com.example.NefesleChat.entity.ChatDTO;
import com.example.NefesleChat.entity.ChatTypeEnum;
import com.example.NefesleChat.entity.RoleEnum;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainView {
    @Getter
    private Stage primaryStage;
    @Getter
    private VBox chatArea;
    @Getter
    private VBox usersArea;
    @Getter
    private TextField messageInput;
    private TextField searchInput;
    private BorderPane root;
    private ChatView chatView;
    private UsersView usersView;
    private ScrollPane scrollMessages;
    private ScrollPane scrollChats;
    private ScrollPane scrollUsers;
    private ScrollPane scrollNotes;
    private ScrollPane scrollTasks;
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
    @Getter
    private int myID;
    @Getter
    private int focusChat;
    private boolean isFocusChat;
    @Setter
    @Getter
    private int focusUser;
    private VBox loadNextList;
    private Button loadNextListButton;
    private BorderPane chatPanel;
    private BorderPane chatPanelNull;

    public MainView() {
        this.primaryStage = new Stage();
//        try {
//            myID = Main.getHttpUtil().getMyID();
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            myID = 0;
//            e.printStackTrace();
//        }
        //Main.getWebSocketUtil().connect(Main.getHttpUtil().getJwtToken());
        //Main.getWebSocketUtil().subscribeToYourself(myID);
        //Main.getWebSocketUtil().setMainView(this);
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
        primaryStage.setOnCloseRequest(windowEvent -> {
            //Main.getWebSocketUtil().disconnect();
        });
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
        topPanel.getChildren().addAll(logo, currentListLabel);

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

        chatPanel = new BorderPane();
        chatPanel.setVisible(false);

        chatPanelNull = new BorderPane();
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

        loadNextList = new VBox();
        loadNextList.setPadding(new Insets(10));
        loadNextList.setAlignment(Pos.CENTER);

        loadNextListButton = new Button("Загрузить ещё");
        loadNextListButton.getStyleClass().add("loadNextListButton");
        loadNextListButton.setOnMouseEntered(event -> loadNextListButton.setCursor(Cursor.HAND));
        loadNextListButton.setOnMouseExited(event -> loadNextListButton.setCursor(Cursor.DEFAULT));
        loadNextListButton.setOnMouseClicked(event -> {
            numberList+=1;
            loadChat(focusChat, numberList);
        });
        loadNextList.getChildren().add(loadNextListButton);

        List<ChatDTO> result = new ArrayList<>();
//        try {
//            result = Main.getHttpUtil().getListChats();
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            result = Collections.emptyList();
//            e.printStackTrace();
//        }

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
            titleChat.setMaxWidth(200);
            String lastMessage;

            if (chat.getLastMessage() == null) {
                lastMessage = "";
            } else {
                lastMessage = chat.getMessageFrom() + " " + chat.getLastMessage().getText();
            }
            Label chatLastText = new Label(lastMessage);
            chatLastText.setAlignment(Pos.CENTER);
            chatLastText.getStyleClass().add("chatLastText");
            chatLastText.setMaxWidth(250);

            Date createdDate = chat.getLastMessage().getCreatedAt();
            SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String formattedTime = timeFormat.format(createdDate);
            Label timeSend = new Label(formattedTime);
            timeSend.getStyleClass().add("message-time");

            Label counterOfOnViewedMessages = new Label(String.valueOf(chat.getNotRead()));
            counterOfOnViewedMessages.setAlignment(Pos.CENTER);
            counterOfOnViewedMessages.getStyleClass().add("counterOfOnViewedMessages");
            counterOfOnViewedMessages.setPrefSize(20,20);

            HBox infoBox = new HBox();
            infoBox.setAlignment(Pos.CENTER_RIGHT);
            infoBox.setSpacing(5);
            infoBox.getChildren().addAll(timeSend);

            BorderPane headerOfChat = new BorderPane();
            headerOfChat.setLeft(titleChat);
            headerOfChat.setRight(infoBox);

            BorderPane bodyOfChat = new BorderPane();
            bodyOfChat.setLeft(chatLastText);
            bodyOfChat.setRight(counterOfOnViewedMessages);
            if (chat.getNotRead() == 0) {
                counterOfOnViewedMessages.setVisible(false);
            } else {
                counterOfOnViewedMessages.setVisible(true);
            }

            chatBox.getChildren().addAll(headerOfChat, bodyOfChat);
            chatBox.setOnMouseEntered(event -> chatBox.setCursor(Cursor.HAND));
            chatBox.setOnMouseExited(event -> chatBox.setCursor(Cursor.DEFAULT));

            //if(chat.getType() != ChatTypeEnum.SINGLE) Main.getWebSocketUtil().subscribeToChat(chat.getId());

            chatBox.setOnMouseClicked(mouseEvent -> {
                for (VBox c : chatList) {
                    c.getStyleClass().removeAll("chatSelected");
                }
                chatBox.getStyleClass().add("chatSelected");
                chatPanel.setVisible(true);
                chatPanelNull.setVisible(false);
                numberList = 0;
                chatArea.getChildren().clear();
                focusChat = chat.getId();
                focusUser = chat.getUserId();
                loadChat(focusChat, numberList);
                if (chat.getType() == ChatTypeEnum.SINGLE) setFocusChat(false);
                else setFocusChat(true);
                selectedChatHeader(name, isTypeChat, focusUser, chat.getType().toString());
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

    public void showTimelineBox() {
        workingBox.getChildren().clear();
        Label text = new Label("расписание тут будет");
        workingBox.getChildren().add(text);
    }

    public void showNotesBox() {
        workingBox.getChildren().clear();

        workingBox = new GridPane();

        workingBox.setVgap(10);
        workingBox.setHgap(10);
        workingBox.setAlignment(Pos.CENTER);

        VBox notesArea = new VBox();
        notesArea.setAlignment(Pos.CENTER);
        notesArea.setPadding(new Insets(10));
        notesArea.getStyleClass().add("authRegForm");
        notesArea.setSpacing(25);

        scrollNotes = new ScrollPane(notesArea);
        scrollNotes.getStyleClass().add("scrollpane");
        scrollNotes.setFitToWidth(true);
        scrollNotes.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for(int i = 0; i < 5; i++) {
            HBox rawNotes = new HBox();
            rawNotes.setSpacing(60);
            for (int j = 0; j < 3; j++) {
                BorderPane noteBox = new BorderPane();
                BorderPane noteBox1 = new BorderPane();

                noteBox.setMinSize(250,300);
                noteBox.setMaxSize(250,300);
                noteBox.setPadding(new Insets(15));
                noteBox1.setMinSize(250,300);
                noteBox1.setMaxSize(250,300);
                noteBox1.setPadding(new Insets(15));

                Label noteText = new Label("gtjhrghjtrbhgjthtrhtrhtrlhnjtjrklhntjrklhntrjklhntjrkhlrtjklhhtjrkhntrjklhtnrjkhtkrbhgjtkbr");
                Label noteText1 = new Label("gtjhrghjtrbhgjthtrhtrhtrlhnjtjrklhntjrklhntrjklhntjrkhlrtjklhhtjrkhntrjklhtnrjkhtkrbhgjtkbr");

                noteText.setWrapText(true);
                noteText1.setWrapText(true);

                Label timeLabel = new Label("12:03:2025 13:09");
                Label timeLabel1 = new Label("12:03:2025 13:09");

                timeLabel.setAlignment(Pos.CENTER_RIGHT);
                timeLabel1.setAlignment(Pos.CENTER_RIGHT);
                noteBox.getStyleClass().add("noteBox");
                noteText.getStyleClass().add("noteText");
                timeLabel.getStyleClass().add("noteTime");
                noteBox1.getStyleClass().add("noteBox1");
                noteText1.getStyleClass().add("noteText");
                timeLabel1.getStyleClass().add("noteTime");
                HBox timeBox = new HBox();
                HBox timeBox1 = new HBox();
                timeBox.setAlignment(Pos.CENTER_RIGHT);
                timeBox.getChildren().add(timeLabel);
                timeBox1.setAlignment(Pos.CENTER_RIGHT);
                timeBox1.getChildren().add(timeLabel1);

                noteBox.setCenter(noteText);
                noteBox.setBottom(timeBox);
                noteBox.setOnMouseEntered(event -> noteBox.setCursor(Cursor.HAND));
                noteBox.setOnMouseExited(event -> noteBox.setCursor(Cursor.DEFAULT));

                noteBox.setOnMouseClicked(event -> {
                    noteBox1.getChildren().clear();
                    noteBox1.setCenter(noteText1);
                    noteBox1.setBottom(timeBox1);
                    setEffects();
                    Stage noteStage = new Stage();
                    noteStage.initStyle(StageStyle.TRANSPARENT);
                    noteStage.setResizable(false);
                    noteStage.setTitle("");
                    noteStage.initModality(Modality.APPLICATION_MODAL);
                    noteStage.initOwner(getPrimaryStage());
                    GridPane notePane = new GridPane();
                    notePane.setAlignment(Pos.CENTER);
                    notePane.getStyleClass().add("delete-panel");

                    Button editButton = new Button("Редактировать");
                    editButton.setOnMouseEntered(e -> editButton.setCursor(Cursor.HAND));
                    editButton.setOnMouseExited(e -> editButton.setCursor(Cursor.DEFAULT));
                    editButton.getStyleClass().add("exitButtonNo");
                    Button deleteButton = new Button("Удалить");
                    deleteButton.setOnMouseEntered(e -> deleteButton.setCursor(Cursor.HAND));
                    deleteButton.setOnMouseExited(e -> deleteButton.setCursor(Cursor.DEFAULT));
                    deleteButton.getStyleClass().add("exitButtonYes");
                    Button backButton = new Button("Назад");
                    backButton.setOnMouseEntered(e -> backButton.setCursor(Cursor.HAND));
                    backButton.setOnMouseExited(e -> backButton.setCursor(Cursor.DEFAULT));
                    backButton.getStyleClass().add("exitButtonNo");
                    VBox buttons = new VBox(3);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.setPadding(new Insets(20,0,0,0));
                    buttons.getChildren().addAll(editButton, deleteButton, backButton);

                    notePane.add(noteBox1, 0, 0);
                    notePane.add(buttons, 0, 1);

                    editButton.setOnMouseClicked(e -> {
                        buttons.setVisible(false);
                        TextArea editedText = new TextArea();
                        editedText.setWrapText(true);
                        editedText.getStyleClass().add("noteTextArea");
                        editedText.setText(noteText1.getText());

                        HBox buttonsEdit = new HBox();
                        buttonsEdit.setSpacing(5);
                        buttonsEdit.setAlignment(Pos.CENTER);
                        Button saveNoteButton = new Button("Сохранить");
                        saveNoteButton.getStyleClass().add("exitButtonNo");
                        Button backNoteButton = new Button("Отмена");
                        backNoteButton.getStyleClass().add("exitButtonNo");
                        buttonsEdit.getChildren().addAll(saveNoteButton, backNoteButton);

                        noteBox1.getChildren().clear();
                        noteBox1.setCenter(editedText);
                        noteBox1.setBottom(buttonsEdit);

                        saveNoteButton.setOnMouseClicked(event1 -> {
                            noteText.setText(editedText.getText());
                            noteText1.setText(editedText.getText());
                            getPrimaryStage().getScene().getRoot().setEffect(null);
                            noteStage.close();
                        });

                        backNoteButton.setOnMouseClicked(event1 -> {
                            noteBox1.getChildren().clear();
                            noteBox1.setCenter(noteText1);
                            noteBox1.setBottom(timeBox1);
                            buttons.setVisible(true);
                        });
                    });

                    deleteButton.setOnMouseClicked(e -> {
                        // лоигка удаления
                        noteBox.getChildren().clear();

                        getPrimaryStage().getScene().getRoot().setEffect(null);
                        noteStage.close();
                    });

                    backButton.setOnMouseClicked(e -> {
                        getPrimaryStage().getScene().getRoot().setEffect(null);
                        noteStage.close();
                    });

                    Scene scene = new Scene(notePane, 700, 500);
                    scene.setFill(Color.TRANSPARENT);
                    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    noteStage.setScene(scene);
                    noteStage.showAndWait();
                });

                rawNotes.getChildren().add(noteBox);
            }
            notesArea.getChildren().add(rawNotes);
        }


        BorderPane newNotePane = new BorderPane();
        newNotePane.setPadding(new Insets(0,0,10,10));
        Label newNote = new Label("+");
        newNote.setOnMouseEntered(e -> newNote.setCursor(Cursor.HAND));
        newNote.setOnMouseExited(e -> newNote.setCursor(Cursor.DEFAULT));
        newNote.setAlignment(Pos.CENTER);
        newNote.setMinWidth(70);
        newNote.getStyleClass().add("newNoteButton");
        newNotePane.setBottom(newNote);

        newNote.setOnMouseClicked(e -> {
            setEffects();
            Stage noteStage = new Stage();
            noteStage.initStyle(StageStyle.TRANSPARENT);
            noteStage.setResizable(false);
            noteStage.setTitle("");
            noteStage.initModality(Modality.APPLICATION_MODAL);
            noteStage.initOwner(getPrimaryStage());
            GridPane createNotePane = new GridPane();
            createNotePane.setAlignment(Pos.CENTER);
            createNotePane.getStyleClass().add("delete-panel");

            Button createButton = new Button("Создать");
            createButton.setOnMouseEntered(e1 -> createButton.setCursor(Cursor.HAND));
            createButton.setOnMouseExited(e1 -> createButton.setCursor(Cursor.DEFAULT));
            createButton.getStyleClass().add("exitButtonNo");

            Button backButton = new Button("Назад");
            backButton.setOnMouseEntered(e1 -> backButton.setCursor(Cursor.HAND));
            backButton.setOnMouseExited(e1 -> backButton.setCursor(Cursor.DEFAULT));
            backButton.getStyleClass().add("exitButtonNo");

            VBox buttons = new VBox(2);
            buttons.setAlignment(Pos.CENTER);
            buttons.setPadding(new Insets(20,0,0,0));
            buttons.getChildren().addAll(createButton, backButton);

            BorderPane createBox = new BorderPane();

            createBox.setMinSize(250,300);
            createBox.setMaxSize(250,300);
            createBox.setPadding(new Insets(15));

            createBox.getStyleClass().add("noteBox1");

            TextArea createdText = new TextArea();
            createdText.setWrapText(true);
            createdText.getStyleClass().add("noteTextArea");

            createBox.setCenter(createdText);

            createNotePane.add(createBox, 0, 0);
            createNotePane.add(buttons, 0, 1);

            backButton.setOnMouseClicked(e1 -> {
                getPrimaryStage().getScene().getRoot().setEffect(null);
                noteStage.close();
            });

            createButton.setOnMouseClicked(e1 -> {
                // чисто через создание сообщения
                getPrimaryStage().getScene().getRoot().setEffect(null);
                noteStage.close();
            });

            Scene scene = new Scene(createNotePane, 700, 500);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            noteStage.setScene(scene);
            noteStage.showAndWait();

        });

        workingBox.add(scrollNotes, 0, 0);
        workingBox.add(newNotePane, 1, 0);
        root.setCenter(workingBox);
    }

    public void showTasksBox() {
        workingBox.getChildren().clear();
        Label text = new Label("задачи тут будет");
        workingBox.getChildren().add(text);
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

    private void selectedChatHeader(String name, int isTypeChat, int userID, String typeChat) {
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
        currentChatBox.setOnMouseClicked(e -> {
            if (typeChat == "SINGLE") {selectedUserBox(userID);}
        });

    }

    public void showMessagesDialog(String name) {
        chatPanel.setVisible(true);
        chatPanelNull.setVisible(false);
        numberList = 0;
        chatArea.getChildren().clear();
        setFocusChat(true);
        setVisibleTrueChat();
        selectedChatHeader(name, 1, focusUser, "SINGLE");
    }

    public void setVisibleFalseChat() {
        currentChatBox.getChildren().clear();
        currentChatBox.setVisible(false);
    }

    public void setFocusChat(boolean focus) {
        isFocusChat = focus;
    }

    public boolean getFocusedChat() {
        return isFocusChat;
    }

    public void setVisibleTrueChat() {
        currentChatBox.getChildren().clear();
        currentChatBox.setVisible(true);
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
        chatArea.getChildren().remove(loadNextList);
        ChatController chatController = new ChatController(chatView);
        chatController.loadChat(chatID, listChat);
        chatArea.getChildren().add(0, loadNextList);
    }

    public void setCurrentListLabel(String text) {
        currentListLabel.setText(text);
    }

    public void setScrollMessages(double i) {scrollMessages.setVvalue(i);};

    public TextField getSearchUsersInput() {
        return searchInput;
    }
}
