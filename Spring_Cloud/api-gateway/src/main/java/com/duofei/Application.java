package com.duofei;

import com.duofei.filter.AccessFilter;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author duofei
 * @date 2019/10/16
 */
@EnableZuulProxy
@SpringCloudApplication
public class Application {

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(Application.class).web(WebApplicationType.SERVLET).run(args);
    }
}
