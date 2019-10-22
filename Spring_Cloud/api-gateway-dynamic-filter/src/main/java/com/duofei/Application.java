package com.duofei;

import com.duofei.config.FilterConfiguration;
import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author duofei
 * @date 2019/10/21
 */
@EnableZuulProxy
@EnableConfigurationProperties({FilterConfiguration.class})
@SpringCloudApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Bean
    public FilterLoader filterLoader(FilterConfiguration filterConfiguration){
        FilterLoader filterLoader = FilterLoader.getInstance();
        filterLoader.setCompiler(new GroovyCompiler());
        FilterFileManager.setFilenameFilter(new GroovyFileFilter());
        try {
            FilterFileManager.init(filterConfiguration.getInterval(),
                    filterConfiguration.getRoot() + "/pre",
                    filterConfiguration.getRoot() + "/post");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filterLoader;
    }
}
