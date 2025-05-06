package org.example.Server.message.requestMessage;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

// 文本消息内容类
@Data
@NoArgsConstructor
public class SingleChatTextRequestMessage extends SingleChatRequestMessage {
    //消息内容
    protected String content;
    protected  Integer recordId;
    @Override
    public int getMessageType() {
        return SingleTextRequestMessage;
    }

    public SingleChatTextRequestMessage(String sendTime, Integer senderID, Integer receiverID, String content){
        super(sendTime,senderID,receiverID);
        this.content = content;
    }

    public void setMessage(String content){
        this.content = content;
    }

    public String getMessage(){
        return content;
    }

    public void setRecord_id(Integer recordId){
        this.recordId = recordId;
    }



}
