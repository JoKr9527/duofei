package com.duofei.service;

import com.duofei.ProviderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author duofei
 * @date 2020/7/31
 */
@Service
public class ProviderServiceImpl implements ProviderService, ApplicationContextAware {

    @Value("${dubbo.application.name}")
    private String name;

    private ApplicationContext applicationContext;

    @Override
    public String getApplicationName() {
        return name;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
