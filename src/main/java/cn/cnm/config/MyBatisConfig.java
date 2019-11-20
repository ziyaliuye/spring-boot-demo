package cn.cnm.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lele
 * @version 1.0
 * @Description MyBatis配置类
 * @Email 414955507@qq.com
 * @date 2019/11/20 23:02
 */
@Configuration
public class MyBatisConfig {
    // ConfigurationCustomizer是MyBatis提供整合SpringBoot配置类
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 开启驼峰命名规则
            configuration.setMapUnderscoreToCamelCase(true);
        };
    }
}
