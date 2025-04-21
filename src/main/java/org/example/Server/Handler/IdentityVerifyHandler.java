package org.example.Server.Handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.example.Server.Session.SessionFactory;
import org.example.Server.message.IdentityVerifyMessage;

@ChannelHandler.Sharable
public class IdentityVerifyHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof IdentityVerifyMessage identityVerifyMessage) {
            String token = identityVerifyMessage.getToken();
            // 从Session中获取存储的身份标识，验证是否一致
            Integer userId = SessionFactory.getSession().getUserIdByToken(token);
            if (userId!= null) {
                // 身份验证成功，将用户与通道绑定
                SessionFactory.getSession().bind(ctx.channel(), userId, token);
//                // 发送身份验证成功的消息给客户端
//                IdentityVerifyResponseMessage response = new IdentityVerifyResponseMessage(true, "身份验证成功");
//                ctx.writeAndFlush(response);
            } else {
                // 身份验证失败，发送失败消息给客户端
//                IdentityVerifyResponseMessage response = new IdentityVerifyResponseMessage(false, "身份验证失败");
//                ctx.writeAndFlush(response);
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }
}