package cn.cnm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/18 22:28
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello spring boot...";
    }

    // 跳转到模板页面
    @RequestMapping("/success")
    public String success(Map<String, Object> map) {
        map.put("hello", "<h1>Hello</h1>");
        map.put("users", Arrays.asList("wocao", "nimei", "shazi"));
        return "success";
    }
}
