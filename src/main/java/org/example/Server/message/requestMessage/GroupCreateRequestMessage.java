package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupCreateRequestMessage extends Message {
    private String groupName;
    private String ownerId;
    private String groupAvatar;
    private List<String> initialMembers;

    public GroupCreateRequestMessage(String groupName, String ownerId, String groupAvatar, List<String> initialMembers) {
        this.groupName = groupName;
        this.ownerId = ownerId;
        this.groupAvatar = groupAvatar;
        this.initialMembers = initialMembers;
    }

    @Override
    public int getMessageType() {
        return GroupCreateRequestEvent;
    }
}