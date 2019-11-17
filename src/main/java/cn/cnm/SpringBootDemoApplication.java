package cn.cnm;

import cn.cnm.pojo.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
/* 新版SpringBoot ConfigurationProperties注解会报错， 加上这个就好了， 暂时不管为什么 */
@EnableConfigurationProperties(Person.class)
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
