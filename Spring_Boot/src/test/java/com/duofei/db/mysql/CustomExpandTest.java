package com.duofei.db.mysql;

import com.duofei.db.mysql.config.ExpandJpaConfiguration;
import com.duofei.db.mysql.custom.CustomUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author duofei
 * @date 2019/9/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomExpandTest {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Test
    public void expandJPA(){
        customUserRepository.findOne("测试");
    }
}

