package cn.cnm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

/**
 * @author lele
 * @version 1.0
 * @Description 跳转到templates文件夹下的登录页面
 * @Email 414955507@qq.com
 * @date 2019/11/18 22:28
 */
@Controller
public class IndexController {
    // 不管是访问 / 还是 /login.html 都跳转到登录页面
    @RequestMapping({"/", "/login.html"})
    public String success() {
        return "login";
    }
}
