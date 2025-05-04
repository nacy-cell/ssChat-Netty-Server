package org.example.Server.Util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.Server.Model.EmailProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MailUtil {
    public static boolean sendMail(String senderNickname, String to, String subject, String content) {
        EmailProperties emailProperties = loadEmailProperties();
        if (emailProperties == null) {
            return false;
        }

        // 配置邮件发送的属性
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", emailProperties.getHost());
        mailProperties.put("mail.smtp.auth", String.valueOf(emailProperties.isAuth()));

        // 使用 TLS 加密连接
        mailProperties.put("mail.smtp.starttls.enable", "true");

        // 创建一个会话对象
        Session session = Session.getInstance(mailProperties, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailProperties.getUser(), emailProperties.getCode());
            }
        });

        try {
            // 创建一个 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人，添加发件人昵称
            message.setFrom(new InternetAddress(emailProperties.getUser(), senderNickname));
            // 设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 设置邮件主题
            message.setSubject(subject);
            // 设置邮件内容
            message.setText(content);

            // 发送邮件
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static EmailProperties loadEmailProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resource/email.properties")) {
            properties.load(fis);
            EmailProperties emailProperties = new EmailProperties();
            emailProperties.setHost(properties.getProperty("mail.smtp.host"));
            emailProperties.setUser(properties.getProperty("mail.user"));
            emailProperties.setCode(properties.getProperty("mail.code"));
            emailProperties.setAuth(Boolean.parseBoolean(properties.getProperty("mail.smtp.auth")));
            return emailProperties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
