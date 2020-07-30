package com.duofei;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.property.DynamicSentinelProperty;
import com.duofei.handler.ExceptionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 * @author duofei
 * @date 2020/7/24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@ServletComponentScan
public class Application {

    @Bean
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        DynamicSentinelProperty<ClusterClientAssignConfig> serverAssignProperty = new DynamicSentinelProperty<>(new ClusterClientAssignConfig("192.168.3.18", 18730));
        ClusterClientConfigManager.registerServerAssignProperty(serverAssignProperty);
        ClusterClientConfig clusterClientConfig = new ClusterClientConfig();
        clusterClientConfig.setRequestTimeout(5000);
        DynamicSentinelProperty<ClusterClientConfig> clientConfigProperty = new DynamicSentinelProperty<>(clusterClientConfig);
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigProperty);
        ClusterStateManager.applyState(0);
    }
}
