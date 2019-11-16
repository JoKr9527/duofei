package com.duofei.listener;

import com.duofei.event.NotifyRemoteApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author duofei
 * @date 2019/11/13
 */
@Component
public class LocalEventListener {

    @EventListener
    public void handleNotify(NotifyRemoteApplicationEvent event) {
        System.out.println("你：" + event.getMsg());
        System.out.println("我：知道啦！" );
    }
}
