package com.duofei.controller;

import com.duofei.receiver.SinkReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofei
 * @date 2019/11/14
 */
@RestController
public class StreamController {

    @Autowired
    private SinkReceiver sinkReceiver;

    @GetMapping("/getCache")
    public String getCache(){
        return SinkReceiver.tempCached.toString();
    }

    @GetMapping("/send")
    public void send(@RequestParam String msg){
        sinkReceiver.send(msg);
    }
}
