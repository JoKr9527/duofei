package com.duofei.filter.post;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author duofei
 * @date 2019/10/21
 */
public class PostFilter extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(PostFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 2000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("this is a post filter: Receive response");
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        try {
            response.getOutputStream().print(",I am duofei");
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
