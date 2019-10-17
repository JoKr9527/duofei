package com.duofei.controller;

import com.duofei.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofei
 * @date 2019/10/10
 */
@RestController
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping(value = "/ribbon-consumer")
    public String helloConsumer(){
        return consumerService.hello();
    }
}
