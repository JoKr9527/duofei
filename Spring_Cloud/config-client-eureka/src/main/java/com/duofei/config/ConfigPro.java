package com.duofei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author duofei
 * @date 2019/11/13
 */
@ConfigurationProperties(prefix = "duofei.test")
public class ConfigPro {

    private String pro;

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }
}
