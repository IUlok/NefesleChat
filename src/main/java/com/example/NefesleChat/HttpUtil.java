package com.example.NefesleChat;

import com.example.NefesleChat.entity.RegistrationForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class HttpUtil {
    private final HttpClient client = HttpClient.newHttpClient();
    private String serverUri;

    public HttpUtil() {
        String propUri = "src/main/resources/application.properties";
        Properties properties = new Properties();

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
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
