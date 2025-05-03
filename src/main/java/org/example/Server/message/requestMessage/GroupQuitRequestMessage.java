package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupQuitRequestMessage extends GroupChatRequestMessage{
    private String groupName;

    private Integer username;

    public GroupQuitRequestMessage(Integer username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupQuitMessage;
    }
}
