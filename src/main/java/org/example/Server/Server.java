package org.example.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import org.example.Server.Handler.*;
import org.example.Server.proto.MessageCodec;
import org.example.Server.proto.ProtoFrameDecoder;

public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.group(bossGroup, workerGroup);

        Channel channel=bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new IdleStateHandler(30, 0, 0));

                ch.pipeline().addLast(new ProtoFrameDecoder());
                ch.pipeline().addLast(new MessageCodec());
                ch.pipeline().addLast(new ServerHeartbeatHandler());
                ch.pipeline().addLast(new IdentityVerifyHandler());
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new LoginStatusRequestMessageHandler());
                ch.pipeline().addLast(new LoginRequestMessageHandler());
                ch.pipeline().addLast(new RegistRequestMessageHandler());
                ch.pipeline().addLast(new RegisterCodeRequestMessageHandler());
                ch.pipeline().addLast(new InitOkMessageHandler());
                ch.pipeline().addLast(new SingleChatMessageHandler());
                ch.pipeline().addLast(new GroupCreateRequestMessageHandler());
                ch.pipeline().addLast(new GroupChatTextRequestMessageHandler());
                ch.pipeline().addLast(new GroupMembersRequestMessageHandler());
                ch.pipeline().addLast(new GroupJoinRequestMessageHandler());
                ch.pipeline().addLast(new GroupQuitRequestMessageHandler());
                ch.pipeline().addLast(new GlobalExceptionHandler());
            }
        }).bind(8080).syncUninterruptibly().channel();

        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
