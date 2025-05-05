package org.example.Server.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Service.FriendService;
import org.example.Server.Service.Impl.FriendServiceImpl;
import org.example.Server.message.requestMessage.FriendListRequestMessage;
import org.example.Server.message.responseMessage.FriendListResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class FriendListRequestHandler extends SimpleChannelInboundHandler<FriendListRequestMessage> {
    private static final Logger logger = LoggerFactory.getLogger(FriendListRequestHandler.class);
    private final FriendService friendService = new FriendServiceImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FriendListRequestMessage msg) throws Exception {
        logger.info("收到好友列表请求: {}", msg);

        String userId = msg.getUserId();
        List<Map<String, Object>> friendList = friendService.getFriendList(userId);

        FriendListResponseMessage response = new FriendListResponseMessage();
        response.setSequenceId(msg.getSequenceId());
        if (friendList != null) {
            response.setSuccess(true);
            response.setFriendList(friendList);
        } else {
            response.setSuccess(false);
            response.setReason("获取好友列表失败");
        }

        ctx.writeAndFlush(response);
    }
}