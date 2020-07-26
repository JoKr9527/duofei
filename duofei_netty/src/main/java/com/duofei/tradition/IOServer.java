package com.duofei.tradition;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统 IO 服务端代码
 * @author duofei
 * @date 2020/3/20
 */
public class IOServer {

    public static int LISTENING_PORT = 8000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(LISTENING_PORT);
        new ServerAcceptThread("Thread 1", serverSocket).start();
    }

    static class ServerAcceptThread extends Thread{

        private ServerSocket serverSocket;

        ServerAcceptThread(String name, ServerSocket serverSocket){
            super(name);
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Socket accept = serverSocket.accept();
                    try {
                        InputStream inputStream = accept.getInputStream();
                        int len;
                        byte[] data = new byte[1024];
                        // inputStream.available() 该方法的获取可能为 0 ，在第二次获取时
                        System.out.println(Thread.currentThread().getName() + "inputStream num: " + inputStream.available());
                        while ((len=inputStream.read(data)) != -1){
                            System.out.println(Thread.currentThread().getName() + new String(data, 0 ,len));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
