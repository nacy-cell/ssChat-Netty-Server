package org.example.Server.message.responseMessage;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;

import java.util.Map;

@Data
@NoArgsConstructor
public class FriendRequestResponseMessage extends Message {
    private boolean success;
    private String reason;
    private Map<String, Object> friendInfo;

    public FriendRequestResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return FriendResponseEvent;
    }
}