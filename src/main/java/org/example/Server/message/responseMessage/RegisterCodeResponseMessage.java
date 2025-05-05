package org.example.Server.message.responseMessage;

public class RegisterCodeResponseMessage extends RequestResponseMessage{
    @Override
    public int getMessageType() {
        return RegisterCodeResponseMessage;
    }

    public RegisterCodeResponseMessage(boolean success, String reason) {
        super(success, reason);
    }
}
