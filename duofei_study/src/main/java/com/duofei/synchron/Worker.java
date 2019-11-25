package com.duofei.synchron;

import java.util.concurrent.CountDownLatch;

/**
 * 工作线程，将阻塞在 CountDownLatch 上
 * @author duofei
 * @date 2019/11/19
 */
public class Worker implements Runnable {

    private final CountDownLatch startSignal;

    private final CountDownLatch doneSignal;

    Worker(CountDownLatch startSignal, CountDownLatch doneSignal){
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        boolean runFlag = true;
        while (runFlag){
            System.out.println("我还在运行");
        }
        try {
            Thread.sleep(1000);
            startSignal.await();
            doWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            doneSignal.countDown();
        }
    }

    private void doWork(){
        System.out.println("我完成工作了！");
    }
}
