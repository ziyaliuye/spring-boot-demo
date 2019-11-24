package cn.cnm.component;

import io.undertow.Undertow;
import io.undertow.server.ConnectorStatistics;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author lele
 * @version 1.0
 * @Description 优雅关闭 Spring Boot undertow
 * @Email 414955507@qq.com
 * @date 2019/11/24 1:02
 */
@Component
public class DowntimeShutdownUndertow implements ApplicationListener<ContextClosedEvent> {
    private final DowntimeShutdownUndertowWrapper gracefulShutdownUndertowWrapper;

    private final ServletWebServerApplicationContext context;

    public DowntimeShutdownUndertow(DowntimeShutdownUndertowWrapper gracefulShutdownUndertowWrapper, ServletWebServerApplicationContext context) {
        this.gracefulShutdownUndertowWrapper = gracefulShutdownUndertowWrapper;
        this.context = context;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent){
        gracefulShutdownUndertowWrapper.getGracefulShutdownHandler().shutdown();
        try {
            UndertowServletWebServer webServer = (UndertowServletWebServer)context.getWebServer();
            Field field = webServer.getClass().getDeclaredField("undertow");
            field.setAccessible(true);
            Undertow undertow = (Undertow) field.get(webServer);
            List<Undertow.ListenerInfo> listenerInfo = undertow.getListenerInfo();
            Undertow.ListenerInfo listener = listenerInfo.get(0);
            ConnectorStatistics connectorStatistics = listener.getConnectorStatistics();
            while (connectorStatistics.getActiveConnections() > 0){}
        }catch (Exception e){
            // Application Shutdown
        }
    }
}
