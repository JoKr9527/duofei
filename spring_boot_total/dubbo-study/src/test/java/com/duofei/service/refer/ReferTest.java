package com.duofei.service.refer;

import com.duofei.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 服务引入测试
 * @author duofei
 * @date 2020/1/17
 */
@SpringBootTest
@ContextConfiguration(classes = Application.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("consumer")
public class ReferTest {

    @Autowired
    private ReferService referService;

    @After
    public void delayExit(){
        try {
            // 延迟测试退出时机
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDefaultUserNames(){
        Assert.assertArrayEquals(new Object[]{"num1", "num2"}, referService.getDefaultUsernames().toArray());
    }
}
