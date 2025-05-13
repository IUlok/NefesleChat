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
    private final HttpClient client;
    private final String serverUri;
    private final String serverPrefix;
    private final String domain;

    @Getter
    private String jwtToken;

    public HttpUtil() {
        client = HttpClient.newBuilder()
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();
        serverUri = Main.getProperties().getProperty("server_uri");
        serverPrefix = Main.getProperties().getProperty("server_prefix");
        domain = Main.getProperties().getProperty("domain");
    }

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

    public void logOut() {
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
        for(HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
            if(cookie.getName().equals("JWT"))
                return;
        }
        HttpCookie jwtTokenCookie = new HttpCookie("JWT", jwtToken);
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setDomain(domain);
        cookieManager.getCookieStore().add(URI.create(serverUri), jwtTokenCookie);
    }

    public UserDetailsDTO getCurrentUser() throws URISyntaxException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + "/my-profile"))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), UserDetailsDTO.class);
    }

    public List<UserInListDTO> getListUsers(String lastName) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + "/users?last-name=" + lastName))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Type userListType = new TypeToken<List<UserInListDTO>>() {}.getType();
        List<UserInListDTO> userList = gson.fromJson(response.body(), userListType);

        return userList;
    }

    public UserDetailsDTO getOtherUser(int id) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + "/user-profile" + "/" + id))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), UserDetailsDTO.class);
    }

    public List<ChatDTO> getListChats() throws URISyntaxException, IOException, InterruptedException {
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

    public List<MessageDTO> getMessagesInChat(int chatID, int numberList) throws URISyntaxException, IOException, InterruptedException {
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

    public int getMyID() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serverPrefix + "/my-id"))
                .header("Content-type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Integer.parseInt(response.body());
    }

}
