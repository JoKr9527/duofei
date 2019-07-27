package com.duofei.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * bean 测试
 * @author duofei
 * @date 2019/7/12
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:beanFactoryTest.xml")
public class BeanFactoryTest {


    @Test
    public void testSimpleLoaded(){
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("beanFactoryTest.xml");
        MyTestBean myTestBean = (MyTestBean)beanFactory.getBean("myTestBean");
        System.out.println(myTestBean.getTestStr());
    }
}
