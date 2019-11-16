package com.duofei.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 自定义远程通知事件
 * @author duofei
 * @date 2019/11/13
 */
public class NotifyRemoteApplicationEvent extends RemoteApplicationEvent {

    private String msg;

    private NotifyRemoteApplicationEvent(){}

    public NotifyRemoteApplicationEvent(String msg, Object source, String originService) {
        super(source, originService);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
