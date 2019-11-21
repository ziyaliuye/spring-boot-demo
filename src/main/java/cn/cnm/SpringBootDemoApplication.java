package cn.cnm;

import cn.cnm.pojo.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
/* 新版SpringBoot ConfigurationProperties注解会报错， 加上这个就好了， 暂时不管为什么 */
@EnableConfigurationProperties(Person.class)
// 开启缓存注解
@EnableCaching
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
