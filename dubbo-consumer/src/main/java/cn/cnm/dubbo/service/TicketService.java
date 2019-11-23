package cn.cnm.dubbo.service;

/**
 * @author lele
 * @version 1.0
 * @Description 消费者调用远程服务接口（服务接口名必须和提供者服务名一样）， 并且没有任何注解
 * @Email 414955507@qq.com
 * @date 2019/11/23 17:05
 */
public interface TicketService {
    String getTicket();
}
