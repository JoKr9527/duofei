package com.duofei.controller;

import com.duofei.config.ConfigPro;
import com.duofei.event.NotifyRemoteApplicationEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofei
 * @date 2019/10/18
 */
@RefreshScope
@RestController
public class TestController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ConfigPro configPro;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BusProperties busProperties;

    @Value("${from}")
    private String from;

    @RequestMapping("/from")
    public String from() {
        return this.from;
    }

    @GetMapping("/send")
    public void send(@RequestParam String msg){
        amqpTemplate.convertAndSend("exchange", "routingKey", msg);
    }

    @GetMapping("/pro")
    public String pro(){
        return configPro.getPro();
    }

    @GetMapping("/notify")
    public void notifyOthers(@RequestParam String  msg) throws Exception{
        applicationContext.publishEvent(new NotifyRemoteApplicationEvent(msg, new Object() , busProperties.getId()));
    }
}
