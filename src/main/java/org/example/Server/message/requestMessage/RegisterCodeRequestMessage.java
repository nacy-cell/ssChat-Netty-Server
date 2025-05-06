package org.example.Server.message.requestMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterCodeRequestMessage extends LoginAndRegisterRequestMessage{
    private String email;
    @Override
    public int getMessageType() {
        return RegisterCodeRequestMessage;
    }
}
