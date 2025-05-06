package org.example.Server.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Server.Dao.GroupMapper;
import org.example.Server.Service.Impl.MessageServiceImpl;
import org.example.Server.Service.MessageService;
import org.example.Server.message.InitOKMessage;
import org.example.Server.message.Message;
import org.example.Server.message.requestMessage.SingleChatRequestMessage;
import org.example.Server.message.requestMessage.SingleChatTextRequestMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InitOkMessageHandler extends SimpleChannelInboundHandler<InitOKMessage> {
    private MessageService messageService=new MessageServiceImpl();

    public InitOkMessageHandler() throws IOException {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InitOKMessage msg) throws Exception {

        System.out.println("InitOkMessage received: " + msg);
        List<SingleChatTextRequestMessage>messageList=messageService.getMessageList(msg.getUserId());

        if(messageList==null|| messageList.isEmpty())
            return;


        for(SingleChatTextRequestMessage message:messageList){
            ctx.writeAndFlush(message);
        }

        messageService.updateLastMessageId(msg.getUserId(), messageList.get(messageList.size()-1).getRecordId());

    }


}
