package org.example.Server.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface GroupMapper {
    /**
     * 创建群聊
     * @param groupName 群聊名称
     * @param creatorId 创建者 ID
     * @return 受影响的行数
     */
    @Insert("INSERT INTO groups (group_name, creator_id) VALUES (#{groupName}, #{creatorId})")
    public int createGroup(@Param("groupName") String groupName, @Param("creatorId") Integer creatorId);

    /**
     * 加入群成员
     * @param groupName 群聊名称
     * @param userId 用户 ID
     * @return 受影响的行数
     */
    @Insert("INSERT INTO group_members (group_name, user_id) VALUES (#{groupName}, #{userId})")
    public int joinMember(@Param("groupName") String groupName, @Param("userId") Integer userId);

    /**
     * 移除群成员
     * @param groupName 群聊名称
     * @param userId 用户 ID
     * @return 受影响的行数
     */
    @Delete("DELETE FROM group_members WHERE group_name = #{groupName} AND user_id = #{userId}")
    public int removeMember(@Param("groupName") String groupName, @Param("userId") Integer userId);

    /**
     * 移除群聊
     * @param groupName 群聊名称
     * @return 受影响的行数
     */
    @Delete("DELETE FROM groups WHERE group_name = #{groupName}")
    public int removeGroup(@Param("groupName") String groupName);

    /**
     * 获取群成员
     * @param groupName 群聊名称
     * @return 群成员 ID 集合
     */
    @Select("SELECT user_id FROM group_members WHERE group_name = #{groupName}")
    public Set<Integer> getMembers(@Param("groupName") String groupName);

    /**
     * 获取所有群聊
     * @return 群聊列表
     */
    @Select("SELECT group_name FROM groups")
    public List<String> getAllGroups();
}

