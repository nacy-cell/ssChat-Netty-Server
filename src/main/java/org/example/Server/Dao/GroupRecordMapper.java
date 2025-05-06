package org.example.Server.Dao;
import org.apache.ibatis.annotations.*;
import org.example.Server.message.GroupChatMessage;

import java.util.List;

public interface GroupRecordMapper {
    // 插入一条聊天记录，接收 GroupChatMessage 对象作为参数
    @Insert("INSERT INTO group_chat_records (group_name, sender_id, message, send_time) VALUES (#{groupChatMessage.groupName}, #{groupChatMessage.senderID}, #{groupChatMessage.content}, #{groupChatMessage.sendTime})")
    @Options(useGeneratedKeys = true, keyProperty = "groupChatMessage.messageID", keyColumn = "record_id")
    int insertRecord(@Param("groupChatMessage") GroupChatMessage groupChatMessage);

    // 根据记录 ID 删除一条聊天记录
    @Delete("DELETE FROM group_chat_records WHERE record_id = #{recordId}")
    int deleteRecordById(@Param("recordId") int recordId);

    // 删除某个群组的所有聊天记录
    @Delete("DELETE FROM group_chat_records WHERE group_name = #{groupName}")
    int deleteRecordsByGroupName(@Param("groupName") String groupName);

    // 删除某个用户发送的所有聊天记录
    @Delete("DELETE FROM group_chat_records WHERE sender_id = #{senderId}")
    int deleteRecordsBySenderId(@Param("senderId") int senderId);

    // 更新某条记录的消息内容
    @Update("UPDATE group_chat_records SET message = #{message} WHERE record_id = #{recordId}")
    int updateRecordMessage(@Param("recordId") int recordId, @Param("message") String message);

    // 更新某个群组的所有记录的发送时间为当前时间
    @Update("UPDATE group_chat_records SET send_time = CURRENT_TIMESTAMP WHERE group_name = #{groupName}")
    int updateRecordsSendTimeByGroupName(@Param("groupName") String groupName);

    // 查询所有聊天记录，返回 GroupChatMessage 列表
    @Select("SELECT record_id AS messageID, group_name AS groupName, sender_id AS senderID, message AS content, send_time AS sendTime FROM group_chat_records")
    List<GroupChatMessage> selectAllRecords();

    // 查询某个群组的聊天记录，返回 GroupChatMessage 列表
    @Select("SELECT record_id AS messageID, group_name AS groupName, sender_id AS senderID, message AS content, send_time AS sendTime FROM group_chat_records WHERE group_name = #{groupName}")
    List<GroupChatMessage> selectRecordsByGroupName(@Param("groupName") String groupName);

    // 查询某个用户发送的聊天记录，返回 GroupChatMessage 列表
    @Select("SELECT record_id AS messageID, group_name AS groupName, sender_id AS senderID, message AS content, send_time AS sendTime FROM group_chat_records WHERE sender_id = #{senderId}")
    List<GroupChatMessage> selectRecordsBySenderId(@Param("senderId") int senderId);

    // 查询某个时间段内的聊天记录，返回 GroupChatMessage 列表
    @Select("SELECT record_id AS messageID, group_name AS groupName, sender_id AS senderID, message AS content, send_time AS sendTime FROM group_chat_records WHERE send_time BETWEEN #{startTime} AND #{endTime}")
    List<GroupChatMessage> selectRecordsByTimeRange(@Param("startTime") String startTime, @Param("endTime") String endTime);

    // 查询指定 record_id 之后指定 group_name 的所有记录
    @Select("SELECT record_id AS messageID, group_name AS groupName, sender_id AS senderID, message AS content, send_time AS sendTime " +
            "FROM group_chat_records " +
            "WHERE group_name = #{groupName} AND record_id > #{recordId} " +
            "ORDER BY record_id ASC")
    List<GroupChatMessage> selectRecordsAfterRecordId(@Param("recordId") int recordId, @Param("groupName") String groupName);
}
