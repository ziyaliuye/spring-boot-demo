package cn.cnm.mail;

import org.springframework.context.ApplicationEvent;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author lele
 * @version 1.0
 * @Description 自定义简单邮件发送事件
 * @Email 414955507@qq.com
 * @date 2019/11/22 23:39
 */
class SimpleMailMessagEvent extends ApplicationEvent {
    private static final long serialVersionUID = -3089877970822063520L;

    SimpleMailMessagEvent(SimpleMailMessage message) {
        super(message);
    }
}
