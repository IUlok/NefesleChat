package com.example.NefesleChat.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

    private int id; // ид чата

    private String name; // с кем переписка

    private ChatTypeEnum type; // тип: групповой, тет а тет, студ.группа

    @SerializedName("last_message")
    private LastMessageDTO lastMessage; // структура о последнем сообщении

    @SerializedName("message_from")
    private String messageFrom; // если отправлено от себя, это приставка "Вы". Кароче всегда перед последним сообщением

    @SerializedName("user_type")
    private RoleEnum userType; // роль юзера для иконок

    @SerializedName("not_read")
    private int notRead; // кол-во непрочитанных сообщений в чате

    @SerializedName("user_id")
    private int userId;
}

