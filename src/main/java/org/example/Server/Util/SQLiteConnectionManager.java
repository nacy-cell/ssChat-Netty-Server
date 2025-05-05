package org.example.Server.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SQLiteConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteConnectionManager.class);
    private static final int MAX_CONNECTIONS = 10;
    private static BlockingQueue<Connection> connectionPool;
    private static String DB_URL = "jdbc:sqlite:ssChat.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connectionPool = new ArrayBlockingQueue<>(MAX_CONNECTIONS);
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                Connection conn = createConnection();
                connectionPool.offer(conn);
            }
            logger.info("SQLite连接池初始化完成，共{}个连接", MAX_CONNECTIONS);
        } catch (Exception e) {
            logger.error("初始化SQLite连接池失败", e);
        }
    }

    private static Connection createConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        try (Statement stmt = conn.createStatement()) {
            // 启用WAL模式提高并发性能
            stmt.execute("PRAGMA journal_mode=WAL;");
            stmt.execute("PRAGMA synchronous=NORMAL;");
            stmt.execute("PRAGMA cache_size=10000;");
            stmt.execute("PRAGMA temp_store=MEMORY;");
        }
        return conn;
    }

    public static Connection getConnection() throws SQLException, InterruptedException {
        Connection conn = connectionPool.poll();
        if (conn == null || conn.isClosed()) {
            conn = createConnection();
        }
        return conn;
    }

    public static void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    connectionPool.offer(conn);
                }
            } catch (SQLException e) {
                logger.error("释放连接失败", e);
            }
        }
    }
}