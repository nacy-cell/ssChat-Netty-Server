package org.example.Server.Handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.example.Server.Session.SessionFactory;

import org.example.Server.Util.Factory.LoginServiceFactory;
import org.example.Server.message.requestMessage.LoginRequestMessage;
import org.example.Server.message.responseMessage.LoginRequestResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    private static final Logger log = LoggerFactory.getLogger(LoginRequestMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        Integer userId = msg.getUserId();
        String password = msg.getPassword();
        boolean login = LoginServiceFactory.getUserService().login(userId, password);
        LoginRequestResponseMessage message;
        if (login) {
            String token = UUID.randomUUID().toString();
            SessionFactory.getSession().bind(ctx.channel(), userId,token);
            message = new LoginRequestResponseMessage(true, Integer.toString(userId),token);
        } else {
            message = new LoginRequestResponseMessage(false, "用户名或密码不正确",null);
        }
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
     //   SessionFactory.getSession().unbind(ctx.channel());
        log.debug("用户断开了连接");
        SessionFactory.getSession().unbind(ctx.channel());
        ctx.fireChannelInactive();
    }
}

