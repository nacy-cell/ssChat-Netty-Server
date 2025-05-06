package org.example.Server.Util.Factory;

import lombok.Getter;
import org.example.Server.Service.GroupChatService;
import org.example.Server.Service.Impl.GroupChatServiceImpl;

public abstract class GroupChatServiceFactory {
    @Getter
    private static GroupChatService userService = new GroupChatServiceImpl();
}
