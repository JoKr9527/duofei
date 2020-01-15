package com.duofei.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ServiceLoader;

/**
 * spi 实现
 * @author duofei
 * @date 2020/1/14
 */
@Service
public class SpiService {

    public void javaSPI(){
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        serviceLoader.forEach(Robot::sayHello);
    }

    public void dubboSPI(){
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
    }

    public void adaptive(){
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot robot = extensionLoader.getAdaptiveExtension();
        robot.showColor(URL.valueOf("dubbo://127.0.0.1:9092?key=customRobot"));
        robot.sayHello();
    }

    public void active(){
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        List<Robot> activateExtension =
                extensionLoader.getActivateExtension(URL.valueOf("dubbo://127.0.0.1:9092?key=bumblebee"),new String[]{"bumblebee","optimusPrime"}, "");
        activateExtension.forEach(Robot::sayHello);
    }

    public void wrapper(){
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot customRobot = extensionLoader.getExtension("customRobot");
        customRobot.sayHello();
        customRobot.showColor(URL.valueOf("dubbo://127.0.0.1:9092?key=bumblebee"));
    }

}
