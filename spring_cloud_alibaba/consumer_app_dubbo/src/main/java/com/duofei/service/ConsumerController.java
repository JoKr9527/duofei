package com.duofei.service;

import com.duofei.ProviderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofei
 * @date 2020/7/31
 */
@RestController
public class ConsumerController {

    @Reference
    private ProviderService providerService;

    @GetMapping("/getApplicationName")
    public void getApplicationName(){
        System.out.printf("providerService applicationName: %s", providerService.getApplicationName());
    }
}
