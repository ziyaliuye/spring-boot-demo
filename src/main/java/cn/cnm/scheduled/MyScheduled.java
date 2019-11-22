package cn.cnm.scheduled;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author lele
 * @version 1.0
 * @Description 定时执行任务
 * @Email 414955507@qq.com
 * @date 2019/11/22 20:02
 */
public class MyScheduled {
    @Scheduled(cron = "* * * * * ?")
    public void schedulePrinting(){
        System.out.println("定时任务执行....");
    }
}
