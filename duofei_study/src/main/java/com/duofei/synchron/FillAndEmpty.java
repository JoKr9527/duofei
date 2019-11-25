package com.duofei.synchron;

import java.nio.ByteBuffer;
import java.util.concurrent.Exchanger;

/**
 * Exchanger 同步工具学习
 * @author duofei
 * @date 2019/11/20
 */
public class FillAndEmpty {

    Exchanger<ByteBuffer> exchanger = new Exchanger<>();

    ByteBuffer initialEmptyBuffer = ByteBuffer.allocateDirect(10);
    ByteBuffer initialFullBuffer = ByteBuffer.allocateDirect(10);

    public static void main(String[] args) {
        FillAndEmpty fillAndEmpty = new FillAndEmpty();
        new Thread(fillAndEmpty.new FillingLoop()).start();
        new Thread(fillAndEmpty.new Emptying()).start();

    }

    class FillingLoop implements Runnable{

        @Override
        public void run() {
            ByteBuffer currentBuffer = initialEmptyBuffer;
            try {
                currentBuffer.clear();
                while (currentBuffer != null){
                    if(currentBuffer.hasRemaining()){
                        currentBuffer.put(new String("he").getBytes());
                    }
                    if(!currentBuffer.hasRemaining()){
                        currentBuffer = exchanger.exchange(currentBuffer);
                        currentBuffer.clear();
                        System.out.println("重新获得空的缓冲区");
                    }
                }
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }

        }
    }

    class Emptying implements Runnable{

        @Override
        public void run() {
            ByteBuffer currentBuffer = initialFullBuffer;
            try{
                currentBuffer.flip();
                byte[] bytes = new byte[10];
                while (currentBuffer != null){
                    if (currentBuffer.hasRemaining()){
                        currentBuffer.get(bytes);
                        System.out.println("缓存区内容为：" + new String(bytes));
                    }
                    if(!currentBuffer.hasRemaining()){
                        currentBuffer = exchanger.exchange(currentBuffer);
                        currentBuffer.flip();
                        System.out.println("重新获得拥有数据的缓冲区");
                    }
                }
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
}
