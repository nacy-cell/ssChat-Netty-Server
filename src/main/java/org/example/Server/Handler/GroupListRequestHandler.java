package org.example.Server.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Service.GroupService;
import org.example.Server.Service.Impl.GroupServiceImpl;
import org.example.Server.message.requestMessage.GroupListRequestMessage;
import org.example.Server.message.responseMessage.GroupListResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class GroupListRequestHandler extends SimpleChannelInboundHandler<GroupListRequestMessage> {
    private static final Logger logger = LoggerFactory.getLogger(GroupListRequestHandler.class);
    private final GroupService groupService = new GroupServiceImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupListRequestMessage msg) throws Exception {
        logger.info("收到群组列表请求: {}", msg);

        String userId = msg.getUserId();
        List<Map<String, Object>> groupList = groupService.getUserGroups(userId);

        GroupListResponseMessage response = new GroupListResponseMessage();
        response.setSuccess(true);
        response.setSequenceId(msg.getSequenceId());
        response.setGroupList(groupList);

        ctx.writeAndFlush(response);
    }
}