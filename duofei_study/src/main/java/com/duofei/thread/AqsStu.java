package com.duofei.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duofei
 * @date 2019/10/21
 */
public class AqsStu {

    public static void main(String[] args) {
        basicStu();
    }

    /**
     * 断点查看AQS对象各状态
     * @author duofei
     * @date 2019/10/21
     */
    public static void basicStu(){
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(()->{
                lock.lock();
                try {
                    Thread.sleep(2000L);
                    System.out.println("结束休眠");
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.setName("duofei--" + i);
            thread.start();
        }
    }
}
