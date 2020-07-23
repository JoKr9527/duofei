package com.duofei;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;

/**
 * 启动类
 * @author duofei
 * @date 2020/7/22
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApp implements InitializingBean {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        namingService.subscribe("provider-app", event -> {
            System.out.println(event);
        });
    }

    @RestController
    public class NacoController{

        @Autowired
        private LoadBalancerClient loadBalancerClient;
        @Autowired
        private RestTemplate restTemplate;

        @Value("${spring.application.name}")
        private String appName;

        @GetMapping("/echo/app-name")
        public String echoAppName(){
            ServiceInstance serviceInstance = loadBalancerClient.choose("provider-app");
            String path = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);
            System.out.println("request path: " + path);
            return restTemplate.getForObject(path, String.class);
        }

    }
}
