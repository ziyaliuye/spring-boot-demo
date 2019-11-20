package cn.cnm;

import cn.cnm.pojo.Person;
import cn.cnm.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}
