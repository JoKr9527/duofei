package com.duofei.spi.provider;

import com.duofei.spi.Robot;
import org.apache.dubbo.common.URL;

import java.util.Optional;

/**
 * 服务接口实现类之一
 * @author duofei
 * @date 2020/1/14
 */
public class Bumblebee implements Robot {

    private Robot next;

    public Bumblebee(Robot robot) {
        this.next = robot;
    }

    public Bumblebee() {
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
        Optional.ofNullable(next).ifPresent(Robot::sayHello);
    }

    @Override
    public void showColor(URL url) {
        System.out.println("Hello, I an yellow.");
        Optional.ofNullable(next).ifPresent(robot -> robot.showColor(url));
    }
}
