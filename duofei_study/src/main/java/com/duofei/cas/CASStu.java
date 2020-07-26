package com.duofei.cas;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author duofei
 * @date 2020/2/26
 */
public class CASStu {

    private static ExecutorService executorService = new ThreadPoolExecutor(3, 10, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private static final String MESSAGE = "taobao";

    private static  String b1 = "tao";
    private static  String c2 = "bao";

    public static void main(String[] args) {
        String a = "tao" + "bao";
        final String b = "tao";
        final String c = "bao";
        System.out.println(a == MESSAGE);
        System.out.println((b1 + c2) == MESSAGE);
        // scene1();
    }

    /**
     * 通过调试 CountDownLatch 学习 AQS
     * @author duofei
     * @date 2020/3/30
     */
    public static void byCountDownLatch(){
        // 通过调试 CountDownLatch 学习 AQS
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(()->{
            try {
                countDownLatch.await();
                System.out.println("执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        countDownLatch.countDown();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过调试 ReentrantLock 来学习 AQS 的 ConditionObject
     * @author duofei
     * @date 2020/3/30
     */
    public static void byReentrantLock(){
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        try{
            condition.await();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }

    /**
     * 场景：a,b,c 三个线程，a 线程请求锁，b线程在 condition 处等待，c 线程唤醒 condition
     * 目的：验证唤醒等待在 condition 上的线程时，它是否还需要重新入队列和其它线程争取锁
     */
    public static void scene1(){
        CountDownLatch waitC = new CountDownLatch(1);
        CountDownLatch waitB = new CountDownLatch(1);
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Runnable taskA = () -> {
            try{
                waitC.await();
                lock.lock();
                System.out.println("taskA 获取到锁");
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("taskA 释放锁");
                lock.unlock();
            }
        };

        Runnable taskB = () -> {
            try{
                lock.lock();
                waitB.countDown();
                System.out.println("taskB 获取到锁, 并在 condition 处等待...");
                condition.await();
                System.out.println("taskB 重新获取锁，开始执行！");
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("taskB 释放锁");
                lock.unlock();
            }
        };

        Runnable taskC = () -> {
            try{
                waitB.await();
                lock.lock();
                waitC.countDown();
                System.out.println("taskC 获取到锁, 并唤醒 condition ...");
                condition.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("taskC 释放锁");
                lock.unlock();
            }
        };

        executorService.execute(taskA);
        executorService.execute(taskB);
        executorService.execute(taskC);

        executorService.shutdown();
    }

    /**
     * scene2:
     */
}
