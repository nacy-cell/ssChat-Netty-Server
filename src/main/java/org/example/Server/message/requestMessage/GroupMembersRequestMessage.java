package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupMembersRequestMessage extends GroupChatRequestMessage{
    private String groupName;

    public GroupMembersRequestMessage(String groupName) {
        this.groupName = groupName;
    }


    @Override
    public int getMessageType() {
        return GroupMembersMessage;
    }
}
