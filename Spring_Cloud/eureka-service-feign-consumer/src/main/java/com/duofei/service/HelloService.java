package com.duofei.service;

import com.duofei.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author duofei
 * @date 2019/10/15
 */
@FeignClient("hello-service")
@RequestMapping("/provider")
public interface HelloService {

    /**
     * hello
     * @author duofei
     * @date 2019/10/15
     * @return String
     */
    @RequestMapping("/hello")
    String hello();

    @GetMapping("/hello1")
    String hello(@RequestParam("name") String name);

    @GetMapping("/hello2")
    User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @PostMapping("/hello3")
    String hello(@RequestBody User user);
}
