package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;


@Data
@NoArgsConstructor
public abstract class ChatRequestMessage extends Message {

    //发送时间
    protected String sendTime;
    //发送人ID

    @Getter
    protected Integer senderID;

    ChatRequestMessage(String sendTime, Integer senderID) {
        super();
        this.sendTime = sendTime;
        this.senderID = senderID;
    }

    public void setSender_id(Integer senderID) {
        this.senderID = senderID;
    }

    public Integer getSenderId() {
        return senderID;
    }

    public Integer getSenderID() {
        return senderID;
    }
}
