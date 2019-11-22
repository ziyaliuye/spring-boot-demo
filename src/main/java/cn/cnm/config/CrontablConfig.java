package cn.cnm.config;

import cn.cnm.crontab.TestCrontab;
import cn.cnm.utils.QuartzUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lele
 * @version 1.0
 * @Description Quartz定时配置类
 * @Email 414955507@qq.com
 * @date 2019/11/22 21:14
 */
@Configuration
public class CrontablConfig {
    // 工具类
    private final QuartzUtils quartzUtils;

    public CrontablConfig(QuartzUtils quartzUtils) {
        this.quartzUtils = quartzUtils;
    }

    @Bean
    // 配置任务执行时间
    public void Initialization() {
        quartzUtils.addJob(TestCrontab.class.getName(), TestCrontab.class, "*/10 * * * * ? ");
    }
}
