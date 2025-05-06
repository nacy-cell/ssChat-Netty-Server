package org.example.Server.Service;

import org.example.Server.message.GroupChatMessage;

import java.util.List;

public interface GroupChatService {
    int addRecode(GroupChatMessage message);

    List<GroupChatMessage>  getRecode(Integer recodeId,String groundName);
}
