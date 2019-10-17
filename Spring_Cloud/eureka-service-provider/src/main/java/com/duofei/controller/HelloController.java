package com.duofei.controller;

import com.duofei.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

/**
 * hello 请求处理
 * @author duofei
 * @date 2019/10/10
 */
@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String index() throws Exception{
        int sleepTime = new Random().nextInt(4000);
        logger.info("sleepTime: " + sleepTime);
        Thread.sleep(sleepTime);
        logger.info("services: " + client.getServices());
        return "Hello World";
    }

    @GetMapping("/hello1")
    public String hello(@RequestParam String name){
        return "Hello " + name;
    }

    @GetMapping("/hello2")
    public User hello(@RequestHeader String name, @RequestHeader Integer age){
        User user = new User();
        user.setAge(age);
        user.setName(name);
        return user;
    }

    @PostMapping("/hello3")
    public String hello(@RequestBody User user){
        return "Hello " + user.getName() + ", " + user.getAge();
    }
}
