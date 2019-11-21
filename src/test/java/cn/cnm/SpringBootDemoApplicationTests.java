package cn.cnm;

import cn.cnm.pojo.Person;
import cn.cnm.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringBootDemoApplicationTests {
    @Autowired
    Person person;
    @Autowired
    HelloService helloService;

    @Test
    void contextLoads() {
        System.out.println(person);
    }

    @Test
    void testBean() {
        System.out.println(helloService);
    }

    /* 日志系统使用测试 */
    private Logger logger = LogManager.getLogger();

    @Test
    void logTest() {
        /* 日志的级别由低到高 */
        // trace输出日志
        logger.trace("trace logger message...");
        // debug输出日志
        logger.debug("debug logger message...");
        // info输出日志
        logger.info("info logger message...");
        // rrror输出日志
        logger.error("info logger message...");
    }

    /* 注入数据源即可使用 */
    @Autowired
    DataSource dataSource;
    /* SpringBoot提供的JdbcTemplate， 简化代码 */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testJDBC() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from flower");
        System.out.println(list);
    }

    /* redis操作示例 */
    // redis操作模板类， key value形式
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    // 因为redis String操作比较频繁， 所以Spring单独抽出一个String的模版类
            StringRedisTemplate stringRedisTemplate;
    /* 名字都叫RedisTemplate， 根据泛型来区别使用哪个模板类 */
    @Autowired
    RedisTemplate<Object, Map<String, Object>> mapRedisTemplate;

    @Test
    void redisTest() {
        /*  操作方法：opsXxxx().xxx  ops是指操作的数据对应的类型， 第二个xxx才是操作的方式 */
        // 将String类型的msg追加"redis.."
        stringRedisTemplate.opsForValue().append("msg", "redis..");
        System.out.println("msg：" + stringRedisTemplate.opsForValue().get("msg"));
        stringRedisTemplate.opsForList().leftPush("mylist", "7");
        stringRedisTemplate.opsForList().leftPop("mylist");
        System.out.println("mylist" + stringRedisTemplate.opsForList().range("mylist", 0, 6));

        // 保存一个对象（注意对象以及对象的属性对象都需要实现序列化）， 通常保存为json串， 不需要实现序列户接口
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from flower");
        for (Map<String, Object> m : list) {
            mapRedisTemplate.opsForValue().set("flower" + m.get("id"), m);
        }
    }

    // @Qualifier指定获取容器指定ID的Bean
    @Qualifier("flowerCacheManager")
    @Autowired
    RedisCacheManager flowerCacheManager;

    @Test
    public void cacheManagerTest() {
        // 直接拉取指定缓存进行操作, 不存在就会新建
        Cache flower = flowerCacheManager.getCache("flower");
        // 相当于新建了一个key-value
        flower.put("heihei", "wocao");
        System.out.println(flower.toString());
    }
}
