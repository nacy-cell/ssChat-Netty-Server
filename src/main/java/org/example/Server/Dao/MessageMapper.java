package org.example.Server.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.example.Server.message.requestMessage.SingleChatTextRequestMessage;

import java.util.List;

public interface MessageMapper {


    @Select("select sender_id ,receiver_id,message ,send_time ,record_id " +
            "from user u join private_chat_records m " +
            "on m.record_id >u.last_message_id AND UserId=receiver_id " +
            "where UserId=#{userId}")
    List<SingleChatTextRequestMessage> getMessageList(Integer userId);


    @Insert("INSERT INTO private_chat_records (sender_id , receiver_id, message, send_time) " +
            "VALUES (#{senderId}, #{receiverId}, #{message}, #{sendTime})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId", keyColumn = "record_id")
    void insertMessage(SingleChatTextRequestMessage message);

}
