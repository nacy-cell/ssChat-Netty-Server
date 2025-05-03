package org.example.Server.message.requestMessage;

public class GroupChatVideoRequestMessage extends GroupChatRequestMessage {
    @Override
    public int getMessageType() {
        return GroupVideoMessage;
    }

}
