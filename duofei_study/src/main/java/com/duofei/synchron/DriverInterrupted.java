package com.duofei.synchron;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch 学习类
 * @author duofei
 * @date 2019/11/19
 */
public class DriverInterrupted {

    public static void main(String[] args) throws InterruptedException{
        int workerNumber = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(workerNumber);

        for (int i = 0; i < 10 ; i++) {
            final Thread thread = new Thread(new Worker(startSignal, doneSignal));
            thread.start();
        }

        System.out.println("工作都准备完毕！");
        System.out.println("开始执行!");
        startSignal.countDown();
        doneSignal.await();
        System.out.println("一共：" + workerNumber + "人都执行完了");
    }
}
