package com.example.NefesleChat;

import com.example.NefesleChat.entity.UserDetailsDTO;
import com.example.NefesleChat.entity.UserInListDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UsersView {
    private MainView mainView;
    private VBox usersArea;
    private javafx.scene.control.TextField searchUsersInput;

    public UsersView(MainView mainView) {
        this.mainView = mainView;
        initialize();
    }

    public void initialize() {
        usersArea = mainView.getUsersArea();
        searchUsersInput = mainView.getSearchUsersInput();
    }

    public javafx.scene.control.TextField getSearchUsersInput() {
        return searchUsersInput;
    }

    public MainView getMainView() {
        return mainView;
    }

    public void showUsers(String searchUsers){
        usersArea.getChildren().clear();
        VBox usersContainer = createUsersContainer(searchUsers);
        usersContainer.setAlignment(Pos.CENTER);
        usersArea.getChildren().add(usersContainer);
    }

    private VBox createUsersContainer(String searchUsers){
        VBox usersContainer = new VBox();

        try {
            List<UserInListDTO> result = HttpUtil.getListUsers(searchUsers);

            for (UserInListDTO user : result) {
                String info = "ID: " + user.getId() + ", Name: " + user.getName() + ", Role: " + user.getRole();
                Label userContainer = new Label(info);
                userContainer.setOnMouseClicked(e -> System.out.println(userContainer));
                userContainer.getStyleClass().add("message-container");
                usersContainer.getChildren().add(userContainer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersContainer;
    }
}
