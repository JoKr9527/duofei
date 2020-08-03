package com.duofei;

import com.duofei.service.ConsumerController;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author duofei
 * @date 2020/7/31
 */
@SpringBootApplication
@EnableDubbo
public class DubboConsumerApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(DubboConsumerApplication.class);
        final ConsumerController bean = applicationContext.getBean(ConsumerController.class);
        bean.getApplicationName();
    }
}
