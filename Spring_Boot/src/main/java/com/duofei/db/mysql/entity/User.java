package com.duofei.db.mysql.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 * @author duofei
 * @date 2019/9/27
 */
@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date createDate;
    @ManyToOne
    @JoinColumn(name="dId")
    private Deparment deparment;
    @ManyToMany(cascade = {},fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name="roles_id")})
    private List<Role> roles;
}
