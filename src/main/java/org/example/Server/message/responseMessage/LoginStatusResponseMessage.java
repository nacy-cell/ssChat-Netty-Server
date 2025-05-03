package org.example.Server.message.responseMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Server.message.Message;

@Data
@AllArgsConstructor
public class LoginStatusResponseMessage extends Message {
    String loginStatus;
    Integer receiverId;
    @Override
    public int getMessageType() {
        return LoginStatusResponseMessage;
    }
}