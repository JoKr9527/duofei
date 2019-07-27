package com.duofei.database.entity;

import lombok.Data;

/**
 * 学生表
 * @author duofei
 * @date 2019/7/1
 */
@Data
public class StudentEntity {

    private String id;

    private String name;

    private Integer age;

    private String classId;
}
