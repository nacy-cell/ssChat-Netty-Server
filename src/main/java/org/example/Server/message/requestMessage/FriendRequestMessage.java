package org.example.Server.message.requestMessage;

import lombok.Data;
import org.example.Server.message.Message;

@Data
public class FriendRequestMessage extends Message {
    private String userId;    // 请求者ID
    private String friendId;  // 好友ID

    public FriendRequestMessage() {
        super();
    }

    public FriendRequestMessage(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    @Override
    public int getMessageType() {
        return FriendRequestEvent;
    }
}