package com.duofei.db.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/**
 * @author duofei
 * @date 2019/10/8
 */
@Configuration
@EnableCaching
public class CustomRedisCacheConfiguration {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        CacheManager cacheManager = new RedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory),redisCacheConfiguration.entryTtl(Duration.ofDays(2)));
        return cacheManager;
    }
}
