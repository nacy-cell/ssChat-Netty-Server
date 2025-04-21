package org.example.Server.Session;

import io.netty.channel.Channel;

public interface Session {


    void bind(Channel channel, Integer userId,String token);

    void unbind(Channel channel);


    /**
     * 根据用户ID获取 channel
     */
    Channel getChannel(Integer userId);


    Integer getUserIdByToken(String token);
}
