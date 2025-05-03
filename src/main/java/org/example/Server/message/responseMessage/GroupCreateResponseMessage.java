package org.example.Server.message.responseMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupCreateResponseMessage extends RequestResponseMessage{

    public GroupCreateResponseMessage(boolean success, String reason) {
        super(success, reason);
    }


    @Override
    public int getMessageType() {
        return GroupCreateResponseMessage;
    }
}
