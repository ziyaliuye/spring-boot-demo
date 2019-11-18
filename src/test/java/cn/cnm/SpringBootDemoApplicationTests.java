package cn.cnm;

import cn.cnm.pojo.Person;
import cn.cnm.service.HelloService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        // debug输出日志
        logger.debug("debug logger message...");
        // info输出日志
        logger.info("info logger message...");
        // rrror输出日志
        logger.error("info logger message...");
    }
}
