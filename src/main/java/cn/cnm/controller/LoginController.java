package cn.cnm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author lele
 * @version 1.0
 * @Description 跳转到templates文件夹下的登录页面
 * @Email 414955507@qq.com
 * @date 2019/11/18 22:28
 */
@Controller
public class LoginController {
    // 不管是访问 / 还是 /login.html 都跳转到登录页面
    @RequestMapping({"/", "/login.html"})
    public String success() {
        return "login";
    }

    /* 定义为Post请求， 但配置太长， 新特性@PostMapping等价于下面的注解 */
    @PostMapping("/user/login")
    // @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    // 同理还有@DeleteMapping、@PutMapping、@GetMapping
    // @RequestParam("username") ：如果username参数为空则报错
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String, Object> map,
                        HttpSession session) {
        // 假定账户为user密码123456
        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
            // 登录成功
            /* 将登录状态存入Session中 */
            session.setAttribute("loginUser", username);
            return "dashboard";
        } else {
            // 登录失败
            map.put("msg", "用户名或密码错误...");
            return "login";
        }
    }
}
