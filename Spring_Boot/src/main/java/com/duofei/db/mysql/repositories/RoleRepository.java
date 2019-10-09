package com.duofei.db.mysql.repositories;

import com.duofei.db.mysql.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * role jpa 库
 * @author duofei
 * @date 2019/9/27
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
