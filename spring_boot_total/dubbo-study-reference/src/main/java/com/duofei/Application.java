package com.duofei;

import com.duofei.service.UserService;
import com.duofei.service.refer.ReferService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * 启动类
 * @author duofei
 * @date 2020/1/14
 */
@SpringBootApplication
//@ImportResource("classpath:consumer.xml")
@EnableDubbo(scanBasePackages = "com.duofei.service")
public class Application implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        ReferService referService = applicationContext.getBean(ReferService.class);
        referService.getDefaultUsernames().forEach(System.out::println);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Application.applicationContext = applicationContext;
    }

}
