package com.duofei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 启动类
 * @author duofei
 * @date 2019/11/5
 */
@SpringBootApplication
@RestController
public class Application {

    @LoadBalanced
    @Bean
    public RestTemplate loadbalancedRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/")
    public String home(){
        return loadbalancedRestTemplate().getForEntity("http://consul-server-register/", String.class).getBody();
    }

    @RequestMapping("/dc")
    public String dc(){
        List<ServiceInstance> list = discoveryClient.getInstances("consul-server-register");
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri().toString();
        }
        return null;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
