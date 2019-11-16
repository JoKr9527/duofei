package com.duofei;

import com.duofei.config.ConfigPro;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;

/**
 * @author duofei
 * @date 2019/10/21
 */
@SpringBootApplication
@RemoteApplicationEventScan({"com.duofei.event"})
public class Application {

    @Bean
    public ConfigPro configPro(){
        return new ConfigPro();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
