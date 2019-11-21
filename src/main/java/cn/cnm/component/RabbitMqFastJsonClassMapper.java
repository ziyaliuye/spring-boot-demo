package cn.cnm.component;

import org.springframework.amqp.support.converter.DefaultClassMapper;

/**
 * @author lele
 * @version 1.0
 * @Description fastjson 转换映射
 * 解决默认仅信任java.util、java.lang两个package的问题
 * @Email 414955507@qq.com
 * @date 2019/11/21 22:50
 */
class RabbitMqFastJsonClassMapper extends DefaultClassMapper {
    /* 改为信任所有的包 */
    RabbitMqFastJsonClassMapper() {
        super();
        setTrustedPackages("*");
    }
}
