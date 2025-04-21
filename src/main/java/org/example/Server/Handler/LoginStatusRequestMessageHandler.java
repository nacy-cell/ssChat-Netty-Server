package org.example.Server.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.Server.Session.SessionFactory;
import org.example.Server.message.requestMessage.LoginStatusRequestMessage;
import org.example.Server.message.responseMessage.LoginStatusResponseMessage;

public class LoginStatusRequestMessageHandler extends SimpleChannelInboundHandler<LoginStatusRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginStatusRequestMessage loginStatusRequestMessage) throws Exception {
        Integer receiverId=loginStatusRequestMessage.getReceiverId();
        if(SessionFactory.getSession().getChannel(receiverId)!=null){
            channelHandlerContext.writeAndFlush(new LoginStatusResponseMessage("在线",receiverId));
        }else channelHandlerContext.writeAndFlush(new LoginStatusResponseMessage("离线",receiverId));
    }
}
