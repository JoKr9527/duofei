package com.duofei.beans.circularDepend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author duofei
 * @date 2020/7/16
 */
//@Component
public class ResetServiceABeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("serviceA")){
            return new ServiceA();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("serviceA")){
            return new ServiceA();
        }
        return bean;
    }
}
