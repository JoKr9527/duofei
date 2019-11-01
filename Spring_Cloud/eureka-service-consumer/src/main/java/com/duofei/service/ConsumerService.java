package com.duofei.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author duofei
 * @date 2019/10/14
 */
@Service
public class ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String hello(){
        return restTemplate.getForEntity("http://HELLO-SERVICE/provider/hello", String.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "helloFallback")
    public Future<String> helloAsync(){
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForEntity("http://HELLO-SERVICE/provider/hello", String.class).getBody();
            }
        };
    }

    private String helloFallback(Throwable e){
        e.printStackTrace();
        return "访问出错";
    }

    public String getName(String name){
        return restTemplate.getForEntity("http://HELLO-SERVICE/provider/hello5?name=" + name, String.class).getBody();
    }

    public List<String> getNames(List<String> names){
        System.out.println("合并请求：" + Thread.currentThread().getName() + "（线程名）" + ",names=" + names);
        HttpEntity<List<String>> request = new HttpEntity<>(names, new HttpHeaders());
        return restTemplate.postForEntity("http://HELLO-SERVICE/provider/hello4", request, List.class).getBody();
    }

    @HystrixCollapser(batchMethod = "getNames2", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL, collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "2000")})
    public String getName1(String name){
        return restTemplate.getForEntity("http://HELLO-SERVICE/provider/hello5?name=" + name, String.class).getBody();
    }

    @HystrixCommand
    public List<String> getNames2(List<String> names){
        System.out.println("合并请求：" + Thread.currentThread().getName() + "（线程名）" + ",names=" + names);
        HttpEntity<List<String>> request = new HttpEntity<>(names, new HttpHeaders());
        return restTemplate.postForEntity("http://HELLO-SERVICE/provider/hello4", request, List.class).getBody();
    }
}
