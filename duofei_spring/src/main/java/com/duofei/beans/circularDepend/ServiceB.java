package com.duofei.beans.circularDepend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author duofei
 * @date 2020/7/16
 */
@Component
public class ServiceB {

    {
        System.out.println("serviceB" + 1);
    }

    public ServiceB(){
        System.out.println("serviceB instant...");
    }

    @Autowired
    private ServiceA serviceA;

}
