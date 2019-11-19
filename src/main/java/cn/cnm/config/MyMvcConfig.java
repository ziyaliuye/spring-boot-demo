package cn.cnm.config;

import cn.cnm.component.LoginHandlerInterceptor;
import cn.cnm.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    // 注册自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* /**表示拦截任意请求, 理论上SpringBoot已经做好静态资源映射， 不需要额外排除Css等文件， 但测试好像没用 */
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                /*
                 * 排除配置：
                 *   /static/下的XX文件夹下拦截规范可以简化成  "/XX/**"
                 *   index.html、/、/user/login分别是对访问首页和  表单提交不拦截
                 *   /webjars/**是对webjars依赖进来的文件不拦截
                 *   /css/**,/js/**,/img/** 是对static下静态资源不拦截，注意在SprngBoot 2.x.x中是要这样指定的，2.x.x之前不需要
                 */
                .excludePathPatterns("/login.html", "/", "/user/login", "/webjars/**", "/asserts/**");

    }

    // 将自定义的国际化组件添加到容器中
    @Bean
    public LocaleResolver localResolver() {
        return new MyLocaleResolver();
    }
}
