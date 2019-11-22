package cn.cnm.controller;

import cn.cnm.service.impl.SyncServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lele
 * @version 1.0
 * @Description 异步调用， 自己编写线程方法太麻烦， 而且风险很高
 * 只需要在方法上注解@Async, 然后在启动类上开启@EnableAsync注解即可生效
 * 线程交由Spring 来处理， 会新启动一个线程池来管理这些线程， 避免自己不使用线程池方式启动很多线程导致一部分线程挂掉的情况
 * <p>
 * === 这里特喵的有个坑， 注解在ServiceImpl上，注意它是没有实现哪个接口的， 实现了就会报bean异常循环获取异常， 因为 @Async 的机制和常规的获取Bean还不一样 ===
 * @Email 414955507@qq.com
 * @date 2019/11/22 17:19
 */
@RestController
public class AsycController {
    @Autowired
    SyncServiceImpl syncService;

    // 正常的同步方式
    @RequestMapping("/synchronization")
    public String synchronization() {
        syncService.synchronization();
        return "this is synchronization controller...";
    }

    /* 异步方式 */
    @RequestMapping("/asynchronous")
    public String asynchronous() {
        syncService.asynchronous();
        return "this is asynchronous controller...";
    }
}
