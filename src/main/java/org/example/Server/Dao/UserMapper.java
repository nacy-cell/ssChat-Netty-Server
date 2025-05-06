package org.example.Server.Dao;

import org.apache.ibatis.annotations.*;
import org.example.Server.Model.User;
import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM user")
    public List<User> getAllUsers();

    @Select("SELECT * FROM user WHERE UserID = #{username}")
    public User getUserByUsername(Integer username);

    @Insert("INSERT INTO user (UserID, email, password) VALUES (#{userID},#{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int insertUser(User user);
}
