package org.example.Server.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Session.GroupSession;
import org.example.Server.Session.GroupSessionFactory;
import org.example.Server.message.requestMessage.GroupChatImageRequestMessage;
import org.example.Server.message.responseMessage.GroupChatImageResponseMessage;

import java.util.List;

@ChannelHandler.Sharable
public class GroupChatImageRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatImageRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatImageRequestMessage msg) throws Exception {
        final GroupSession groupSession = GroupSessionFactory.getGroupSession();
        final List<Channel> channelList = groupSession.getMembersChannel(msg.getGroupName());

        for (Channel channel : channelList) {
            channel.writeAndFlush(new GroupChatImageResponseMessage(msg.getTime(),msg.getFrom(),msg.getGroupName(), msg.getContent()));
        }
    }
}
