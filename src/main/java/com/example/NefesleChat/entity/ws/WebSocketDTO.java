package com.example.NefesleChat.entity.ws;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebSocketDTO implements Serializable {

	private String type;

	private Object payload;
}
