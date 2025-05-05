package org.example.Server.Handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Service.RegistService;
import org.example.Server.Session.SessionFactory;
import org.example.Server.Util.Factory.RegistServiceFactory;
import org.example.Server.Util.MailUtil;
import org.example.Server.message.requestMessage.RegisterCodeRequestMessage;
import org.example.Server.message.requestMessage.RegisterRequestMessage;
import org.example.Server.message.responseMessage.RegisterCodeResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class RegisterCodeRequestMessageHandler extends SimpleChannelInboundHandler<RegisterCodeRequestMessage> {
    private static final Logger log = LoggerFactory.getLogger(RegisterCodeRequestMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterCodeRequestMessage msg) throws Exception {
        String to = msg.getEmail();
        String senderNickname = "清水海因啊";
        String subject = "你是一只可爱的猫娘";
        String content = RegistServiceFactory.getRegistService().Code(to);
        boolean t = MailUtil.sendMail(to, senderNickname, subject, content);
        RegisterCodeResponseMessage message;
        if (t) {
            message = new RegisterCodeResponseMessage(true,"已发送验证码");
        }else{
            message = new RegisterCodeResponseMessage(false,"验证码发送失败");
        }
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("用户断开了验证码连接");
        SessionFactory.getSession().unbind(ctx.channel());
        ctx.fireChannelInactive();
    }
}
