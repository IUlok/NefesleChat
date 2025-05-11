package com.example.NefesleChat;

import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebSocketUtil {

    private final WebSocketStompClient client;

    private StompSession session;

    private String url = "ws://" + Main.getProperties().getProperty("domain_port") + "/api/messenger";

    public WebSocketUtil() {
        WebSocketClient wsclient = new StandardWebSocketClient();
        client = new WebSocketStompClient(wsclient);
        client.setMessageConverter(new GsonMessageConverter());
    }

    public void connect(String jwtToken) {
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("JWT", jwtToken);
        session = client.connectAsync(url, headers, new StompSessionHandlerAdapter() {
        }).join();
    }

    public void disconnect() {
        if(session != null)
            session.disconnect();
    }
}
