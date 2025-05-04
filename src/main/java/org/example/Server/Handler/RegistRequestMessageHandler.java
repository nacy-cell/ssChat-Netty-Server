package org.example.Server.Handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Session.SessionFactory;
import org.example.Server.Util.Factory.RegistServiceFactory;
import org.example.Server.message.requestMessage.RegisterRequestMessage;
import org.example.Server.message.responseMessage.RegisterRequestResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class RegistRequestMessageHandler extends SimpleChannelInboundHandler<RegisterRequestMessage> {
    private static final Logger log = LoggerFactory.getLogger(RegistRequestMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        Integer register = RegistServiceFactory.getRegistService().regist(username, password);
        RegisterRequestResponseMessage message;
        if (register != 0) {
            message = new RegisterRequestResponseMessage(true, "注册成功，请重新登录", register);
        }else {
            message = new RegisterRequestResponseMessage(false, "注册失败，可能用户名已存在",0);
        }
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("用户断开了注册相关连接");
        SessionFactory.getSession().unbind(ctx.channel());
        ctx.fireChannelInactive();
    }
}
