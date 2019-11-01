package com.duofei;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author duofei
 * @date 2019/10/31
 */
@EnableHystrixDashboard
@SpringBootApplication
public class Apllication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Apllication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
