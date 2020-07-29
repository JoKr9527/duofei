package com.duofei.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;

/**
 * 测试
 * @author duofei
 * @date 2020/7/24
 */
@RestController
public class TestController{

    @Resource
    private EchoService echoService;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/hello")
    @SentinelResource(value = "hello", blockHandler = "helloBlockHandler")
    public String hello() throws InterruptedException, BrokenBarrierException {
        System.out.println("hello Sentinel " + System.currentTimeMillis());
        return "Hello Sentinel";
    }

    @GetMapping("/hello1")
    @SentinelResource(value = "hello", blockHandler = "helloBlockHandler")
    public String hello1() throws InterruptedException, BrokenBarrierException {
        System.out.println("hello1 Sentinel " + System.currentTimeMillis());
        return "Hello1 Sentinel";
    }

    @GetMapping("/readHello")
    @SentinelResource(value = "readHello", blockHandler = "helloBlockHandler")
    public String readHello(){
        System.out.println("readHello Sentinel " + System.currentTimeMillis());
        return "readHello Sentinel";
    }

    @GetMapping("/writeHello")
    @SentinelResource(value = "writeHello", blockHandler = "helloBlockHandler")
    public String writeHello(){
        System.out.println("writeHello Sentinel " + System.currentTimeMillis());
        return "writeHello Sentinel";
    }

    public String helloBlockHandler(BlockException exception){
        System.out.println("hello block " + System.currentTimeMillis());
        return "hello block!";
    }

    @GetMapping("/echo/{str}")
    public String echo(@PathVariable String str){
        return echoService.echo(str);
    }

    @GetMapping("/echo/app-name")
    public String echoAppName(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider-app");
        String path = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);
        System.out.println("request path: " + path);
        return restTemplate.getForObject(path, String.class);
    }

    @GetMapping("/helloWorld")
    public String helloWorld(){
        try(Entry entry = SphU.entry("helloWorld")){
            //被保护的业务逻辑
            return "helloWorld";
        }catch (BlockException exception){
            //资源访问阻止，被限流或被降级
            System.out.println("blocked!");
            return "blocked!";
        }
    }
}
