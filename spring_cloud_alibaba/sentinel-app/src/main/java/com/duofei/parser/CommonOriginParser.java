package com.duofei.parser;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * request path parse to origin
 * @author duofei
 * @date 2020/7/28
 */
@Component
public class CommonOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
