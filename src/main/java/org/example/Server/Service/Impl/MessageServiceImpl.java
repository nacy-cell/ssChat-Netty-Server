package org.example.Server.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Server.Dao.MessageMapper;
import org.example.Server.Dao.UserMapper;
import org.example.Server.Service.MessageService;
import org.example.Server.message.requestMessage.SingleChatTextRequestMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class MessageServiceImpl implements MessageService {

    private final SqlSessionFactory sqlSessionFactory;

    public  MessageServiceImpl() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Override
    public List<SingleChatTextRequestMessage> getMessageList(Integer userId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper messageMapper = session.getMapper(MessageMapper.class);
            return messageMapper.getMessageList(userId);
        }
    }

    @Override
    public Integer addMessage(SingleChatTextRequestMessage singleChatTextRequestMessage){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MessageMapper messageMapper = session.getMapper(MessageMapper.class);
            messageMapper.insertMessage(singleChatTextRequestMessage);
            Integer lastMessageId=singleChatTextRequestMessage.getRecordId();
            log.debug("插入成功{}",lastMessageId);
            session.commit();
            return  lastMessageId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateLastMessageId(Integer userId, Integer lastMessageId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.updateLastMessageId(userId,lastMessageId);
            session.commit();
        }

    }
}
