package cn.cnm;

import cn.cnm.pojo.Person;
import cn.cnm.service.HelloService;
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
     void testBean(){
        System.out.println(helloService);
    }
}
