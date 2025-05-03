package org.example.Server.Service.Impl;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Server.Dao.UserMapper;
import org.example.Server.Model.User;
import org.example.Server.Service.RegistService;

import java.io.IOException;
import java.io.InputStream;

public class RegistServiceImpl implements RegistService {
    private SqlSessionFactory sqlSessionFactory;

    public RegistServiceImpl() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean regist(Integer username, String password) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            // 检查用户名是否已存在
            User existingUser = userMapper.getUserByUsername(username);
            if (existingUser != null) {
                return false; // 用户名已存在，注册失败
            }

            // 创建新用户对象
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);

            // 插入新用户到数据库
            int result = userMapper.insertUser(newUser);
            session.commit(); // 提交事务
            return result > 0; // 根据插入结果判断注册是否成功
        }
    }
}
