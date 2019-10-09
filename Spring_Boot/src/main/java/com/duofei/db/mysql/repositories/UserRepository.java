package com.duofei.db.mysql.repositories;

import com.duofei.db.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * user jpa 库
 * @author duofei
 * @date 2019/9/27
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
