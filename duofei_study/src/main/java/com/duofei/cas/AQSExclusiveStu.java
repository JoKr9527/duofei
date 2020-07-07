package com.duofei.cas;

import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS 独占模式学习
 * @author duofei
 * @date 2020/3/20
 */
public class AQSExclusiveStu {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new LockGetThread("Thread 11", lock).start();
        new LockGetThread("Thread 22", lock).start();
    }

    static class LockGetThread extends Thread{

        private ReentrantLock lock;

        LockGetThread(String name, ReentrantLock lock){
            super(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "加锁成功！");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
