package com.duofei.synchron;

import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

/**
 * semaphore 学习
 * @author duofei
 * @date 2019/11/20
 */
public class Pool {

    private static final int MAX_AVAILABLE = 5;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

    private Integer data = new Integer(10);

    /**
     * 处理池中数据
     */
    int handleData(Consumer<Integer> handle){
        try {
            available.acquire();
            handle.accept(data);
            return data.intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 释放池中数据
     */
    void releaseData(){
        available.release();
    }

    public static void main(String[] args) {
        Pool pool = new Pool();
        for (int i = 0; i < 10; i++) {
            new Thread(()-> pool.handleData(System.out::println)).start();
            // 处理完数据释放
            // pool.releaseData();
        }
    }
}
