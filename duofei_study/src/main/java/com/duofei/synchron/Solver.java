package com.duofei.synchron;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CyclicBarrier
 * @author duofei
 * @date 2019/11/20
 */
public class Solver {

    final int N;
    final float[][] data;
    final CyclicBarrier barrier;

    public static void main(String[] args) {
        Solver solver = new Solver(new float[5][2]);

        for (int i = 0; i < solver.N; i++) {
            final Thread thread = new Thread(solver.new Worker(i));
            thread.start();
            //System.out.println(System.currentTimeMillis() + ": 打断" + thread.getName());
            // 测试线程打断
            //thread.interrupt();
        }
    }

    class Worker implements Runnable{

        int myRow;
        Worker(int row){
            myRow = row;
        }

        @Override
        public void run() {
            System.out.println("我处理完成第" + myRow + "行");
            try {
                long start = System.currentTimeMillis();
                final int await = barrier.await(1000, TimeUnit.NANOSECONDS);
                System.out.println(System.currentTimeMillis() - start);
                System.out.println(Thread.currentThread().getName() + ": 我完成了" + myRow+ "行，我是第" + await + "个到达屏障处的");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                System.out.println(System.currentTimeMillis() + "屏障破坏异常：" + Thread.currentThread().getName());
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public Solver(float[][] matrix){
        data = matrix;
        N = matrix.length;
        barrier = new CyclicBarrier(N, ()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "行数据合并");
        });
    }
}
