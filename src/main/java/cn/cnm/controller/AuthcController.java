package cn.cnm.controller;

import cn.cnm.component.RabbitMqFastJsonConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author lele
 * @version 1.0
 * @Description  通过URL授权
 * @Email 414955507@qq.com
 * @date 2019/11/23 14:45
 */
@Controller
public class AuthcController {
    // 日志对象实例
    private static final Logger logger = LogManager.getLogger(RabbitMqFastJsonConverter.class);

    @RequestMapping("/watch")
    public Object watch(Principal principal) {
        String str = Thread.currentThread().getStackTrace()[1].getMethodName() + " " + System.currentTimeMillis();
        logger.info("{} {}", principal.getName(), str);
        return str;
    }

    @RequestMapping("/speak")
    public Object speak(Principal principal) {
        String str = Thread.currentThread().getStackTrace()[1].getMethodName() + " " + System.currentTimeMillis();
        logger.info("{} {}", principal.getName(), str);
        return str;
    }

    @RequestMapping("/walk")
    public Object walk(Principal principal) {
        String str = Thread.currentThread().getStackTrace()[1].getMethodName() + " " + System.currentTimeMillis();
        logger.info("{} {}", principal.getName(), str);
        return str;
    }
}
