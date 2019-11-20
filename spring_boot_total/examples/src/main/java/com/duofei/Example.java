package com.duofei;

import com.duofei.app.Author;
import com.duofei.endpoint.AppInfoEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofei
 * @date 2019/11/19
 */
@RestController
@EnableAutoConfiguration
public class Example {

    @Bean
    public AppInfoEndpoint appInfoEndpoint(){
        Author author = new Author();
        author.setName("duofei");
        author.setAge(25);
        author.setEmail("662@.com");
        return new AppInfoEndpoint("我仅仅是一个测试程序", author);
    }

    @RequestMapping("/")
    String home(){
        return "Hello World!";
    }

    public static void main(String[] args){
        SpringApplication.run(Example.class, args);
    }
}
