package com.example.NefesleChat;

import com.example.NefesleChat.entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtil {
    private static HttpClient client = HttpClient.newBuilder()
            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
            .build();
    private static final String serverUri = "http://linedown.ru";
    private static final String serverPrefix = "http://linedown.ru:3254/api";
    private static final String myProfilePath = "/my-profile";
    private static final String userProfilePath = "/user-profile";
    private static final String userListPath = "/users?last-name=";

    @Getter
    private static String jwtToken;

    public HttpResponse<String> regUser(RegistrationForm regForm) {
        try {
            String regJson = new Gson().toJson(regForm);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(serverPrefix + "/auth/register"))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(regJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jwtToken = retrieveJwtFromCookie();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> authUser(AuthForm authForm) {
        try {
            String authJson = new Gson().toJson(authForm);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(serverPrefix + "/auth"))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(authJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jwtToken = retrieveJwtFromCookie();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void logOut() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(serverPrefix + "/auth/logout"))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            jwtToken = "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void restartSession(String jwtToken) {
        this.jwtToken = jwtToken;
        putJwtInCookie(jwtToken);
    }

    private String retrieveJwtFromCookie() {
        CookieManager cookieManager = (CookieManager) client.cookieHandler().get();
        for(HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
            if(cookie.getName().equals("JWT"))
                return cookie.getValue();
        }
        return null;
    }

    private void putJwtInCookie(String jwtToken) {
        CookieManager cookieManager = (CookieManager) client.cookieHandler().get();
        for(HttpCookie cookie : cookieManager.getCookieStore().getCookies())
            if(cookie.getName().equals("JWT"))
                return;
        HttpCookie httpCookie = new HttpCookie("JWT", jwtToken);
        httpCookie.setPath("/");
        cookieManager.getCookieStore().add(URI.create(serverUri), httpCookie);
    }

    public static UserDetailsDTO getCurrentUser() throws URISyntaxException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + myProfilePath))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), UserDetailsDTO.class);
    }

    public static List<UserInListDTO> getListUsers(String lastName) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + userListPath + lastName))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Type userListType = new TypeToken<List<UserInListDTO>>() {}.getType();
        List<UserInListDTO> userList = gson.fromJson(response.body(), userListType);

        return userList;
    }

    public static UserDetailsDTO getOtherUser(int id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + userProfilePath + "/" + id))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), UserDetailsDTO.class);
    }

    public static List<ChatDTO> getListChats() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + "/chats"))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Type chatListType = new TypeToken<List<ChatDTO>>() {}.getType();
        List<ChatDTO> chatList = gson.fromJson(response.body(), chatListType);

        return chatList;
    }

    public static List<MessageDTO> getMessagesInChat(int chatID, int numberList) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + "/chat/" + chatID + "/messages?page=" + numberList))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Type messageListType = new TypeToken<List<MessageDTO>>() {}.getType();
        List<MessageDTO> messagesList = gson.fromJson(response.body(), messageListType);

        return messagesList;
    }

    public static int getMyID() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + "/my-id"))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Integer.parseInt(response.body());
    }

}
