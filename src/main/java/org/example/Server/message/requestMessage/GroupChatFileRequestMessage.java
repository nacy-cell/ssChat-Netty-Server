package org.example.Server.message.requestMessage;

public class GroupChatFileRequestMessage extends GroupChatRequestMessage {

    @Override
    public int getMessageType() {
        return GroupFileMessage;
    }

}
