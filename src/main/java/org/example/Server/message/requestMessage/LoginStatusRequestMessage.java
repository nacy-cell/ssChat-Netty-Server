package org.example.Server.message.requestMessage;


import lombok.Data;
import org.example.Server.message.Message;

@Data
public class LoginStatusRequestMessage extends Message {
    private Integer receiverId;

    public LoginStatusRequestMessage(Integer receiverId) {
        super();
        this.receiverId = receiverId;
    }

    @Override
    public int getMessageType() {
        return LoginStatusRequestMessage;
    }
}
