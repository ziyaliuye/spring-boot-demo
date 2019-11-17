package cn.cnm.config;

import cn.cnm.service.HelloService;
import cn.cnm.service.impl.HelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lele
 * @version 1.0
 * @Description 替代Spring的XMl配置文件， @Bean就相当于之前的一个<bean>标签
 * @Email 414955507@qq.com
 * @date 2019/11/17 21:45
 */
/* 指定当前类是一个配置类， 就是替代之前的Spring配置文件 */
@Configuration
public class myConfig {
    /* 将方法的返回值添加到容器中， 组件ID默认就是方法名 */
    @Bean
    public HelloService helloService() {
        return new HelloServiceImpl();
    }
}
