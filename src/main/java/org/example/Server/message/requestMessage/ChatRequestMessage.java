package org.example.Server.message.requestMessage;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Server.message.Message;


@Data
@NoArgsConstructor
public abstract class ChatRequestMessage extends Message {


    //发送时间
    protected String sendTime;
    //发送人ID
    protected Integer senderID;

    ChatRequestMessage(String sendTime, Integer senderID) {
        super();
        this.sendTime = sendTime;
        this.senderID = senderID;
    }


}
