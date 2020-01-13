package com.duofei;

import com.duofei.service.RedisService;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author duofei
 * @date 2019/12/26
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class Application implements ApplicationContextAware {

    private static ApplicationContext applicationContext ;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        RedisService redisService = applicationContext.getBean(RedisService.class);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Application.applicationContext = applicationContext;
    }
}
