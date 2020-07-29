package com.duofei;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * test controller
 * @author duofei
 * @date 2020/7/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class TestControllerTest {

    private static RestTemplate restTemplate = new RestTemplate();

    @Test
    public void test() throws InterruptedException {
        String writeHelloUrl = "http://192.168.3.18:18085/writeHello";
        String readHelloUrl = "http://192.168.3.18:18085/readHello";
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch stop = new CountDownLatch(7);
        new Thread(new GetRequest(readHelloUrl, start, stop)).start();
        new Thread(new GetRequest(writeHelloUrl, start, stop)).start();
        new Thread(new GetRequest(writeHelloUrl, start, stop)).start();
        new Thread(new GetRequest(writeHelloUrl, start, stop)).start();
        new Thread(new GetRequest(readHelloUrl, start, stop)).start();
        new Thread(new GetRequest(writeHelloUrl, start, stop)).start();
        new Thread(new GetRequest(writeHelloUrl, start, stop)).start();
        start.countDown();
        stop.await();
    }

    static class GetRequest implements Runnable{

        private String url;

        private CountDownLatch start;

        private CountDownLatch stop;

        public GetRequest(String url, CountDownLatch start, CountDownLatch stop){
            this.url = url;
            this.start = start;
            this.stop = stop;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int pass = 0;
            int block = 0;
            for (int i = 0; i < 30; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String result = restTemplate.getForObject(url, String.class);
                if(result.contains("Sentinel")){
                    pass ++;
                }else if(result.contains("block")){
                    block ++;
                }
            }
            System.out.println(url + "---- pass " + pass + ", " + "block " + block);
            stop.countDown();
        }
    }
}
