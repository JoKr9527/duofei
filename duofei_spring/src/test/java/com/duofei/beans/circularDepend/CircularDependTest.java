package com.duofei.beans.circularDepend;

import com.duofei.beans.MyTestBean;
import com.duofei.beans.TestA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author duofei
 * @date 2020/7/16
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:beanFactoryTest.xml")
public class CircularDependTest {

    @Test
    public void testSimpleLoaded(){
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("circularDepend.xml");
        ServiceA serviceA = (ServiceA)beanFactory.getBean("serviceA");
        System.out.println("end。。。");
        List<? super Integer> foo4 = new ArrayList<Integer>();
        Object object = foo4.get(1);
        foo4.add(new Integer(1));
    }
}
