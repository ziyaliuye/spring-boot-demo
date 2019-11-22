package cn.cnm.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ESConfig {
    private static ArrayList<HttpHost> hostList;
    private static int connectTimeOut = 1000; // 连接超时时间, 单位ms
    private static int socketTimeOut = 30000; // 连接超时时间， 单位ms
    private static int connectionRequestTimeOut = 500; // 获取连接的超时时间
    private static int maxConnectNum = 100; // 最大连接数
    private static int maxConnectPerRoute = 100; // 最大路由连接数

    /* 连接配置 */
    static {
        hostList = new ArrayList<>();
        // 集群地址，多个用,隔开
        String hosts = "192.168.58.128";
        String[] hostStrs = hosts.split(",");
        for (String host : hostStrs) {
            // 使用的端口号
            int port = 9200;
            // 使用的协议
            String schema = "http";
            hostList.add(new HttpHost(host, port, schema));
        }
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
        // 异步http client连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
            return requestConfigBuilder;
        });
        // 异步http client连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }
}
