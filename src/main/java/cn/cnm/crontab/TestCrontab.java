package cn.cnm.crontab;

import cn.cnm.component.CustomQuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/22 20:48
 */
public class TestCrontab {
    @Autowired
    Scheduler scheduler;

    public void buildGoodStockTimer() throws Exception {
        // 任务名称
        String name = UUID.randomUUID().toString();
        // 任务所属分组
        String group = CustomQuartzJob.class.getName();
        // 执行时间
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("* * * * * ?");
        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(CustomQuartzJob.class).withIdentity(name, group).build();
        // 创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
        // 将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
