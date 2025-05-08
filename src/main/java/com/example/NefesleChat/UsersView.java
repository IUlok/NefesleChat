package com.example.NefesleChat;

import com.example.NefesleChat.entity.UserDetailsDTO;
import com.example.NefesleChat.entity.UserInListDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        GridPane usersContainer = createUsersContainer(searchUsers);
        usersContainer.setAlignment(Pos.CENTER);
        usersArea.getChildren().add(usersContainer);
    }

    private GridPane createUsersContainer(String searchUsers){
        GridPane usersContainer = new GridPane();
        usersContainer.setPadding(new Insets(10));
        usersContainer.setVgap(10);
        usersContainer.setHgap(35);

        try {
            List<UserInListDTO> result = HttpUtil.getListUsers(searchUsers);
            int i = 0;
            int j = 0;

            for (UserInListDTO user : result) {
                if (j == 2) { j = 0; i++; }
                String name, role, groupDepartment;
                HBox userContainer = new HBox(2);
                userContainer.setPadding(new Insets(22));
                userContainer.setSpacing(10);
                userContainer.setAlignment(Pos.CENTER_LEFT);
                Label userIcon = new Label();
                userIcon.setMinSize(60,60);
                //userContainer.setAlignment(Pos.CENTER);
                if (user.getRole().equals("Студент")) {
                    name = user.getName();
                    role = user.getRole();
                    groupDepartment = user.getGroup();
                    userIcon.getStyleClass().add("userLogoSettings");
                    userContainer.getStyleClass().add("user-container");
                } else {
                    name = user.getName();
                    role = user.getRole();
                    groupDepartment = user.getDepartment();
                    userIcon.getStyleClass().add("teacherLogoSettings");
                    userContainer.getStyleClass().add("teacher-container");
                }
                Label nameContainer = new Label(name);
                nameContainer.getStyleClass().add("userNameContainer");
                Label roleContainer = new Label(role);
                roleContainer.getStyleClass().add("userRoleContainer");
                Label groupDepartmentContainer = new Label( groupDepartment);
                groupDepartmentContainer.getStyleClass().add("userRoleContainer");

                VBox userText = new VBox(2);
                userText.getChildren().addAll(nameContainer, roleContainer, groupDepartmentContainer);

                userContainer.getChildren().addAll(userIcon, userText);
                userContainer.setMinSize(500, 100);
                userContainer.setOnMouseEntered(event -> userContainer.setCursor(Cursor.HAND));
                userContainer.setOnMouseExited(event -> userContainer.setCursor(Cursor.DEFAULT));

                //userContainer.setOnMouseClicked(e -> System.out.println(userContainer));
                usersContainer.add(userContainer, j, i);
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersContainer;
    }
}
