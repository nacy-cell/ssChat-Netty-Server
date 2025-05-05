package org.example.Server.message.responseMessage;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;

import java.util.Map;

@Data
@NoArgsConstructor
public class GroupCreateResponseMessage extends Message {
    private boolean success;
    private String reason;
    private Map<String, Object> groupInfo;

    public GroupCreateResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return GroupCreateResponseEvent;
    }
}