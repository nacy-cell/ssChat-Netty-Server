package org.example.Server.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Session.Group;
import org.example.Server.Session.GroupSession;
import org.example.Server.Session.GroupSessionFactory;
import org.example.Server.message.requestMessage.GroupCreateRequestMessage;
import org.example.Server.message.responseMessage.GroupCreateResponseMessage;

import java.util.List;
import java.util.Set;

@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        final String groupName = msg.getGroupName();
        final Set<Integer> members = msg.getMembers();
        final Integer creator = msg.getCreator();
        // 群管理器
        final GroupSession groupSession = GroupSessionFactory.getGroupSession();
        final int group = groupSession.createGroup(creator,groupName, members);

        // 创建成功
        if(group != 0){
            // 发送成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, "成功创建群聊:" + groupName));

            // 发送拉群消息
            final List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel ch : channels){
                ch.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入群聊:" + groupName));
            }
        }
        // 创建失败
        else{
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "已存在群: " + groupName));
        }
    }
}
