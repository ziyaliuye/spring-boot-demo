package cn.cnm.utils;

import cn.cnm.component.RabbitMqFastJsonConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author lele
 * @version 1.0
 * @Description 定时测试类
 * @Email 414955507@qq.com
 * @date 2019/11/22 20:40
 */
public class TestQuartz {
    // 日志对象实例
    private static final Logger logger = LogManager.getLogger(RabbitMqFastJsonConverter.class);

    // 测试定时任务构建
    @Bean
    public JobDetail testTaskJobDetail() {
        return JobBuilder.newJob(TestTask.class)
                .withIdentity(TestTask.class.getName())
                .storeDurably(true)
                .build();
    }

    // 测试定时任务配置
    @Bean
    public Trigger testTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(testTaskJobDetail())
                .withIdentity(TestTask.class.getName())
                .withSchedule(scheduleBuilder)
                .build();
    }

    // 执行定时任务
    /* @DisallowConcurrentExecution注解指定其在分布式环境下不可并发执行 */
    @DisallowConcurrentExecution
    private static class TestTask extends QuartzJobBean {
        @Override
        protected void executeInternal(JobExecutionContext jobExecutionContext) {
            logger.debug("执行测试定时任务");
        }
    }
}
