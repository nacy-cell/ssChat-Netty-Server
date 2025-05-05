-- 好友关系表
CREATE TABLE IF NOT EXISTS friend_relation (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id TEXT NOT NULL,
    friend_id TEXT NOT NULL,
    relation_status INTEGER NOT NULL, -- 0:请求中 1:已接受 2:已拒绝 3:已删除
    create_time TEXT DEFAULT (datetime('now','localtime')),
    update_time TEXT DEFAULT (datetime('now','localtime')),
    UNIQUE(user_id, friend_id)
    );

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_friend_relation_user_id ON friend_relation(user_id);
CREATE INDEX IF NOT EXISTS idx_friend_relation_friend_id ON friend_relation(friend_id);

-- 群组信息表
CREATE TABLE IF NOT EXISTS chat_group (
    group_id TEXT PRIMARY KEY,
    group_name TEXT NOT NULL,
    group_avatar TEXT,
    owner_id TEXT NOT NULL,
    create_time TEXT DEFAULT (datetime('now','localtime')),
    update_time TEXT DEFAULT (datetime('now','localtime'))
    );

-- 群组成员表
CREATE TABLE IF NOT EXISTS group_member (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    group_id TEXT NOT NULL,
    member_id TEXT NOT NULL,
    member_nickname TEXT,
    member_role INTEGER NOT NULL, -- 0:普通成员 1:管理员 2:群主
    join_time TEXT DEFAULT (datetime('now','localtime')),
    UNIQUE(group_id, member_id)
    );

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_group_member_group_id ON group_member(group_id);
CREATE INDEX IF NOT EXISTS idx_group_member_member_id ON group_member(member_id);