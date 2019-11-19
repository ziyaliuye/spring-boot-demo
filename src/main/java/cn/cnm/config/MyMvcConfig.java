package cn.cnm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lele
 * @version 1.0
 * @Description 使用这个配置可以拓展SpringMVC的功能
 * @Email 414955507@qq.com
 * @date 2019/11/19 19:54
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    // 添加视图映射规则
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器发起caca请求也会跳转到success页面， 无需在写一个Controller
        registry.addViewController("/caca").setViewName("success");
    }
}
