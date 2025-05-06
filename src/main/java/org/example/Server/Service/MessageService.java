package org.example.Server.Service;

import org.example.Server.message.requestMessage.SingleChatTextRequestMessage;

import java.util.List;

public interface MessageService {

    List<SingleChatTextRequestMessage> getMessageList(Integer userId);

    Integer addMessage(SingleChatTextRequestMessage message);

    void updateLastMessageId(Integer userId, Integer lastMessageId);
}
