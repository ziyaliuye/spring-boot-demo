package cn.cnm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/23 11:02
 */
@Controller
public class KungfuController {
    private final String PREFIX = "pages/";

    // 欢迎页
    @GetMapping("/security")
    public String index() {
        return "welcome";
    }

    //登陆页
    @GetMapping("/security/login")
    public String loginPage() {
        return PREFIX + "login";
    }

    // level1页面映射

    @GetMapping("security/level1/{path}")
    public String level1(@PathVariable("path") String path) {
        return PREFIX + "level1/" + path;
    }

    // level2页面映射
    @GetMapping("/security/level2/{path}")
    public String level2(@PathVariable("path") String path) {
        return PREFIX + "level2/" + path;
    }

    // level3页面映射
    @GetMapping("/security/level3/{path}")
    public String level3(@PathVariable("path") String path) {
        return PREFIX + "level3/" + path;
    }

    // level3页面映射
    @GetMapping("/security/logout")
    public String logout() {
        return "froward:/login.html";
    }
}
