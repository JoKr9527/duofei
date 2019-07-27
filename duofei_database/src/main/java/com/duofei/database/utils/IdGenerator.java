package com.duofei.database.utils;

import java.util.UUID;

/**
 * 唯一主键生成工具类
 * @author duofei
 * @date 2019/7/1
 */
public class IdGenerator {

    public static String newId() {
        return UUID.randomUUID().toString();
    }

    public static String newShortId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
