package com.duofei;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author duofei
 * @date 2019/10/17
 */
@EnableConfigServer
@SpringBootApplication
@EnableWebSecurity
@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
