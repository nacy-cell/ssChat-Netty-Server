package org.example.Server.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Session.GroupSession;
import org.example.Server.Session.GroupSessionFactory;
import org.example.Server.message.requestMessage.GroupChatTextRequestMessage;
import org.example.Server.message.responseMessage.GroupChatTextResponseMessage;

import java.util.List;

@ChannelHandler.Sharable
public class GroupChatTextRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatTextRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatTextRequestMessage msg) throws Exception {
        final GroupSession groupSession = GroupSessionFactory.getGroupSession();
        final List<Channel> channelList = groupSession.getMembersChannel(msg.getGroupName());

        for (Channel channel : channelList) {

            channel.writeAndFlush(new GroupChatTextResponseMessage(msg.getTime(),msg.getFrom(), msg.getContent(),msg.getGroupName()));
        }
    }
}