package com.duofei.db.redis.server;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author duofei
 * @date 2019/10/8
 */
@Service
public class UserService {

    @Cacheable(cacheNames = {"UserService:findById:id","UserService:findById2:id"})
    public String findById(String id) {
        System.out.println("执行数据库调用");
        return "获取到结果";
    }
}
