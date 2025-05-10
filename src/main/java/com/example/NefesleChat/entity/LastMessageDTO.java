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

public class LastMessageDTO {
    private MessageTypeEnum type; // текст или файл

    private String text; // текст сообщения

    private boolean seen; // true если прочитали, false если не прочитали

    @SerializedName("created_at")
    private Date createdAt; // дата отправки сообщения
}
