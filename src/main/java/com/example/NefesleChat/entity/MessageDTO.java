package com.example.NefesleChat.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private int id; // id сообщения в системе

    @SerializedName("sender_id")
    private int senderId; // id отправителя

    @SerializedName("chat_id")
    private int chatId; // id чата

    private MessageTypeEnum type; // текст, системное или файл

    @SerializedName("created_at")
    private Date createdAt; // дата отправки

    private String message; // сообщение

    private String filename; // имя файла

    @SerializedName("sender_name")
    private String senderName; // имя отправителя

    private boolean seen; // прочитано или нет
}