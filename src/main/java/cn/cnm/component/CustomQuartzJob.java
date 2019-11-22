package cn.cnm.component;

import cn.cnm.component.RabbitMqFastJsonConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/22 20:51
 */
public class CustomQuartzJob extends QuartzJobBean {
    // 日志对象实例
    private static final Logger logger = LogManager.getLogger(RabbitMqFastJsonConverter.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        logger.info("执行定时任务...");
    }
}
