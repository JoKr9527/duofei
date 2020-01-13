package com.duofei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author duofei
 * @date 2019/12/31
 */
@Service
public class RedisService {

    @Autowired
    @Qualifier("stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    public String checkToken(String token){
        // hash 数据结构 hget 命令
        return (String)redisTemplate.opsForHash().get("login:", token);
    }

    public void updateToken(String token, String userName, String item){
        long timestamp = System.currentTimeMillis();
        // 维持令牌与已登录用户之间的映射
        redisTemplate.opsForHash().put("login:", token, userName);
        // 记录令牌最后一次出现的时间
        redisTemplate.opsForZSet().add("recent:", token, timestamp);

        if (Objects.nonNull(item)) {
            // 记录用户浏览商品记录
            redisTemplate.opsForZSet().add("viewed:" + token, item, timestamp);
            // 仅仅保留最近浏览的 25 条商品
            redisTemplate.opsForZSet().removeRange("viewed:" + token, 0, -26);
        }
    }

    public void cleanSession(){
        long limit = 10000;
        Long size = redisTemplate.opsForZSet().zCard("recent:");
        if(size <= limit){

        }
        long endIndex = Math.min(size - limit, 100);
        Set<String> tokens = redisTemplate.opsForZSet().range("recent:", 0, endIndex - 1);
        redisTemplate.delete(tokens.stream().map(token -> "viewed:" + token).collect(Collectors.toSet()));

        if(!tokens.isEmpty()){
            redisTemplate.opsForHash().delete("login:", tokens.toArray());
            redisTemplate.opsForZSet().remove("recent:", tokens.toArray());
        }
    }

    public void addToCart(String sessionId, String item, int count){
        if(count < 0){
            redisTemplate.opsForHash().delete("cart:" + sessionId, item);
        }else{
            redisTemplate.opsForHash().increment("cart:" + sessionId, item, count);
        }
    }
}
