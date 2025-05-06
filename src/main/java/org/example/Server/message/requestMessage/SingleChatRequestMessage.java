package org.example.Server.message.requestMessage;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.annotation.Target;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class SingleChatRequestMessage extends ChatRequestMessage {

    protected Integer receiverID;

    SingleChatRequestMessage(String sendTime, Integer senderID, Integer receiverID) {
        super(sendTime,senderID);
        this.receiverID = receiverID;
    }

    public void setReceiver_id(Integer receiverID) {
        this.receiverID = receiverID;
    }


    public Integer getReceiverId() {
        return receiverID;
    }

    public Integer getReceiverID() {
        return receiverID;
    }
}
