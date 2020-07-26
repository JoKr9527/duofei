package com.duofei.beans.circularDepend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author duofei
 * @date 2020/7/16
 */
@Component
public class ServiceA {
    {
        System.out.println("ServiceA" + 1);
    }

    public ServiceA(){
        System.out.println("serviceA instant...");
    }

    @Autowired
    private ServiceB serviceB;

}
