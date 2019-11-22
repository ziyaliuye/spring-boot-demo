package cn.cnm.crontab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/22 21:11
 */
/* @DisallowConcurrentExecution：在分布式环境下允许并发执行 */
@DisallowConcurrentExecution
/* 不要忘了加入容器中 */
@Service
public class TestCrontab implements Job {
    // 日志对象实例
    private static final Logger logger = LogManager.getLogger(TestCrontab.class);

    @Override
    public void execute(JobExecutionContext context) {
        logger.info(logger.getName() + "：定时任务开始执行 ==>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        logger.info(logger.getName() + "：定时任务执行结束 ==>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
