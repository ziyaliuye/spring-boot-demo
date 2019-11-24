package cn.cnm.component;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author lele
 * @version 1.0
 * @Description 置一下buffer pool,不然他就使用默认的， 启动时会有WARN警告， 强迫症...
 * @Email 414955507@qq.com
 * @date 2019/11/24 13:07
 */
@Component
public class UndertowBufferPool implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {
    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
        });
    }
}
