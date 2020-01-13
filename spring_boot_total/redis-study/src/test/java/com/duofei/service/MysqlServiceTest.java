package com.duofei.service;

import com.duofei.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author duofei
 * @date 2020/1/3
 */
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
@RunWith(SpringRunner.class)
public class MysqlServiceTest {

    @Autowired
    private MySqlService mySqlService;

    @Test
    public void first() throws Exception{
        mySqlService.firstA();
    }

    @Test
    public void test() {
        List<String> linkedList = new LinkedList<>();
        linkedList.add("11s");
        linkedList.add("123");
        linkedList.add("13s");
        Iterator<String> iterator = linkedList.iterator();
        while(iterator.hasNext()){
            String value = iterator.next();
            System.out.println(value);
            if(value.equals("123")){
                linkedList.remove("11s");
            }
        }
        int size = linkedList.size();


    }
}
