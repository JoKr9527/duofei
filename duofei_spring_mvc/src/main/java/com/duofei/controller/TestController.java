package com.duofei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.*;

/**
 * @author duofei
 * @date 2020/7/9
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/1")
    public void test1(){
        HttpSession session = request.getSession();
        if(session != null){
            String id = session.getId();
            System.out.println(id);
        }
    }

}
