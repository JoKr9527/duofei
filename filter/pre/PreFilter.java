package com.duofei;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author duofei
 * @date 2019/10/21
 */
public class PreFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        HttpServletRequest request =RequestContext.getCurrentContext().getRequest();
        logger.info("this is a pre filter: send {} request to {}",request.getMethod(), request.getRequestURL().toString());
        return null;
    }
}
