package com.duofei.database.constant;

/**
 * 数据库url常量
 * @author duofei
 * @date 2019/6/29
 */
public enum DbURLConsts {
    /**
     * duofei 数据库
     */
    DUOFEIDB("jdbc:mysql://localhost/duofei?user=root&password=root&serverTimezone=Hongkong");

    private String value;

    DbURLConsts(String value){
        this.value = value;
    }

    public String getURL(){
        return value;
    }
}
