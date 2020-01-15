package com.duofei.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * 服务接口
 * @author duofei
 * @date 2020/1/14
 */
@SPI
public interface Robot {

    void sayHello();

    @Adaptive("key")
    void showColor(URL url);
}
