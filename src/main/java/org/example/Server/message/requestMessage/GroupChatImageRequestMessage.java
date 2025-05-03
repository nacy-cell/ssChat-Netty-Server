package org.example.Server.message.requestMessage;

public class GroupChatImageRequestMessage extends GroupChatRequestMessage {
    @Override
    public int getMessageType() {
        return GroupImageMessage;
    }
}
