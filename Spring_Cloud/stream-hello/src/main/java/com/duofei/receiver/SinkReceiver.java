package com.duofei.receiver;

import com.duofei.bindings.EchoTalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.GenericMessage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duofei
 * @date 2019/10/22
 */
@EnableBinding({Sink.class, Source.class, EchoTalk.class})
public class SinkReceiver {

    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

    public static List<String> tempCached = new ArrayList<>();

    @Resource
    private EchoTalk echoTalk;

    @StreamListener(Sink.INPUT)
    @SendTo(Source.OUTPUT)
    public String receive(Object payload) {
        logger.info("Received: " + payload);
        return payload instanceof String ? (String) payload : "我发送了一个不认识的对象";
    }

    @StreamListener(Source.OUTPUT)
    public void receiveFromOutput(Object msg){
        logger.info("receiveFromOutput: " + msg);
        tempCached.add(msg instanceof String ? (String) msg : "我得到了一个不认识的对象");
    }

    public String send(String payload) {
        logger.info("Received wait Send Msg: " + payload);
        echoTalk.echoTalkSend().send(new GenericMessage(payload));
        return payload ;
    }

    @StreamListener("echoTalkRec")
    public void receiveFromTopicTalk(Object msg){
        logger.info("echoTalkRec: " + msg);
    }

}
