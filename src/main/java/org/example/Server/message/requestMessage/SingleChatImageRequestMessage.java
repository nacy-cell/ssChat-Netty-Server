package org.example.Server.message.requestMessage;

import lombok.Getter;

// 图片消息内容类
@Getter
public class SingleChatImageRequestMessage extends SingleChatRequestMessage {
    @Override
    public int getMessageType() {
        return SingleImageRequestMessage;
    }
    byte[] content;
    public SingleChatImageRequestMessage(String sendTime, Integer senderID, Integer receiverID, byte[] content){
        super(sendTime,senderID,receiverID);
        this.content = content;
    }
}