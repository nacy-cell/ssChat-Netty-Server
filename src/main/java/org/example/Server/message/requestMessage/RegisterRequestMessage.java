package org.example.Server.message.requestMessage;

import lombok.Data;

@Data
public class RegisterRequestMessage extends LoginAndRegisterRequestMessage {
    Integer userId;
    String password;

    public  RegisterRequestMessage(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return RegisterRequestEvent;
    }
}
