package com.duofei.listener;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * rabbit Mq 消息接收器
 * @author duofei
 * @date 2019/11/13
 */
@Component
@RabbitListener(bindings = {
        @QueueBinding(value = @Queue("testSpringCloudRabbit"), exchange = @Exchange("exchange"), key = "routingKey")
})
public class Receiver {

    @RabbitHandler
    public void process(String msg){
        System.out.println("receiving :  " + msg);
    }
}
