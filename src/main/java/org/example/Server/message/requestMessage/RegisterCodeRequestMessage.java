package org.example.Server.message.requestMessage;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterCodeRequestMessage extends LoginAndRegisterRequestMessage{
    private String email;
    @Override
    public int getMessageType() {
        return RegisterCodeRequestMessage;
    }
}
