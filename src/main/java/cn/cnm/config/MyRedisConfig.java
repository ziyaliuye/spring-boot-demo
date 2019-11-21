package cn.cnm.config;

import cn.cnm.pojo.Flower;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.Map;

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
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Map.class);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    /*
     * 自定义 CacheManagerCustomizers  来定制缓存策略， 注意SpringBoot2.x和1.x写法有差别, 直接复制的， 不要管为什么
     *  当有自定义的该组件后就会替换SpringBoot默认的策略
     *  SpringBoot本身就设置了如果没有自定义策略就是用默认的， 我们只需要将组件添加到容器中即可生效
     *
     *  注意：这里指定了泛型， 只针对其中一个实体类进行Json转换， 其他实体类使用这个会出现只能存异常数据无法取的情况， 那就需要定义多个CacheManager
     *    定义多个后需要指定一个默认的CacheManager， 建议还是使用默认的， 使用 @Primary 注解就表示默认的CacheManager
     */
    // @Primary
    @Bean
    public CacheManager flowerCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Flower> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Flower.class);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(100))  // 设置缓存有效期100s
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
