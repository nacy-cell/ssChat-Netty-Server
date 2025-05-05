package org.example.Server.Handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Service.FriendService;
import org.example.Server.Service.Impl.FriendServiceImpl;
import org.example.Server.Session.Session;
import org.example.Server.Session.SessionFactory;
import org.example.Server.message.requestMessage.FriendRequestMessage;
import org.example.Server.message.responseMessage.FriendRequestResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FriendRequestHandler extends SimpleChannelInboundHandler<FriendRequestMessage> {
    private static final Logger logger = LoggerFactory.getLogger(FriendRequestHandler.class);
    private final FriendService friendService = new FriendServiceImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FriendRequestMessage msg) throws Exception {
        logger.info("收到好友请求: {}", msg);

        String userId = msg.getUserId();
        String friendId = msg.getFriendId();

        // 处理好友请求
        boolean result = friendService.addFriendRequest(userId, friendId);

        // 响应给请求方
        FriendRequestResponseMessage response = new FriendRequestResponseMessage();
        response.setSuccess(result);
        response.setSequenceId(msg.getSequenceId());

        if (!result) {
            response.setReason("添加好友请求失败");
        } else {
            // 获取好友信息
            Map<String, Object> friendInfo = getUserInfo(friendId);
            if (friendInfo != null) {
                Map<String, Object> friendInfoMap = new HashMap<>();
                friendInfoMap.put("friendId", friendId);
                friendInfoMap.put("friendNickname", friendInfo.get("nickname"));
                friendInfoMap.put("friendAvatar", friendInfo.get("avatar"));
                response.setFriendInfo(friendInfoMap);
            }

            // 如果对方在线，发送通知
            Session session = SessionFactory.getSession();
            io.netty.channel.Channel friendChannel = session.getChannel(friendId);
            if (friendChannel != null) {
                // 创建好友请求通知消息
                FriendRequestResponseMessage notify = new FriendRequestResponseMessage();
                notify.setSuccess(true);

                Map<String, Object> userInfo = getUserInfo(userId);
                if (userInfo != null) {
                    Map<String, Object> requestUserInfo = new HashMap<>();
                    requestUserInfo.put("userId", userId);
                    requestUserInfo.put("username", userInfo.get("username"));
                    requestUserInfo.put("nickname", userInfo.get("nickname"));
                    requestUserInfo.put("avatar", userInfo.get("avatar"));
                    notify.setFriendInfo(requestUserInfo);
                }

                // 发送通知
                friendChannel.writeAndFlush(notify);
            }
        }

        ctx.writeAndFlush(response);
    }

    private Map<String, Object> getUserInfo(String userId) {
        // 从数据库获取用户信息
        // 示例实现，实际应该查询数据库
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("username", "user_" + userId);
        userInfo.put("nickname", "昵称_" + userId);
        userInfo.put("avatar", "default_avatar.png");
        return userInfo;
    }
}