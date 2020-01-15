package com.duofei.spi.provider;

import com.duofei.spi.Robot;
import org.apache.dubbo.common.URL;

import java.util.Optional;

/**
 * 服务接口实现类之一
 * @author duofei
 * @date 2020/1/14
 */
public class OptimusPrime implements Robot {

    private Robot next;

    public OptimusPrime(Robot robot) {
        this.next = robot;
    }

    public OptimusPrime() {
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
        Optional.ofNullable(next).ifPresent(Robot::sayHello);
    }

    @Override
    public void showColor(URL url) {
        System.out.println("Hello, I am blue and white.");
        Optional.ofNullable(next).ifPresent(robot -> robot.showColor(url));
    }
}
