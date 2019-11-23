package cn.cnm.dubbo.user;

import cn.cnm.dubbo.service.TicketService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @author lele
 * @version 1.0
 * @Description 消费者自己的业务逻辑, 逻辑中需要调用远程服务
 * @Email 414955507@qq.com
 * @date 2019/11/23 17:07
 */
@Service
public class UserService {
    // Dubbo的注解， 可以从注册中心获取服务信息并获取服务的代理对象
    @Reference
    TicketService ticketService;

    public void hello() {
        String ticket = ticketService.getTicket();
        System.out.println("买到票了：" + ticket);
    }
}
