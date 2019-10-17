package com.duofei.controller;

import com.duofei.bean.User;
import com.duofei.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author duofei
 * @date 2019/10/15
 */
@RestController
public class ConsumerController {

    @Resource
    private HelloService helloService;

    @GetMapping("/feign-consumer")
    public String helloConsumer() {
        return helloService.hello();
    }

    @GetMapping("/feign-consumer2")
    public String helloConsumer2() {
        StringBuilder sb = new StringBuilder();
        // 该请求接口服务端故意编写了线程休眠，在此处先注释掉: 加入了重试机制，此处开放
        sb.append(helloService.hello()).append("\n");
        /*sb.append(helloService.hello("duofei")).append("\n");
        sb.append(helloService.hello("duofei", 24)).append("\n");*/
        User user = new User();
        user.setName("duofei");
        user.setAge(25);
        sb.append(helloService.hello(user)).append("\n");
        return sb.toString();
    }
}
