package com.duofei.derby;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * dervt 自动配置类
 * @author duofei
 * @date 2019/11/25
 */
@Configuration
@EnableConfigurationProperties(DerbyProperties.class)
public class DerbyAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = {"derbyConnection"})
    public Connection derbyConnection(DerbyProperties derbyProperties) throws Exception{
        System.setProperty("jdbc.drivers", derbyProperties.getDrivers());
        return DriverManager.getConnection(derbyProperties.getUrl(),
                derbyProperties.getUserName(), derbyProperties.getPassword());
    }
}
