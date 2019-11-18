package cn.cnm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/18 22:28
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello spring boot...";
    }
}
