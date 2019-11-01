package com.duofei.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 缓存上下文初始化
 * @author duofei
 * @date 2019/10/26
 */
@Configuration
@WebFilter(urlPatterns = "/**")
public class RequestCacheContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("我Filter初始化了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            hystrixRequestContext.shutdown();
        }
    }
}
