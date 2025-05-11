package com.example.NefesleChat.entity.ws;

import com.example.NefesleChat.entity.MessageTypeEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendDTO {

	private MessageTypeEnum type;

	private String message;
}