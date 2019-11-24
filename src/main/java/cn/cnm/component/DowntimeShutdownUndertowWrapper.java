package cn.cnm.component;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import org.springframework.stereotype.Component;

/**
 * @author lele
 * @version 1.0
 * @Description 优雅停服助手
 * @Email 414955507@qq.com
 * @date 2019/11/24 1:02
 */
@Component
public class DowntimeShutdownUndertowWrapper implements HandlerWrapper {
    private GracefulShutdownHandler gracefulShutdownHandler;

    @Override
    public HttpHandler wrap(HttpHandler handler) {
        if (gracefulShutdownHandler == null) {
            this.gracefulShutdownHandler = new GracefulShutdownHandler(handler);
        }
        return gracefulShutdownHandler;
    }

    GracefulShutdownHandler getGracefulShutdownHandler() {
        return gracefulShutdownHandler;
    }
}
