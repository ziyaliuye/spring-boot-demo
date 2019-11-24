package cn.cnm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lele
 * @version 1.0
 * @Description 前台访问请求睡眠一段时间模拟任务
 * @Email 414955507@qq.com
 * @date 2019/11/24 0:53
 */
@RestController
public class DowntimeTestController {
    @RequestMapping("/hello1")
    public String test1() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success1....";
    }

    @RequestMapping("/hello2")
    public String test2() {
        return "success2....";
    }
}
