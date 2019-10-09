package com.duofei.db.mysql.custom;

import com.duofei.db.mysql.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 使用 拓展的 jpa 接口
 * @author duofei
 * @date 2019/9/28
 */
@Repository
public interface CustomUserRepository extends ExpandJpaRepository<User, Long> {
}
