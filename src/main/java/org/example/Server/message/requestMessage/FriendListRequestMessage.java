package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;

@Data
@NoArgsConstructor
public class FriendListRequestMessage extends Message {
    private String userId;

    public FriendListRequestMessage(String userId) {
        this.userId = userId;
    }

    @Override
    public int getMessageType() {
        return FriendListRequestEvent;
    }
}