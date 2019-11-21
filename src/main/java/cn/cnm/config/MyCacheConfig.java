package cn.cnm.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author lele
 * @version 1.0
 * @Description 缓存配置
 * @Email 414955507@qq.com
 * @date 2019/11/21 12:56
 */
@Configuration
public class MyCacheConfig {
    // 自定义ID生成器， 组件注入容器后就可以直接在注解中使用这个组件
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            // ID生产规则
            return method.getName() + "[" + Collections.singletonList(Arrays.toString(params)) + "]";
        };
    }
}
