package cn.cnm.listener;

import cn.cnm.pojo.Flower;
import org.springframework.amqp.core.Message;

/**
 * @author lele
 * @version 1.0
 * @Description RabbitMQ监听器
 * @Email 414955507@qq.com
 * @date 2019/11/22 0:25
 */
// @Component
public class MqListener {
    /* 消息监听， queues属性对应一个数组， 可以监听多个队列 */
    // @RabbitListener(queues = "cn.news")
    public void receive(Flower flower) {
        // 队列的消息会自动转换
        System.out.println("接收到消息：" + flower);
    }

    /* 还可以获取消息实例， 从中获取消息头信息 */
    // @RabbitListener(queues = "cnm.emps")
    public void receiveMessage(Message message) {
        if (message != null) {
            System.out.println("消息头信息：：" + message.getMessageProperties());
            System.out.println("消息体信息：" + message);
        }
    }
}
