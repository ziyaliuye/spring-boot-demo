package cn.cnm.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author lele
 * @version 1.0
 * @Description 简单邮件发送
 * @Email 414955507@qq.com
 * @date 2019/11/22 23:34
 */
@Service
public class MailMessageService {
    @Value("${spring.mail.username}")
    private String username;
    private ApplicationContext applicationContext;

    public MailMessageService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 发送简单邮件
     *
     * @param subject 主题
     * @param text    内容
     * @param to      收件人
     */
    public void sendSimple(String subject, String text, String... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        applicationContext.publishEvent(new SimpleMailMessagEvent(message));
    }
}
