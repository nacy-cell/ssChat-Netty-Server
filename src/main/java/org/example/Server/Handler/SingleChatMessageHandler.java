package org.example.Server.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.example.Server.Service.Impl.MessageServiceImpl;
import org.example.Server.Service.MessageService;
import org.example.Server.Session.SessionFactory;
import org.example.Server.message.requestMessage.SingleChatRequestMessage;
import org.example.Server.message.requestMessage.SingleChatTextRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SingleChatMessageHandler extends SimpleChannelInboundHandler<SingleChatRequestMessage> {
    private static final Logger log = LoggerFactory.getLogger(SingleChatMessageHandler.class);
    private MessageService messageService;

    public SingleChatMessageHandler() throws IOException {
        messageService = new MessageServiceImpl();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SingleChatRequestMessage singleChatRequestMessage) throws Exception {
        Integer receiverID = singleChatRequestMessage.getReceiverID();
        Channel receiverChannel = SessionFactory.getSession().getChannel(receiverID);
        log.debug("RECEIVER ID: " + receiverID);

        if(singleChatRequestMessage instanceof SingleChatTextRequestMessage singleChatTextRequestMessage){
            messageService.addMessage(singleChatTextRequestMessage);
            Integer currentId = singleChatTextRequestMessage.getRecordId();
            log.debug("CURRENT ID: " + currentId);
            if(receiverChannel != null){
                receiverChannel.writeAndFlush(singleChatTextRequestMessage);
                log.debug("处理了一条请求");
                messageService.updateLastMessageId(singleChatTextRequestMessage.getSenderID(),currentId);
            }
        }

        else if(receiverChannel != null) {
            receiverChannel.writeAndFlush(singleChatRequestMessage);
            log.debug("处理了一条请求");
        }
    }
}
