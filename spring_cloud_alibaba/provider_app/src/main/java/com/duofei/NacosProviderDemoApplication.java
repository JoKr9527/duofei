package com.duofei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 * @author duofei
 * @date 2020/7/22
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosProviderDemoApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        //while (true){
            System.out.println(environment.getProperty("username"));
            System.out.println(environment.getProperty("password"));
        //}
    }

    @RestController
    public class EchoController{
        @GetMapping("/echo/{string}")
        public String echo(@PathVariable String string){
            return "Hello Nacos Discovery " + string;
        }
    }
}
