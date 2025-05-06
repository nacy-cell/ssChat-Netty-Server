package org.example.Server.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Session.SessionFactory;
import org.example.Server.Util.Factory.GroupChatServiceFactory;
import org.example.Server.message.GroupChatMessage;
import org.example.Server.message.requestMessage.GroupInitRequestMessage;
import org.example.Server.message.responseMessage.GroupChatTextResponseMessage;

import java.util.List;

public class GroupInitMessageHandler extends SimpleChannelInboundHandler<GroupInitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext cxt, GroupInitRequestMessage msg) throws Exception {
        Integer receiverID = msg.getFrom();
        List<GroupChatMessage> messages = GroupChatServiceFactory.getUserService().getRecode(msg.getMessageID(),msg.getGroupName());
        final Channel requestChannel = SessionFactory.getSession().getChannel(receiverID);
        if(messages != null && !messages.isEmpty()) {
             for(GroupChatMessage message : messages) {
                 requestChannel.writeAndFlush(new GroupChatTextResponseMessage(message.getSendTime(),message.getSenderID(),message.getContent(),message.getGroupName(),message.getMessageID()));
             }
        }else{
            requestChannel.writeAndFlush(new GroupChatTextResponseMessage(false,"已经是最新消息"));
        }
    }
}
