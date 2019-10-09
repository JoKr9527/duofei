package com.duofei.db.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * redis 服务类
 * @author duofei
 * @date 2019/9/27
 */
@Repository
public class RedisServer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void putValue(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
