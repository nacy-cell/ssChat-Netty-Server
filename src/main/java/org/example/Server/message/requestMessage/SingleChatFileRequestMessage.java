package org.example.Server.message.requestMessage;

//未实现
public class SingleChatFileRequestMessage extends SingleChatRequestMessage {

    @Override
    public int getMessageType() {
        return SingleFileRequestMessage;
    }
}
