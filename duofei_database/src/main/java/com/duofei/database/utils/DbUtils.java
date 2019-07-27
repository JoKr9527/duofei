package com.duofei.database.utils;

import com.duofei.database.constant.DbURLConsts;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 简单的db 工具类
 * @author duofei
 * @date 2019/6/29
 */
public class DbUtils {

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return getConnection(DbURLConsts.DUOFEIDB.getURL());
    }

    public static Connection getConnection(String url){
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
