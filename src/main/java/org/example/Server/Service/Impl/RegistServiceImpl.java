package org.example.Server.Service.Impl;
import java.util.Random;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Server.Dao.UserMapper;
import org.example.Server.Model.User;
import org.example.Server.Service.RegistService;
import org.example.Server.Util.RedisUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;

public class RegistServiceImpl implements RegistService {
    private SqlSessionFactory sqlSessionFactory;

    private static final int CAPTCHA_LENGTH = 6;
    private static final int CAPTCHA_EXPIRE_TIME = 60 * 5; // 5 minutes

    public String generateCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            captcha.append(random.nextInt(10));
        }
        return captcha.toString();
    }

    public void saveCaptchaToRedis(String clientId, String captcha) {
        Jedis jedis = RedisUtil.getJedis();
        try {
            jedis.setex(clientId, CAPTCHA_EXPIRE_TIME, captcha);
        } finally {
            RedisUtil.closeJedis(jedis);
        }
    }

    public boolean verifyCaptcha(String clientId, String inputCaptcha) {
        Jedis jedis = RedisUtil.getJedis();
        try {
            String storedCaptcha = jedis.get(clientId);
            System.out.println("storedCaptcha: " + storedCaptcha);
            if (storedCaptcha != null && storedCaptcha.equals(inputCaptcha)) {
                return true;
            }
        } finally {
            RedisUtil.closeJedis(jedis);
        }
        return false;
    }

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
    public Integer regist(String code,String email, String password) {
        if(verifyCaptcha(email,code)){
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
                newUser.setEmail(email);
                newUser.setPassword(password);

                // 插入新用户到数据库
                int result = userMapper.insertUser(newUser);
                session.commit(); // 提交事务
                return userID; // 根据插入结果判断注册是否成功
            }
        }else {
            return -1;
        }
    }

    @Override
    public String Code(String email) {
        System.out.println("已发送验证码");
        String captcha = generateCaptcha();
        saveCaptchaToRedis(email, captcha);
        return captcha;
    }
}
