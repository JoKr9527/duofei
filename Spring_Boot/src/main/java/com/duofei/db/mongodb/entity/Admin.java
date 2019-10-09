package com.duofei.db.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ADMIN 实体类
 * @author duofei
 * @date 2019/9/28
 */
@Document(collection = "admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    private String adminId;
    private String username;
    private String password;
    private String email;
    private Date registrationDate;
    private Set<String> roles = new HashSet<>();
}
