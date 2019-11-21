package cn.cnm.config;

import cn.cnm.component.RabbitMqFastJsonConverter;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lele
 * @version 1.0
 * @Description RabbitMQ配置类
 * @Email 414955507@qq.com
 * @date 2019/11/21 22:09
 */
@Configuration
public class MyAmqpConfig {

    // 配置消息队列模版， 并且设置MessageConverter为自定义FastJson转换器
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new RabbitMqFastJsonConverter());
        return template;
    }

    // 自定义队列容器工厂， 并且设置MessageConverter为自定义FastJson转换器
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new RabbitMqFastJsonConverter());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}
