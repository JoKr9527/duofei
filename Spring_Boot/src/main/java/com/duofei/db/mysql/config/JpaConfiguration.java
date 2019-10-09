package com.duofei.db.mysql.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * jpa 配置类
 * @author duofei
 * @date 2019/9/27
 */
/*@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.duofei.db.mysql.repositories"})
@EntityScan("com.duofei.db.mysql.entity")*/
public class JpaConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource druidDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.other")
    public DataSource druidDataSourceOther(){
        return DruidDataSourceBuilder.create().build();
    }
}
