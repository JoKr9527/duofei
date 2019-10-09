package com.duofei.db.redis;

import com.duofei.db.redis.server.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * cache 测试
 * @author duofei
 * @date 2019/10/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Autowired
    private UserService userService;

    @Test
    public void findById(){
        String result = userService.findById("nothing");
        System.out.println(result);
    }
}
