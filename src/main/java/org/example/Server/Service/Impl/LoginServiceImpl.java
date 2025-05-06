package org.example.Server.Service.Impl;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Server.Dao.UserMapper;
import org.example.Server.Model.User;
import org.example.Server.Service.LoginService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginServiceImpl implements LoginService {
    private SqlSessionFactory sqlSessionFactory;
    public LoginServiceImpl()
    {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(Integer userId, String password) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User existingUser = userMapper.getUserByUsername(userId);
            if (existingUser != null && existingUser.getPassword().equals(password)) {
               // userMapper.updateLoginTime(userId);
                return true;
            }else{
                return false;
            }
        }
    }
}
