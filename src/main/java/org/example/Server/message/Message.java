package org.example.Server.message;

import lombok.Data;
import org.example.Server.Util.SequenceIdUtil;
import org.example.Server.message.requestMessage.*;
import org.example.Server.message.responseMessage.*;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {

    /**
     * 根据消息类型字节，获得对应的消息 class
     * @param messageType 消息类型字节
     * @return 消息 class
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int sequenceId;
    private int messageType;

    public Message() {
        this.sequenceId= SequenceIdUtil.getSequenceId();
    }

    public abstract int getMessageType();

    public static final int LoginRequestEvent = 0;
    public static final int LoginResponseEvent = 1;
    public static final int RegisterRequestEvent = 2;
    public static final int RegisterResponseEvent = 3;
    public static final int LoginStatusRequestMessage=4;
    public static final int LoginStatusResponseMessage=5;

    // 新增好友请求相关消息类型
    public static final int FriendRequestEvent = 6;
    public static final int FriendResponseEvent = 7;
    // 添加好友列表相关消息类型
    public static final int FriendListRequestEvent = 8;
    public static final int FriendListResponseEvent = 9;

    public static final int SingleTextRequestMessage=10;
    public static final int SingleImageRequestMessage = 11;
    public static final int SingleVideoRequestMessage = 12;
    public static final int SingleFileRequestMessage = 13;

    // 添加群组相关消息类型
    public static final int GroupCreateRequestEvent = 20;
    public static final int GroupCreateResponseEvent = 21;
    public static final int GroupListRequestEvent = 22;
    public static final int GroupListResponseEvent = 23;


    public static final int GroupTextMessage = 40;
    public static final int GroupImageMessage = 41;
    public static final int GroupVideoMessage = 42;
    public static final int GroupFileMessage = 43;

    public static final int IdentityVerifyMessage=110;
    //   public static final int IdentityVerifyResponseMessage=111;

    public static final int PingMessage=126;
    public static final int PongMessage=127;

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(LoginRequestEvent, LoginRequestMessage.class);
        messageClasses.put(LoginResponseEvent, LoginRequestResponseMessage.class);
        messageClasses.put(RegisterRequestEvent, RegisterRequestMessage.class);
        messageClasses.put(RegisterResponseEvent, RegisterRequestResponseMessage.class);
        messageClasses.put(LoginStatusRequestMessage, LoginStatusRequestMessage.class);
        messageClasses.put(LoginStatusResponseMessage, LoginStatusResponseMessage.class);
        messageClasses.put(SingleTextRequestMessage, SingleChatTextRequestMessage.class);
        messageClasses.put(SingleImageRequestMessage, SingleChatImageRequestMessage.class);
        messageClasses.put(SingleVideoRequestMessage, SingleChatVideoRequestMessage.class);
        messageClasses.put(SingleFileRequestMessage, SingleChatFileRequestMessage.class);
        messageClasses.put(IdentityVerifyMessage, IdentityVerifyMessage.class);
        messageClasses.put(PingMessage, PingMessage.class);
        messageClasses.put(PongMessage, PongMessage.class);

//        //新增加群组好友列表映射
//        messageClasses.put(GroupCreateRequestEvent, GroupCreateRequestMessage.class);
//        messageClasses.put(GroupCreateResponseEvent, GroupCreateResponseMessage.class);
//        messageClasses.put(GroupListRequestEvent, GroupListRequestMessage.class);
//        messageClasses.put(GroupListResponseEvent, GroupListResponseMessage.class);
//        messageClasses.put(FriendListRequestEvent, FriendRequestMessage.class);
//        messageClasses.put(FriendListResponseEvent, FriendRequestResponseMessage.class);
//        messageClasses.put(FriendRequestEvent, FriendRequestMessage.class);
//        messageClasses.put(FriendResponseEvent, FriendRequestResponseMessage.class);
        // 注册好友相关消息类型
        messageClasses.put(FriendRequestEvent, FriendRequestMessage.class);
        messageClasses.put(FriendResponseEvent, FriendRequestResponseMessage.class);
        messageClasses.put(FriendListRequestEvent, FriendListRequestMessage.class);
        messageClasses.put(FriendListResponseEvent, FriendListResponseMessage.class);

        // 注册群组相关消息类型
        messageClasses.put(GroupCreateRequestEvent, GroupCreateRequestMessage.class);
        messageClasses.put(GroupCreateResponseEvent, GroupCreateResponseMessage.class);
        messageClasses.put(GroupListRequestEvent, GroupListRequestMessage.class);
        messageClasses.put(GroupListResponseEvent, GroupListResponseMessage.class);
    }



}
