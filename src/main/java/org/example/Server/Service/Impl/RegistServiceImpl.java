package org.example.Server.Service.Impl;
import java.util.Random;
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
    public Integer regist(String username, String password) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 创建 Random 对象
            Random random = new Random();
            // 生成 8 位随机数字
            Integer userID = random.nextInt(90000000) + 10000000;

            UserMapper userMapper = session.getMapper(UserMapper.class);

            User existingUser = userMapper.getUserByUsername(userID);
            while(existingUser != null){
                userID = random.nextInt(90000000) + 10000000;
                existingUser = userMapper.getUserByUsername(userID);
            }
            // 创建新用户对象
            User newUser = new User();
            newUser.setUserID(userID);
            newUser.setUsername(username);
            newUser.setPassword(password);

            // 插入新用户到数据库
            int result = userMapper.insertUser(newUser);
            session.commit(); // 提交事务
            return userID; // 根据插入结果判断注册是否成功
        }
    }
}
