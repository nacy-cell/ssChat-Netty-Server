package org.example.Server.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Session.Group;
import org.example.Server.Session.GroupSession;
import org.example.Server.Session.GroupSessionFactory;
import org.example.Server.message.requestMessage.GroupJoinRequestMessage;
import org.example.Server.message.responseMessage.GroupChatTextResponseMessage;
import org.example.Server.message.responseMessage.GroupCreateResponseMessage;
import org.example.Server.message.responseMessage.GroupJoinResponseMessage;

import java.util.List;
import java.util.Set;

@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        final String groupName = msg.getGroupName();
        // 群管理器
        final GroupSession groupSession = GroupSessionFactory.getGroupSession();
        final List<Channel> channelList = groupSession.getMembersChannel(msg.getGroupName());
        // 创建成功
        ctx.writeAndFlush(new GroupCreateResponseMessage(true, "您已加入群聊:" + groupName));
        for (Channel channel : channelList) {
            channel.writeAndFlush(new GroupJoinResponseMessage(msg.getUsername(),groupName));
        }
    }
}
