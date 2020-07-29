package com.duofei.filter;

import com.alibaba.csp.sentinel.context.ContextUtil;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author duofei
 * @date 2020/7/28
 */
@WebFilter(urlPatterns = {"/*"})
public class ContextInitFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContextUtil.getContext().setOrigin(((RequestFacade) request).getRequestURI());
        chain.doFilter(request, response);
    }
}
