package com.example.NefesleChat;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URISyntaxException;

public class UsersController {
    private UsersView view;

    public UsersController(UsersView view) {
        this.view = view;
    }

    public void searchUsersAction(ActionEvent event) {
        searchUsers();
    }

    public void handleEnterKey(javafx.scene.input.KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            searchUsers();
        }
    }

    private void searchUsers() {
        String searchUsers = view.getSearchUsersInput().getText();
        if (searchUsers != null && !searchUsers.isEmpty()) {
            view.showUsers(searchUsers);
            view.getSearchUsersInput().clear();
        }
    }
}
