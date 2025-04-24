package com.example.NefesleChat.entity;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthForm {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;
}
