package com.duofei.db.mongodb.config;

import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * mongodb 配置
 * @author duofei
 * @date 2019/9/28
 */
@Configuration
@EnableMongoRepositories(basePackages = {"com.duofei.db.mongodb.repository"})
public class MongoConfig {

    @Bean
    public MongoDbFactory mongoDbFactory(){
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://localhost/test");
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
        return mongoDbFactory;
    }

}
