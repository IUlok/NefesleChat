package com.example.NefesleChat;

import com.example.NefesleChat.entity.MessageTypeEnum;
import com.example.NefesleChat.entity.ws.EditMessagePayload;
import com.example.NefesleChat.entity.ws.MessagePayload;
import com.example.NefesleChat.entity.ws.MessageSendDTO;
import com.example.NefesleChat.entity.ws.WebSocketDTO;
import com.google.gson.Gson;
import javafx.application.Platform;
import org.springframework.messaging.converter.GsonMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

public class WebSocketUtil {

    private final WebSocketStompClient client;

    private StompSession session;

    private String url = "ws://" + Main.getProperties().getProperty("domain_port") + "/api/messenger";

    private MainView view;

    public WebSocketUtil() {
        WebSocketClient wsclient = new StandardWebSocketClient();
        client = new WebSocketStompClient(wsclient);
        client.setMessageConverter(new GsonMessageConverter());
    }

    public void setMainView(MainView mainView) {
        this.view = mainView;
    }

    public void connect(String jwtToken) {
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("JWT", jwtToken);
        session = client.connectAsync(url, headers, new StompSessionHandlerAdapter() {
        }).join();
    }

    public void sendMessageToUser(int userId, String message) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("sendMessage");
            wsDto.setPayload(new MessageSendDTO(MessageTypeEnum.TEXT, message));
            session.send("/app/user/" + userId, wsDto);
        }
    }

    public void sendMessageToChat(int chatId, String message) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("sendMessage");
            wsDto.setPayload(new MessageSendDTO(MessageTypeEnum.TEXT, message));
            session.send("/app/chat/" + chatId, wsDto);
        }
    }

    public void readMessageUser(int userId, int messageId) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("readMessage");
            wsDto.setPayload(messageId);
            session.send("/app/user/" + userId, wsDto);
        }
    }

    public void readMessageChat(int chatId, int messageId) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("readMessage");
            wsDto.setPayload(messageId);
            session.send("/app/chat/" + chatId, wsDto);
        }
    }

    public void deleteMessageUser(int userId, int messageId) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("deleteMessage");
            wsDto.setPayload(messageId);
            session.send("/app/user/" + userId, wsDto);
        }
    }

    public void deleteMessageChat(int chatId, int messageId) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("deleteMessage");
            wsDto.setPayload(messageId);
            session.send("/app/chat/" + chatId, wsDto);
        }
    }

    public void editMessageUser(int userId, int messageId, String message) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("editMessage");
            wsDto.setPayload(new EditMessagePayload(messageId, message));
            session.send("/app/user/" + userId, wsDto);
        }
    }

    public void editMessageChat(int chatId, int messageId, String message) {
        if(session != null && session.isConnected()) {
            WebSocketDTO wsDto = new WebSocketDTO();
            wsDto.setType("editMessage");
            wsDto.setPayload(new EditMessagePayload(messageId, message));
            session.send("/app/chat/" + chatId, wsDto);
        }
    }

    public void subscribeToYourself(int myId) {
        session.subscribe("/topic/user/" + myId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return WebSocketDTO.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    WebSocketDTO wsDto = (WebSocketDTO) payload;
                    Object messagePayloadObj = wsDto.getPayload();
                    if (wsDto.getType().equals("newMessage")) {
                        MessagePayload messagePayload = new Gson().fromJson(new Gson().toJson(messagePayloadObj), MessagePayload.class);

                        Platform.runLater(()->{
                            ChatView chatView = new ChatView(view);
                            chatView.addMessageFromSocket(messagePayload.getSenderName(), messagePayload.getMessage(), messagePayload.getSenderId(), messagePayload.getCreatedAt(), String.valueOf(messagePayload.getType()), messagePayload.isSeen(), messagePayload.getChatId(), messagePayload.getId(), messagePayload.isSeenByMe());
                        });

                        System.out.printf("%s\n", messagePayload);
                    }
                    if (wsDto.getType().equals("wasRead")) {
                        Integer messageId = new Gson().fromJson(new Gson().toJson(messagePayloadObj), Integer.class);
                        System.out.printf("message was read: %d\n", messageId);
                    }
                    if (wsDto.getType().equals("wasEdited")) {
                        EditMessagePayload emp = new Gson().fromJson(new Gson().toJson(messagePayloadObj), EditMessagePayload.class);
                        System.out.printf("message was edited: id:%d  text:%s\n", emp.getMessageId(), emp.getMessage());
                    }
                    if (wsDto.getType().equals("wasDeleted")) {
                        Integer messageId = new Gson().fromJson(new Gson().toJson(messagePayloadObj), Integer.class);
                        System.out.printf("message was deleted: %d\n", messageId);
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void subscribeToChat(int chatId) {
        session.subscribe("/topic/chat/" + chatId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return WebSocketDTO.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                WebSocketDTO wsDto = (WebSocketDTO) payload;
                Object messagePayloadObj = wsDto.getPayload();
                if(wsDto.getType().equals("newMessage")) {
                    MessagePayload messagePayload = new Gson().fromJson(new Gson().toJson(messagePayloadObj), MessagePayload.class);

                    Platform.runLater(()->{
                        ChatView chatView = new ChatView(view);
                        chatView.addMessageFromSocket(messagePayload.getSenderName(), messagePayload.getMessage(), messagePayload.getSenderId(), messagePayload.getCreatedAt(), String.valueOf(messagePayload.getType()), messagePayload.isSeen(), messagePayload.getChatId(), messagePayload.getId(), messagePayload.isSeenByMe());
                    });

                    System.out.printf("%s\n", messagePayload);
                }
                if(wsDto.getType().equals("wasRead")) {
                    Integer messageId = new Gson().fromJson(new Gson().toJson(messagePayloadObj), Integer.class);
                    System.out.printf("message was read: %d\n", messageId);
                }
                if(wsDto.getType().equals("wasEdited")) {
                    EditMessagePayload emp = new Gson().fromJson(new Gson().toJson(messagePayloadObj), EditMessagePayload.class);
                    System.out.printf("message was edited: id:%d  text:%s\n", emp.getMessageId(), emp.getMessage());
                }
                if(wsDto.getType().equals("wasDeleted")) {
                    Integer messageId = new Gson().fromJson(new Gson().toJson(messagePayloadObj), Integer.class);
                    System.out.printf("message was deleted: %d\n", messageId);
                }
            }
        });
    }

    public void disconnect() {
        if(session != null && session.isConnected())
            session.disconnect();
    }
}
