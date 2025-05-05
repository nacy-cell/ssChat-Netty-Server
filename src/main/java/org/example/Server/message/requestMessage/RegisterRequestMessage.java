package org.example.Server.message.requestMessage;

import lombok.Data;

@Data
public class RegisterRequestMessage extends LoginAndRegisterRequestMessage {
    String email;
    String code;
    String password;

    public  RegisterRequestMessage(String email, String code, String password) {
        this.email = email;
        this.code = code;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return RegisterRequestEvent;
    }
}
