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
        String email = msg.getEmail();
        String code = msg.getCode();
        String password = msg.getPassword();
        Integer register = RegistServiceFactory.getRegistService().regist(code,email, password);
        RegisterRequestResponseMessage message;
        if (register == -1) {
            message = new RegisterRequestResponseMessage(true, register.toString());
           // message = new RegisterRequestResponseMessage(false, "验证码错误");
        }else if (register != 0) {
            message = new RegisterRequestResponseMessage(true, register.toString());
        }else {
            message = new RegisterRequestResponseMessage(false, "注册失败，可能用户名已存在");
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
