package cn.cnm.controller;

import cn.cnm.mail.MailMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author lele
 * @version 1.0
 * @Description 通过前台触发，监听器兼听后会触发发送邮件代码
 * 复杂邮件写得乱， 不用监听事的发送其实可以写得更简洁
 * @Email 414955507@qq.com
 * @date 2019/11/23 0:14
 */
@RestController
public class SendMail {
    private final MailMessageService mailMessageService;
    private final JavaMailSenderImpl mailSender;

    // 收件人
    @Value("${touser}")
    String touser;
    // 抄送人
    @Value("${copyuser}")
    String copyuser;
    @Value("${spring.mail.username}")
    private String username;

    public SendMail(MailMessageService mailMessageService, JavaMailSenderImpl mailSender) {
        this.mailMessageService = mailMessageService;
        this.mailSender = mailSender;
    }

    // 通过设置监听发送文字邮件
    @RequestMapping("/send1")
    public String sendMail() {
        String[] tousers = touser.split(",");
        // 主题
        String topic = "在吗";
        // 内容
        String text = "吃了吗， 没吃就多吃点...";
        mailMessageService.sendSimple(topic, text, tousers);
        return "简单邮件发送成功...";
    }

    // 直接发送带附件邮件
    @RequestMapping("/send2")
    public String sendFileMail() {
        String[] tousers = touser.split(",");
        // 主题
        String topic = "在吗";
        // 内容
        String text = "<b style='color:red'> 吃了吗， 没吃就多吃点... </b>";
        mailMessageService.sendSimple(topic, text, tousers);
        // 创建复杂消息
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // 第二个参数表示开启HTML格式支持
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(tousers);
            helper.setSubject(topic);
            helper.setText(text);
            // 添加附件， 第一个参数是附件名， 第二个参数就是附件本身
            helper.addAttachment("1.jpg", new File(("D:\\photo\\1.jpg")));
            helper.addAttachment("2.jpg", new File(("D:\\photo\\2.jpg")));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "复杂邮件发送成功...";
    }
}
