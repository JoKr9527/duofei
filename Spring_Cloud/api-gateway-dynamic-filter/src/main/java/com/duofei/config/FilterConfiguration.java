package com.duofei.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author duofei
 * @date 2019/10/21
 */
@ConfigurationProperties("zuul.filter")
public class FilterConfiguration {

    /**
     * 动态加载的过滤器的存储路径
     */
    private String root;
    /**
     * 动态加载的间隔时间，秒为单位
     */
    private Integer interval;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
