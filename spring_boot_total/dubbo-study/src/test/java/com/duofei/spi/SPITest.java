package com.duofei.spi;

import com.duofei.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author duofei
 * @date 2020/1/14
 */
@SpringBootTest
@ContextConfiguration(classes = Application.class)
@RunWith(SpringRunner.class)
public class SPITest {

    @Autowired
    private SpiService spiService;

    @Test
    public void javaSPI(){
        spiService.javaSPI();
    }

    @Test
    public void dubboSPI(){
        spiService.dubboSPI();
    }

    @Test
    public void adaptive(){
        spiService.adaptive();
    }

    @Test
    public void active(){
        spiService.active();
    }

    @Test
    public void wrapper(){
        spiService.wrapper();
    }

    @Test
    public void getProtocol(){
        spiService.getProtocol();
    }

    @Test
    public void getLoadBalance(){spiService.getLoadBalance();}

}
