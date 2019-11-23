package cn.cnm;

import cn.cnm.dubbo.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DubboConsumerApplicationTests {
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        userService.hello();
    }

}
