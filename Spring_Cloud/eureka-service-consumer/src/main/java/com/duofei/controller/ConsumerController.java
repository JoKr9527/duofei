package com.duofei.controller;

import com.duofei.command.HelloCollapseCommand;
import com.duofei.command.HelloCommand;
import com.duofei.service.ConsumerService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author duofei
 * @date 2019/10/10
 */
@RestController
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/ribbon-consumer")
    public String helloConsumer(){
        return consumerService.hello();
    }

    @GetMapping(value="/ribbon-consumer1")
    public String helloConsumer1(){
        // 同步执行
        HelloCommand helloCommand = new HelloCommand(HystrixCommand.Setter.withGroupKey(()->"hello"), restTemplate);
        // 测试缓存
        new HelloCommand(HystrixCommand.Setter.withGroupKey(()->"hello"), restTemplate).execute();
        // 清除缓存
        HelloCommand.flushRequestCache();
        new HelloCommand(HystrixCommand.Setter.withGroupKey(()->"hello"), restTemplate).execute();
        return helloCommand.execute();
    }

    @GetMapping(value="/ribbon-consumer2")
    public String helloConsumer2() throws ExecutionException, InterruptedException {
        HelloCommand helloCommand = new HelloCommand(HystrixCommand.Setter.withGroupKey(()->"hello"), restTemplate);
        // 异步执行
        Future<String> result = helloCommand.queue();
        return result.get();
    }

    @GetMapping(value="/ribbon-consumer3")
    public String helloConsumer3() throws ExecutionException, InterruptedException {
        HelloCommand helloCommand = new HelloCommand(HystrixCommand.Setter.withGroupKey(()->"hello"), restTemplate);
        // observe 方法测试
        Observable<String> observe = helloCommand.observe();
        final String[] hello = new String[1];
        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Observer onCompleted 方法执行");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("我看起来发生了错误");
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                hello[0] = s;
                System.out.println("我收到了一个字符串：" + s);
            }
        });
        return hello[0];
    }

    @GetMapping(value="/ribbon-consumer4")
    public String helloConsumer4() throws ExecutionException, InterruptedException {
        // 异步的注解测试
        Future<String> resultAsync = consumerService.helloAsync();
        return resultAsync.get();
    }

    @GetMapping(value="/ribbon-consumer5")
    public String helloConsumer5(@RequestParam String name) {

        Future<String> queue1 = new HelloCollapseCommand(consumerService, name + "12").queue();
        Future<String> queue2 = new HelloCollapseCommand(consumerService, name + "123").queue();
        Future<String> queue3 = new HelloCollapseCommand(consumerService, name).queue();
        Future<String> queue4 = new HelloCollapseCommand(consumerService, name).queue();

        try {
            System.out.println("线程名：" + Thread.currentThread().getName() + "," + queue1.get() + queue2.get() + queue3.get() + queue4.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return consumerService.getName(name);
    }
}
