package com.duofei.db.mysql.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 角色实体
 * @author duofei
 * @date 2019/9/27
 */
@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
