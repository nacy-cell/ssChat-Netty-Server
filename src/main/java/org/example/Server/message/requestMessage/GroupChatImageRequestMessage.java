package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupChatImageRequestMessage extends GroupChatRequestMessage {
    byte[] content;
    private String groupName;
    private Integer from;
    private String time;

    @Override
    public int getMessageType() {
        return GroupImageMessage;
    }

    public GroupChatImageRequestMessage(String sendTime, Integer senderID, String groupName, byte[] content){
        this.time = sendTime;
        this.content = content;
        this.from = senderID;
        this.groupName = groupName;
    }
}
