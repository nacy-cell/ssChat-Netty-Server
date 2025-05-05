package org.example.Server.Service.Impl;

import org.example.Server.Service.GroupService;
import org.example.Server.Util.SQLiteConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Override
    public String createGroup(String groupName, String ownerId, String groupAvatar) {
        String groupId = "group-" + UUID.randomUUID().toString().substring(0, 8);
        Connection conn = null;

        try {
            conn = SQLiteConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // 创建群组
            String createSql = "INSERT INTO chat_group (group_id, group_name, group_avatar, owner_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(createSql)) {
                ps.setString(1, groupId);
                ps.setString(2, groupName);
                ps.setString(3, groupAvatar);
                ps.setString(4, ownerId);
                ps.executeUpdate();
            }

            // 添加群主作为成员
            String memberSql = "INSERT INTO group_member (group_id, member_id, member_nickname, member_role) VALUES (?, ?, ?, 2)";
            try (PreparedStatement ps = conn.prepareStatement(memberSql)) {
                ps.setString(1, groupId);
                ps.setString(2, ownerId);
                ps.setString(3, getUserNickname(ownerId));
                ps.executeUpdate();
            }

            conn.commit();
            return groupId;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    logger.error("回滚事务失败", ex);
                }
            }
            logger.error("创建群组失败", e);
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    logger.error("关闭连接失败", e);
                }
            }
        }
    }

    private String getUserNickname(String userId) {
        String sql = "SELECT nickname FROM user WHERE user_id = ?";

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nickname");
                }
            }
        } catch (Exception e) {
            logger.error("获取用户昵称失败", e);
        }

        return "用户-" + userId;
    }

    @Override
    public boolean addGroupMember(String groupId, String memberId, String nickname, int role) {
        String sql = "INSERT OR IGNORE INTO group_member (group_id, member_id, member_nickname, member_role) VALUES (?, ?, ?, ?)";

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupId);
            ps.setString(2, memberId);
            ps.setString(3, nickname != null ? nickname : getUserNickname(memberId));
            ps.setInt(4, role);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error("添加群成员失败", e);
            return false;
        }
    }

    @Override
    public boolean removeGroupMember(String groupId, String memberId) {
        String sql = "DELETE FROM group_member WHERE group_id = ? AND member_id = ?";

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupId);
            ps.setString(2, memberId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error("移除群成员失败", e);
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getGroupMembers(String groupId) {
        String sql = "SELECT gm.*, u.username, u.avatar FROM group_member gm " +
                "JOIN user u ON gm.member_id = u.user_id " +
                "WHERE gm.group_id = ?";

        List<Map<String, Object>> members = new ArrayList<>();

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> member = new HashMap<>();
                    member.put("memberId", rs.getString("member_id"));
                    member.put("memberNickname", rs.getString("member_nickname"));
                    member.put("username", rs.getString("username"));
                    member.put("avatar", rs.getString("avatar"));
                    member.put("role", rs.getInt("member_role"));
                    members.add(member);
                }
            }
        } catch (Exception e) {
            logger.error("获取群成员列表失败", e);
        }

        return members;
    }

    @Override
    public List<Map<String, Object>> getUserGroups(String userId) {
        String sql = "SELECT g.* FROM chat_group g " +
                "JOIN group_member gm ON g.group_id = gm.group_id " +
                "WHERE gm.member_id = ?";

        List<Map<String, Object>> groups = new ArrayList<>();

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> group = new HashMap<>();
                    group.put("groupId", rs.getString("group_id"));
                    group.put("groupName", rs.getString("group_name"));
                    group.put("groupAvatar", rs.getString("group_avatar"));
                    group.put("ownerId", rs.getString("owner_id"));
                    groups.add(group);
                }
            }
        } catch (Exception e) {
            logger.error("获取用户群组列表失败", e);
        }

        return groups;
    }

    @Override
    public boolean isGroupMember(String groupId, String userId) {
        String sql = "SELECT 1 FROM group_member WHERE group_id = ? AND member_id = ?";

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, groupId);
            ps.setString(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            logger.error("检查群成员失败", e);
            return false;
        }
    }
}
