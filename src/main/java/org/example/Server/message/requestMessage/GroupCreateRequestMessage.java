package org.example.Server.message.requestMessage;
import lombok.Data;
import lombok.ToString;
import java.util.Set;

@Data
@ToString(callSuper = true)
public class GroupCreateRequestMessage extends GroupChatRequestMessage{
    private String groupName;
    private Set<Integer> members;

    public GroupCreateRequestMessage(String groupName, Set<Integer> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupCreateMessage;
    }
}
