package com.duofei.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author duofei
 * @date 2019/11/14
 */
public interface EchoTalk {

    @Input()
    SubscribableChannel echoTalkRec();

    @Output()
    MessageChannel echoTalkSend();

}
