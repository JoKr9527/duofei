package com.duofei.db.mysql.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 部门实体
 * @author duofei
 * @date 2019/9/27
 */
@Data
@Entity
@Table(name = "deparment")
public class Deparment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
