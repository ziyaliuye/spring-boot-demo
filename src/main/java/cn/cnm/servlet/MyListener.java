package cn.cnm.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/20 17:36
 */
public class MyListener implements ServletContextListener {
    // web应用初始化时调用
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("web应用启动...");
    }

    // web应用销毁时调用
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("web应用关闭...");
    }
}
