package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupChatTextRequestMessage extends GroupChatRequestMessage {
    private String content;
    private String groupName;
    private Integer from;

    public GroupChatTextRequestMessage(Integer from, String groupName, String content) {
        this.content = content;
        this.groupName = groupName;
        this.from = from;
    }

    @Override
    public int getMessageType() {
        return GroupTextMessage;
    }
}
