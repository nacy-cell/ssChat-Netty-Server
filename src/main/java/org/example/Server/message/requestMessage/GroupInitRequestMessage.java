package org.example.Server.message.requestMessage;

import lombok.Data;

@Data
public class GroupInitRequestMessage extends GroupChatRequestMessage {
    private String groupName;
    private Integer from;
    private Integer messageID;

    public GroupInitRequestMessage(String groupName, Integer from, Integer messageID) {
        this.groupName = groupName;
        this.from = from;
        this.messageID = messageID;
    }

    @Override
    public int getMessageType() {
        return GroupInitRequestMessage;
    }
}
