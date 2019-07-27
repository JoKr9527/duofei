package com.duofei.database;

import com.duofei.database.utils.DbUtils;

import java.sql.Connection;

/**
 * @author duofei
 * @date 2019/6/29
 */
public class ConnectionTest {

    public static void main(String[] args) {
        final Connection connection = DbUtils.getConnection();
        System.out.println("连接成功");
    }
}
