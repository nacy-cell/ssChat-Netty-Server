package org.example.Server.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Service.GroupService;
import org.example.Server.Service.Impl.GroupServiceImpl;
import org.example.Server.Session.Session;
import org.example.Server.Session.SessionFactory;
import org.example.Server.message.requestMessage.GroupCreateRequestMessage;
import org.example.Server.message.responseMessage.GroupCreateResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupCreateHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    private static final Logger logger = LoggerFactory.getLogger(GroupCreateHandler.class);
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        logger.info("收到创建群组请求: {}", msg);

        String groupName = msg.getGroupName();
        String ownerId = msg.getOwnerId();
        String groupAvatar = msg.getGroupAvatar();
        List<String> initialMembers = msg.getInitialMembers();

        // 创建群组
        String groupId = groupService.createGroup(groupName, ownerId, groupAvatar);

        GroupCreateResponseMessage response = new GroupCreateResponseMessage();
        response.setSequenceId(msg.getSequenceId());

        if (groupId != null) {
            response.setSuccess(true);

            // 添加初始成员
            for (String memberId : initialMembers) {
                if (!memberId.equals(ownerId)) { // 群主已经添加
                    groupService.addGroupMember(groupId, memberId, null, 0); // 0为普通成员
                }
            }

            // 返回群组信息
            Map<String, Object> groupInfo = new HashMap<>();
            groupInfo.put("groupId", groupId);
            groupInfo.put("groupName", groupName);
            groupInfo.put("groupAvatar", groupAvatar);
            groupInfo.put("ownerId", ownerId);
            response.setGroupInfo(groupInfo);

            // 通知所有成员
            Session session = SessionFactory.getSession();
            for (String memberId : initialMembers) {
                if (!memberId.equals(ownerId)) { // 不用通知群主自己
                    io.netty.channel.Channel memberChannel = session.getChannel(memberId);
                    if (memberChannel != null) {
                        // 创建群组通知消息
                        GroupCreateResponseMessage notify = new GroupCreateResponseMessage();
                        notify.setSuccess(true);
                        notify.setGroupInfo(groupInfo);
                        memberChannel.writeAndFlush(notify);
                    }
                }
            }
        } else {
            response.setSuccess(false);
            response.setReason("创建群组失败");
        }

        ctx.writeAndFlush(response);
    }
}