package org.example.Server.Session;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMemoryImpl implements Session {

    private static final Logger log = LoggerFactory.getLogger(SessionMemoryImpl.class);
    private final Map<Integer, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private final Map<Channel, Integer> channelUserIdMap = new ConcurrentHashMap<>();
    // 新增一个 Map 用于存储 token 和用户 ID 的映射关系
    private final Map<String, Integer> tokenUserIdMap = new ConcurrentHashMap<>();
    private final Map<Integer, String> userIdTokenMap = new ConcurrentHashMap<>();

    public void bind(Channel channel, Integer userId, String token) {

        //如果这个ID之前绑定了token,解绑之前的token->userId关系
        if(userIdTokenMap.containsKey(userId)){
            String oldToken = token;
            tokenUserIdMap.remove(oldToken);
        }
        //这一句会覆盖之前的userId->token
        userIdTokenMap.put(userId, token);
        //添加新的token->userId
        tokenUserIdMap.put(token, userId);

        userIdChannelMap.put(userId, channel);
        channelUserIdMap.put(channel, userId);


    }

    @Override
    public void unbind(Channel channel) {
        log.debug("Unbind channel: {}", channel);
        Integer userId = channelUserIdMap.remove(channel);
        if (userId != null)
          userIdChannelMap.remove(userId);
    }



    @Override
    public Channel getChannel(Integer username) {
        return userIdChannelMap.get(username);
    }

    @Override
    public Integer getUserIdByToken(String token) {
        // 根据 token 获取对应的用户 ID
        return tokenUserIdMap.get(token);
    }
}
