package com.duofei.db.mysql.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.duofei.db.mysql.custom.ExpandJpaRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author duofei
 * @date 2019/9/28
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {"com.duofei.db.mysql.custom"}, repositoryFactoryBeanClass = ExpandJpaRepositoryFactoryBean.class)
@EntityScan("com.duofei.db.mysql.entity")
public class ExpandJpaConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource druidDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
