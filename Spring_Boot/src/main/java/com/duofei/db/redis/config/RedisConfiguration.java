package com.duofei.db.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * redis 配置类
 * @author duofei
 * @date 2019/9/27
 */
@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPassword("123456");
        RedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(configuration);
        return redisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate(){
        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory());
        return redisTemplate;
    }
}
