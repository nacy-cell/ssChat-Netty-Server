package org.example.Server.Dao;

import org.apache.ibatis.annotations.*;
import org.example.Server.Model.User;
import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users")
    public List<User> getAllUsers();

    @Select("SELECT * FROM users WHERE username = #{username}")
    public User getUserByUsername(Integer username);

    @Insert("INSERT INTO users (username, password) VALUES (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int insertUser(User user);
}
