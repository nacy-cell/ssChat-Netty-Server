package org.example.Server.message.requestMessage;

import lombok.Data;

@Data
public abstract class GroupChatRequestMessage extends ChatRequestMessage {
    //群组ID
    protected Integer groupID;
}
