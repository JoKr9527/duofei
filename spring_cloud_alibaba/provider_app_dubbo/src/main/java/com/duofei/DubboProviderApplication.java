package com.duofei;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author duofei
 * @date 2020/7/31
 */
@SpringBootApplication
@EnableDubbo
public class DubboProviderApplication {

    public static void main(String[] args) throws IOException {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(DubboProviderApplication.class, args);
        System.out.println(applicationContext.getEnvironment());
        final File file = new File("C:\\Users\\delliu\\.dubbo", "dubbo-registry-consumer_app_dubbo-192.168.3.17:45");
        if(!file.exists()){
            final boolean newFile = file.createNewFile();
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write("sjskdjflsajf".getBytes());
            fileOutputStream.flush();
        }
        System.out.println(file.getAbsolutePath());
    }
}
