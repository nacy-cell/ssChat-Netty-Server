package org.example.Server.Session;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

public interface GroupSession {
    /**
     * 创建一个聊天组, 如果不存在才能创建成功
     * @param name 组名
     * @param members 成员
     * @return 成功时返回 null , 失败返回 原来的value
     */
    int createGroup(Integer creator,String name, Set<Integer> members);

    /**
     * 加入聊天组
     * @param name 组名
     * @param member 成员名
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    int joinMember(String name, Integer member);

    /**
     * 移除组成员
     * @param name 组名
     * @param member 成员名
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    int removeMember(String name, Integer member);

    /**
     * 移除聊天组
     * @param name 组名
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    int removeGroup(String name);

    /**
     * 获取组成员
     * @param name 组名
     * @return 成员集合, 没有成员会返回 empty set
     */
    Set<Integer> getMembers(String name);

    /**
     * 获取组成员的 channel 集合, 只有在线的 channel 才会返回
     * @param name 组名
     * @return 成员 channel 集合
     */
    List<Channel> getMembersChannel(String name);
}
