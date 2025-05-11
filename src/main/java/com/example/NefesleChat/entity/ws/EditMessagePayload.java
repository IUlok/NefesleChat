package com.example.NefesleChat.entity.ws;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditMessagePayload {

	@SerializedName("message_id")
	private int messageId;

	private String message;
}
