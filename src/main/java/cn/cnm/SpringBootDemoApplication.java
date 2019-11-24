package cn.cnm;

import cn.cnm.component.DowntimeShutdownUndertowWrapper;
import cn.cnm.pojo.Person;
import io.undertow.UndertowOptions;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
/* 新版SpringBoot ConfigurationProperties注解会报错， 加上这个就好了， 暂时不管为什么 */
@EnableConfigurationProperties(Person.class)
// 开启缓存注解
@EnableCaching
// 开启RabbitMQ功能
@EnableRabbit
// 开启异步任务功能
@EnableAsync
// 开启定时任务功能, 集成Quartz后不需要开启
// @EnableScheduling
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    /*
     * Undertow优雅停服
     * Http停机请求参考：curl -X POST -u root:root http://localhost:8080/actuator/shutdown
     * linux命令：kill -2 pid
     * （不能使用 kill -9 pid）
     */
    private final DowntimeShutdownUndertowWrapper gracefulShutdownUndertowWrapper;

    public SpringBootDemoApplication(DowntimeShutdownUndertowWrapper gracefulShutdownUndertowWrapper) {
        this.gracefulShutdownUndertowWrapper = gracefulShutdownUndertowWrapper;
    }

    @Bean
    public UndertowServletWebServerFactory servletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addDeploymentInfoCustomizers(deploymentInfo -> deploymentInfo.addOuterHandlerChainWrapper(gracefulShutdownUndertowWrapper));
        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_STATISTICS, true));
        return factory;
    }
}
