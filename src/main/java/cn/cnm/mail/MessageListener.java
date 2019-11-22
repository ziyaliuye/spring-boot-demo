package cn.cnm.mail;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author lele
 * @version 1.0
 * @Description 邮件发送监听
 * @Email 414955507@qq.com
 * @date 2019/11/22 23:31
 */
@Component
public class MessageListener {
    private final JavaMailSenderImpl mailSender;

    public MessageListener(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @EventListener
    @Async
    public void simpleMailMessagEvent(SimpleMailMessagEvent event) {
        mailSender.send((SimpleMailMessage) event.getSource());
    }
}