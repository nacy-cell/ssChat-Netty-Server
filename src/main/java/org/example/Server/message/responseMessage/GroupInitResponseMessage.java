package org.example.Server.message.responseMessage;

public class GroupInitResponseMessage extends RequestResponseMessage{
    private String groupName;

    @Override
    public int getMessageType() {
        return GroupInitResponseMessage;
    }
}
