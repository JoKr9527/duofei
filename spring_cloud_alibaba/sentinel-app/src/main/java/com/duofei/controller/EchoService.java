package com.duofei.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * feign测试
 * @author duofei
 * @date 2020/7/24
 */
@FeignClient(name = "provider-app", fallback = EchoServiceFallback.class, configuration = FeignConfiguration.class)
public interface EchoService {

    @GetMapping("/echo/{str}")
    String echo(@PathVariable("str") String str);
}

class FeignConfiguration{
    @Bean
    public EchoServiceFallback echoServiceFallback(){
        return new EchoServiceFallback();
    }
}

class EchoServiceFallback implements EchoService{

    @Override
    public String echo(String str) {
        return "echo fallback";
    }
}
