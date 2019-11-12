package com.duofei.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供
 * @author duofei
 * @date 2019/11/12
 */
@RestController
public class EchoController {

    @GetMapping(value= "/echo/{echoValue}")
    public String echo(@PathVariable String echoValue){
        return echoValue;
    }
}
