package com.example.NefesleChat.entity;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RegistrationForm {
    @SerializedName("reg_token")
    private String reg_token;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;
}
