package com.duofei.thread;

/**
 * ThreadLocal类学习
 *
 * @author duofei
 * @date 2019/9/19
 */
public class ThreadLocalStu {

    public static void main(String[] args) {
        parentChildUser();
    }

    /**
     * ThreadLocal 类的基础使用
     *
     * @param
     * @return
     * @throws
     * @author duofei
     * @date 2019/9/19
     */
    public static void basicUse() {
        ThreadLocal<Integer> count = new ThreadLocal() {
            @Override
            protected Object initialValue() {
                return 1;
            }
        };
        System.out.println("初始值" + count.get());
        for (int i = 0; i < 3; i++) {
            // 糟糕的线程创建方式
            new Thread() {
                @Override
                public void run() {
                    Integer countV = count.get();
                    countV++;
                    System.out.println(Thread.currentThread().getName() + "对应的cout值为" + count.get());
                }
            }.start();
        }
        System.out.println("结束值" + count.get());
    }

    /**
     * 在父子线程中使用 ThreadLocal
     *
     * @param
     * @return
     * @throws
     * @author duofei
     * @date 2019/9/19
     */
    public static void parentChildUser() {
        ThreadLocal<Integer> count = new InheritableThreadLocal() {
            @Override
            protected Object initialValue() {
                return 1;
            }
        };
        System.out.println("初始值" + count.get());
        count.set(2);
        // 糟糕的线程创建方式
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "对应的cout值为" + count.get());
                count.set(3);
                new Thread() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + "对应的cout值为" + count.get());
                    }
                }.start();
            }
        }.start();
        System.out.println("结束值" + count.get());
    }

    /**
     * java 8 初始化方法
     *
     * @param
     * @return
     * @throws
     * @author duofei
     * @date 2019/9/19
     */
    public static void java8Use() {
        ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 1);
        System.out.println("初始值" + count.get());
        for (int i = 0; i < 3; i++) {
            // 糟糕的线程创建方式
            new Thread() {
                @Override
                public void run() {
                    Integer countV = count.get();
                    countV++;
                    System.out.println(Thread.currentThread().getName() + "对应的cout值为" + count.get());
                }
            }.start();
        }
        System.out.println("结束值" + count.get());
    }
}
