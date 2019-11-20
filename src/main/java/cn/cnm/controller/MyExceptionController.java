package cn.cnm.controller;

import cn.cnm.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lele
 * @version 1.0
 * @Description 抛出异常， 仅供测试
 * @Email 414955507@qq.com
 * @date 2019/11/18 22:28
 */
@Controller
public class MyExceptionController {
    // 抛出一个异常
    @RequestMapping("/existence")
    public String error(String username) {
        if (username == null || "".equals(username)) {
            throw new UserNotExistException();
        }
        return "redirect:login.html";
    }
}
