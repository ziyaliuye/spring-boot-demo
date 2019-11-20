package cn.cnm.config;

import cn.cnm.servlet.MyFilter;
import cn.cnm.servlet.MyListener;
import cn.cnm.servlet.MyServlet;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author lele
 * @version 1.0
 * @Description Servlet组件配置类
 * @Email 414955507@qq.com
 * @date 2019/11/20 17:21
 */
@Configuration
public class MyServerConfig {
    // 添加一个 WebServerFactoryCustomizer（嵌入式Servlet容器定制器）组件
    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        // 定制嵌入式的Servlet容器相关规则
        return (WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>) factory -> {
            // 定制端口号
            factory.setPort(8089);
        };
    }

    /* 注册Servlet、Filter、Listener三大组件 */
    // 注册一个Servlet
    @Bean
    public ServletRegistrationBean myServlet() {
        // 第一个参数是注册的Servlet对象， 第二个参数对应的映射路径
        return new ServletRegistrationBean(new MyServlet(), "/myservlet");
    }

    // 注册一个Filter
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        // 设置Filter对象
        filterRegistrationBean.setFilter(new MyFilter());
        // 设置Filter要拦截的路径
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/", "/myservlet"));
        return filterRegistrationBean;
    }

    // 注册一个Listener
    @Bean
    public ServletListenerRegistrationBean myListener() {
        return new ServletListenerRegistrationBean(new MyListener());
    }
}
