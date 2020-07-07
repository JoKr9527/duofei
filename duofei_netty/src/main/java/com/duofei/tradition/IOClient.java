package com.duofei.tradition;

import java.io.IOException;
import java.net.Socket;

/**
 * 传统客户端
 * @author duofei
 * @date 2020/3/20
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(()->{
            try {
                Socket socket = new Socket("127.0.0.1", IOServer.LISTENING_PORT);
                socket.getOutputStream().write("Hello, World".getBytes());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
