package com.duofei.spi.provider;

import com.duofei.spi.Robot;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * @author duofei
 * @date 2020/1/15
 */
@Activate(group = "111")
public class CustomRobot implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am from earth.");
    }

    @Override
    public void showColor(URL url) {
        System.out.println("Hello, I am red.");
    }
}
