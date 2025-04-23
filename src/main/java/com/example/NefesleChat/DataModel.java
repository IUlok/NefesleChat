package com.example.NefesleChat;

// DataModel.java
import java.util.ArrayList;
import java.util.List;

public class DataModel {
    private String username;
    private List<String> allUsers = new ArrayList<>(); // List of ALL usernames

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getAllUsers() {
        return allUsers;
    }

    public void addAllUsers(String user) {
        allUsers.add(user);
    }
}
