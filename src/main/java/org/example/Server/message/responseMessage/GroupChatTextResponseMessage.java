package org.example.Server.message.responseMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupChatTextResponseMessage extends RequestResponseMessage{
    private Integer from;
    private String content;

    public GroupChatTextResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatTextResponseMessage(Integer from, String content) {
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return GroupTextResponseMessage;
    }
}
