package org.example.Server.message.responseMessage;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class FriendListResponseMessage extends Message {
    private boolean success;
    private String reason;
    private List<Map<String, Object>> friendList;

    public FriendListResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    @Override
    public int getMessageType() {
        return FriendListResponseEvent;
    }
}