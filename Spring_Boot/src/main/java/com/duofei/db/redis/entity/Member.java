package com.duofei.db.redis.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


/**
 * user 实体
 * @author duofei
 * @date 2019/9/27
 */
@Data
@RedisHash("member")
public class Member {

    private String id;
    @Id
    private String name;

    private Integer age;
}
