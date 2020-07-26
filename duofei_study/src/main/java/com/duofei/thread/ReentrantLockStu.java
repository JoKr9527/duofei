package com.duofei.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁学习
 *
 * @author duofei
 * @date 2019/9/24
 */
public class ReentrantLockStu {

    private static ExecutorService executorService = new ThreadPoolExecutor(2, 5, 1000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), runnable -> {
        Thread thread = new Thread(runnable);
        return thread;
    });

    private static Integer data = new Integer(0);

    public static void main(String[] args) {
        // simpleUse();
        //fairOrUnfair();
        //uninterruptedLock();
        //interruptedLock();
        testSignal();
        executorService.shutdown();
    }

    /**
     * 简单使用
     *
     * @param
     * @return
     * @throws
     * @author duofei
     * @date 2019/9/26
     */
    public static void simpleUse() {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            final int tempI = i;
            executorService.execute(() -> {
                lock.lock();
                try {
                    // 更新数据
                    data = data + 1;
                    System.out.println("i 值为：" + tempI + ", data 值为：" + data);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }
    }

    /**
     * 公平锁与不公平锁：simpleUse() 方法使用的是不公平锁
     *
     * @author duofei
     * @date 2019/9/26
     */
    public static void fairOrUnfair() {
        ReentrantLock lock = new ReentrantLock(true);
        for (int i = 0; i < 10; i++) {
            final int tempI = i;
            executorService.execute(() -> {
                lock.lock();
                try {
                    // 更新数据
                    data = data + 1;
                    System.out.println("i 值为：" + tempI + ", data 值为：" + data);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }
    }

    /**
     * A 线程一直持有锁，当  B 线程通过不可中断来请求锁，尝试中断 B 线程，观察 锁的 等待队列中是否还有该线程；
     *
     * @param
     * @return
     * @throws
     * @author duofei
     * @date 2019/9/26
     */
    private static void uninterruptedLock() {
        ReentrantLock lock = new ReentrantLock();
        executorService.execute(() -> {
            lock.lock();
            try {
                while (true) {
                    System.out.println("一直持有锁，等待队列长度：" + lock.getQueueLength());
                    Thread.sleep(10000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    System.out.println("持有锁，等待队列长度：" + lock.getQueueLength());
                    Thread.sleep(100000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread.start();
        System.out.println("中断前的中断标志：" + thread.isInterrupted());
        thread.interrupt();
        System.out.println("中断后的中断标志：" + thread.isInterrupted());
    }

    /**
     * A 线程一直持有锁，当  B 线程通过可中断来请求锁，尝试中断 B 线程，观察 锁的 等待队列中是否还有该线程
     *
     * @param
     * @return
     * @throws
     * @author duofei
     * @date 2019/9/26
     */
    private static void interruptedLock() {
        ReentrantLock lock = new ReentrantLock();
        executorService.execute(() -> {
            lock.lock();
            try {
                while (true) {
                    System.out.println("一直持有锁，等待队列长度：" + lock.getQueueLength());
                    Thread.sleep(10000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread thread = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                while (true) {
                    System.out.println("持有锁，等待队列长度：" + lock.getQueueLength());
                    Thread.sleep(100000);
                }
            } catch (Exception e) {
                System.out.println("捕获到异常：" + e.getClass().getName());
                System.out.println("响应中断后的中断标志：" + Thread.currentThread().isInterrupted());
            } finally {
                lock.unlock();
            }
        });
        thread.start();
        System.out.println("中断前的中断标志：" + thread.isInterrupted());
        thread.interrupt();
        System.out.println("中断后的中断标志：" + thread.isInterrupted());
    }


    private static void testSignal() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        executorService.execute(() -> {
            lock.lock();
            System.out.println("a lock" + lock.getQueueLength());
            try {
                while (true) {
                    condition.await();
                    System.out.println("a，again lock" + lock.getQueueLength());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("a release!");
            }
        });
        Thread thread = new Thread(() -> {
            try {
                lock.lockInterruptibly();
                System.out.println("b lock");
                condition.signal();
                System.out.println("b again lock" + lock.getQueueLength());
            } catch (Exception e) {
                System.out.println("捕获到异常：" + e.getClass().getName());
                System.out.println("响应中断后的中断标志：" + Thread.currentThread().isInterrupted());
            } finally {
                lock.unlock();
                System.out.println("b release!");
            }
        });
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
