package com.example.NefesleChat;

import com.example.NefesleChat.entity.AuthForm;
import com.example.NefesleChat.entity.RegistrationForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class HttpUtil {
    private final HttpClient client;
    private final String serverUri;

    public HttpUtil() {
        String propUri = "src/main/resources/application.properties";
        Properties properties = new Properties();

        client = HttpClient.newBuilder()
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();

        try {
            properties.load(new FileInputStream(propUri));
            serverUri = properties.getProperty("server_uri");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> regUser(RegistrationForm regForm) {
        try {
            String regJson = new Gson().toJson(regForm);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(serverUri + "/auth/register"))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(regJson))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> authUser(AuthForm authForm) {
        try {
            String authJson = new Gson().toJson(authForm);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(serverUri + "/auth"))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(authJson))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
