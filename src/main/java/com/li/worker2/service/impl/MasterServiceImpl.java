package com.li.worker2.service.impl;

import com.li.worker2.entity.Master;
import com.li.worker2.entity.User;
import com.li.worker2.mapper.MasterMapper;
import com.li.worker2.mapper.UserMapper;
import com.li.worker2.service.MasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author li
 * @since 2021-12-31
 */
@Service
public class MasterServiceImpl extends ServiceImpl<MasterMapper, Master> implements MasterService {
    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private UserMapper userMapper;

    private Properties props;
    private Session session;

    @Override
    public String iniMail() {
        try {
            Master master = masterMapper.selectOne(null);
            props = new Properties();
            props.setProperty("mail.host", master.getMailHost());
            props.setProperty("mail.smtp.auth", master.getMailAuth());
            props.setProperty("mailServer", master.getMailServer());
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(master.getMailServer(), master.getMailPassword());
                }
            };
            session = Session.getDefaultInstance(props, authenticator);
            return "邮箱系统初始化成功";
        } catch (Exception e) {
            return "邮箱系统初始化失败";
        }
    }

    @Override
    public String sendMail(String email, String subject, String emailMsg) {
        if (session == null) {
            iniMail();
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("mailServer")));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setContent(emailMsg, "text/html;charset=utf-8");
            Transport.send(message);
            System.out.println(email + "  邮件已发送");
            return "邮件发送成功";
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("邮箱发送出错");
            return "邮箱发送出错";
        }
    }

    @Override
    public String getAllUser() {
        List<User> users = userMapper.selectList(null);
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user).append("\n");
        }
        return sb.toString();
    }
}
