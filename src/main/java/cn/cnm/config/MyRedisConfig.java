package cn.cnm.config;

import cn.cnm.pojo.Flower;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Map;
import java.util.concurrent.Flow;

/**
 * @author lele
 * @version 1.0
 * @Description redis配置类
 * @Email 414955507@qq.com
 * @date 2019/11/21 16:17
 */
@Configuration
public class MyRedisConfig {
    // 调用这个组件可以返回一个自定义的redis模板操作对象
    @Bean
    public RedisTemplate flowerRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 编写自定义的处理， 将对象处理为json串
        Jackson2JsonRedisSerializer<Flower> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Flower.class);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate mapRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 编写自定义的处理， 将对象处理为json串
        Jackson2JsonRedisSerializer<Map<String, Object>> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Map.class);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
