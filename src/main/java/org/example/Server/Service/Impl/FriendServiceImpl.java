package org.example.Server.Service.Impl;

import org.example.Server.Service.FriendService;
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

public class FriendServiceImpl implements FriendService {
    private static final Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

    @Override
    public boolean addFriendRequest(String userId, String friendId) {
        String sql = "INSERT OR IGNORE INTO friend_relation (user_id, friend_id, relation_status) VALUES (?, ?, 0)";

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, friendId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error("添加好友请求失败", e);
            return false;
        }
    }

    @Override
    public boolean acceptFriendRequest(String userId, String friendId) {
        Connection conn = null;
        try {
            conn = SQLiteConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // 更新请求状态
            String updateSql = "UPDATE friend_relation SET relation_status = 1, " +
                    "update_time = datetime('now','localtime') " +
                    "WHERE user_id = ? AND friend_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setString(1, friendId);
                ps.setString(2, userId);
                ps.executeUpdate();
            }

            // 添加反向好友关系
            String insertSql = "INSERT OR IGNORE INTO friend_relation (user_id, friend_id, relation_status) VALUES (?, ?, 1)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, userId);
                ps.setString(2, friendId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    logger.error("回滚事务失败", ex);
                }
            }
            logger.error("接受好友请求失败", e);
            return false;
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

    @Override
    public boolean rejectFriendRequest(String userId, String friendId) {
        String sql = "UPDATE friend_relation SET relation_status = 2, " +
                "update_time = datetime('now','localtime') " +
                "WHERE user_id = ? AND friend_id = ?";

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, friendId);
            ps.setString(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error("拒绝好友请求失败", e);
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getFriendRequests(String userId) {
        String sql = "SELECT fr.*, u.username, u.nickname FROM friend_relation fr " +
                "JOIN user u ON fr.user_id = u.user_id " +
                "WHERE fr.friend_id = ? AND fr.relation_status = 0";

        List<Map<String, Object>> requests = new ArrayList<>();

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> request = new HashMap<>();
                    request.put("userId", rs.getString("user_id"));
                    request.put("username", rs.getString("username"));
                    request.put("nickname", rs.getString("nickname"));
                    request.put("createTime", rs.getString("create_time"));
                    requests.add(request);
                }
            }
        } catch (Exception e) {
            logger.error("获取好友请求失败", e);
        }

        return requests;
    }

    @Override
    public List<Map<String, Object>> getFriendList(String userId) {
        String sql = "SELECT fr.*, u.username, u.nickname, u.avatar FROM friend_relation fr " +
                "JOIN user u ON fr.friend_id = u.user_id " +
                "WHERE fr.user_id = ? AND fr.relation_status = 1";

        List<Map<String, Object>> friends = new ArrayList<>();

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> friend = new HashMap<>();
                    friend.put("friendId", rs.getString("friend_id"));
                    friend.put("username", rs.getString("username"));
                    friend.put("nickname", rs.getString("nickname"));
                    friend.put("avatar", rs.getString("avatar"));
                    friends.add(friend);
                }
            }
        } catch (Exception e) {
            logger.error("获取好友列表失败", e);
        }

        return friends;
    }
}
