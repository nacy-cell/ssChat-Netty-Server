package org.example.Server.message.responseMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupChatTextResponseMessage extends RequestResponseMessage{
    private String sendTime;
    private Integer from;
    private String content;
    private String groupName;
    private Integer insertId;

    public GroupChatTextResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatTextResponseMessage(String sendTime,Integer from, String content,String groupName,Integer insertId) {
        this.sendTime = sendTime;
        this.from = from;
        this.content = content;
        this.groupName = groupName;
        this.insertId = insertId;
    }

    @Override
    public int getMessageType() {
        return GroupTextResponseMessage;
    }
}
