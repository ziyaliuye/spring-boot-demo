package cn.cnm.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author lele
 * @version 1.0
 * @Description 服务提供者编写的服务接口实现类（特喵的无解）
 * @Email 414955507@qq.com
 * @date 2019/11/23 16:55
 */
/* @Service注意是Dubbo的注解， 将服务发布出去 */
@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "吖屎啊， 雷...";
    }
}
