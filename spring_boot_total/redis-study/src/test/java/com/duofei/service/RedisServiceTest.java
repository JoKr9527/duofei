package com.duofei.service;

import com.duofei.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author duofei
 * @date 2020/1/2
 */
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
@RunWith(SpringRunner.class)
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void checkToken(){
        // hash 数据结构 hget 命令
        String token = UUID.randomUUID().toString();
        String userName = redisService.checkToken(token);
        Assert.assertNull(userName);
    }

    @Test
    public void updateToken(){
        redisService.updateToken("number001", "hxf", "goods");
    }

    @Test
    public void cleanSession(){
        redisService.cleanSession();
    }

    @Test
    public void addToCart(){
        redisService.addToCart("number001", "goods", 3);
    }
}
