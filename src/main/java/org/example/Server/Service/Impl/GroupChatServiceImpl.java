package org.example.Server.Service.Impl;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Server.Dao.GroupRecordMapper;
import org.example.Server.Service.GroupChatService;
import org.example.Server.message.GroupChatMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GroupChatServiceImpl implements GroupChatService {
    private SqlSessionFactory sqlSessionFactory;
    public GroupChatServiceImpl()
    {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int addRecode(GroupChatMessage groupChatMessage) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupRecordMapper groupRecordMapper = session.getMapper(GroupRecordMapper.class);
            groupRecordMapper.insertRecord(groupChatMessage);
            return groupChatMessage.getMessageID();
        }
    }

    @Override
    public List<GroupChatMessage> getRecode(Integer recodeId, String groundName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            GroupRecordMapper groupRecordMapper = session.getMapper(GroupRecordMapper.class);
            return groupRecordMapper.selectRecordsAfterRecordId(recodeId, groundName);
        }
    }
}
