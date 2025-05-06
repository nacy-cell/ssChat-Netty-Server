package org.example.Server.message;

import lombok.Data;

@Data
public class GroupChatMessage {
    private String content;
    private String groupName;
    private Integer senderID;
    private String sendTime;
    private Integer messageID;

    public GroupChatMessage(String content, String groupName, Integer senderID, String sendTime, Integer messageID) {
        this.content = content;
        this.groupName = groupName;
        this.senderID = senderID;
        this.sendTime = sendTime;
        this.messageID = messageID;
    }
}
