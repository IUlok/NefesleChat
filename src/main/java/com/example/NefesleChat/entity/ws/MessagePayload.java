package com.example.NefesleChat.entity.ws;

import com.example.NefesleChat.entity.MessageTypeEnum;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessagePayload implements Serializable {

	private int id;

	private String message;

	@SerializedName("chat_id")
	private int chatId;

	@SerializedName("sender_id")
	private int senderId;

	@SerializedName("sender_name")
	private String senderName;

	@SerializedName("chat_name")
	private String chatName;

	private MessageTypeEnum type;

	private String filename;

	@SerializedName("created_at")
	private Date createdAt;

	private boolean seen;

	@SerializedName("seen_by_me")
	private boolean seenByMe;
}
