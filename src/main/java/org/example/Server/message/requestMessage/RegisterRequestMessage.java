package org.example.Server.message.requestMessage;

import lombok.Data;

@Data
public class RegisterRequestMessage extends LoginAndRegisterRequestMessage {
    String username;
    String password;

    public  RegisterRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return RegisterRequestEvent;
    }
}
