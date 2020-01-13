package com.duofei.service;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务传播性测试
 * @author duofei
 * @date 2020/1/2
 */
@Service
public class MySqlService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Service1 service1;

    @Transactional(rollbackFor = Exception.class)
    public void firstA() throws Exception{
        jdbcTemplate.update("update deparment set `id` = 2 ");
        try{
            service1.secondB();
        }catch (Exception e){

        }
        //throw new Exception("执行错误");
    }



    @Service
    class Service1{

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Transactional(rollbackFor = Exception.class)
        public void secondB() throws Exception{
            jdbcTemplate.update("update deparment set `name` = 'b2' ");
            throw new Exception("b 执行错误");
        }
    }
}
